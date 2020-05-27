package com.trd.oecms.constants.enums;

/**
 * 用户状态的枚举
 * @author tanruidong
 * @date 2020-05-27 16:29
 */
public enum UserStatusEnum {
    /**
     * 启用
     */
    ENABLE,
    /**
     * 禁用
     */
    DISABLE;

    public byte state(){
        return (byte) this.ordinal();
    }
}
