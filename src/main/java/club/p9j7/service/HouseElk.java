package club.p9j7.service;

import club.p9j7.model.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface HouseElk extends ElasticsearchRepository<House, Long> {
    Integer countByCityName(String cityName);

    Integer countByAreaName(String areaName);

    Integer countByCityNameAndRoomMainInfoAndStatus(String cityName, String roomMainInfo, Integer status);

    Integer countByCityNameAndDealYearAndDealMonth(String cityName, String dealYear, String dealMonth);

    Integer countByCityNameAndAreaSubInfo(String cityName, String areaSubInfo);
}
