import club.p9j7.Application;
import club.p9j7.model.Aqi;
import club.p9j7.model.CityDataCount;
import club.p9j7.model.House;
import club.p9j7.service.AqiElk;
import club.p9j7.service.HouseElk;
import club.p9j7.support.SpiderMan;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MatchQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<CityDataCount> cityList = new ArrayList<>();
        for (String city: SpiderMan.cityList) {
            Integer count = aqiElk.countByCity(city);
            CityDataCount cityDataCount = new CityDataCount(city, count);
            cityList.add(cityDataCount);
        }
        System.out.println(cityList.toString());
    }

    @Test
    public void testHouseElk(){
//        Long n = houseElk.countByDealYear("2019");
//        System.out.println(n);
        int n = houseElk.countByCityNameAndRoomMainInfoAndStatus("gz", "2室2厅", 1);
        System.out.println(n);
//        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("areaMainInfo", "9474");
//        Iterable<House> iterable = houseElk.search(queryBuilder);
//        iterable.forEach(System.out::println);
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(new MatchQueryBuilder("dealYear", "2019"))
//                .withPageable(new PageRequest(1,1)).build();
//        Page<House> houses = houseElk.search(searchQuery);
//        System.out.println(houses.getTotalElements());
    }

    @Test
    public void testAqiElk(){
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(new MatchQueryBuilder("co", "0.9"))
                .withPageable(new PageRequest(1,1)).build();
        Page<Aqi> houses = aqiElk.search(searchQuery);
        System.out.println(houses.getTotalElements());
//        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("co.", "鞍山");
//        Iterable<Aqi> iterable = aqiElk.search(queryBuilder);
//        iterable.forEach(System.out::println);
    }
}
