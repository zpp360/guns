package cn.stylefeng.guns.modular.shenjiang.mapper;

import cn.stylefeng.guns.modular.shenjiang.entity.Machine;
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
 * @since 2019-10-17
 */
public interface MachineMapper extends BaseMapper<Machine> {
   Page<Map<String, Object>> selectMachine(@Param("page") Page page,@Param("machineName") String machineName);
}
