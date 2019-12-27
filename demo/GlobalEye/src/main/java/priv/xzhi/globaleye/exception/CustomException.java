package priv.xzhi.globaleye.exception;

import priv.xzhi.globaleye.enums.ResultCode;

/**
 * Desc: 自定义异常
 * Created by 陈冠志 on 2019-09-29 9:13.
 */
public class CustomException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private int code;

    public CustomException() {
        super();
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(ResultCode resultCode) {
        super(resultCode.getMsg());
    }

    public CustomException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
