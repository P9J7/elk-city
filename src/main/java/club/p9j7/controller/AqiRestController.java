package club.p9j7.controller;

import club.p9j7.model.AqiResultContent;
import club.p9j7.model.HouseResultContent;
import club.p9j7.service.AqiElk;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AqiRestController {
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    AqiElk aqiElk;

    @RequestMapping("/getAqiCount")
    public List<AqiResultContent> getCityAqiCount(){
        List<AqiResultContent> resultList = new ArrayList<>();
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
}
