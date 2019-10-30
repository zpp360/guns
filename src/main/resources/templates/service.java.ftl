package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import cn.stylefeng.guns.modular.shuheng.mapper.${table.mapperName};
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.Map;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;

/**
 * <p>
 * ${table.comment} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
class ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
@Service
public class ${table.serviceName} extends ServiceImpl<${entity}Mapper,${entity}> {
   public Page<Map<String,Object>> list${entity}(String ${table.entityPath}Name) {
      Page page = LayuiPageFactory.defaultPage();
      return this.baseMapper.select${entity}(page,${table.entityPath}Name);
   }
}
</#if>
