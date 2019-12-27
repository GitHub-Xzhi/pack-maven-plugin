package priv.xzhi.globaleye.enums;

/**
 * Desc: 消息与状态码枚举
 * Created by 陈冠志 on 2019-09-29 10:11.
 */
public enum ResultCode {

	PARAM_NOT_NULL(611, "参数不能为空"),
    REQUEST_BODY_NOT_NULL(400, "请求体不能为空"),
	SUCCESS(1, "成功"),
	FAILURE(0, "失败"),
    NOT_FOUND(404, "接口找不到");

	private int code;
	private String msg;

	ResultCode(String msg) {
		this.msg = msg;
	}

	ResultCode(int code, String msg) {
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public int getCode() {
		return code;
	}
}
