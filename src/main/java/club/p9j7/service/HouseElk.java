package club.p9j7.service;

import club.p9j7.model.House;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface HouseElk extends ElasticsearchRepository<House, Long> {
    Integer countByCityName(String cityName);

    Integer countByCityNameAndRoomMainInfoAndStatus(String cityName, String roomMainInfo, Integer status);

    Integer countByCityNameAndDealYearAndDealMonth(String cityName, String dealYear, String dealMonth);

    Integer countByCityNameAndAreaSubInfo(String cityName, String areaSubInfo);

    Integer deleteAllByCityName(String cityName);

    Integer deleteAllByCityNameAndStatus(String cityName, Integer status);
}
