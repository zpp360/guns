package cn.stylefeng.guns.modular.shenjiang.mapper;

import cn.stylefeng.guns.modular.shenjiang.entity.House;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface HouseMapper extends BaseMapper<House> {

    Page<Map<String,Object>> selectHouse(@Param("page") Page page, @Param("houseName") String houseName);
}
