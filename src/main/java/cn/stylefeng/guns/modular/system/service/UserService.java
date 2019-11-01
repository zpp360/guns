package cn.stylefeng.guns.modular.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.stylefeng.guns.core.common.constant.Const;
import cn.stylefeng.guns.core.common.constant.state.ManagerStatus;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.node.MenuNode;
import cn.stylefeng.guns.core.common.node.ZTreeNode;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.shiro.ShiroUser;
import cn.stylefeng.guns.core.shiro.service.UserAuthService;
import cn.stylefeng.guns.core.util.ApiMenuFilter;
import cn.stylefeng.guns.modular.system.entity.Relation;
import cn.stylefeng.guns.modular.system.entity.User;
import cn.stylefeng.guns.modular.system.factory.UserFactory;
import cn.stylefeng.guns.modular.system.mapper.UserMapper;
import cn.stylefeng.guns.modular.system.model.UserDto;
import cn.stylefeng.roses.core.datascope.DataScope;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserAuthService userAuthService;

    /**
     * 添加用戶
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:51
     */
    public void addUser(UserDto user) {

        // 判断账号是否重复
        User theUser = this.getByAccount(user.getAccount());
        if (theUser != null) {
            throw new ServiceException(BizExceptionEnum.USER_ALREADY_REG);
        }
        //判断手机号是否重复
        theUser = this.baseMapper.getByPhone(user.getPhone());
        if (theUser != null) {
            throw new ServiceException(BizExceptionEnum.USER_ALREADY_PHONE);
        }

        // 完善账号信息
        String salt = ShiroKit.getRandomSalt(5);
        String password = ShiroKit.md5(user.getPassword(), salt);

        this.save(UserFactory.createUser(user, password, salt));
    }

    /**
     * 修改用户
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:53
     */
    public void editUser(UserDto user) {
        User oldUser = this.getById(user.getUserId());

        User exitPhone = this.baseMapper.eixtPhone(user);
        //判断手机号是否正确
        if(exitPhone!=null){
            throw new ServiceException(BizExceptionEnum.USER_ALREADY_PHONE);
        }
        if (ShiroKit.hasRole(Const.ADMIN_NAME)) {
            this.updateById(UserFactory.editUser(user, oldUser));
        } else {
            this.assertAuth(user.getUserId());
            ShiroUser shiroUser = ShiroKit.getUserNotNull();
            if (shiroUser.getId().equals(user.getUserId())) {
                this.updateById(UserFactory.editUser(user, oldUser));
            } else {
                throw new ServiceException(BizExceptionEnum.NO_PERMITION);
            }
        }
    }

    /**
     * 删除用户
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:54
     */
    public void deleteUser(Long userId) {

        //不能删除超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new ServiceException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }
        this.assertAuth(userId);
        this.setStatus(userId, ManagerStatus.DELETED.getCode());
    }

    /**
     * 修改用户状态
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:45
     */
    public int setStatus(Long userId, String status) {
        return this.baseMapper.setStatus(userId, status);
    }

    /**
     * 修改密码
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:45
     */
    public void changePwd(String oldPassword, String newPassword) {
        Long userId = ShiroKit.getUserNotNull().getId();
        User user = this.getById(userId);

        String oldMd5 = ShiroKit.md5(oldPassword, user.getSalt());

        if (user.getPassword().equals(oldMd5)) {
            String newMd5 = ShiroKit.md5(newPassword, user.getSalt());
            user.setPassword(newMd5);
            this.updateById(user);
        } else {
            throw new ServiceException(BizExceptionEnum.OLD_PWD_NOT_RIGHT);
        }
    }

    /**
     * 根据条件查询用户列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:45
     */
    public Page<Map<String, Object>> selectUsers(DataScope dataScope, String name, String beginTime, String endTime, Long plazaId) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.selectUsers(page, dataScope, name, beginTime, endTime, plazaId);
    }

    /**
     * 设置用户的角色
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:45
     */
    public int setRoles(Long userId, String roleIds) {
        return this.baseMapper.setRoles(userId, roleIds);
    }

    /**
     * 通过账号获取用户
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:46
     */
    public User getByAccount(String account) {
        return this.baseMapper.getByAccount(account);
    }

    /**
     * 获取用户菜单列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:46
     */
    public List<MenuNode> getUserMenuNodes(List<Long> roleList) {
        if(ShiroKit.isGeneral() && !ShiroKit.isPlazaAdmin()){
            //场馆自定义权限的管理员
            List<MenuNode> menus = menuService.getMenusByUserId(ShiroKit.getUser().getId());
            List<MenuNode> titles = MenuNode.buildTitle(menus);
            return ApiMenuFilter.build(titles);
        }
        if (roleList == null || roleList.size() == 0) {
            return new ArrayList<>();
        } else {
            List<MenuNode> menus = menuService.getMenusByRoleIds(roleList);
            List<MenuNode> titles = MenuNode.buildTitle(menus);
            return ApiMenuFilter.build(titles);
        }

    }

    /**
     * 判断当前登录的用户是否有操作这个用户的权限
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    public void assertAuth(Long userId) {
        if (ShiroKit.isAdmin()) {
            return;
        }
        //普通管理员，纪念馆的总管理员
        if(ShiroKit.isGeneral()){
            return;
        } else {
            throw new ServiceException(BizExceptionEnum.NO_PERMITION);
        }

    }

    /**
     * 刷新当前登录用户的信息
     *
     * @author fengshuonan
     * @Date 2019/1/19 5:59 PM
     */
    public void refreshCurrentUser() {
        ShiroUser user = ShiroKit.getUserNotNull();
        Long id = user.getId();
        User currentUser = this.getById(id);
        ShiroUser shiroUser = userAuthService.shiroUser(currentUser);
        ShiroUser lastUser = ShiroKit.getUser();
        BeanUtil.copyProperties(shiroUser, lastUser);
    }

    /**
     * 设置用户管理的纪念馆id
     * @param userId
     * @param plazaId
     */
    public void updateUserPlaza(Long userId, Long plazaId) {
        User user = new User();
        user.setUserId(userId);
        user.setPlazaId(plazaId);
        this.baseMapper.updateById(user);
    }

    /**
     * 根据userId获取用户菜单权限列表
     * @param userId
     * @return
     */
    public List<Long> getMenuIdsByUserId(Long userId) {
        return this.baseMapper.getMenuIdsByUserId(userId);
    }

    /**
     * 获取菜单列表树
     *
     * @return
     * @date 2017年2月19日 下午1:33:51
     */
    public List<ZTreeNode> menuTreeList(String roleDesc) {
        return this.baseMapper.menuTreeList(roleDesc);
    }

    /**
     * 查询有权限的菜单列表树
     * @param roleDesc
     * @param menuIds
     * @return
     */
    public List<ZTreeNode> menuTreeListByMenuIds(String roleDesc, List<Long> menuIds) {
        Map<String,Object> map = new HashMap<>();
        map.put("roleDesc",roleDesc);
        map.put("list",menuIds);
        return this.baseMapper.menuTreeListByMenuIds(map);
    }

    /**
     * 配置自定义权限
     * @param user
     * @param menuIds
     */
    public void setPermission(User user, String menuIds) {
        //先设置plaza_id
        this.baseMapper.updateById(user);
        //删除用户权限
        this.baseMapper.deleteMenuIdsByUserId(user.getUserId());
        // 添加新的权限
        List<Map<String,Long>> list = new ArrayList<>();
        Long[] menuIdArr = Convert.toLongArray(menuIds.split(","));
        if(ToolUtil.isNotEmpty(menuIds) && menuIdArr!=null && menuIdArr.length>0){
            for (Long id : menuIdArr) {
                Map<String,Long> map = new HashMap<>();
                map.put("userId",user.getUserId());
                map.put("menuId",id);
                list.add(map);
            }
            this.baseMapper.batchSaveUserMenu(list);
        }
    }


    /**
     * 删除用户自定义权限
     * @param userId
     */
    public void deleteMenuIdsByUserId(Long userId){
        //删除用户权限
        this.baseMapper.deleteMenuIdsByUserId(userId);
    }
}
