package cn.stylefeng.guns.modular.shuheng.dictmap;

import cn.stylefeng.guns.core.common.constant.dictmap.base.AbstractDictMap;

public class ColumnDict extends AbstractDictMap {
    @Override
    public void init() {
        put("columnId","栏目ID");
        put("columnName","栏目名称");
        put("parentId","上级栏目");
        put("delFlag","删除标识");
        put("imgWidth","图片宽");
        put("imgHeight","图片高");
    }

    @Override
    protected void initBeWrapped() {

    }
}
