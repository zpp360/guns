package cn.stylefeng.guns.modular.system.mapper;

import cn.stylefeng.guns.core.common.node.ZTreeNode;
import cn.stylefeng.guns.modular.system.entity.User;
import cn.stylefeng.guns.modular.system.model.UserDto;
import cn.stylefeng.roses.core.datascope.DataScope;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 修改用户状态
     */
    int setStatus(@Param("userId") Long userId, @Param("status") String status);

    /**
     * 修改密码
     */
    int changePwd(@Param("userId") Long userId, @Param("pwd") String pwd);

    /**
     * 根据条件查询用户列表
     */
    Page<Map<String, Object>> selectUsers(@Param("page") Page page, @Param("dataScope") DataScope dataScope, @Param("name") String name, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("plazaId") Long plazaId);

    /**
     * 设置用户的角色
     */
    int setRoles(@Param("userId") Long userId, @Param("roleIds") String roleIds);

    /**
     * 通过账号获取用户
     */
    User getByAccount(@Param("account") String account);

    /**
     * 通过手机号获取用户
     * @param phone
     * @return
     */
    User getByPhone(@Param("phone") String phone);

    /**
     * 是否存在该手机号的用户
     * @param user
     * @return
     */
    User eixtPhone(@Param("user")UserDto user);

    /**
     * 根据用户id查询菜单权限
     * @param userId
     * @return
     */
    List<Long> getMenuIdsByUserId(@Param("userId")Long userId);

    /**
     * 获取角色下所有菜单
     * @param roleDesc
     * @return
     */
    List<ZTreeNode> menuTreeList(@Param("roleDesc")String roleDesc);

    /**
     * 查询有权限的菜单列表树
     * @param map
     * @return
     */
    List<ZTreeNode> menuTreeListByMenuIds(Map map);

    /**
     * 删除用户自定义权限
     * @param userId
     */
    void deleteMenuIdsByUserId(@Param("userId")Long userId);

    /**
     * 批量保存用户自定义权限
     * @param list
     */
    void batchSaveUserMenu(List<Map<String, Long>> list);
}
