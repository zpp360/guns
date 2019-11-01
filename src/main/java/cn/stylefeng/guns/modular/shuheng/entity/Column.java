package cn.stylefeng.guns.modular.shuheng.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhengpp
 * @since 2019-10-31
 */
@TableName("m_column")
public class Column implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "column_id", type = IdType.ID_WORKER)
    private Long columnId;

    /**
     * 栏目名称
     */
    @TableField("column_name")
    private String columnName;

    /**
     * 纪念馆
     */
    @TableField("plaza_id")
    private Long plazaId;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 父栏目
     */
    @TableField("parent_id")
    private Long parentId;

    @TableField(exist = false)
    private String parentName;

    /**
     * 删除标识
     */
    @TableField(value = "del_flag",fill = FieldFill.INSERT)
    private String delFlag;

    /**
     * 栏目路径
     */
    @TableField("column_path")
    private String columnPath;

    /**
     * 图片宽度
     */
    @TableField("img_width")
    private Integer imgWidth;

    /**
     * 图片高度
     */
    @TableField("img_height")
    private Integer imgHeight;

    /**
     * 排序号码
     */
    private Integer sort;

    /**
     * 插入人
     */
    @TableField(value = "create_user",fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 插入时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(value = "update_user",fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    /**
     * 更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    public Long getPlazaId() {
        return plazaId;
    }

    public void setPlazaId(Long plazaId) {
        this.plazaId = plazaId;
    }
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
    public String getColumnPath() {
        return columnPath;
    }

    public void setColumnPath(String columnPath) {
        this.columnPath = columnPath;
    }
    public Integer getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(Integer imgWidth) {
        this.imgWidth = imgWidth;
    }
    public Integer getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(Integer imgHeight) {
        this.imgHeight = imgHeight;
    }
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
        return "Column{" +
        "columnId=" + columnId +
        ", columnName=" + columnName +
        ", plazaId=" + plazaId +
        ", level=" + level +
        ", parentId=" + parentId +
        ", delFlag=" + delFlag +
        ", columnPath=" + columnPath +
        ", imgWidth=" + imgWidth +
        ", imgHeight=" + imgHeight +
        ", sort=" + sort +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
