package cn.stylefeng.guns.modular.shuheng.controller;


import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.shiro.ShiroUser;
import cn.stylefeng.guns.modular.shuheng.entity.News;
import cn.stylefeng.guns.modular.shuheng.entity.Plaza;
import cn.stylefeng.guns.modular.shuheng.service.NewsService;
import cn.stylefeng.guns.modular.shuheng.service.PlazaService;
import cn.stylefeng.guns.modular.shuheng.warpper.NewsWrapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;
import java.util.Date;

import org.springframework.stereotype.Controller;

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

   @Autowired
   private PlazaService plazaService;

   @RequestMapping("")
   public String index(){
      return PREFIX + "news.html";
   }

   @RequestMapping("/list")
   @ResponseBody
   public Object list(@RequestParam(value = "newsName", required = false) String newsName,
                      @RequestParam(value = "columnId", required = false) String columnId){
      Page<Map<String,Object>> page = newsService.listNews(newsName,columnId);
      page = new NewsWrapper(page).wrap();
      return LayuiPageFactory.createPageInfo(page);
   }

   @RequestMapping("/news_add")
   public String addView(@RequestParam(value = "newsModel") String newsModel,@RequestParam(value = "columnId") String columnId, Model model) {
      model.addAttribute("newsModel",newsModel);
      model.addAttribute("columnId",columnId);
      return PREFIX + "news_add.html";
   }

   @RequestMapping(value = "/add")
   @ResponseBody
   public ResponseData add(News news) {
      ShiroUser user = ShiroKit.getUser();
      if (news == null) {
         throw new RequestEmptyException();
      }
      if(ToolUtil.isEmpty(news.getReleaseTime())){
         news.setReleaseTime(new Date());
      }
      if(ToolUtil.isEmpty(news.getNewsSource()) && user.isPlazaAdmin()){
         Plaza plaza = plazaService.getById(user.getPlazaId());
         news.setNewsSource(plaza.getPlazaName());
      }

      setDigest(news);

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

      setDigest(news);

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

   /**
    * 设置摘要
    * @param news
    */
   private void setDigest(News news){
      if(ToolUtil.isEmpty(news.getNewsDigest())){
         String html = news.getNewsContent();
         Document doc = Jsoup.parse(html);
         String text = doc.text();
         if (text.length() > 200) {
            text = text.substring(0, 200);
         }
         news.setNewsDigest(text);
      }else{
         if(news.getNewsDigest().length()>200){
            news.setNewsDigest(news.getNewsDigest().substring(0, 200));
         }
      }
   }
}
