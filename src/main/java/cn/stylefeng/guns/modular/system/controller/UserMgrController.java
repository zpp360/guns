/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.guns.modular.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.stylefeng.guns.config.properties.GunsProperties;
import cn.stylefeng.guns.core.common.annotion.BussinessLog;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.constant.Const;
import cn.stylefeng.guns.core.common.constant.dictmap.UserDict;
import cn.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import cn.stylefeng.guns.core.common.constant.state.ManagerStatus;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.node.ZTreeNode;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.shiro.ShiroUser;
import cn.stylefeng.guns.modular.system.entity.Role;
import cn.stylefeng.guns.modular.system.entity.User;
import cn.stylefeng.guns.modular.system.factory.UserFactory;
import cn.stylefeng.guns.modular.system.model.UserDto;
import cn.stylefeng.guns.modular.system.service.RoleService;
import cn.stylefeng.guns.modular.system.service.UserService;
import cn.stylefeng.guns.modular.system.warpper.UserWrapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.datascope.DataScope;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 系统管理员控制器
 *
 * @author fengshuonan
 * @Date 2017年1月11日 下午1:08:17
 */
@Controller
@RequestMapping("/mgr")
public class UserMgrController extends BaseController {

    private static String PREFIX = "/modular/system/user/";

    @Autowired
    private GunsProperties gunsProperties;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 跳转到查看管理员列表的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "user.html";
    }

    /**
     * 跳转到查看管理员列表的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/user_add")
    public String addView() {
        return PREFIX + "user_add.html";
    }

    /**
     * 跳转到角色分配页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @Permission
    @RequestMapping("/role_assign")
    public String roleAssign(@RequestParam Long userId, Model model) {
        if (ToolUtil.isEmpty(userId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        model.addAttribute("userId", userId);
        return PREFIX + "user_roleassign.html";
    }

    /**
     * 跳转到编辑管理员页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @Permission
    @RequestMapping("/user_edit")
    public String userEdit(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        User user = this.userService.getById(userId);
        LogObjectHolder.me().set(user);
        return PREFIX + "user_edit.html";
    }

    /**
     * 获取用户详情
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public Object getUserInfo(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new RequestEmptyException();
        }

        this.userService.assertAuth(userId);
        User user = this.userService.getById(userId);
        Map<String, Object> map = UserFactory.removeUnSafeFields(user);

        HashMap<Object, Object> hashMap = CollectionUtil.newHashMap();
        hashMap.putAll(map);
        hashMap.put("roleName", ConstantFactory.me().getRoleName(user.getRoleId()));
        hashMap.put("deptName", ConstantFactory.me().getDeptName(user.getDeptId()));

        return ResponseData.success(hashMap);
    }

    /**
     * 修改当前用户的密码
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/changePwd")
    @ResponseBody
    public Object changePwd(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        if (ToolUtil.isOneEmpty(oldPassword, newPassword)) {
            throw new RequestEmptyException();
        }
        this.userService.changePwd(oldPassword, newPassword);
        return SUCCESS_TIP;
    }

    /**
     * 查询管理员列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/list")
    @Permission
    @ResponseBody
    public Object list(@RequestParam(required = false) String name,
                       @RequestParam(required = false) String timeLimit,
                       @RequestParam(required = false) Long plazaId) {

        //拼接查询条件
        String beginTime = "";
        String endTime = "";

        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            beginTime = split[0];
            endTime = split[1];
        }

        if (ShiroKit.isAdmin()) {
            Page<Map<String, Object>> users = userService.selectUsers(null, name, beginTime, endTime, plazaId);
            Page wrapped = new UserWrapper(users).wrap();
            return LayuiPageFactory.createPageInfo(wrapped);
        } else if(ShiroKit.isGeneral() && ShiroKit.isPlazaAdmin()){
            //纪念馆管理员
            plazaId = ShiroKit.getUser().getPlazaId();
            Page<Map<String, Object>> users = userService.selectUsers(null, name, beginTime, endTime, plazaId);
            Page wrapped = new UserWrapper(users).wrap();
            return LayuiPageFactory.createPageInfo(wrapped);
        }else{
            Page<Map<String, Object>> page = new Page<>();
            return LayuiPageFactory.createPageInfo(page);
        }
    }

    /**
     * 添加管理员
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/add")
    @BussinessLog(value = "添加管理员", key = "account", dict = UserDict.class)
    @Permission({Const.ADMIN_NAME,Const.GENERAL_NAME})
    @ResponseBody
    public ResponseData add(UserDto user) {
        if (ToolUtil.isOneEmpty(user,user.getName(),user.getPhone(),user.getAccount(),user.getPassword(),user.getSex())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        //普通管理员添加用户只能是自己纪念馆的
        if(ShiroKit.isGeneral()){
            user.setPlazaId(ShiroKit.getUser().getPlazaId());
            //设置role_id
            QueryWrapper wrapper = new QueryWrapper<Role>();
            wrapper.eq("description",Const.GENERAL_NAME);
            Role role = roleService.getOne(wrapper);
            if(ToolUtil.isOneEmpty(role,role.getRoleId())){
                throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
            }
            user.setRoleId(role.getRoleId().toString());
        }
        this.userService.addUser(user);
        return SUCCESS_TIP;
    }

    /**
     * 修改管理员
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/edit")
    @BussinessLog(value = "修改管理员", key = "account", dict = UserDict.class)
    @ResponseBody
    public ResponseData edit(UserDto user) {
        if (ToolUtil.isOneEmpty(user,user.getUserId(),user.getName(),user.getPhone(),user.getSex())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        ShiroUser shiroUser = ShiroKit.getUser();
        if(ShiroKit.isGeneral() && !shiroUser.isPlazaAdmin()){
            //纪念馆管理员没有总权限
            throw new ServiceException(BizExceptionEnum.NO_PERMITION);
        }
        this.userService.editUser(user);
        return SUCCESS_TIP;
    }

    /**
     * 删除管理员（逻辑删除）
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/delete")
    @BussinessLog(value = "删除管理员", key = "userId", dict = UserDict.class)
    @Permission
    @ResponseBody
    public ResponseData delete(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        ShiroUser user = ShiroKit.getUser();
        if(user.getId().equals(userId)){
            throw new ServiceException(BizExceptionEnum.LIMIT_OPERATE_SELF);
        }
        if(ShiroKit.isGeneral() && !user.isPlazaAdmin()){
            //纪念馆管理员没有总权限
            throw new ServiceException(BizExceptionEnum.NO_PERMITION);
        }
        this.userService.deleteUser(userId);
        return SUCCESS_TIP;
    }

    /**
     * 查看管理员详情
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/view/{userId}")
    @ResponseBody
    public User view(@PathVariable Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.userService.assertAuth(userId);
        return this.userService.getById(userId);
    }

    /**
     * 重置管理员的密码
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/reset")
    @BussinessLog(value = "重置管理员密码", key = "userId", dict = UserDict.class)
    @Permission({Const.ADMIN_NAME,Const.GENERAL_NAME})
    @ResponseBody
    public ResponseData reset(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        ShiroUser shiroUser = ShiroKit.getUser();
        if(ShiroKit.isGeneral() && !shiroUser.isPlazaAdmin()){
            //纪念馆管理员没有总权限
            throw new ServiceException(BizExceptionEnum.NO_PERMITION);
        }
        this.userService.assertAuth(userId);
        User user = this.userService.getById(userId);
        user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(ShiroKit.md5(Const.DEFAULT_PWD, user.getSalt()));
        this.userService.updateById(user);
        return SUCCESS_TIP;
    }

    /**
     * 冻结用户
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/freeze")
    @BussinessLog(value = "冻结用户", key = "userId", dict = UserDict.class)
    @Permission({Const.ADMIN_NAME,Const.GENERAL_NAME})
    @ResponseBody
    public ResponseData freeze(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能冻结超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new ServiceException(BizExceptionEnum.CANT_FREEZE_ADMIN);
        }

        ShiroUser user = ShiroKit.getUser();
        //不能冻结自己
        if(user.getId().equals(userId)){
            throw new ServiceException(BizExceptionEnum.LIMIT_OPERATE_SELF);
        }

        if(ShiroKit.isGeneral() && !user.isPlazaAdmin()){
            //纪念馆管理员没有总权限
            throw new ServiceException(BizExceptionEnum.NO_PERMITION);
        }

        this.userService.assertAuth(userId);
        this.userService.setStatus(userId, ManagerStatus.FREEZED.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 解除冻结用户
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/unfreeze")
    @BussinessLog(value = "解除冻结用户", key = "userId", dict = UserDict.class)
    @Permission({Const.ADMIN_NAME,Const.GENERAL_NAME})
    @ResponseBody
    public ResponseData unfreeze(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        ShiroUser user = ShiroKit.getUser();
        if(user.getId().equals(userId)){
            throw new ServiceException(BizExceptionEnum.LIMIT_OPERATE_SELF);
        }

        if(ShiroKit.isGeneral() && !user.isPlazaAdmin()){
            //纪念馆管理员没有总权限
            throw new ServiceException(BizExceptionEnum.NO_PERMITION);
        }
        this.userService.assertAuth(userId);
        this.userService.setStatus(userId, ManagerStatus.OK.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 分配角色
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/setRole")
    @BussinessLog(value = "分配角色", key = "userId,roleIds", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData setRole(@RequestParam("userId") Long userId, @RequestParam("roleIds") String roleIds, @RequestParam(value = "plazaId",required = false) Long plazaId) {
        if (ToolUtil.isOneEmpty(userId, roleIds)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能修改超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new ServiceException(BizExceptionEnum.CANT_CHANGE_ADMIN);
        }
        this.userService.assertAuth(userId);
        this.userService.setRoles(userId, roleIds);
        //保存关联的纪念馆id,设置纪念馆管理员
        User user = userService.getById(userId);
        if(ToolUtil.isNotEmpty(plazaId)){
            user.setPlazaAdmin(true);
            user.setPlazaId(plazaId);
            //删除sys_user_menu
            userService.deleteMenuIdsByUserId(userId);
        }else{
            user.setPlazaAdmin(false);
            user.setPlazaId(null);
        }
        this.userService.updateById(user);
        return SUCCESS_TIP;
    }



    /**
     * 跳转到权限分配页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @Permission
    @RequestMapping("/permission_assign")
    public String permissionAssign(@RequestParam Long userId, Model model) {
        if (ToolUtil.isEmpty(userId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        model.addAttribute("userId", userId);
        return PREFIX + "permission_roleassign.html";
    }


    @RequestMapping("/setPermission")
    @BussinessLog(value = "分配权限", key = "userId,menuIds", dict = UserDict.class)
    @Permission({Const.ADMIN_NAME,Const.GENERAL_NAME})
    @ResponseBody
    public ResponseData setPermission(@RequestParam("userId") Long userId, @RequestParam(value = "menuIds",required = false) String menuIds, @RequestParam(value = "isPlazaAdmin") Integer isPlazaAdmin){
        ShiroUser shiroUser = ShiroKit.getUser();
        if (ToolUtil.isOneEmpty(userId,isPlazaAdmin)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        if(userId.equals(shiroUser.getId())){
            //不允许修改自己权限
            throw new ServiceException(BizExceptionEnum.LIMIT_OPERATE_SELF);
        }
        if(ShiroKit.isGeneral() && !shiroUser.isPlazaAdmin()){
            //纪念馆管理员但没有总权限
            throw new ServiceException(BizExceptionEnum.NO_PERMITION);
        }
        User user = this.userService.getById(userId);
        user.setPlazaId(shiroUser.getPlazaId());
        if(isPlazaAdmin == 1){
            //是场馆管理员
            QueryWrapper wrapper = new QueryWrapper<Role>();
            wrapper.eq("description",Const.GENERAL_NAME);
            Role role = roleService.getOne(wrapper);
            if(ToolUtil.isOneEmpty(role,role.getRoleId())){
                throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
            }
            user.setRoleId(role.getRoleId().toString());
            user.setPlazaAdmin(true);
            userService.updateById(user);
        }
        if(isPlazaAdmin == 0){
            //自定义权限
            user.setPlazaAdmin(false);
            userService.setPermission(user,menuIds);
        }
        return SUCCESS_TIP;
    }

    /**
     * 上传图片
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public String upload(@RequestPart("file") MultipartFile picture) {

        String pictureName = UUID.randomUUID().toString() + "." + ToolUtil.getFileSuffix(picture.getOriginalFilename());
        try {
            String fileSavePath = gunsProperties.getFileUploadPath();
            picture.transferTo(new File(fileSavePath + pictureName));
        } catch (Exception e) {
            throw new ServiceException(BizExceptionEnum.UPLOAD_ERROR);
        }
        return pictureName;
    }

    /**
     * 根据用户id查询拥有权限的普通管理员菜单树
     * @param userId
     * @return
     */
    @RequestMapping(value = "/menuTreeByUserId/{userId}")
    @ResponseBody
    public List<ZTreeNode> menuTreeByUserId(@PathVariable Long userId) {
        List<Long> menuIds = this.userService.getMenuIdsByUserId(userId);

        if (ToolUtil.isEmpty(menuIds)) {
            return this.userService.menuTreeList(Const.GENERAL_NAME);
        } else {
            return this.userService.menuTreeListByMenuIds(Const.GENERAL_NAME,menuIds);
        }
    }
}
