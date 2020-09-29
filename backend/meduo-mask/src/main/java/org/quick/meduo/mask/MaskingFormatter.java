package org.quick.meduo.mask;

import org.apache.commons.lang3.StringUtils;
import org.quick.meduo.mask.api.SensitiveType;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * MaskingFormatter
 */
public class MaskingFormatter  implements Formatter<String> {
    private SensitiveType typeEnum;

    public SensitiveType getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(SensitiveType typeEnum) {
        this.typeEnum = typeEnum;
    }

    @Override
    public String parse(String valueStr, Locale locale) throws ParseException {
        if (StringUtils.isNotBlank(valueStr)) {
            switch (typeEnum) {
                case CHINESE_NAME:
                    valueStr = MaskUtil.chineseName(valueStr);
                    break;
                case ID_CARD:
                    valueStr = MaskUtil.idCardNum(valueStr);
                    break;
                case FIXED_PHONE:
                    valueStr = MaskUtil.fixedPhone(valueStr);
                    break;
                case MOBILE_PHONE:
                    valueStr = MaskUtil.mobilePhone(valueStr);
                    break;
                case ADDRESS:
                    valueStr = MaskUtil.address(valueStr, 8);
                    break;
                case EMAIL:
                    valueStr = MaskUtil.email(valueStr);
                    break;
                case BANK_CARD:
                    valueStr = MaskUtil.bankCard(valueStr);
                    break;
                case PASSWORD:
                    valueStr = MaskUtil.password(valueStr);
                    break;
                case CARNUMBER:
                    valueStr = MaskUtil.carNumber(valueStr);
                    break;
                default:
            }
        }
        return valueStr;
    }

    @Override
    public String print(String s, Locale locale) {
        return s;
    }
}
