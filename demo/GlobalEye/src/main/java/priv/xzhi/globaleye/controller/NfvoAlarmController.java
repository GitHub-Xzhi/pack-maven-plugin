package priv.xzhi.globaleye.controller;

import priv.xzhi.globaleye.bean.AlarmJson;
import priv.xzhi.globaleye.bean.GlobalResponse;
import priv.xzhi.globaleye.service.NfvoAlarmService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Desc:控制层
 * Created by 陈冠志 on 2019-09-27 15:47.
 */
@Slf4j(topic = "srcAlarm")
@RestController
@Api(tags = "Nfvo告警采集相关接口")
public class NfvoAlarmController {

	@Autowired
	private NfvoAlarmService nfvoAlarmService;

	@Autowired
	private ObjectMapper objectMapper;

	@ApiOperation(value = "原始告警采集")
	@ApiImplicitParam(name = "alarmJson", value = "原始告警对象json格式", required = true)
	@PostMapping("/srcAlarm")
	public GlobalResponse getSrcAlarm(HttpServletRequest request) {
		String bodyData = getBodyData(request);
		log.info("原始告警：\n{}", bodyData);
		try {
			AlarmJson alarmJson = objectMapper.readValue(bodyData, AlarmJson.class);
			nfvoAlarmService.inserSrcAlarm(alarmJson);
			return GlobalResponse.success();
		} catch (Exception e) {
			log.error("json转换异常", e);
			return GlobalResponse.failure("json转换异常：" + e.toString());
		}
	}

	/**
	 * 获取请求体数据
	 */
	private String getBodyData(HttpServletRequest request) {
		StringBuilder data = new StringBuilder();
		String line = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
			while (null != (line = reader.readLine())) {
				data.append(line);
			}
		} catch (IOException e) {
			log.error("", e);
		}
		return data.toString();
	}

}
