package cn.stylefeng.guns.modular.shuheng.mapper;

import cn.stylefeng.guns.modular.shuheng.entity.Plaza;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.Map;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhengpp
 * @since 2019-10-22
 */
public interface PlazaMapper extends BaseMapper<Plaza> {
   Page<Map<String, Object>> selectPlaza(@Param("page") Page page,@Param("plazaName") String plazaName,@Param("plazaId") Long plazaId);
}
