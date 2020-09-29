package org.quick.meduo.common.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * Define user credential level
 */
@Getter
public enum UserCredentialLevel {
    LEVEL1(1, "一般"),
    LEVEL2(2, "公开"),
    LEVEL3(3, "秘密");

    UserCredentialLevel(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @EnumValue
    private final int code;
    private final String desc;

}