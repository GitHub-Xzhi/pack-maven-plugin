package priv.xzhi.globaleye.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "t_alarm_event")
public class TAlarmEvent implements Serializable {
     @TableId(value = "i_id", type = IdType.AUTO)
    private Integer iId;

    @TableField(value = "d_insert_time")
    private Date dInsertTime;

    @TableField(value = "s_notification_id")
    private String sNotificationId;

    @TableField(value = "s_vd_alarm_id")
    private String sVdAlarmId;

    @TableField(value = "s_vendor_object_name")
    private String sVendorObjectName;

    @TableField(value = "s_object_name")
    private String sObjectName;

    @TableField(value = "i_object_type")
    private Integer iObjectType;

    @TableField(value = "d_ne_time")
    private Date dNeTime;

    @TableField(value = "d_ems_time")
    private Date dEmsTime;

    @TableField(value = "i_perceived_severity")
    private Integer iPerceivedSeverity;

    @TableField(value = "s_probable_cause")
    private String sProbableCause;

    @TableField(value = "s_detail_probable_cause")
    private String sDetailProbableCause;

    @TableField(value = "s_alarm_code")
    private String sAlarmCode;

    @TableField(value = "i_event_type")
    private Integer iEventType;

    @TableField(value = "s_additional_text")
    private String sAdditionalText;

    @TableField(value = "s_additional_info")
    private String sAdditionalInfo;

    @TableField(value = "s_extensions")
    private String sExtensions;

    private static final long serialVersionUID = 1L;

    public static final String COL_D_INSERT_TIME = "d_insert_time";

    public static final String COL_S_NOTIFICATION_ID = "s_notification_id";

    public static final String COL_S_VD_ALARM_ID = "s_vd_alarm_id";

    public static final String COL_S_VENDOR_OBJECT_NAME = "s_vendor_object_name";

    public static final String COL_S_OBJECT_NAME = "s_object_name";

    public static final String COL_I_OBJECT_TYPE = "i_object_type";

    public static final String COL_D_NE_TIME = "d_ne_time";

    public static final String COL_D_EMS_TIME = "d_ems_time";

    public static final String COL_I_PERCEIVED_SEVERITY = "i_perceived_severity";

    public static final String COL_S_PROBABLE_CAUSE = "s_probable_cause";

    public static final String COL_S_DETAIL_PROBABLE_CAUSE = "s_detail_probable_cause";

    public static final String COL_S_ALARM_CODE = "s_alarm_code";

    public static final String COL_I_EVENT_TYPE = "i_event_type";

    public static final String COL_S_ADDITIONAL_TEXT = "s_additional_text";

    public static final String COL_S_ADDITIONAL_INFO = "s_additional_info";

    public static final String COL_S_EXTENSIONS = "s_extensions";
}