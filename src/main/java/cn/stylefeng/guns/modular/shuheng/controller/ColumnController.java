package cn.stylefeng.guns.modular.shuheng.controller;


import cn.stylefeng.guns.core.common.annotion.BussinessLog;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.constant.Const;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.node.ZTreeNode;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.common.page.LayuiPageInfo;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.shiro.ShiroUser;
import cn.stylefeng.guns.modular.shuheng.dictmap.ColumnDict;
import cn.stylefeng.guns.modular.shuheng.entity.Column;
import cn.stylefeng.guns.modular.shuheng.service.ColumnService;
import cn.stylefeng.guns.modular.shuheng.warpper.ColumnWrapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Date;

import org.springframework.stereotype.Controller;
import cn.stylefeng.roses.core.base.controller.BaseController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhengpp
 * @since 2019-10-31
 */
@Controller
@RequestMapping("/column")
public class ColumnController extends BaseController {

   private String PREFIX = "/modular/shuheng/cms/column/";

   @Autowired
   private ColumnService  columnService;

   @RequestMapping("")
   public String index(){
      return PREFIX + "column.html";
   }

   @Permission({Const.ADMIN_NAME,Const.GENERAL_NAME})
   @RequestMapping(value = "/listTree")
   @ResponseBody
   public Object listTree(@RequestParam(required = false) String columnName,
                          @RequestParam(required = false) Long plazaId){
      if(ShiroKit.isGeneral()){
         plazaId = ShiroKit.getUser().getPlazaId();
      }
      List<Map<String,Object>> columns = this.columnService.selectColumnTree(columnName,plazaId);
      List<Map<String,Object>> columnWarp = new ColumnWrapper(columns).wrap();

      LayuiPageInfo result = new LayuiPageInfo();
      result.setData(columnWarp);
      return result;
   }

   @RequestMapping("/column_add")
   public String addView() {
      return PREFIX + "column_add.html";
   }

   @RequestMapping(value = "/add")
   @ResponseBody
   @BussinessLog(value = "添加栏目",key = "columnName",dict = ColumnDict.class)
   public ResponseData add(Column column) {
      if (ToolUtil.isOneEmpty(column,column.getColumnName(),column.getParentId(),column.getImgHeight(),column.getImgWidth(),column.getSort())) {
         throw new RequestEmptyException();
      }
      if(ShiroKit.isGeneral()){
         //纪念馆管理员创建栏目设置plazaId
         column.setPlazaId(ShiroKit.getUser().getPlazaId());
      }
      this.columnService.save(column);
      //更新column_path
      Long columnId = column.getColumnId();
      String columnPath = columnId.toString();
      if (!column.getParentId().equals(0L)) {
         columnPath = getPath(column.getParentId(), columnPath);
      }
      column.setColumnPath(columnPath);
      columnService.updateById(column);
      return SUCCESS_TIP;
   }

   /**
    * 递归获取column_path
    * @param parentId
    * @param columnPath
    * @return
    */
   private String getPath(Long parentId, String columnPath) {
      columnPath = parentId + "|" + columnPath;
      Column pColumn = columnService.getById(parentId);
      if (pColumn != null && !pColumn.getParentId().equals(0L)) {
         return getPath(pColumn.getParentId(), columnPath);
      }
      return columnPath;
   }


   @RequestMapping("/column_edit")
   public String editView(@RequestParam Long columnId){
      if (ToolUtil.isEmpty(columnId)) {
         throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
      }
      //用来记录日志
      Column column = this.columnService.getById(columnId);
      LogObjectHolder.me().set(column);
      return PREFIX + "column_edit.html";
   }

   @RequestMapping(value = "/edit")
   @ResponseBody
   @BussinessLog(value = "修改栏目",key = "columnName",dict = ColumnDict.class)
   public ResponseData edit(Column column){
      if (ToolUtil.isOneEmpty(column,column.getColumnName(),column.getParentId(),column.getImgHeight(),column.getImgWidth(),column.getSort())) {
         throw new RequestEmptyException();
      }
      if(ShiroKit.isGeneral()){
         //纪念馆管理员创建栏目设置plazaId
         column.setPlazaId(ShiroKit.getUser().getPlazaId());
      }
      //更新column_path
      Long columnId = column.getColumnId();
      String columnPath = columnId.toString();
      if (!column.getParentId().equals(0L)) {
         columnPath = getPath(column.getParentId(), columnPath);
      }
      column.setColumnPath(columnPath);
      this.columnService.updateById(column);
      //查询当前修改的column下级栏目，修改其column_path
      updateColumnPath(columnPath,column);

      return SUCCESS_TIP;
   }

   /**
    * 递归修改下级栏目column_path
    * @param columnPath
    * @param column
    */
   private void updateColumnPath(String columnPath, Column column) {
      QueryWrapper<Column> wrapper = new QueryWrapper<>();
      wrapper.eq("parent_id",column.getColumnId());
      List<Column> list = columnService.list(wrapper);
      if(CollectionUtils.isNotEmpty(list)){
         for(Column c:list){
            columnPath = columnPath + "|" +c.getColumnId();
            c.setColumnPath(columnPath);
            columnService.updateById(c);
            updateColumnPath(columnPath,c);
         }
      }
   }

   @RequestMapping(value = "/getColumnInfo")
   @ResponseBody
   public ResponseData getColumnInfo(@RequestParam Long columnId) {
      if (ToolUtil.isEmpty(columnId)) {
         throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
      }
      Column column = this.columnService.getColumnInfo(columnId);
      return ResponseData.success(column);
   }


   /**
   * 删除
   * @param columnId
   * @return
   */
   @RequestMapping(value = "/delete")
   @ResponseBody
   @BussinessLog(value = "删除栏目",key = "columnName",dict = ColumnDict.class)
   public ResponseData delete(@RequestParam Long columnId) {
      if (ToolUtil.isEmpty(columnId)) {
         throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
      }
      //存在下级栏目，不允许删除
      QueryWrapper<Column> wrapper = new QueryWrapper<>();
      wrapper.eq("parent_id",columnId);
      List<Column> list = columnService.list(wrapper);
      if(CollectionUtils.isNotEmpty(list)){
         throw new ServiceException(BizExceptionEnum.EXIT_SUB_COLUMN);
      }
      this.columnService.removeById(columnId);
      return SUCCESS_TIP;
   }


   /**
    * 获取菜单列表(选择父级菜单用)
    *
    * @author fengshuonan
    * @Date 2018/12/23 5:54 PM
    */
   @RequestMapping(value = "/selectColumnTreeList")
   @ResponseBody
   public List<ZTreeNode> selectColumnTreeList(@RequestParam(value = "plazaId",required = false) Long plazaId) {
      if(ShiroKit.isGeneral()){
         plazaId = ShiroKit.getUser().getPlazaId();
      }

      List<ZTreeNode> columnTreeList = this.columnService.columnTreeList(plazaId);
      columnTreeList.add(ZTreeNode.createParent());
      return columnTreeList;
   }
}
