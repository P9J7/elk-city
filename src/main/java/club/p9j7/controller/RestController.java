package club.p9j7.controller;


import club.p9j7.mapper.AqiMapper;
import club.p9j7.model.AreaDataCount;
import club.p9j7.model.CityDataCount;
import club.p9j7.model.HouseTypeCount;
import club.p9j7.service.AqiElk;
import club.p9j7.service.HouseElk;
import club.p9j7.service.HouseService;
import club.p9j7.support.SpiderMan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    AqiMapper aqiMapper;

    @Autowired
    HouseService houseService;

    @Autowired
    HouseElk houseElk;

    @Autowired
    AqiElk aqiElk;

    @RequestMapping("/getCityCount")
    public List<CityDataCount> getCityHouseCount(){
        List<CityDataCount> cityList = new ArrayList<>();
        String[] cityName = {"北京", "上海", "广州", "深圳"};
        for (String city:cityName) {
            Integer count = houseElk.countByCityName(city);
            CityDataCount cityDataCount = new CityDataCount(city, count);
            cityList.add(cityDataCount);
        }
        return cityList;
    }

    @RequestMapping("/getAqiCount")
    public List<CityDataCount> getCityAqiCount(){
        List<CityDataCount> cityList = new ArrayList<>();
        for (String city: SpiderMan.cityList) {
//            Long count = aqiElk.countByCity(city);
            Integer count = aqiMapper.countByCity(city);
            if (count > 0) {
                CityDataCount cityDataCount = new CityDataCount(city, count);
                cityList.add(cityDataCount);
            }
        }
        return cityList;
    }

    /**
     * 由IndexController传值到页面，js获取值发起通用接口请求，这个方法实现很精巧
     * 也丑陋，在售房子才有AreaName
     */
    @PostMapping("/getAreaCount")
    public List<AreaDataCount> getAreaCount(String cityName){
        List<AreaDataCount> areaCountList = new ArrayList<>();
        List<String> areaList = SpiderMan.areaMap.get(cityName);
        areaList.forEach((item) -> {
            Long count = houseElk.countByAreaName(item);
            AreaDataCount areaDataCount = new AreaDataCount(item, count);
            areaCountList.add(areaDataCount);
        });
        return areaCountList;
    }


    /**
     * 在售和成交通用
     * @param cityName
     * @return
     */
    @PostMapping("/getHouseType")
    public List<HouseTypeCount> getHouseType(String cityName, Integer status){
        List<HouseTypeCount> houseTypeCounts = new ArrayList<>();
        //只关注主要类型
        List<String> houseTypes = Arrays.asList("1室0厅","1室1厅","2室1厅","2室2厅","3室1厅","3室2厅","4室1厅","4室2厅","5室2厅");
        houseTypes.forEach((item) -> {
            Integer count = houseElk.countByCityNameAndRoomMainInfoAndStatus(cityName, item, status);
            HouseTypeCount houseTypeCount = new HouseTypeCount(item, count);
            houseTypeCounts.add(houseTypeCount);
        });
        return houseTypeCounts;
    }
}
