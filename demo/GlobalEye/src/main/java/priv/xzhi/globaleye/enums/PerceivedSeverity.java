package priv.xzhi.globaleye.enums;

import org.apache.commons.collections4.MapUtils;

import java.util.Map;

import cn.hutool.core.util.EnumUtil;

/**
 * Desc: 告警等级枚举
 * Created by 陈冠志 on 2019-10-28 14:33.
 */
public enum PerceivedSeverity {

    MINOR(3,"一般告警"),
    WARNING(4, "系统告警");

    private int code;
    private String msg;

    public String getMsg() {
        return msg;
    }

	public static int getCode(String eventTypeStr) {
		Map<String, Object> enumMap = EnumUtil.getNameFieldMap(PerceivedSeverity.class, "code");
		return MapUtils.getInteger(enumMap, eventTypeStr, -1);
	}

    public int getCode() {
        return code;
    }

    PerceivedSeverity(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
