package club.p9j7.service.impl;

import club.p9j7.mapper.HouseMapper;
import club.p9j7.model.House;
import club.p9j7.service.HouseElk;
import club.p9j7.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseServiceImpl implements HouseService {
    @Autowired
    HouseMapper houseMapper;

    @Override
    public void insertHouse(House house) {
        houseMapper.insertHouse(house);
    }
}
