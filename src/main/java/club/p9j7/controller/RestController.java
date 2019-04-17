package club.p9j7.controller;


import club.p9j7.mapper.AqiMapper;
import club.p9j7.model.City;
import club.p9j7.model.ResultContent;
import club.p9j7.service.AqiElk;
import club.p9j7.service.HouseElk;
import club.p9j7.service.HouseService;
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
import org.elasticsearch.search.aggregations.metrics.valuecount.InternalValueCount;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    AqiMapper aqiMapper;

    @Autowired
    HouseService houseService;

    @Autowired
    HouseElk houseElk;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    AqiElk aqiElk;

    @RequestMapping("/getCityCount")
    public List<ResultContent> getCityHouseCount(){
        List<ResultContent> cityList = new ArrayList<>();
        String[] cityName = {"bj", "sh", "gz", "sz"};
        for (String city:cityName) {
            Integer count = houseElk.countByCityName(city);
            ResultContent resultContent = new ResultContent(City.valueOf(city).getValue(), count);
            cityList.add(resultContent);
        }
        return cityList;
    }

    @RequestMapping("/getAqiCount")
    public List<ResultContent> getCityAqiCount(){
        List<ResultContent> resultList = new ArrayList<>();
        SpiderMan.cityList.forEach(item -> {
            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("city.keyword", item);
            ValueCountAggregationBuilder vAB = AggregationBuilders.count("city_count").field("city.keyword");
            SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQueryBuilder).addAggregation(vAB).build();
            Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
            InternalValueCount internalValueCount = (InternalValueCount) aggregations.asMap().get("city_count");
            if (internalValueCount.getValue() > 0)
            resultList.add(new ResultContent(item, internalValueCount.getValue()));
        });
        return resultList;
    }

    /**
     * 由IndexController传值到页面，js获取值发起通用接口请求，这个方法实现很精巧
     * 也丑陋，在售房子才有AreaName
     */
    @PostMapping("/getAreaCount")
    public List<ResultContent> getAreaCount(String cityName){
        List<ResultContent> areaCountList = new ArrayList<>();
        List<String> areaList = SpiderMan.areaMap.get(cityName);
        areaList.forEach((item) -> {
            Integer count = houseElk.countByAreaName(item);
            ResultContent resultContent = new ResultContent(item, count);
            areaCountList.add(resultContent);
        });
        return areaCountList;
    }


    /**
     * 在售和成交通用
     * @param cityName
     * @return
     */
    @PostMapping("/getHouseType")
    public List<ResultContent> getHouseType(String cityName, Integer status){
        List<ResultContent> resultContents = new ArrayList<>();
        //只关注主要类型
        List<String> houseTypes = Arrays.asList("1室0厅","1室1厅","2室1厅","2室2厅","3室1厅","3室2厅","4室1厅","4室2厅","5室2厅");
        houseTypes.forEach((item) -> {
            Integer count = houseElk.countByCityNameAndRoomMainInfoAndStatus(cityName, item, status);
            ResultContent houseTypeCount = new ResultContent(item, count);
            resultContents.add(houseTypeCount);
        });
        return resultContents;
    }

    @PostMapping("/getMonthCount")
    public List<ResultContent> getMonthCount(String cityName) {
        List<ResultContent> resultContents = new ArrayList<>();
        //只关注最近半年
        Map<String, List<String>> yearMonths = new LinkedHashMap<>();
        List<String> months = Arrays.asList("07", "08", "09","10","11","12","01","02","03");
        yearMonths.put("2018", months.subList(0, 6));
        yearMonths.put("2019", months.subList(6, 9));
        yearMonths.forEach((year, month) -> {
            month.forEach((monthItem) -> {
                Integer count = houseElk.countByCityNameAndDealYearAndDealMonth(cityName, year, monthItem);
                resultContents.add(new ResultContent(year + monthItem, count));
            });
        });
        return resultContents;
    }

    @PostMapping("/getAverPrice")
    public List<ResultContent> getAverPrice(String cityName) {
        List<ResultContent> resultContents = new ArrayList<>();
        List<String> areaList = SpiderMan.areaMap.get(cityName);
        areaList.forEach((item) -> {
            TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("areaName", item);
            AvgAggregationBuilder aAB = AggregationBuilders.avg("aver_price").field("unitprice");
            SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termsQueryBuilder).addAggregation(aAB).build();
            Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
            InternalAvg internalAvg = (InternalAvg) aggregations.asMap().get("aver_price");
            resultContents.add(new ResultContent(item, internalAvg.getValue()));
        });
        Collections.sort(resultContents, (a,b) -> (int) ((double)b.getCount()-(double)a.getCount()));
        return resultContents;
    }

    @PostMapping("/getConYear")
    public List<ResultContent> getConYear(String cityName) {
        List<ResultContent> resultContents = new ArrayList<>();
        List<String> yearList = Arrays.asList("1995","1996","1997","1998","1999","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017","2018");
        yearList.forEach((item) -> {
            resultContents.add(new ResultContent(item, houseElk.countByCityNameAndAreaSubInfo(cityName, item)));
        });
        return resultContents;
    }

    @PostMapping("/getAreaRange")
    public List<ResultContent> getAreaRange(String cityName) {
        List<ResultContent> resultContents = new ArrayList<>();
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("cityName", cityName);
        MatchQueryBuilder limitStatusBuilder = QueryBuilders.matchQuery("status", 1);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(termsQueryBuilder).filter(limitStatusBuilder);
        RangeAggregationBuilder rAB = AggregationBuilders.range("range_area").field("areaMainInfo").addRange(0, 50).addRange(50, 100).addRange(100, 150).addRange(150, 200).addRange(200, 250).addRange(250, 300).addUnboundedFrom(300);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).addAggregation(rAB).build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
        InternalRange internalRange = (InternalRange) aggregations.asMap().get("range_area");
        List<InternalRange.Bucket> bucketList = internalRange.getBuckets();
        bucketList.forEach((item) -> {
            resultContents.add(new ResultContent(item.getKeyAsString(), item.getDocCount()));
        });
        return resultContents;
    }

    @PostMapping("/getPriceRange")
    public List<ResultContent> getPriceRange(String cityName) {
        List<ResultContent> resultContents = new ArrayList<>();
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("cityName", cityName);
        MatchQueryBuilder limitStatusBuilder = QueryBuilders.matchQuery("status", 1);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(termsQueryBuilder).filter(limitStatusBuilder);
        RangeAggregationBuilder rAB = AggregationBuilders.range("range_price").field("price").addRange(0, 200).addRange(200, 400).addRange(400, 600).addRange(600, 800).addRange(800, 1000).addRange(1000, 1500).addRange(1500, 2000).addUnboundedFrom(2000);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).addAggregation(rAB).build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
        InternalRange internalRange = (InternalRange) aggregations.asMap().get("range_price");
        List<InternalRange.Bucket> bucketList = internalRange.getBuckets();
        bucketList.forEach((item) -> {
            resultContents.add(new ResultContent(item.getKeyAsString(), item.getDocCount()));
        });
        return resultContents;
    }
}
