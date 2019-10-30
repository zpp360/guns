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
 * @since 2019-10-22
 */
@TableName("m_plaza")
public class Plaza implements Serializable {

    private static final long serialVersionUID = 2528059331293603511L;
    /**
     * ID
     */
    @TableId(value = "plaza_id", type = IdType.ID_WORKER)
    private Long plazaId;

    /**
     * 纪念馆名称
     */
    @TableField("plaza_name")
    private String plazaName;

    /**
     * 电话
     */
    @TableField("plaza_tel")
    private String plazaTel;

    /**
     * 地址
     */
    @TableField("plaza_addres")
    private String plazaAddres;

    /**
     * 纪念馆简介
     */
    @TableField("plaza_desc")
    private String plazaDesc;

    /**
     * 场馆图片
     */
    @TableField("plaza_img")
    private String plazaImg;


    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField(value = "update_user", fill = FieldFill.UPDATE)
    private Long updateUser;

    public Long getPlazaId() {
        return plazaId;
    }

    public void setPlazaId(Long plazaId) {
        this.plazaId = plazaId;
    }
    public String getPlazaName() {
        return plazaName;
    }

    public void setPlazaName(String plazaName) {
        this.plazaName = plazaName;
    }
    public String getPlazaTel() {
        return plazaTel;
    }

    public void setPlazaTel(String plazaTel) {
        this.plazaTel = plazaTel;
    }
    public String getPlazaAddres() {
        return plazaAddres;
    }

    public void setPlazaAddres(String plazaAddres) {
        this.plazaAddres = plazaAddres;
    }
    public String getPlazaDesc() {
        return plazaDesc;
    }

    public void setPlazaDesc(String plazaDesc) {
        this.plazaDesc = plazaDesc;
    }
    public String getPlazaImg() {
        return plazaImg;
    }

    public void setPlazaImg(String plazaImg) {
        this.plazaImg = plazaImg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    public String toString() {
        return "Plaza{" +
        "plazaId=" + plazaId +
        ", plazaName=" + plazaName +
        ", plazaTel=" + plazaTel +
        ", plazaAddres=" + plazaAddres +
        ", plazaDesc=" + plazaDesc +
        ", plazaImg=" + plazaImg +
        "}";
    }
}
