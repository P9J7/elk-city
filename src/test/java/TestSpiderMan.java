import club.p9j7.Application;
import club.p9j7.model.Aqi;
import club.p9j7.model.HouseResultContent;
import club.p9j7.service.AqiElk;
import club.p9j7.service.HouseElk;
import club.p9j7.support.LianjiaSpider;
import club.p9j7.support.SpiderMan;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.sort.SortBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import us.codecraft.webmagic.Spider;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles(profiles = "dev")
public class TestSpiderMan {
    @Autowired
    SpiderMan spiderMan;
    @Autowired
    HouseElk houseElk;
    @Autowired
    AqiElk aqiElk;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    LianjiaSpider lianjiaSpider;


    @Test
    public void testAqi(){
            spiderMan.crwalAqi();
    }

    @Test
    public void testHouse(){
        spiderMan.crawlHouse("fz");
    }

    public void delete() {
        houseElk.deleteAllByCityName("fz");
    }
    @Test
    public void secondCrawl(){
        Spider houseSpider = Spider.create(lianjiaSpider).addPipeline(lianjiaSpider.lianjiaPipeline).thread(5);
        houseSpider.addUrl("https://cq.lianjia.com/chengjiao/jiangjing/");
        houseSpider.run();
    }

    @Test
    public void testCityCount(){
        List<HouseResultContent> cityList = new ArrayList<>();
        for (String city: SpiderMan.cityList) {
            Integer count = aqiElk.countByCity(city);
            HouseResultContent houseResultContent = new HouseResultContent(city, count);
            cityList.add(houseResultContent);
        }
        System.out.println(cityList.toString());
    }

    @Test
    public void testHouseElk(){

    }

    @Test
    public void testAqiElk(){
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("city", "娄底"))
        .must(QueryBuilders.rangeQuery("time_point"))
        .must(QueryBuilders.rangeQuery("time_point.month").from("3").to("4")))
                .withSort(SortBuilders.fieldSort("time_point"))
                .withPageable(PageRequest.of(0, 100))
                .addAggregation(AggregationBuilders.dateHistogram("monthagg").field("time_point")
                        .dateHistogramInterval(DateHistogramInterval.MONTH))
                .build();

        List<Aqi> aqiList = elasticsearchTemplate.queryForList(searchQuery, Aqi.class);

        aqiList.forEach(System.out::println);

//        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("timePoint.year").gte(2019);
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(rangeQueryBuilder).withFilter(QueryBuilders.rangeQuery("timePoint.month").gte(3))
//                .addAggregation(AggregationBuilders.terms("group_by_city").field("city.keyword").size(369)).build();
//        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
//        StringTerms stringTerms = (StringTerms) aggregations.getAsMap().get("group_by_city");
//        List<StringTerms.Bucket> cityBuckets = stringTerms.getBuckets();
//        cityBuckets.forEach((city) -> System.out.println(city.getKeyAsString() + city.getDocCount()));
        //todo 尚未解决管道问题
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchAllQuery())
//                .addAggregation(AggregationBuilders.dateHistogram("aqig").field("timePoint.year")
//                        .dateHistogramInterval(DateHistogramInterval.YEAR).subAggregation(AggregationBuilders.sum("sumg").field("aqi")
//                )).build();
//        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
//        aggregations
//        DoubleTerms doubleTerms = (DoubleTerms) aggregations.getAsMap().get("max_month_aqi_sum");

    }
}
