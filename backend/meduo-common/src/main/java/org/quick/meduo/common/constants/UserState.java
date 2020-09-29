package org.quick.meduo.common.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum UserState {
    NORMAL(1, "正常"),
    ABNORMAL(2, "异常"),
    LOCKED(3, "锁定");

    UserState(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @EnumValue
    private final int code;
    private final String desc;

}