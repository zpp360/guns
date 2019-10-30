package cn.stylefeng.guns.modular.shuheng.service;

import cn.stylefeng.guns.modular.shuheng.entity.Plaza;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.stylefeng.guns.modular.shuheng.mapper.PlazaMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import java.util.Map;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhengpp
 * @since 2019-10-22
 */
@Service
public class PlazaService extends ServiceImpl<PlazaMapper,Plaza> {
   public Page<Map<String,Object>> listPlaza(String plazaName) {
      Page page = LayuiPageFactory.defaultPage();
      return this.baseMapper.selectPlaza(page,plazaName);
   }
}
