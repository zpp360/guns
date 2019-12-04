package cn.stylefeng.guns.core.util;

import cn.stylefeng.roses.core.util.ToolUtil;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;

public class FileUtil {

    /**
     * 获取根路径，jar包获取同目录下upload路径
     * @param request
     * @return
     */
    public static String getRootPath(HttpServletRequest request){
        String uploadPath = null;
        try {
            uploadPath = ResourceUtils.getURL("classpath:application.yml").getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int result = uploadPath.lastIndexOf("/guns.jar!/");
        if(result > -1){
            uploadPath = uploadPath.substring(0,result);
            //路径去掉file:
            if(uploadPath.contains("file:")){
                uploadPath = uploadPath.substring(5);
            }
        }else{
            uploadPath = request.getServletContext().getRealPath("/");
        }
        return uploadPath;
    }

    /**
     * 将url路径转为路径
     * @param url
     */
    public static String url2Path(String url) {
        if(ToolUtil.isEmpty(url)){
            return null;
        }
        return url.replace("/", File.separator);
    }
}
