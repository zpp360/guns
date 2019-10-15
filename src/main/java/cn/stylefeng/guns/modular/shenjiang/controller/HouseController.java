package cn.stylefeng.guns.modular.shenjiang.controller;

import cn.stylefeng.guns.modular.shenjiang.service.HouseService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("house")
public class HouseController extends BaseController {
    private String PREFIX = "/modular/shenjiang/house/";

    @Autowired
    private HouseService houseService;

    @RequestMapping("")
    public String index(){
        return PREFIX + "house.html";
    }


}
