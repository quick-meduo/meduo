/*
 * Copyright (c) 2019 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      https://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.quick.meduo.mask;

import com.alibaba.fastjson.serializer.ValueFilter;
import lombok.extern.slf4j.Slf4j;
import org.quick.meduo.mask.annotation.Masking;
import org.quick.meduo.mask.api.SensitiveType;

import java.lang.reflect.Field;

/**
 * Masking Filter definition
 */
public class ValueMaskingFilter implements ValueFilter {
    @Override
    public Object process(Object object, String name, Object value) {
        if (null == value || !(value instanceof String) || ((String) value).length() == 0) {
            return value;
        }
        try {
            Field field = object.getClass().getDeclaredField(name);
            Masking desensitization;
            if (String.class != field.getType() || (desensitization = field.getAnnotation(Masking.class)) == null) {
                return value;
            }
            String valueStr = (String) value;
            SensitiveType type = desensitization.value();
            switch (type) {
                case CHINESE_NAME:
                    return MaskUtil.chineseName(valueStr);
                case ID_CARD:
                    return MaskUtil.idCardNum(valueStr);
                case FIXED_PHONE:
                    return MaskUtil.fixedPhone(valueStr);
                case MOBILE_PHONE:
                    return MaskUtil.mobilePhone(valueStr);
                case ADDRESS:
                    return MaskUtil.address(valueStr, 8);
                case EMAIL:
                    return MaskUtil.email(valueStr);
                case BANK_CARD:
                    return MaskUtil.bankCard(valueStr);
                case PASSWORD:
                    return MaskUtil.password(valueStr);
                case CARNUMBER:
                    return MaskUtil.carNumber(valueStr);
                default:
            }
        } catch (NoSuchFieldException e) {
            return value;
        }
        return value;
    }
}
