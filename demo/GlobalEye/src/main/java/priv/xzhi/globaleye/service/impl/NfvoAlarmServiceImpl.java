package priv.xzhi.globaleye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import priv.xzhi.globaleye.bean.AlarmJson;
import priv.xzhi.globaleye.bean.TAlarmEvent;
import priv.xzhi.globaleye.bean.TSrcAlarm;
import priv.xzhi.globaleye.dao.TAlarmEventMapper;
import priv.xzhi.globaleye.dao.TSrcAlarmMapper;
import priv.xzhi.globaleye.enums.EventType;
import priv.xzhi.globaleye.enums.PerceivedSeverity;
import priv.xzhi.globaleye.service.NfvoAlarmService;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateUtil;

/**
 * Desc:
 * Created by 陈冠志 on 2019-09-27 17:36.
 */
@Service
//@Slf4j
public class NfvoAlarmServiceImpl extends ServiceImpl<TSrcAlarmMapper, TSrcAlarm> implements NfvoAlarmService {

	@Autowired
	private TAlarmEventMapper tAlarmEventMapper;

	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	@Override
	public void inserSrcAlarm(AlarmJson alarmJson) {

//		try {
			TSrcAlarm tSrcAlarm = new TSrcAlarm();
			tSrcAlarm.setId(alarmJson.getId());
			tSrcAlarm.setManagedObjectId(alarmJson.getManagedObjectId());
			AlarmJson.RootCauseFaultyComponentBean rootCauseFaultyComponent = alarmJson.getRootCauseFaultyComponent();
			tSrcAlarm.setFaultyNestedNsInstanceId(rootCauseFaultyComponent.getFaultyNestedNsInstanceId());
			tSrcAlarm.setFaultyNsVirtualLinkInstanceId(rootCauseFaultyComponent.getFaultyNsVirtualLinkInstanceId());
			tSrcAlarm.setFaultyVnfInstanceId(rootCauseFaultyComponent.getFaultyVnfInstanceId());
			AlarmJson.RootCauseFaultyResourceBean.FaultyResourceBean faultyResource = alarmJson.getRootCauseFaultyResource().getFaultyResource();
			tSrcAlarm.setVimId(faultyResource.getVimId());
			tSrcAlarm.setResourceProviderId(faultyResource.getResourceProviderId());
			tSrcAlarm.setResourceId(faultyResource.getResourceId());
			tSrcAlarm.setVimLevelResourceType(faultyResource.getVimLevelResourceType());
			tSrcAlarm.setFaultyResourceType(alarmJson.getRootCauseFaultyResource().getFaultyResourceType());
			tSrcAlarm.setAlarmRaisedTime(alarmJson.getAlarmRaisedTime());
			tSrcAlarm.setAlarmChangedTime(alarmJson.getAlarmChangedTime());
			tSrcAlarm.setAlarmClearedTime(alarmJson.getAlarmClearedTime());
			tSrcAlarm.setAckState(alarmJson.getAckState());
			tSrcAlarm.setPerceivedSeverity(alarmJson.getPerceivedSeverity());
			tSrcAlarm.setEventTime(alarmJson.getEventTime());
			tSrcAlarm.setEventType(alarmJson.getEventType());
			tSrcAlarm.setFaultType(alarmJson.getFaultType());
			tSrcAlarm.setProbableCause(alarmJson.getProbableCause());
			tSrcAlarm.setSRootCause(String.valueOf(alarmJson.isIsRootCause()));
			List<String> correlatedAlarmIds = alarmJson.getCorrelatedAlarmIds();
			if(CollectionUtils.isNotEmpty(correlatedAlarmIds)) {
				String s = correlatedAlarmIds.toString().replaceAll("[\\[\\]]", "");
				tSrcAlarm.setCorrelatedAlarmIds(s);
			}
			List<String> faultDetails = alarmJson.getFaultDetails();
			if(CollectionUtils.isNotEmpty(faultDetails)) {
				String s = faultDetails.toString().replaceAll("[\\[\\]]", "");
				tSrcAlarm.setFaultDetails(s);
			}
			String selfHref = alarmJson.get_links().getSelf().getHref();
			tSrcAlarm.setSelf(selfHref);
			String alarmsHref = alarmJson.get_links().getAlarms().getHref();
			tSrcAlarm.setAlarms(alarmsHref);

			inserEvent(tSrcAlarm);

			baseMapper.insert(tSrcAlarm);
//		} catch (Exception e) {
//			log.error("", e);
//		}

	}

	@Override
	public void inserEvent(TSrcAlarm tSrcAlarm) {
		TAlarmEvent alarmEvent = new TAlarmEvent();
		alarmEvent.setSNotificationId(tSrcAlarm.getId());
		alarmEvent.setSVdAlarmId(tSrcAlarm.getId());
		alarmEvent.setSVendorObjectName("");
		StringBuilder objectName = new StringBuilder("#####/Ne=");
//		String separator = "-";
//		objectName.append(tSrcAlarm.getFaultyNestedNsInstanceId()).append(separator);
//		objectName.append(tSrcAlarm.getFaultyNsVirtualLinkInstanceId()).append(separator);
//		objectName.append(tSrcAlarm.getFaultyVnfInstanceId());
		objectName.append(tSrcAlarm.getResourceId());
		alarmEvent.setSObjectName(objectName.toString());
		alarmEvent.setIObjectType(99);
		String alarmRaisedTime = tSrcAlarm.getAlarmRaisedTime();
		Date date = new Date();
		if(StringUtils.isNotBlank(alarmRaisedTime)) {
			date = DateUtil.parse(alarmRaisedTime, DATE_FORMAT).toJdkDate();
		}
		alarmEvent.setDNeTime(date);
		alarmEvent.setDEmsTime(date);
		alarmEvent.setIPerceivedSeverity(PerceivedSeverity.getCode(tSrcAlarm.getPerceivedSeverity()));
		alarmEvent.setSProbableCause(tSrcAlarm.getProbableCause());
		alarmEvent.setSDetailProbableCause("");
		alarmEvent.setSAlarmCode("");
		alarmEvent.setIEventType(EventType.getCode(tSrcAlarm.getEventType()));
		alarmEvent.setSAdditionalText("");
		alarmEvent.setSAdditionalInfo("");
		alarmEvent.setSExtensions("");

		tAlarmEventMapper.insert(alarmEvent);
	}

}
