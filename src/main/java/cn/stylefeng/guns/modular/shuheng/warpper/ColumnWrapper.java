package cn.stylefeng.guns.modular.shuheng.warpper;

import cn.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import cn.stylefeng.guns.core.util.DecimalUtil;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

public class ColumnWrapper extends BaseControllerWrapper {

    public ColumnWrapper(Map<String, Object> single) {
        super(single);
    }

    public ColumnWrapper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public ColumnWrapper(Page<Map<String, Object>> page) {
        super(page);
    }

    public ColumnWrapper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        map.put("parentName", ConstantFactory.me().getColumnNameById(DecimalUtil.getLong(map.get("parentId"))));
        map.put("plazaName", ConstantFactory.me().getPlazaName(DecimalUtil.getLong(map.get("plazaId"))));
    }
}