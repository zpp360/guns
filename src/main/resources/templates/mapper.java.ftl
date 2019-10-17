package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.Map;


/**
 * <p>
 * ${table.comment} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {
   Page<Map<String, Object>> select${entity}(@Param("page") Page page,@Param("${table.entityPath}Name") String ${table.entityPath}Name);
}
</#if>
