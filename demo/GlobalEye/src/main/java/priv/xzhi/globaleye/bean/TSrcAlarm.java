package priv.xzhi.globaleye.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "t_src_alarm")
public class TSrcAlarm implements Serializable {
    @TableId(value = "iid", type = IdType.AUTO)
    private Integer iid;

    /**
     * 告警ID，系统唯一
     */
    @TableField(value = "id")
    private String id;

    /**
     * 关联NS的id
     */
    @TableField(value = "managedObjectId")
    private String managedObjectId;

    /**
     * 故障NS实例ID
     */
    @TableField(value = "faultyNestedNsInstanceId")
    private String faultyNestedNsInstanceId;

    /**
     * 故障VL实例ID
     */
    @TableField(value = "faultyNsVirtualLinkInstanceId")
    private String faultyNsVirtualLinkInstanceId;

    /**
     * 故障VNF实例ID
     */
    @TableField(value = "faultyVnfInstanceId")
    private String faultyVnfInstanceId;

    /**
     * Vim id
     */
    @TableField(value = "vimId")
    private String vimId;

    /**
     * Vendor id
     */
    @TableField(value = "resourceProviderId")
    private String resourceProviderId;

    /**
     * 资源id
     */
    @TableField(value = "resourceId")
    private String resourceId;

    /**
     * Vim资源类型
     */
    @TableField(value = "vimLevelResourceType")
    private String vimLevelResourceType;

    /**
     * 故障资源类型
     */
    @TableField(value = "faultyResourceType")
    private String faultyResourceType;

    /**
     * 告警发生时间
     */
    @TableField(value = "alarmRaisedTime")
    private String alarmRaisedTime;

    /**
     * 告警改变时间
     */
    @TableField(value = "alarmChangedTime")
    private String alarmChangedTime;

    /**
     * 告警清除时间
     */
    @TableField(value = "alarmClearedTime")
    private String alarmClearedTime;

    /**
     * 告警确认状态：确认、未确认
     */
    @TableField(value = "ackState")
    private String ackState;

    /**
     * 告警优先级：严重、重要、轻微、警告、不确定、清除
     */
    @TableField(value = "perceivedSeverity")
    private String perceivedSeverity;

    /**
     * 告警事件发现时间
     */
    @TableField(value = "eventTime")
    private String eventTime;

    /**
     * 事件类型
     */
    @TableField(value = "eventType")
    private String eventType;

    /**
     * 故障类型
     */
    @TableField(value = "faultType")
    private String faultType;

    /**
     * 故障发生原因
     */
    @TableField(value = "probableCause")
    private String probableCause;

    /**
     * 是否根故障
     */
    @TableField(value = "sRootCause")
    private String sRootCause;

    /**
     * 关联告警id列表
     */
    @TableField(value = "correlatedAlarmIds")
    private String correlatedAlarmIds;

    /**
     * 告警详细信息列表
     */
    @TableField(value = "faultDetails")
    private String faultDetails;

    /**
     * 服务url
     */
    @TableField(value = "self")
    private String self;

    /**
     * 告警列表url
     */
    @TableField(value = "alarms")
    private String alarms;

    /**
     * 插入时间
     */
    @TableField(value = "dInsertTime")
    private Date dInsertTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_MANAGEDOBJECTID = "managedObjectId";

    public static final String COL_FAULTYNESTEDNSINSTANCEID = "faultyNestedNsInstanceId";

    public static final String COL_FAULTYNSVIRTUALLINKINSTANCEID = "faultyNsVirtualLinkInstanceId";

    public static final String COL_FAULTYVNFINSTANCEID = "faultyVnfInstanceId";

    public static final String COL_VIMID = "vimId";

    public static final String COL_RESOURCEPROVIDERID = "resourceProviderId";

    public static final String COL_RESOURCEID = "resourceId";

    public static final String COL_VIMLEVELRESOURCETYPE = "vimLevelResourceType";

    public static final String COL_FAULTYRESOURCETYPE = "faultyResourceType";

    public static final String COL_ALARMRAISEDTIME = "alarmRaisedTime";

    public static final String COL_ALARMCHANGEDTIME = "alarmChangedTime";

    public static final String COL_ALARMCLEAREDTIME = "alarmClearedTime";

    public static final String COL_ACKSTATE = "ackState";

    public static final String COL_PERCEIVEDSEVERITY = "perceivedSeverity";

    public static final String COL_EVENTTIME = "eventTime";

    public static final String COL_EVENTTYPE = "eventType";

    public static final String COL_FAULTTYPE = "faultType";

    public static final String COL_PROBABLECAUSE = "probableCause";

    public static final String COL_SROOTCAUSE = "sRootCause";

    public static final String COL_CORRELATEDALARMIDS = "correlatedAlarmIds";

    public static final String COL_FAULTDETAILS = "faultDetails";

    public static final String COL_SELF = "self";

    public static final String COL_ALARMS = "alarms";

    public static final String COL_DINSERTTIME = "dInsertTime";
}