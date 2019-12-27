package priv.xzhi.globaleye.service;

import priv.xzhi.globaleye.bean.AlarmJson;
import priv.xzhi.globaleye.bean.TSrcAlarm;

/**
 * Desc:
 * Created by 陈冠志 on 2019-09-27 17:22.
 */
public interface NfvoAlarmService {

    void inserSrcAlarm(AlarmJson alarmJson);

    void inserEvent(TSrcAlarm tSrcAlarm);
}
