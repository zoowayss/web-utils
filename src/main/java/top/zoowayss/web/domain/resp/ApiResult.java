package top.zoowayss.web.domain.resp;

import lombok.Data;
import top.zoowayss.web.domain.enums.CommonErrorEnum;

/**
 * Description: 通用返回体
 * Author: <a href="https://github.com/zooways">zooways</a>
 * Date: 2023-03-23
 */
@Data
public class ApiResult<T> {
    /**
     * 成功标识true or false
     */
    private Boolean success;
    /**
     * 错误码
     */
    private Integer errCode;
    /**
     * 错误消息
     */
    private String errMsg;
    /**
     * 返回对象
     */
    private T data;

    public static <T> ApiResult<T> success() {
        ApiResult<T> result = new ApiResult<T>();
        result.setData(null);
        result.setSuccess(Boolean.TRUE);
        return result;
    }

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> result = new ApiResult<T>();
        result.setData(data);
        result.setSuccess(Boolean.TRUE);
        return result;
    }

    public static <T> ApiResult<T> fail(Integer code, String msg) {
        ApiResult<T> result = new ApiResult<T>();
        result.setSuccess(Boolean.FALSE);
        result.setErrCode(code);
        result.setErrMsg(msg);
        return result;
    }

    public static <T> ApiResult<?> error(String errMsg) {
        ApiResult<T> result = new ApiResult<T>();
        result.setSuccess(Boolean.FALSE);
        result.setErrCode(500);
        result.setErrMsg(errMsg);
        return result;
    }

    public static <T> ApiResult<T> fail(CommonErrorEnum error) {
        ApiResult<T> result = new ApiResult<T>();
        result.setSuccess(Boolean.FALSE);
        result.setErrCode(error.getCode());
        result.setErrMsg(error.getMsg());
        return result;
    }

    public static <T> ApiResult<T> fail(CommonErrorEnum error, String msg) {
        ApiResult<T> result = new ApiResult<T>();
        result.setSuccess(Boolean.FALSE);
        result.setErrCode(error.getCode());
        result.setErrMsg(msg);
        return result;
    }

    public boolean isSuccess() {
        return this.success;
    }
}
