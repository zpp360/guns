package cn.stylefeng.guns.modular.shuheng.mapper;

import cn.stylefeng.guns.modular.shuheng.entity.News;
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
 * @since 2019-11-05
 */
public interface NewsMapper extends BaseMapper<News> {
   Page<Map<String, Object>> selectNews(@Param("page") Page page, @Param("newsName") String newsName,@Param("columnId") String columnId);
}
