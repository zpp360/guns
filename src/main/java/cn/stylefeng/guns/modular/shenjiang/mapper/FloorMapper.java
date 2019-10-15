package cn.stylefeng.guns.modular.shenjiang.mapper;

import cn.stylefeng.guns.modular.shenjiang.entity.Floor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by zpp360 on 2019/10/12.
 */
public interface FloorMapper extends BaseMapper<Floor> {

    Page<Map<String, Object>> selectFloor(@Param("page") Page page);

}
