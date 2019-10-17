package cn.stylefeng.guns.modular.shenjiang.controller;


import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.shenjiang.entity.Machine;
import cn.stylefeng.guns.modular.shenjiang.service.MachineService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

import org.springframework.stereotype.Controller;
import cn.stylefeng.roses.core.base.controller.BaseController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhengpp
 * @since 2019-10-17
 */
@Controller
@RequestMapping("/machine")
public class MachineController extends BaseController {

   private String PREFIX = "/modular/shenjiang/machine/";

   @Autowired
   private MachineService  machineService;

   @RequestMapping("")
   public String index(){
      return PREFIX + "machine.html";
   }

   @RequestMapping("/list")
   @ResponseBody
   public Object list(@RequestParam(value = "machineName", required = false) String machineName){
      Page<Map<String,Object>> page = machineService.listMachine(machineName);
      return LayuiPageFactory.createPageInfo(page);
   }

   @RequestMapping("/machine_add")
   public String addView() {
      return PREFIX + "machine_add.html";
   }

   @RequestMapping(value = "/add")
   @ResponseBody
   public ResponseData add(Machine machine) {
      if (machine == null || ToolUtil.isOneEmpty(machine.getMachineName(),machine.getMachineFunction(),machine.getMachineImg())) {
         throw new RequestEmptyException();
      }
      this.machineService.save(machine);
      return SUCCESS_TIP;
   }


   @RequestMapping("/machine_edit")
   public String editView(@RequestParam Long machineId){
      if (ToolUtil.isEmpty(machineId)) {
         throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
      }
      //用来记录日志
      Machine machine = this.machineService.getById(machineId);
      LogObjectHolder.me().set(machine);
      return PREFIX + "machine_edit.html";
   }

   @RequestMapping(value = "/edit")
   @ResponseBody
   public ResponseData edit(Machine machine){
      if (machine == null) {
         throw new RequestEmptyException();
      }
      this.machineService.updateById(machine);
      return SUCCESS_TIP;
   }

   @RequestMapping(value = "/getMachineInfo")
   @ResponseBody
   public ResponseData getMachineInfo(@RequestParam Long machineId) {
      if (ToolUtil.isEmpty(machineId)) {
         throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
      }
      Machine machine = this.machineService.getById(machineId);
      return ResponseData.success(machine);
   }


   /**
   * 删除
   * @param machineId
   * @return
   */
   @RequestMapping(value = "/delete")
   @ResponseBody
   public ResponseData delete(@RequestParam Long machineId) {
      if (ToolUtil.isEmpty(machineId)) {
         throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
      }
      this.machineService.removeById(machineId);
      return SUCCESS_TIP;
   }
}
