package cn.stylefeng.guns.modular.shuheng.controller;

import com.baidu.ueditor.ActionEnter;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class UeditorController {

    @RequestMapping("/ueditor")
    public void controller(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding( "utf-8" );
        response.setHeader("Content-Type" , "text/html");
        //ueditor源码获取config.json修改为ClassPathResource方式获取
        String rootPath = "assets"+File.separator+"expand"+File.separator+"plugins"+File.separator+"ueditor"+File.separator+"jsp";
        String filePath = ResourceUtils.getURL("classpath:application.yml").getPath();
        int result = filePath.lastIndexOf("/guns.jar!/");
        if(result > -1) {
            filePath = filePath.substring(0, result);
            //路径去掉file:
            if(filePath.contains("file:")){
                filePath = filePath.substring(5);
            }
        }else{
            filePath = request.getServletContext().getRealPath(File.separator);
        }
        PrintWriter out = response.getWriter();
        out.write( new ActionEnter( request, rootPath ,filePath).exec() );
        out.close();
    }

}
