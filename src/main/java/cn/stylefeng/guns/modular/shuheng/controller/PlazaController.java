package cn.stylefeng.guns.modular.shuheng.controller;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.shiro.ShiroUser;
import cn.stylefeng.guns.core.util.HtmlUtil;
import cn.stylefeng.guns.modular.shuheng.entity.Plaza;
import cn.stylefeng.guns.modular.shuheng.service.PlazaService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhengpp
 * @since 2019-10-22
 */
@Controller
@RequestMapping("/plaza")
public class PlazaController extends BaseController {

   private String PREFIX = "/modular/shuheng/plaza/";

   @Autowired
   private PlazaService  plazaService;

   @RequestMapping("")
   public String index(){
      return PREFIX + "plaza.html";
   }

   @RequestMapping("/list")
   @ResponseBody
   public Object list(@RequestParam(value = "plazaName", required = false) String plazaName){
      Page<Map<String,Object>> page = new Page();
      if(ShiroKit.isAdmin()){
         page = plazaService.listPlaza(plazaName,null);
      }else if(ShiroKit.isGeneral() && ShiroKit.isPlazaAdmin()){
         ShiroUser shiroUser = ShiroKit.getUser();
         Long plazaId = shiroUser.getPlazaId();
         page = plazaService.listPlaza(plazaName,plazaId);
      }
      return LayuiPageFactory.createPageInfo(page);
   }

   @RequestMapping("/plaza_add")
   public String addView() {
      return PREFIX + "plaza_add.html";
   }

   @RequestMapping(value = "/add")
   @ResponseBody
   public ResponseData add(Plaza plaza) {
      ShiroUser user = ShiroKit.getUser();
      if (ToolUtil.isOneEmpty(plaza,plaza.getPlazaName())) {
         throw new RequestEmptyException();
      }
      plaza.setCreateUser(user.getId());
      plaza.setCreateTime(new Date());
      plaza.setUpdateUser(user.getId());
      plaza.setUpdateTime(new Date());
      this.plazaService.save(plaza);
      return SUCCESS_TIP;
   }


   @RequestMapping("/plaza_edit")
   public String editView(@RequestParam Long plazaId){
      if (ToolUtil.isEmpty(plazaId)) {
         throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
      }
      //用来记录日志
      Plaza plaza = this.plazaService.getById(plazaId);
      LogObjectHolder.me().set(plaza);
      return PREFIX + "plaza_edit.html";
   }

   @RequestMapping(value = "/edit")
   @ResponseBody
   public ResponseData edit(Plaza plaza){
      ShiroUser user = ShiroKit.getUser();
      if (plaza == null) {
         throw new RequestEmptyException();
      }
      plaza.setUpdateUser(user.getId());
      plaza.setUpdateTime(new Date());
      this.plazaService.updateById(plaza);
      return SUCCESS_TIP;
   }

   @RequestMapping(value = "/getPlazaInfo")
   @ResponseBody
   public ResponseData getPlazaInfo(@RequestParam Long plazaId) {
      if (ToolUtil.isEmpty(plazaId)) {
         throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
      }
      Plaza plaza = this.plazaService.getById(plazaId);
      return ResponseData.success(plaza);
   }


   /**
   * 删除
   * @param plazaId
   * @return
   */
   @RequestMapping(value = "/delete")
   @ResponseBody
   public ResponseData delete(@RequestParam Long plazaId) {
      if (ToolUtil.isEmpty(plazaId)) {
         throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
      }
      this.plazaService.removeById(plazaId);
      return SUCCESS_TIP;
   }

   /**
    * 纪念馆下拉框
    * @return
    */
   @RequestMapping(value = "/selectPlaza")
   @ResponseBody
   public Object selectPlaza(){
      QueryWrapper<Plaza> queryWrapper = new QueryWrapper<>();
      queryWrapper.select("plaza_id","plaza_name");
      List<Map<String,Object>> list = plazaService.listMaps(queryWrapper);
      String options = HtmlUtil.listMap2HtmlOptions(list,"plaza_id","plaza_name");
      return options;
   }
}
