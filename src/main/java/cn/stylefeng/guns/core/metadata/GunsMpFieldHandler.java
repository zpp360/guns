/**
 * Copyright 2018-2020 stylefeng & fengshuonan (sn93@qq.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.guns.core.metadata;

import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.roses.core.metadata.CustomMetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 字段填充器
 *
 * @author fengshuonan
 * @Date 2018/12/8 15:01
 */
@Component
public class GunsMpFieldHandler extends CustomMetaObjectHandler {

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName(this.getUpdateTimeFieldName(), new Date(), metaObject);
        Object accountId = this.getUserUniqueId();
        this.setFieldValByName(this.getUpdateUserFieldName(), accountId, metaObject);
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        Object delFlag = this.getFieldValByName(this.getDeleteFlagFieldName(), metaObject);
        if (delFlag == null) {
            this.setFieldValByName(this.getDeleteFlagFieldName(), this.getDefaultDelFlagValue(), metaObject);
        }

        Object createTime = this.getFieldValByName(this.getCreateTimeFieldName(), metaObject);
        if (createTime == null) {
            this.setFieldValByName(this.getCreateTimeFieldName(), new Date(), metaObject);
        }

        Object createUser = this.getFieldValByName(this.getCreateUserFieldName(), metaObject);
        if (createUser == null) {
            Object accountId = this.getUserUniqueId();
            this.setFieldValByName(this.getCreateUserFieldName(), accountId, metaObject);
        }

        Object updateTime = this.getFieldValByName(this.getUpdateTimeFieldName(),metaObject);
        if(updateTime == null){
            this.setFieldValByName(this.getUpdateTimeFieldName(), new Date(), metaObject);
        }

        Object updateUser = this.getFieldValByName(this.getUpdateUserFieldName(), metaObject);
        if(updateUser == null){
            Object accountId = this.getUserUniqueId();
            this.setFieldValByName(this.getUpdateUserFieldName(), accountId, metaObject);
        }

    }

    @Override
    protected Object getUserUniqueId() {
        try {

            return ShiroKit.getUser().getId();

        } catch (Exception e) {

            //如果获取不到当前用户就存空id
            return "";
        }
    }
}
