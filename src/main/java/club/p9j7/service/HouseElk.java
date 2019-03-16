package club.p9j7.service;

import club.p9j7.model.House;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface HouseElk extends ElasticsearchRepository<House, Long> {
}
