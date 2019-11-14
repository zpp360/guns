package cn.stylefeng.guns.modular.shuheng.controller;


import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.shiro.ShiroUser;
import cn.stylefeng.guns.modular.shuheng.entity.News;
import cn.stylefeng.guns.modular.shuheng.service.NewsService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;
import java.util.Date;

import org.springframework.stereotype.Controller;
import cn.stylefeng.roses.core.base.controller.BaseController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhengpp
 * @since 2019-11-05
 */
@Controller
@RequestMapping("/news")
public class NewsController extends BaseController {

   private String PREFIX = "/modular/shuheng/cms/news/";

   @Autowired
   private NewsService  newsService;

   @RequestMapping("")
   public String index(){
      return PREFIX + "news.html";
   }

   @RequestMapping("/list")
   @ResponseBody
   public Object list(@RequestParam(value = "newsName", required = false) String newsName){
      Page<Map<String,Object>> page = newsService.listNews(newsName);
      return LayuiPageFactory.createPageInfo(page);
   }

   @RequestMapping("/news_add")
   public String addView(@RequestParam(value = "newsModel") String newsModel,Model model) {
      model.addAttribute("newsModel",newsModel);
      return PREFIX + "news_add.html";
   }

   @RequestMapping(value = "/add")
   @ResponseBody
   public ResponseData add(News news) {
      ShiroUser user = ShiroKit.getUser();
      if (news == null) {
         throw new RequestEmptyException();
      }
      this.newsService.save(news);
      return SUCCESS_TIP;
   }


   @RequestMapping("/news_edit")
   public String editView(@RequestParam Long newsId){
      if (ToolUtil.isEmpty(newsId)) {
         throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
      }
      //用来记录日志
      News news = this.newsService.getById(newsId);
      LogObjectHolder.me().set(news);
      return PREFIX + "news_edit.html";
   }

   @RequestMapping(value = "/edit")
   @ResponseBody
   public ResponseData edit(News news){
      ShiroUser user = ShiroKit.getUser();
      if (news == null) {
         throw new RequestEmptyException();
      }
      this.newsService.updateById(news);
      return SUCCESS_TIP;
   }

   @RequestMapping(value = "/getNewsInfo")
   @ResponseBody
   public ResponseData getNewsInfo(@RequestParam Long newsId) {
      if (ToolUtil.isEmpty(newsId)) {
         throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
      }
      News news = this.newsService.getById(newsId);
      return ResponseData.success(news);
   }


   /**
   * 删除
   * @param newsId
   * @return
   */
   @RequestMapping(value = "/delete")
   @ResponseBody
   public ResponseData delete(@RequestParam Long newsId) {
      if (ToolUtil.isEmpty(newsId)) {
         throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
      }
      this.newsService.removeById(newsId);
      return SUCCESS_TIP;
   }
}
