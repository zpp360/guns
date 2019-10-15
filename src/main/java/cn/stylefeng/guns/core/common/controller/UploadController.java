package cn.stylefeng.guns.core.common.controller;

import cn.stylefeng.guns.core.common.constant.Constants;
import cn.stylefeng.guns.core.util.UUIDUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("upload")
public class UploadController extends BaseController {

    @RequestMapping("/uploadImg")
    @ResponseBody
    public Map uploadCertify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map data = new HashMap() ;
        data.put("code",0);
        data.put("msg","上传成功");
        // 图片路径
        String imgPath = "";
        // 图片名称
        String orgFileName = null;
        // 图片后缀
        String fileExt = null;
        // 文件格式错误信息
        String fileExtError = null;
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // 上传文件
            MultipartFile mf = entity.getValue();
            // 获取图片大小
            int picSize = Integer.parseInt(String.valueOf(mf.getSize()));
            // 获取原文件名
            orgFileName = mf.getOriginalFilename();
            String fileName =  mf.getOriginalFilename();
            // 获取图片后缀
            fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            if (!Constants.JPG.equals(fileExt) && !Constants.JPEG.equals(fileExt) && !Constants.PNG.equals(fileExt) && !Constants.GIF.equals(fileExt)
                    && !Constants.BMP.equals(fileExt)) {
                fileExtError = "nonsupport_type";
                data.put("code","1");
                data.put("msg",fileExtError);
                return data;
            } else if (picSize > Constants.ONE_HUNDRED_MB) {
                fileExtError = "out_size";
                data.put("code","2");
                data.put("msg",fileExtError);
                return data;
            } else {
                // 对原文件名进行重命名
                fileName = UUIDUtil.uuid() + "." + fileExt;
                // 返回图片路径
                String rootPath = request.getServletContext().getRealPath("/");
                imgPath = Constants.IMG_PATH + fileName;
                File dic = new File(rootPath + Constants.IMG_PATH);
                if(!dic.exists()){
                    dic.mkdirs();
                }
                //上传至服务器
                String realPath = rootPath + imgPath;
                mf.transferTo(new File(realPath));
                data.put("img_name",orgFileName);
                data.put("img_path",imgPath);
                return data;
            }
        }
        return data;
    }

}
