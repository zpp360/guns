package cn.stylefeng.guns.modular.shenjiang.service;

import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.shenjiang.entity.House;
import cn.stylefeng.guns.modular.shenjiang.mapper.HouseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class HouseService extends ServiceImpl<HouseMapper,House> {
    /**
     * 库房列表
     * @param houseName
     * @return
     */
    public Page<Map<String,Object>> listHouse(String houseName) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.selectHouse(page,houseName);
    }
}
