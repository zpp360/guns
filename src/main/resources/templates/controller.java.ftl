package ${package.Controller};


import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.shiro.ShiroUser;
import cn.stylefeng.guns.modular.shuheng.entity.${entity};
import cn.stylefeng.guns.modular.shuheng.service.${entity}Service;
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
import java.util.Date;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {

</#if>
</#if>

   private String PREFIX = "/modular/shuheng/${table.entityPath}/";

   @Autowired
   private ${table.serviceName}  ${table.entityPath}Service;

   @RequestMapping("")
   public String index(){
      return PREFIX + "${table.entityPath}.html";
   }

   @RequestMapping("/list")
   @ResponseBody
   public Object list(@RequestParam(value = "${table.entityPath}Name", required = false) String ${table.entityPath}Name){
      Page<Map<String,Object>> page = ${table.entityPath}Service.list${entity}(${table.entityPath}Name);
      return LayuiPageFactory.createPageInfo(page);
   }

   @RequestMapping("/${table.entityPath}_add")
   public String addView() {
      return PREFIX + "${table.entityPath}_add.html";
   }

   @RequestMapping(value = "/add")
   @ResponseBody
   public ResponseData add(${entity} ${table.entityPath}) {
      ShiroUser user = ShiroKit.getUser();
      if (${table.entityPath} == null) {
         throw new RequestEmptyException();
      }
      this.${table.entityPath}Service.save(${table.entityPath});
      return SUCCESS_TIP;
   }


   @RequestMapping("/${table.entityPath}_edit")
   public String editView(@RequestParam Long ${table.entityPath}Id){
      if (ToolUtil.isEmpty(${table.entityPath}Id)) {
         throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
      }
      //用来记录日志
      ${entity} ${table.entityPath} = this.${table.entityPath}Service.getById(${table.entityPath}Id);
      LogObjectHolder.me().set(${table.entityPath});
      return PREFIX + "${table.entityPath}_edit.html";
   }

   @RequestMapping(value = "/edit")
   @ResponseBody
   public ResponseData edit(${entity} ${table.entityPath}){
      ShiroUser user = ShiroKit.getUser();
      if (${table.entityPath} == null) {
         throw new RequestEmptyException();
      }
      this.${table.entityPath}Service.updateById(${table.entityPath});
      return SUCCESS_TIP;
   }

   @RequestMapping(value = "/get${entity}Info")
   @ResponseBody
   public ResponseData get${entity}Info(@RequestParam Long ${table.entityPath}Id) {
      if (ToolUtil.isEmpty(${table.entityPath}Id)) {
         throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
      }
      ${entity} ${table.entityPath} = this.${table.entityPath}Service.getById(${table.entityPath}Id);
      return ResponseData.success(${table.entityPath});
   }


   /**
   * 删除
   * @param ${table.entityPath}Id
   * @return
   */
   @RequestMapping(value = "/delete")
   @ResponseBody
   public ResponseData delete(@RequestParam Long ${table.entityPath}Id) {
      if (ToolUtil.isEmpty(${table.entityPath}Id)) {
         throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
      }
      this.${table.entityPath}Service.removeById(${table.entityPath}Id);
      return SUCCESS_TIP;
   }
}
