package cn.stylefeng.guns.modular.shuheng.entity;

import com.baomidou.mybatisplus.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhengpp
 * @since 2019-11-05
 */
@TableName("m_news")
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "news_id", type = IdType.ID_WORKER)
    private Long newsId;

    /**
     * 标题
     */
    @TableField("news_title")
    private String newsTitle;

    /**
     * 来源
     */
    @TableField("news_source")
    private String newsSource;

    /**
     * 发布时间
     */
    @TableField(value = "release_time")
    @DateTimeFormat(pattern="YYYY-MM-dd HH:mm")
    private Date releaseTime;

    /**
     * 摘要
     */
    @TableField("news_digest")
    private String newsDigest;

    /**
     * 标题图
     */
    @TableField("news_img")
    private String newsImg;

    /**
     * 内容
     */
    @TableField("news_content")
    private String newsContent;

    /**
     * 模型news新闻video视频download下载
     */
    @TableField("news_model")
    private String newsModel;

    /**
     * 附件名称
     */
    @TableField("news_file_name")
    private String newsFileName;

    /**
     * 附件路径
     */
    @TableField("news_file_path")
    private String newsFilePath;

    /**
     * 视频名称
     */
    @TableField("news_video_name")
    private String newsVideoName;

    /**
     * 视频路径
     */
    @TableField("news_video_path")
    private String newsVideoPath;

    /**
     * 栏目
     */
    @TableField("column_id")
    private Long columnId;

    /**
     * 固定级别
     */
    @TableField("top_level")
    private Integer topLevel;

    /**
     * 固定级别到期时间
     */
    @TableField("top_level_time")
    private Date topLevelTime;

    @TableField("news_push")
    private Integer newsPush;

    @TableField(value = "del_flag",fill = FieldFill.INSERT)
    private String delFlag;

    @TableField(value = "create_user",fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_user",fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }
    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }
    public String getNewsSource() {
        return newsSource;
    }

    public void setNewsSource(String newsSource) {
        this.newsSource = newsSource;
    }
    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }
    public String getNewsDigest() {
        return newsDigest;
    }

    public void setNewsDigest(String newsDigest) {
        this.newsDigest = newsDigest;
    }
    public String getNewsImg() {
        return newsImg;
    }

    public void setNewsImg(String newsImg) {
        this.newsImg = newsImg;
    }
    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }
    public String getNewsModel() {
        return newsModel;
    }

    public void setNewsModel(String newsModel) {
        this.newsModel = newsModel;
    }
    public String getNewsFileName() {
        return newsFileName;
    }

    public void setNewsFileName(String newsFileName) {
        this.newsFileName = newsFileName;
    }
    public String getNewsFilePath() {
        return newsFilePath;
    }

    public void setNewsFilePath(String newsFilePath) {
        this.newsFilePath = newsFilePath;
    }
    public String getNewsVideoName() {
        return newsVideoName;
    }

    public void setNewsVideoName(String newsVideoName) {
        this.newsVideoName = newsVideoName;
    }
    public String getNewsVideoPath() {
        return newsVideoPath;
    }

    public void setNewsVideoPath(String newsVideoPath) {
        this.newsVideoPath = newsVideoPath;
    }
    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }
    public Integer getTopLevel() {
        return topLevel;
    }

    public void setTopLevel(Integer topLevel) {
        this.topLevel = topLevel;
    }
    public Date getTopLevelTime() {
        return topLevelTime;
    }

    public void setTopLevelTime(Date topLevelTime) {
        this.topLevelTime = topLevelTime;
    }
    public Integer getNewsPush() {
        return newsPush;
    }

    public void setNewsPush(Integer newsPush) {
        this.newsPush = newsPush;
    }
    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "News{" +
        "newsId=" + newsId +
        ", newsTitle=" + newsTitle +
        ", newsSource=" + newsSource +
        ", releaseTime=" + releaseTime +
        ", newsDigest=" + newsDigest +
        ", newsImg=" + newsImg +
        ", newsContent=" + newsContent +
        ", newsModel=" + newsModel +
        ", newsFileName=" + newsFileName +
        ", newsFilePath=" + newsFilePath +
        ", newsVideoName=" + newsVideoName +
        ", newsVideoPath=" + newsVideoPath +
        ", columnId=" + columnId +
        ", topLevel=" + topLevel +
        ", topLevelTime=" + topLevelTime +
        ", newsPush=" + newsPush +
        ", delFlag=" + delFlag +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
