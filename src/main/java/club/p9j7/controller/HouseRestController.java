package club.p9j7.controller;


import club.p9j7.model.City;
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
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
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

    @RequestMapping("/getCityCount")
    public List<HouseResultContent> getCityHouseCount() {
        List<HouseResultContent> cityList = new ArrayList<>();
        String[] cityName = {"bj", "sh", "gz", "sz"};
        for (String city : cityName) {
            Integer count = houseElk.countByCityName(city);
            HouseResultContent houseResultContent = new HouseResultContent(City.valueOf(city).getValue(), count);
            cityList.add(houseResultContent);
        }
        return cityList;
    }

    /**
     * 由IndexController传值到页面，js获取值发起通用接口请求，这个方法实现很精巧
     * 也丑陋，在售房子才有AreaName
     */
    @PostMapping("/getAreaCount")
    public List<HouseResultContent> getAreaCount(String cityName) {
        List<HouseResultContent> areaCountList = new ArrayList<>();
        List<String> areaList = SpiderMan.areaMap.get(cityName);
        areaList.forEach((item) -> {
            Integer count = houseElk.countByAreaName(item);
            HouseResultContent houseResultContent = new HouseResultContent(item, count);
            areaCountList.add(houseResultContent);
        });
        return areaCountList;
    }


    /**
     * 在售和成交通用
     *
     * @param cityName
     * @return
     */
    @PostMapping("/getHouseType")
    public List<HouseResultContent> getHouseType(String cityName, Integer status) {
        List<HouseResultContent> houseResultContents = new ArrayList<>();
        //只关注主要类型
        List<String> houseTypes = Arrays.asList("1室0厅", "1室1厅", "2室1厅", "2室2厅", "3室1厅", "3室2厅", "4室1厅", "4室2厅", "5室2厅");
        houseTypes.forEach((item) -> {
            Integer count = houseElk.countByCityNameAndRoomMainInfoAndStatus(cityName, item, status);
            HouseResultContent houseTypeCount = new HouseResultContent(item, count);
            houseResultContents.add(houseTypeCount);
        });
        return houseResultContents;
    }

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
                Integer count = houseElk.countByCityNameAndDealYearAndDealMonth(cityName, year, monthItem);
                houseResultContents.add(new HouseResultContent(year + monthItem, count));
            });
        });
        return houseResultContents;
    }

    @PostMapping("/getAverPrice")
    public List<HouseResultContent> getAverPrice(String cityName) {
        List<HouseResultContent> houseResultContents = new ArrayList<>();
        List<String> areaList = SpiderMan.areaMap.get(cityName);
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

    @PostMapping("/getConYear")
    public List<HouseResultContent> getConYear(String cityName) {
        List<HouseResultContent> houseResultContents = new ArrayList<>();
        List<String> yearList = Arrays.asList("1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018");
        yearList.forEach((item) -> {
            houseResultContents.add(new HouseResultContent(item, houseElk.countByCityNameAndAreaSubInfo(cityName, item)));
        });
        return houseResultContents;
    }

    @PostMapping("/getAreaRange")
    public List<HouseResultContent> getAreaRange(String cityName) {
        List<HouseResultContent> houseResultContents = new ArrayList<>();
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("cityName", cityName);
        MatchQueryBuilder limitStatusBuilder = QueryBuilders.matchQuery("status", 1);
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

    @PostMapping("/getPriceRange")
    public List<HouseResultContent> getPriceRange(String cityName) {
        List<HouseResultContent> houseResultContents = new ArrayList<>();
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("cityName", cityName);
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
}
