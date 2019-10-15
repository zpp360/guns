package cn.stylefeng.guns.modular.shenjiang.service;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.shenjiang.entity.Floor;
import cn.stylefeng.guns.modular.shenjiang.mapper.FloorMapper;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by zpp360 on 2019/10/12.
 */
@Service
public class FloorService extends ServiceImpl<FloorMapper,Floor> {


    public Page<Map<String,Object>> listFloor(String floorName) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.selectFloor(page,floorName);
    }

    public void addFloor(Floor floor) {
        if (ToolUtil.isOneEmpty(floor,floor.getFloorName(),floor.getSort() )) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.save(floor);
    }
}
