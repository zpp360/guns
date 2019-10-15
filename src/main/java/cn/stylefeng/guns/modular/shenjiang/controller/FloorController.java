package cn.stylefeng.guns.modular.shenjiang.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.guns.core.common.annotion.BussinessLog;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.constant.dictmap.DeptDict;
import cn.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.shenjiang.entity.Floor;
import cn.stylefeng.guns.modular.shenjiang.service.FloorService;
import cn.stylefeng.guns.modular.system.entity.Menu;
import cn.stylefeng.guns.modular.system.model.MenuDto;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Object list(@RequestParam(value = "floorName", required = false) String floorName){
        Page<Map<String,Object>> page = floorService.listFloor(floorName);
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

    @RequestMapping("/floor_edit")
    public String editView(@RequestParam Long floorId){
        if (ToolUtil.isEmpty(floorId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        //用来记录日志
        Floor floor = this.floorService.getById(floorId);
        LogObjectHolder.me().set(floor);
        return PREFIX + "floor_edit.html";
    }

    @RequestMapping(value = "/edit")
    @ResponseBody
    public ResponseData edit(Floor floor){
        if (floor == null || ToolUtil.isOneEmpty(floor.getFloorName(),floor.getFloorId(),floor.getSort())) {
            throw new RequestEmptyException();
        }
        this.floorService.updateById(floor);
        return SUCCESS_TIP;
    }


    /**
     * 获取楼层信息
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:53 PM
     */
    @RequestMapping(value = "/getFloorInfo")
    @ResponseBody
    public ResponseData getMenuInfo(@RequestParam Long floorId) {
        if (ToolUtil.isEmpty(floorId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }

        Floor floor = this.floorService.getById(floorId);

        return ResponseData.success(floor);
    }

    /**
     * 删除
     * @param floorId
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ResponseData delete(@RequestParam Long floorId) {
        if (ToolUtil.isEmpty(floorId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.floorService.removeById(floorId);

        return SUCCESS_TIP;
    }




}
