package priv.xzhi.globaleye.enums;

/**
 * Desc: 事件类型枚举
 * Created by 陈冠志 on 2019-10-28 14:13.
 */
public enum EventType {

    COMMUNICATIONS_ALARM(2,"通信告警"),
    PROCESSING_ERROR_ALARM(3, "软件进程告警"),
    ENVIRONMENTAL_ALARM(4, "环境告警"),
    QOS_ALARM(5, "服务告警"),
    EQUIPMENT_ALARM(1, "设备告警");

	private int code;
	private String msg;

	public String getMsg() {
		return msg;
	}

	public static int getCode(String eventTypeStr) {

		for (EventType eventType : EventType.values()) {
			if (eventTypeStr.equals(eventType.name())) {
				return eventType.code;
			}
		}
		return -1;
	}

	EventType(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}
