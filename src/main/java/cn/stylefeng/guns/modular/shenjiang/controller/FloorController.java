package cn.stylefeng.guns.modular.shenjiang.controller;

import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.shenjiang.entity.Floor;
import cn.stylefeng.guns.modular.shenjiang.service.FloorService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by zpp360 on 2019/10/12.
 */
@Controller
@RequestMapping("/floor")
public class FloorController extends BaseController{
    private String PREFIX = "/modular/shenjiang/floor/";

    @Autowired
    private FloorService floorService;

    @RequestMapping("")
    public String index(){
        return PREFIX + "floor.html";
    }


    @RequestMapping("/list")
    @ResponseBody
    public Object list(){
        Page<Map<String,Object>> page = floorService.listFloor();
        return LayuiPageFactory.createPageInfo(page);
    }

    @RequestMapping("/floor_add")
    public String addView() {
        return PREFIX + "floor_add.html";
    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public ResponseData add(Floor floor) {
        this.floorService.addFloor(floor);
        return SUCCESS_TIP;
    }

}
