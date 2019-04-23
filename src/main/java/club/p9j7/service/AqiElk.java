package club.p9j7.service;

import club.p9j7.model.Aqi;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface AqiElk extends ElasticsearchRepository<Aqi, Integer> {
    Integer countByCity(String city);

//    List<Aqi> findByTimepointBetween(String from, String to);
}
