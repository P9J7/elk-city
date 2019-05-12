package club.p9j7.repository;

import club.p9j7.model.Aqi;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AqiElk extends ElasticsearchRepository<Aqi, Integer> {
    Integer countByCity(String city);
}
