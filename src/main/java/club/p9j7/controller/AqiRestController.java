package club.p9j7.controller;

import club.p9j7.model.AqiResultContent;
import club.p9j7.service.AqiElk;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class AqiRestController {
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    AqiElk aqiElk;

    @RequestMapping("/getAqiCount")
    public List<AqiResultContent> getCityAqiCount(){
        List<AqiResultContent> resultList = Collections.synchronizedList(new ArrayList<>());
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchAllQuery())
                .addAggregation(AggregationBuilders.terms("group_by_city").field("city.keyword").size(369)
                        .subAggregation(AggregationBuilders.terms("group_by_quality").field("quality.keyword")))
                        .build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
        StringTerms cityTerms = (StringTerms) aggregations.getAsMap().get("group_by_city");
        List<StringTerms.Bucket> cityBuckets = cityTerms.getBuckets();
        cityBuckets.forEach((city) -> {
            int cityCount = (int) city.getDocCount();
            StringTerms qualityTerms = (StringTerms) city.getAggregations().getAsMap().get("group_by_quality");
            List<StringTerms.Bucket> qualityBucket = qualityTerms.getBuckets();
            Map<String, Integer> map = new HashMap<>();
            qualityBucket.forEach(((quality) -> {
                map.put(quality.getKeyAsString(), (int) quality.getDocCount());
            }));
            AqiResultContent aqiResultContent = new AqiResultContent(city.getKeyAsString(), cityCount, map);
            resultList.add(aqiResultContent);
        });
        return resultList;
    }

    @PostMapping("/getAqiRank")
    public List<AqiResultContent> getAqiRank(Integer year, Integer month) {
        List<AqiResultContent> resultContents = Collections.synchronizedList(new ArrayList<>());
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("timePoint.year", year))
                .must(QueryBuilders.matchQuery("timePoint.monthValue", month)))
                .addAggregation(AggregationBuilders.terms("group_by_city").field("city.keyword").size(369)
                .subAggregation(AggregationBuilders.avg("avg_aqi_month").field("aqi"))
                        .subAggregation(AggregationBuilders.avg("avg_pm2_5_month").field("pm2_5"))
                        .subAggregation(AggregationBuilders.avg("avg_pm10_month").field("pm10"))
                        .subAggregation(AggregationBuilders.avg("avg_so2_month").field("so2"))
                        .subAggregation(AggregationBuilders.avg("avg_co_month").field("co"))
                        .subAggregation(AggregationBuilders.avg("avg_no2_month").field("no2"))
                        .subAggregation(AggregationBuilders.avg("avg_o3_month").field("o3"))
                        .order(BucketOrder.aggregation("avg_aqi_month", false))).build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
        StringTerms cityTerms = (StringTerms) aggregations.getAsMap().get("group_by_city");
        List<StringTerms.Bucket> cityBuckets = cityTerms.getBuckets();
        DecimalFormat df = new DecimalFormat("0.00");
        AtomicInteger i = new AtomicInteger(1);
        cityBuckets.forEach((city) -> {
            InternalAvg aqi = (InternalAvg) city.getAggregations().asMap().get("avg_aqi_month");
            InternalAvg pm2_5 = (InternalAvg) city.getAggregations().asMap().get("avg_pm2_5_month");
            InternalAvg pm10 = (InternalAvg) city.getAggregations().asMap().get("avg_pm10_month");
            InternalAvg so2 = (InternalAvg) city.getAggregations().asMap().get("avg_so2_month");
            InternalAvg co = (InternalAvg) city.getAggregations().asMap().get("avg_co_month");
            InternalAvg no2 = (InternalAvg) city.getAggregations().asMap().get("avg_no2_month");
            InternalAvg o3 = (InternalAvg) city.getAggregations().asMap().get("avg_o3_month");
            Map<String, String> map = new HashMap<>();
            map.put("aqi", df.format(aqi.getValue()));
            map.put("pm2_5", df.format(pm2_5.getValue()));
            map.put("pm10", df.format(pm10.getValue()));
            map.put("so2", df.format(so2.getValue()));
            map.put("co", df.format(co.getValue()));
            map.put("no2", df.format(no2.getValue()));
            map.put("o3", df.format(o3.getValue()));
            if (aqi.getValue() > 0 && city.getDocCount() > 15) {
                AqiResultContent aqiResultContent = new AqiResultContent(city.getKeyAsString(), i.getAndIncrement(), map);
                resultContents.add(aqiResultContent);
            }
        });
        return resultContents;
    }

    @PostMapping("/getAqiArea")
    public List<AqiResultContent> getAqiArea(Integer year, Integer month) {
        List<AqiResultContent> resultContents = Collections.synchronizedList(new ArrayList<>());
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("timePoint.year", year))
                .must(QueryBuilders.matchQuery("timePoint.monthValue", month)))
                .addAggregation(AggregationBuilders.terms("group_by_city").field("city.keyword").size(369)
                        .subAggregation(AggregationBuilders.avg("avg_aqi_month").field("aqi"))
                        .order(BucketOrder.aggregation("avg_aqi_month", false))).build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
        StringTerms cityTerms = (StringTerms) aggregations.getAsMap().get("group_by_city");
        List<StringTerms.Bucket> cityBuckets = cityTerms.getBuckets();
        cityBuckets.forEach(city -> {
            InternalAvg internalAvg = (InternalAvg) city.getAggregations().asMap().get("avg_aqi_month");
            if (internalAvg.getValue() > 0 && city.getDocCount() > 15) {
                AqiResultContent aqiResultContent = new AqiResultContent(city.getKeyAsString(), (int) internalAvg.value());
                resultContents.add(aqiResultContent);
            }
        });
        return resultContents;
    }
}
