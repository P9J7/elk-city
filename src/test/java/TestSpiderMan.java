import club.p9j7.Application;
import club.p9j7.model.Aqi;
import club.p9j7.model.ResultContent;
import club.p9j7.service.AqiElk;
import club.p9j7.service.HouseElk;
import club.p9j7.support.SpiderMan;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.aggregations.metrics.min.InternalMin;
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

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


    @Test
    public void testAqi(){
            spiderMan.crwalAqi();
    }

    @Test
    public void testHouse(){
        spiderMan.crawlHouse("gz");
    }

    @Test
    public void testCityCount(){
        List<ResultContent> cityList = new ArrayList<>();
        for (String city: SpiderMan.cityList) {
            Integer count = aqiElk.countByCity(city);
            ResultContent resultContent = new ResultContent(city, count);
            cityList.add(resultContent);
        }
        System.out.println(cityList.toString());
    }

    @Test
    public void testHouseElk(){

    }

    @Test
    public void testAqiElk(){
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(new MatchQueryBuilder("co", "0.9"))
                .withPageable(new PageRequest(1,1)).build();
        Page<Aqi> houses = aqiElk.search(searchQuery);
        System.out.println(houses.getTotalElements());
    }
}
