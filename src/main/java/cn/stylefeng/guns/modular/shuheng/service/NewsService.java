package cn.stylefeng.guns.modular.shuheng.service;

import cn.stylefeng.guns.modular.shuheng.entity.News;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.stylefeng.guns.modular.shuheng.mapper.NewsMapper;
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
 * @since 2019-11-05
 */
@Service
public class NewsService extends ServiceImpl<NewsMapper,News> {
   public Page<Map<String,Object>> listNews(String newsName,String columnId) {
      Page page = LayuiPageFactory.defaultPage();
      return this.baseMapper.selectNews(page,newsName,columnId);
   }
}
