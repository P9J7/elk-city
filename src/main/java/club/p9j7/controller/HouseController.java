package club.p9j7.controller;

import club.p9j7.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HouseController {
    @Autowired
    HouseService houseService;

    @RequestMapping("/")
    public String index() {
        System.out.println("hello");
        return "index.html";
    }

}
