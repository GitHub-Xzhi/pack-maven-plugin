package priv.xzhi.globaleye.intercept;

import priv.xzhi.globaleye.enums.ResultCode;
import priv.xzhi.globaleye.exception.CustomException;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Desc: NfvoAlarm拦截器
 * Created by 陈冠志 on 2019-09-29 15:05.
 */
public class NfvoAlarmInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String queryString = request.getMethod();
        if(request.getInputStream() != null) {
            System.out.println("拦截放行");
            return true;
        }
        throw new CustomException(ResultCode.PARAM_NOT_NULL);
    }

//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//    }
}
