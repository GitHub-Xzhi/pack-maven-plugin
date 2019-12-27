package priv.xzhi.globaleye.exception;

import priv.xzhi.globaleye.bean.GlobalResponse;
import priv.xzhi.globaleye.enums.ResultCode;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Desc: 全局异常处理
 * Created by 陈冠志 on 2019-09-29 9:26.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    private WebApplicationContext applicationContext;

    /**
     * 存储所有URL
     */
    private Map<String, String> allUrlMap = new HashMap<>();

    /**
     * 定义捕获的异常
     */
    @ExceptionHandler(CustomException.class)
    public GlobalResponse customExceptionHandler(HttpServletRequest request, CustomException e, HttpServletResponse response) {
        int badRequestCode = HttpStatus.BAD_REQUEST.value();
        response.setStatus(badRequestCode);
		int statusCode = e.getCode() == 0 ? badRequestCode : e.getCode();
        log.error("", e);
        return GlobalResponse.success(statusCode, e.getMessage());
    }

    /**
     * 捕获Exception异常
     */
    @ExceptionHandler(Exception.class)
    public GlobalResponse runtimeExceptionHandler(HttpServletRequest request, Exception e, HttpServletResponse response) {
        int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        response.setStatus(statusCode);
        log.error("", e);
        return GlobalResponse.success(statusCode, "服务端异常：" + e.getMessage());
    }
     
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public GlobalResponse httpRequestMethodNotSupportedException(HttpServletRequest request, Exception e, HttpServletResponse response) {
        int statusCode = HttpStatus.METHOD_NOT_ALLOWED.value();
        response.setStatus(statusCode);
        String requestUrl = (String) request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
        if(StringUtils.isBlank(requestUrl)) {
            requestUrl = request.getRequestURI();
        }
        log.error("", e);
        if(!"GET".equals(request.getMethod()) && StringUtils.isBlank(allUrlMap.get(requestUrl))) {
            return GlobalResponse.success(ResultCode.NOT_FOUND);
        }
		return GlobalResponse.success(statusCode, "该接口不存在" + request.getMethod() + "请求方式");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public GlobalResponse httpMessageNotReadableException(HttpServletRequest request, Exception e, HttpServletResponse response) {
        int statusCode = ResultCode.REQUEST_BODY_NOT_NULL.getCode();
        response.setStatus(statusCode);
        log.error("", e);
		return GlobalResponse.success(statusCode, ResultCode.REQUEST_BODY_NOT_NULL.getMsg());
    }

    @PostConstruct
    private void getAllUrl() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        Set<RequestMappingInfo> requestMappingInfos = map.keySet();
        for (RequestMappingInfo info : requestMappingInfos) {
            Set<String> patterns = info.getPatternsCondition().getPatterns();
            for (String url : patterns) {
                allUrlMap.put(url, "yes");
            }
        }
    }
}
