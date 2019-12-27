package priv.xzhi.globaleye.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Desc: 原告警json实体类
 * Created by 陈冠志 on 2019-09-27 13:46.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlarmJson implements Serializable {

	private String id;
	private String managedObjectId;
	private RootCauseFaultyComponentBean rootCauseFaultyComponent;
	private RootCauseFaultyResourceBean rootCauseFaultyResource;
	private String alarmRaisedTime;
	private String alarmChangedTime;
	private String alarmClearedTime;
	private String ackState;
	private String perceivedSeverity;
	private String eventTime;
	private String eventType;
	private String faultType;
	private String probableCause;
	private LinksBean _links;
	private boolean isRootCause;
	private List<String> correlatedAlarmIds;
	private List<String> faultDetails;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getManagedObjectId() {
		return managedObjectId;
	}

	public void setManagedObjectId(String managedObjectId) {
		this.managedObjectId = managedObjectId;
	}

	public RootCauseFaultyComponentBean getRootCauseFaultyComponent() {
		return rootCauseFaultyComponent;
	}

	public void setRootCauseFaultyComponent(RootCauseFaultyComponentBean rootCauseFaultyComponent) {
		this.rootCauseFaultyComponent = rootCauseFaultyComponent;
	}

	public RootCauseFaultyResourceBean getRootCauseFaultyResource() {
		return rootCauseFaultyResource;
	}

	public void setRootCauseFaultyResource(RootCauseFaultyResourceBean rootCauseFaultyResource) {
		this.rootCauseFaultyResource = rootCauseFaultyResource;
	}

	public String getAlarmRaisedTime() {
		return alarmRaisedTime;
	}

	public void setAlarmRaisedTime(String alarmRaisedTime) {
		this.alarmRaisedTime = alarmRaisedTime;
	}

	public String getAlarmChangedTime() {
		return alarmChangedTime;
	}

	public void setAlarmChangedTime(String alarmChangedTime) {
		this.alarmChangedTime = alarmChangedTime;
	}

	public String getAlarmClearedTime() {
		return alarmClearedTime;
	}

	public void setAlarmClearedTime(String alarmClearedTime) {
		this.alarmClearedTime = alarmClearedTime;
	}

	public String getAckState() {
		return ackState;
	}

	public void setAckState(String ackState) {
		this.ackState = ackState;
	}

	public String getPerceivedSeverity() {
		return perceivedSeverity;
	}

	public void setPerceivedSeverity(String perceivedSeverity) {
		this.perceivedSeverity = perceivedSeverity;
	}

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getFaultType() {
		return faultType;
	}

	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}

	public String getProbableCause() {
		return probableCause;
	}

	public void setProbableCause(String probableCause) {
		this.probableCause = probableCause;
	}

	public LinksBean get_links() {
		return _links;
	}

	public void set_links(LinksBean _links) {
		this._links = _links;
	}

	public boolean isIsRootCause() {
		return isRootCause;
	}

	public void setIsRootCause(boolean isRootCause) {
		this.isRootCause = isRootCause;
	}

	public List<String> getCorrelatedAlarmIds() {
		return correlatedAlarmIds;
	}

	public void setCorrelatedAlarmIds(List<String> correlatedAlarmIds) {
		this.correlatedAlarmIds = correlatedAlarmIds;
	}

	public List<String> getFaultDetails() {
		return faultDetails;
	}

	public void setFaultDetails(List<String> faultDetails) {
		this.faultDetails = faultDetails;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class RootCauseFaultyComponentBean {
		private String faultyNestedNsInstanceId;
		private String faultyNsVirtualLinkInstanceId;
		private String faultyVnfInstanceId;

		public String getFaultyNestedNsInstanceId() {
			return faultyNestedNsInstanceId;
		}

		public void setFaultyNestedNsInstanceId(String faultyNestedNsInstanceId) {
			this.faultyNestedNsInstanceId = faultyNestedNsInstanceId;
		}

		public String getFaultyNsVirtualLinkInstanceId() {
			return faultyNsVirtualLinkInstanceId;
		}

		public void setFaultyNsVirtualLinkInstanceId(String faultyNsVirtualLinkInstanceId) {
			this.faultyNsVirtualLinkInstanceId = faultyNsVirtualLinkInstanceId;
		}

		public String getFaultyVnfInstanceId() {
			return faultyVnfInstanceId;
		}

		public void setFaultyVnfInstanceId(String faultyVnfInstanceId) {
			this.faultyVnfInstanceId = faultyVnfInstanceId;
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class RootCauseFaultyResourceBean {
		private FaultyResourceBean faultyResource;
		private String faultyResourceType;

		public FaultyResourceBean getFaultyResource() {
			return faultyResource;
		}

		public void setFaultyResource(FaultyResourceBean faultyResource) {
			this.faultyResource = faultyResource;
		}

		public String getFaultyResourceType() {
			return faultyResourceType;
		}

		public void setFaultyResourceType(String faultyResourceType) {
			this.faultyResourceType = faultyResourceType;
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class FaultyResourceBean {
			private String vimId;
			private String resourceProviderId;
			private String resourceId;
			private String vimLevelResourceType;

			public String getVimId() {
				return vimId;
			}

			public void setVimId(String vimId) {
				this.vimId = vimId;
			}

			public String getResourceProviderId() {
				return resourceProviderId;
			}

			public void setResourceProviderId(String resourceProviderId) {
				this.resourceProviderId = resourceProviderId;
			}

			public String getResourceId() {
				return resourceId;
			}

			public void setResourceId(String resourceId) {
				this.resourceId = resourceId;
			}

			public String getVimLevelResourceType() {
				return vimLevelResourceType;
			}

			public void setVimLevelResourceType(String vimLevelResourceType) {
				this.vimLevelResourceType = vimLevelResourceType;
			}
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class LinksBean {
		private SelfBean self;
		private AlarmsBean alarms;

		public SelfBean getSelf() {
			return self;
		}

		public void setSelf(SelfBean self) {
			this.self = self;
		}

		public AlarmsBean getAlarms() {
			return alarms;
		}

		public void setAlarms(AlarmsBean alarms) {
			this.alarms = alarms;
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class SelfBean {
			private String href;

			public String getHref() {
				return href;
			}

			public void setHref(String href) {
				this.href = href;
			}
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class AlarmsBean {
			private String href;

			public String getHref() {
				return href;
			}

			public void setHref(String href) {
				this.href = href;
			}
		}
	}
}
