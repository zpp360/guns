package cn.stylefeng.guns.modular.shenjiang.service;

import cn.stylefeng.guns.modular.shenjiang.entity.Machine;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.stylefeng.guns.modular.shenjiang.mapper.MachineMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.Map;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhengpp
 * @since 2019-10-17
 */
@Service
public class MachineService extends ServiceImpl<MachineMapper,Machine> {
   public Page<Map<String,Object>> listMachine(String machineName) {
      Page page = LayuiPageFactory.defaultPage();
      return this.baseMapper.selectMachine(page,machineName);
   }
}
