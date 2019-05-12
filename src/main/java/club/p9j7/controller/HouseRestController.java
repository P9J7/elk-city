package club.p9j7.controller;


import club.p9j7.model.House;
import club.p9j7.model.HouseResultContent;
import club.p9j7.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HouseRestController {
    @Autowired
    HouseService houseService;

    //系统概览城市房源数量分析
    @RequestMapping("/getCityCount")
    public List<HouseResultContent> getCityHouseCount() {
        return houseService.getCityHouseCount();
    }

    //在售区域分析
    @PostMapping("/getAreaCount")
    public List<HouseResultContent> getAreaCount(String cityName) {
        return houseService.getAreaCount(cityName);
    }


    //在售和成交通用的户型分析
    @PostMapping("/getHouseType")
    public List<HouseResultContent> getHouseType(String cityName, Integer status) {
        return houseService.getHouseType(cityName, status);
    }

    //成交月份分析
    @PostMapping("/getMonthCount")
    public List<HouseResultContent> getMonthCount(String cityName) {
        return houseService.getMonthCount(cityName);
    }

    //在售区域均价分析
    @PostMapping("/getAverPrice")
    public List<HouseResultContent> getAverPrice(String cityName) {
        return houseService.getAverPrice(cityName);
    }

    //在售楼龄分析
    @PostMapping("/getConYear")
    public List<HouseResultContent> getConYear(String cityName) {
        return houseService.getConYear(cityName);
    }

    //成交面积分析
    @PostMapping("/getAreaRange")
    public List<HouseResultContent> getAreaRange(String cityName) {
        return houseService.getAreaRange(cityName);
    }

    //成交价格分析
    @PostMapping("/getPriceRange")
    public List<HouseResultContent> getPriceRange(String cityName) {
        return houseService.getPriceRange(cityName);
    }

    //城市在售整体均价分析
    @GetMapping("/getCityAverPrice")
    public List<HouseResultContent> getCityAverPrice() {
        return houseService.getCityAverPrice();
    }

    //城市在售区域均价分析
    @GetMapping("/getMaxAverPrice")
    public List<HouseResultContent> getMaxAverPrice() {
        return houseService.getMaxAverPrice();
    }

    //关注度最高的房分析
    @GetMapping("/getMaxFav")
    public List<House> getMaxFav() {
        return houseService.getMaxFav();
    }

    //关注度top100户型分析
    @GetMapping("/getMaxHouseType")
    public List<HouseResultContent> getMaxHouseType() {
        return houseService.getMaxHouseType();
    }

    //关注度top100房龄分析
    @GetMapping("/getMaxCon")
    public List<HouseResultContent> getMaxCon() {
        return houseService.getMaxCon();
    }

    //关注度面积和价格分析
    @GetMapping("/getMaxArea")
    public List<List<Double>> getMaxArea() {
        return houseService.getMaxArea();
    }

    //城市各个户型在售均价比较
    @GetMapping("/getHouseCompare")
    public List<List<String>> getHouseCompare() {
        return houseService.getHouseCompare();
    }
}
