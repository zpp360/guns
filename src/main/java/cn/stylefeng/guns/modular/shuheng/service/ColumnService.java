package cn.stylefeng.guns.modular.shuheng.service;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.guns.core.common.node.ZTreeNode;
import cn.stylefeng.guns.modular.shuheng.entity.Column;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.stylefeng.guns.modular.shuheng.mapper.ColumnMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhengpp
 * @since 2019-10-31
 */
@Service
public class ColumnService extends ServiceImpl<ColumnMapper,Column> {
    public Page<Map<String,Object>> listColumn(String columnName) {
      Page page = LayuiPageFactory.defaultPage();
      return this.baseMapper.selectColumn(page,columnName);
    }

    /**
     * 栏目树列表
     * @return
     */
    public List<ZTreeNode> columnTreeList(Long plazaId) {
        return this.baseMapper.columnTreeList(plazaId);
    }

    /**
     * 栏目layuitree
     * @param columnName
     * @param plazaId
     * @return
     */
    public List<Map<String,Object>> selectColumnTree(String columnName, Long plazaId) {
        List<Map<String,Object>> list =  this.baseMapper.selectColumnTree(columnName,plazaId);
        if(list == null){
            list = new ArrayList<>();
        }
        //创建根节点
        Column column = new Column();
        column.setColumnId(-1L);
        column.setColumnName("根节点");

        list.add(BeanUtil.beanToMap(column));
        return list;
    }

    /**
     * 获取栏目详情
     * @param columnId
     * @return
     */
    public Column getColumnInfo(Long columnId) {
        return this.baseMapper.getColumnInfo(columnId);
    }
}
