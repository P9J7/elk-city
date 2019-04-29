package club.p9j7.controller;


import club.p9j7.model.HouseResultContent;
import club.p9j7.service.HouseElk;
import club.p9j7.support.SpiderMan;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.range.InternalRange;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class HouseRestController {
    @Autowired
    HouseElk houseElk;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    //系统概览城市房源数量分析
    @RequestMapping("/getCityCount")
    public List<HouseResultContent> getCityHouseCount() {
        List<HouseResultContent> cityList = new ArrayList<>();
        SpiderMan.cityMap.forEach((k,v) -> {
            Integer count = houseElk.countByCityName(v);
            HouseResultContent houseResultContent = new HouseResultContent(k, count);
            cityList.add(houseResultContent);
        });
        return cityList;
    }

    //在售区域分析
    @PostMapping("/getAreaCount")
    public List<HouseResultContent> getAreaCount(String cityName) {
        List<HouseResultContent> areaCountList = new ArrayList<>();
        List<String> areaList = SpiderMan.areaMap.get(SpiderMan.cityMap.get(cityName) + "Area");
        areaList.forEach((item) -> {
            Integer count = houseElk.countByAreaName(item);
            HouseResultContent houseResultContent = new HouseResultContent(item, count);
            areaCountList.add(houseResultContent);
        });
        return areaCountList;
    }


    //在售和成交通用的户型分析
    @PostMapping("/getHouseType")
    public List<HouseResultContent> getHouseType(String cityName, Integer status) {
        List<HouseResultContent> houseResultContents = new ArrayList<>();
        //只关注主要类型
        List<String> houseTypes = Arrays.asList("1室0厅", "1室1厅", "2室1厅", "2室2厅", "3室1厅", "3室2厅", "4室1厅", "4室2厅", "5室2厅");
        houseTypes.forEach((item) -> {
            Integer count = houseElk.countByCityNameAndRoomMainInfoAndStatus(SpiderMan.cityMap.get(cityName), item, status);
            HouseResultContent houseTypeCount = new HouseResultContent(item, count);
            houseResultContents.add(houseTypeCount);
        });
        return houseResultContents;
    }

    //成交月份分析
    @PostMapping("/getMonthCount")
    public List<HouseResultContent> getMonthCount(String cityName) {
        List<HouseResultContent> houseResultContents = new ArrayList<>();
        //只关注最近半年
        Map<String, List<String>> yearMonths = new LinkedHashMap<>();
        List<String> months = Arrays.asList("07", "08", "09", "10", "11", "12", "01", "02", "03");
        yearMonths.put("2018", months.subList(0, 6));
        yearMonths.put("2019", months.subList(6, 9));
        yearMonths.forEach((year, month) -> {
            month.forEach((monthItem) -> {
                Integer count = houseElk.countByCityNameAndDealYearAndDealMonth(SpiderMan.cityMap.get(cityName), year, monthItem);
                houseResultContents.add(new HouseResultContent(year + monthItem, count));
            });
        });
        return houseResultContents;
    }

    //在售区域均价分析
    @PostMapping("/getAverPrice")
    public List<HouseResultContent> getAverPrice(String cityName) {
        List<HouseResultContent> houseResultContents = new ArrayList<>();
        List<String> areaList = SpiderMan.areaMap.get(SpiderMan.cityMap.get(cityName) + "Area");
        areaList.forEach((item) -> {
            TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("areaName", item);
            AvgAggregationBuilder aAB = AggregationBuilders.avg("aver_price").field("unitprice");
            SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termsQueryBuilder).addAggregation(aAB).build();
            Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
            InternalAvg internalAvg = (InternalAvg) aggregations.asMap().get("aver_price");
            houseResultContents.add(new HouseResultContent(item, internalAvg.getValue()));
        });
        Collections.sort(houseResultContents, (a, b) -> (int) ((double) b.getCount() - (double) a.getCount()));
        return houseResultContents;
    }

    //在售楼龄分析
    @PostMapping("/getConYear")
    public List<HouseResultContent> getConYear(String cityName) {
        List<HouseResultContent> houseResultContents = new ArrayList<>();
        List<String> yearList = Arrays.asList("1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018");
        yearList.forEach((item) -> {
            houseResultContents.add(new HouseResultContent(item, houseElk.countByCityNameAndAreaSubInfo(SpiderMan.cityMap.get(cityName), item)));
        });
        return houseResultContents;
    }

    //成交面积分析
    @PostMapping("/getAreaRange")
    public List<HouseResultContent> getAreaRange(String cityName) {
        List<HouseResultContent> houseResultContents = new ArrayList<>();
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("cityName", SpiderMan.cityMap.get(cityName));
        MatchQueryBuilder limitStatusBuilder = QueryBuilders.matchQuery("status", 2);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(termsQueryBuilder).filter(limitStatusBuilder);
        RangeAggregationBuilder rAB = AggregationBuilders.range("range_area").field("areaMainInfo").addRange(0, 50).addRange(50, 100).addRange(100, 150).addRange(150, 200).addRange(200, 250).addRange(250, 300).addUnboundedFrom(300);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).addAggregation(rAB).build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
        InternalRange internalRange = (InternalRange) aggregations.asMap().get("range_area");
        List<InternalRange.Bucket> bucketList = internalRange.getBuckets();
        bucketList.forEach((item) -> {
            houseResultContents.add(new HouseResultContent(item.getKeyAsString(), item.getDocCount()));
        });
        return houseResultContents;
    }

    //成交价格分析
    @PostMapping("/getPriceRange")
    public List<HouseResultContent> getPriceRange(String cityName) {
        List<HouseResultContent> houseResultContents = new ArrayList<>();
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("cityName", SpiderMan.cityMap.get(cityName));
        MatchQueryBuilder limitStatusBuilder = QueryBuilders.matchQuery("status", 1);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(termsQueryBuilder).filter(limitStatusBuilder);
        RangeAggregationBuilder rAB = AggregationBuilders.range("range_price").field("price").addRange(0, 200).addRange(200, 400).addRange(400, 600).addRange(600, 800).addRange(800, 1000).addRange(1000, 1500).addRange(1500, 2000).addUnboundedFrom(2000);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).addAggregation(rAB).build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
        InternalRange internalRange = (InternalRange) aggregations.asMap().get("range_price");
        List<InternalRange.Bucket> bucketList = internalRange.getBuckets();
        bucketList.forEach((item) -> {
            houseResultContents.add(new HouseResultContent(item.getKeyAsString(), item.getDocCount()));
        });
        return houseResultContents;
    }

    //城市整体均价分析
    @GetMapping("/getCityAverPrice")
    public List<HouseResultContent> getCityAverPrice() {
        List<HouseResultContent> houseResultContents = new ArrayList<>();
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchAllQuery())
                .addAggregation(AggregationBuilders.terms("city").field("cityName").size(50)
                        .subAggregation(AggregationBuilders.avg("aver_price").field("unitprice"))).build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
        StringTerms stringTerms = (StringTerms) aggregations.getAsMap().get("city");
        List<StringTerms.Bucket> buckets = stringTerms.getBuckets();
        buckets.forEach(item -> {
            InternalAvg internalAvg = (InternalAvg) item.getAggregations().getAsMap().get("aver_price");
            houseResultContents.add(new HouseResultContent(item.getKeyAsString(), internalAvg.getValue()));
        });
        return houseResultContents;
    }
}
