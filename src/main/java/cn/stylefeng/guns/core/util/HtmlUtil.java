package cn.stylefeng.guns.core.util;

import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.Map;

public class HtmlUtil extends HtmlUtils {

    /**
     * 把查询结果转化成select中option的html
     * @param list
     * @param id
     * @param name
     * @return
     */
    public static String listMap2HtmlOptions(List<Map<String,Object>> list,String id,String name){
        if(list==null || list.size()<1){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map<String,Object> map : list){
            sb.append("<option value='"+map.get(id)+"'>"+map.get(name)+"</option>");
        }
        return sb.toString();
    }

}
