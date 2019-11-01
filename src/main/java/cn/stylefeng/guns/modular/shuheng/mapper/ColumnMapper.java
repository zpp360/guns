package cn.stylefeng.guns.modular.shuheng.mapper;

import cn.stylefeng.guns.core.common.node.ZTreeNode;
import cn.stylefeng.guns.modular.shuheng.entity.Column;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhengpp
 * @since 2019-10-31
 */
public interface ColumnMapper extends BaseMapper<Column> {
    Page<Map<String, Object>> selectColumn(@Param("page") Page page, @Param("columnName") String columnName);

    /**
     * 栏目树
     * @return
     */
    List<ZTreeNode> columnTreeList(@Param("plazaId") Long plazaId);

    /**
     * 栏目layuitree
     * @param columnName
     * @param plazaId
     * @return
     */
    List<Map<String,Object>> selectColumnTree(@Param("columnName") String columnName, @Param("plazaId") Long plazaId);

    /**
     * 栏目详情
     * @param columnId
     * @return
     */
    Column getColumnInfo(@Param("columnId") Long columnId);
}
