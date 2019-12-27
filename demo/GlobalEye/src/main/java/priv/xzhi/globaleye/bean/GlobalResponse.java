package priv.xzhi.globaleye.bean;

import priv.xzhi.globaleye.enums.ResultCode;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Desc: 全局结果响应实体类
 * Created by 陈冠志 on 2019-09-29 9:22.
 */
@Data
@AllArgsConstructor
public class GlobalResponse {

	private GlobalResponse() {
	}

	private static volatile GlobalResponse globalResponse;

	private int code;
	private String msg;
	private Object data;

	public GlobalResponse(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public GlobalResponse(String msg) {
		this.msg = msg;
	}

	private static GlobalResponse getInstance() {
		if (globalResponse == null) {
			synchronized (GlobalResponse.class) {
				if (globalResponse == null) {
					globalResponse = new GlobalResponse();
				}
			}
		}
		return globalResponse;
	}

	public static GlobalResponse success() {
		return setResultCode(ResultCode.SUCCESS);
	}

	public static GlobalResponse success(Object... objs) {
		GlobalResponse response = setResultCode(ResultCode.SUCCESS);
		body(response, objs);
		return response;
	}

	public static GlobalResponse failure() {
		return setResultCode(ResultCode.FAILURE);
	}

	public static GlobalResponse failure(Object... objs) {
		GlobalResponse response = setResultCode(ResultCode.FAILURE);
		body(response, objs);
		return response;
	}

	private static GlobalResponse setResultCode(ResultCode code) {
		GlobalResponse response = getInstance();
		response.setCode(code.getCode());
		response.setMsg(code.getMsg());
		return response;
	}

	private static void body(GlobalResponse response, Object[] objs) {
		for (Object obj : objs) {
			if (obj instanceof String) {
				response.setMsg((String) obj);
			} else if (obj instanceof Integer) {
				response.setCode((Integer) obj);
			} else if (obj instanceof ResultCode) {
				setResultCode((ResultCode) obj);
			} else {
				response.setData(obj);
			}
		}
	}
}
