package cn.stylefeng.guns.modular.shenjiang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhengpp
 * @since 2019-10-17
 */
@TableName("m_machine")
public class Machine implements Serializable {


    private static final long serialVersionUID = 7452769060537547352L;
    /**
     * ID
     */
    @TableId(value = "machine_id", type = IdType.AUTO)
    private Integer machineId;

    /**
     * 设备名称
     */
    @TableField("machine_name")
    private String machineName;

    /**
     * 加热，制冷，除湿，加湿，净化，消毒，关机
     */
    @TableField("machine_function")
    private String machineFunction;

    /**
     * 设备图标
     */
    @TableField("machine_img")
    private String machineImg;

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }
    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }
    public String getMachineFunction() {
        return machineFunction;
    }

    public void setMachineFunction(String machineFunction) {
        this.machineFunction = machineFunction;
    }
    public String getMachineImg() {
        return machineImg;
    }

    public void setMachineImg(String machineImg) {
        this.machineImg = machineImg;
    }

    @Override
    public String toString() {
        return "Machine{" +
        "machineId=" + machineId +
        ", machineName=" + machineName +
        ", machineFunction=" + machineFunction +
        ", machineImg=" + machineImg +
        "}";
    }
}
