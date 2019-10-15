package cn.stylefeng.guns.modular.shenjiang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("m_house")
public class House implements Serializable {

    private static final long serialVersionUID = -7864867629982483052L;

    @TableId(value = "house_id", type = IdType.AUTO)
    private String houseId;

    @TableField("house_name")
    private String houseName;

    @TableField("house_img")
    private String houseImg;

    @TableField("floor_id")
    private Integer floorId;

    @TableField("machine_id")
    private String machineId;

    @TableField("sort")
    private Integer sort;

    @TableField("ip")
    private String ip;

    @TableField("port")
    private Integer port;

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHouseImg() {
        return houseImg;
    }

    public void setHouseImg(String houseImg) {
        this.houseImg = houseImg;
    }

    public Integer getFloorId() {
        return floorId;
    }

    public void setFloorId(Integer floorId) {
        this.floorId = floorId;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
