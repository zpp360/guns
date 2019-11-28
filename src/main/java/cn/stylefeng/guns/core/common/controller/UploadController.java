package cn.stylefeng.guns.core.common.controller;

import cn.stylefeng.guns.core.common.constant.Constants;
import cn.stylefeng.guns.core.util.UUIDUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("upload")
public class UploadController extends BaseController {

    /**
     * 上传图片
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/uploadImg")
    @ResponseBody
    public Map uploadImg(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
                fileExtError = "不支持的文件格式";
                data.put("code","1");
                data.put("msg",fileExtError);
                return data;
            } else if (picSize > Constants.ONE_HUNDRED_MB) {
                fileExtError = "图片最大100M";
                data.put("code","2");
                data.put("msg",fileExtError);
                return data;
            } else {
                // 对原文件名进行重命名
                fileName = UUIDUtil.uuid() + "." + fileExt;
                // 返回图片路径
                String rootPath = getRootPath(request);
                imgPath = Constants.NEWS_PATH + fileName;
                File dic = new File(rootPath + Constants.NEWS_PATH);
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

    /**
     * 上传视频
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/uploadVideo")
    @ResponseBody
    public Map uploadVideo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map data = new HashMap() ;
        data.put("code",0);
        data.put("msg","上传成功");
        // 视频路径
        String videoPath = "";
        // 视频名称
        String orgFileName = null;
        // 视频后缀
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
            long picSize = mf.getSize();
            // 获取原文件名
            orgFileName = mf.getOriginalFilename();
            String fileName =  mf.getOriginalFilename();
            // 获取图片后缀
            fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            if (!Constants.MP4.equals(fileExt) ) {
                fileExtError = "只允许mp4格式";
                data.put("code","1");
                data.put("msg",fileExtError);
                return data;
            } else if (picSize > Constants.ONE_HUNDRED_MB) {
                fileExtError = "视频最大100M";
                data.put("code","2");
                data.put("msg",fileExtError);
                return data;
            } else {
                // 对原文件名进行重命名
                fileName = UUIDUtil.uuid() + "." + fileExt;
                // 返回图片路径
                String rootPath = getRootPath(request);
                videoPath = Constants.NEWS_PATH + fileName;
                File dic = new File(rootPath + Constants.NEWS_PATH);
                if(!dic.exists()){
                    dic.mkdirs();
                }
                //上传至服务器
                String realPath = rootPath + videoPath;
                mf.transferTo(new File(realPath));
                data.put("video_name",orgFileName);
                data.put("video_path",videoPath);
                return data;
            }
        }
        return data;
    }


    /**
     * 上传视频
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public Map uploadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map data = new HashMap() ;
        data.put("code",0);
        data.put("msg","上传成功");
        // 视频路径
        String filePath = "";
        // 视频名称
        String orgFileName = null;
        // 视频后缀
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
            long picSize = mf.getSize();
            // 获取原文件名
            orgFileName = mf.getOriginalFilename();
            String fileName =  mf.getOriginalFilename();
            // 获取图片后缀
            fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            if (!Constants.RAR.equals(fileExt) && !Constants.ZIP.equals(fileExt) && !Constants.TXT.equals(fileExt) && !Constants.PDF.equals(fileExt)
                    && !Constants.PPT.equals(fileExt) && !Constants.PPTX.equals(fileExt) && !Constants.DOC.equals(fileExt) && !Constants.DOCX.equals(fileExt)
                    && !Constants.XLS.equals(fileExt) && !Constants.XLSX.equals(fileExt)) {
                fileExtError = "不支持的文件格式";
                data.put("code","1");
                data.put("msg",fileExtError);
                return data;
            } else if (picSize > Constants.ONE_HUNDRED_MB) {
                fileExtError = "文件最大100M";
                data.put("code","2");
                data.put("msg",fileExtError);
                return data;
            } else {
                // 对原文件名进行重命名
                fileName = UUIDUtil.uuid() + "." + fileExt;
                // 返回图片路径
                String rootPath = getRootPath(request);
                filePath = Constants.NEWS_PATH + fileName;
                File dic = new File(rootPath + Constants.NEWS_PATH);
                if(!dic.exists()){
                    dic.mkdirs();
                }
                //上传至服务器
                String realPath = rootPath + filePath;
                mf.transferTo(new File(realPath));
                data.put("file_name",orgFileName);
                data.put("file_path",filePath);
                return data;
            }
        }
        return data;
    }

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


    @RequestMapping(value = "/uploadStatus")
    @ResponseBody
    public Integer uploadStatus(HttpServletRequest request){
        HttpSession session = request.getSession();
        Object percent = session.getAttribute("upload_percent");
        return null != percent ? (Integer) percent : 0;
    }


}
