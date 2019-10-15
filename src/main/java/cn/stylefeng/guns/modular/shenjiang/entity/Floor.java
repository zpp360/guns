package cn.stylefeng.guns.modular.shenjiang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * Created by zpp360 on 2019/10/12.
 */
@TableName("m_floor")
public class Floor  implements Serializable {

    private static final long serialVersionUID = 7264255369423317524L;
    /**
     * 主键id
     AUTO(0),//数据库自增 依赖数据库
     NONE(1),// 表示该类型未甚至主键类型 （如果没有主键策略）默认根据雪花算法生成
     INPUT(2),//用户输入ID（该类型可以通过自己注册填充插件进行填充）
     　　//下面这三种类型,只有当插入对象id为空时 才会自动填充。
     ID_WORKER(3),//全局唯一（idWorker）数值类型
     UUID(4),//全局唯一（UUID）
     ID_WORKER_STR(5);//全局唯一（idWorker的字符串表示）
     */
    @TableId(value = "floor_id", type = IdType.AUTO)
    private Long floorId;

    @TableField("floor_name")
    private String floorName;

    @TableField("floor_img")
    private String floorImg;

    @TableField("sort")
    private Integer sort;

    public Long getFloorId() {
        return floorId;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getFloorImg() {
        return floorImg;
    }

    public void setFloorImg(String floorImg) {
        this.floorImg = floorImg;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
