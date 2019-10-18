package cn.stylefeng.guns.modular.shenjiang.controller;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.util.HtmlUtil;
import cn.stylefeng.guns.modular.shenjiang.entity.Floor;
import cn.stylefeng.guns.modular.shenjiang.entity.House;
import cn.stylefeng.guns.modular.shenjiang.entity.Machine;
import cn.stylefeng.guns.modular.shenjiang.service.FloorService;
import cn.stylefeng.guns.modular.shenjiang.service.HouseService;
import cn.stylefeng.guns.modular.shenjiang.service.MachineService;
import cn.stylefeng.guns.modular.shenjiang.warpper.HouseWrapper;
import cn.stylefeng.guns.modular.system.warpper.UserWrapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {
    private String PREFIX = "/modular/shenjiang/house/";

    @Autowired
    private HouseService houseService;

    @Autowired
    private FloorService floorService;

    @Autowired
    private MachineService machineService;

    @RequestMapping("")
    public String index(){
        return PREFIX + "house.html";
    }

    @RequestMapping("/house_add")
    public String addView(){
        return PREFIX + "house_add.html";
    }

    @RequestMapping("/house_edit")
    public String editView(@RequestParam Long houseId){
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
        Page wrapped = new HouseWrapper(page).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }

    /**
     * 楼层选择框
     * @return
     */
    @RequestMapping("/selectFloor")
    @ResponseBody
    public Object selectFloor(){
        QueryWrapper<Floor> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("floor_id","floor_name");
        List<Map<String,Object>> list = floorService.listMaps(queryWrapper);
        String options = HtmlUtil.listMap2HtmlOptions(list,"floor_id","floor_name");
        return options;
    }


    /**
     * 设备选择
     * @return
     */
    @RequestMapping("/selectMachine")
    @ResponseBody
    public Object selectMachine(){
        QueryWrapper<Machine> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("machine_id","machine_name");
        List<Map<String,Object>> list = machineService.listMaps(queryWrapper);
        String options = HtmlUtil.listMap2HtmlOptions(list,"machine_id","machine_name");
        return options;
    }

    /**
     * 添加
     * @param house
     * @return
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public ResponseData add(House house) {
        if (ToolUtil.isOneEmpty(house,house.getFloorId(),house.getHouseName(),house.getIp(),house.getMachineId(),house.getPort(),house.getSort())) {
            throw new RequestEmptyException();
        }
        this.houseService.save(house);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     * @param house
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public ResponseData edit(House house){
        if (ToolUtil.isOneEmpty(house,house.getHouseId())) {
            throw new RequestEmptyException();
        }
        this.houseService.updateById(house);
        return SUCCESS_TIP;
    }

    /**
     * 获取详细信息
     * @param houseId
     * @return
     */
    @RequestMapping(value = "/getHouseInfo")
    @ResponseBody
    public ResponseData getMachineInfo(@RequestParam Long houseId) {
        if (ToolUtil.isEmpty(houseId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        House house = this.houseService.getById(houseId);
        return ResponseData.success(house);
    }


    /**
     * 删除
     * @param houseId
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ResponseData delete(@RequestParam Long houseId) {
        if (ToolUtil.isEmpty(houseId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.houseService.removeById(houseId);
        return SUCCESS_TIP;
    }

}
