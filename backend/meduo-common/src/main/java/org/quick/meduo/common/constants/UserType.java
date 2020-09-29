package org.quick.meduo.common.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum UserType {
    ADMIN(1, "管理员"),
    SYS(2, "系统人员"),
    BIZ(3, "业务人员"),
    OTHER(4,"其他");

    UserType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @EnumValue
    private final int code;
    private final String desc;

}