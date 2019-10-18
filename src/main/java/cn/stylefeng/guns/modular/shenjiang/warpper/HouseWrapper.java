package cn.stylefeng.guns.modular.shenjiang.warpper;

import cn.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

public class HouseWrapper extends BaseControllerWrapper {

    public HouseWrapper(Map<String, Object> single) {
        super(single);
    }

    public HouseWrapper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public HouseWrapper(Page<Map<String, Object>> page) {
        super(page);
    }

    public HouseWrapper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        map.put("floorName", ConstantFactory.me().getFloorName(map.get("floorId").toString()));
        map.put("machineName", ConstantFactory.me().getMachineName(map.get("machineId").toString()));
    }
}
