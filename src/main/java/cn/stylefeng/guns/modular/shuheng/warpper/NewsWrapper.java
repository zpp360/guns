package cn.stylefeng.guns.modular.shuheng.warpper;

import cn.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import cn.stylefeng.guns.modular.shuheng.state.NewsModelEnum;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

public class NewsWrapper extends BaseControllerWrapper {

    public NewsWrapper(Map<String, Object> single) {
        super(single);
    }

    public NewsWrapper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public NewsWrapper(Page<Map<String, Object>> page) {
        super(page);
    }

    public NewsWrapper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        map.put("newsModel", NewsModelEnum.getMessage(map.get("newsModel")+""));
        map.put("columnName", ConstantFactory.me().getColumnNameById((Long)map.get("columnId")));
    }
}