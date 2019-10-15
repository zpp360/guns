package cn.stylefeng.guns.modular.shenjiang.controller;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.shenjiang.entity.House;
import cn.stylefeng.guns.modular.shenjiang.service.HouseService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {
    private String PREFIX = "/modular/shenjiang/house/";

    @Autowired
    private HouseService houseService;

    @RequestMapping("")
    public String index(){
        return PREFIX + "house.html";
    }

    @RequestMapping("/house_add")
    public String addView(){
        return PREFIX + "house_add.html";
    }

    @RequestMapping("/house_edit")
    public String editView(@RequestParam Integer houseId){
        if (ToolUtil.isEmpty(houseId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        House house = this.houseService.getById(houseId);
        LogObjectHolder.me().set(house);
        return PREFIX + "house_edit.html";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(value = "houseName", required = false) String houseName){
        Page<Map<String,Object>> page = houseService.listHouse(houseName);
        return LayuiPageFactory.createPageInfo(page);
    }


}
