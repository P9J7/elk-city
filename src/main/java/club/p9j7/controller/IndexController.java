package club.p9j7.controller;

import club.p9j7.service.AqiElk;
import club.p9j7.service.HouseElk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    @Autowired
    HouseElk houseElk;

    @Autowired
    AqiElk aqiElk;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/show")
    public ModelAndView showSum() {
        ModelAndView mv = new ModelAndView();
        Long houseCount = houseElk.count();
        Long aqiCount = aqiElk.count();
        mv.addObject("houseCount", houseCount);
        mv.addObject("aqiCount", aqiCount);
        mv.setViewName("show");
        return mv;
    }

    @RequestMapping("/deal/fixedAnalysis")
    public ModelAndView showDeal() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("deal");
        return mv;
    }

    @RequestMapping("/sale/fixedAnalysis")
    public ModelAndView showSale() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sale");
        return mv;
    }

    @RequestMapping("/aqi/rank")
    public String aqiRank() {
        return "aqiRank";
    }

    @RequestMapping("/aqi/area")
    public String aqiArea() {
        return "aqiArea";
    }

    @RequestMapping("/aqi/compare")
    public String aqiCompare() {
        return "aqiCompare";
    }

    @RequestMapping("/aqi/gdp")
    public String houseSearch(){
        return "aqiGDP";
    }

    @RequestMapping("/search")
    public String search() {
        return "search";
    }
}
