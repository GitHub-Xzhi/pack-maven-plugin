package priv.xzhi.globaleye.config;

import priv.xzhi.globaleye.intercept.NfvoAlarmInterceptor;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Desc: 程序配置
 * Created by 陈冠志 on 2019-09-29 15:16.
 */
//@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new NfvoAlarmInterceptor()).addPathPatterns("/**");
    }
}
