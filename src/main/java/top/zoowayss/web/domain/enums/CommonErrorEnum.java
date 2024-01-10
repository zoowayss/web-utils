package top.zoowayss.web.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description: 通用异常码
 * Author: <a href="https://github.com/zooways">zooways</a>
 * Date: 2023-03-26
 */
@AllArgsConstructor
@Getter
public enum CommonErrorEnum implements ErrorEnum {

    APP_ID_NOT_FOUND(1001, "AppId不存在"),
    SYSTEM_ERROR(-1, "系统出小差了，请稍后再试哦~~"),
    PARAM_VALID(-2, "参数校验失败{0}"),
    FREQUENCY_LIMIT(-3, "Please try again later"),
    LOCK_LIMIT(-4, "请求太频繁了，请稍后再试哦~~"),
    FILE_NOT_FOUND(-5, "文件不存在");
    private final Integer code;
    private final String msg;

    @Override
    public Integer getErrorCode() {
        return this.code;
    }

    @Override
    public String getErrorMsg() {
        return this.msg;
    }
}
