package priv.xzhi.globaleye;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.net.Inet4Address;
import java.net.InetAddress;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication(scanBasePackages = "priv.xzhi.globaleye.*")
@MapperScan("priv.xzhi.globaleye.dao")
@PropertySource("file:${user.dir}/config/conf.properties")
public class GlobalEyeApplication
{
    @Value("${server.port}")
    private  String serverPort;

	public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(GlobalEyeApplication.class, args);
        GlobalEyeApplication application = applicationContext.getBean(GlobalEyeApplication.class);
//        application.startShutDownHook();
        application.execute();
    }


    private void execute() {
		String swaggerUrl = "swagger: http://" + getLocalHost() + ":" + serverPort + "/swagger-ui.html";
		log.info(swaggerUrl);
        System.out.println(swaggerUrl);
    }

    /**
     * 获取本地IP
     */
    private String getLocalHost() {
        try {
            InetAddress localHost = Inet4Address.getLocalHost();
            return localHost.getHostAddress();
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    private void startShutDownHook() {
        log.info("程序启动钩子函数监听功能...");
        //注册一个新的虚拟机关闭钩子
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                log.info("开始执行钩子函数的任务...");
            } catch (Exception e) {
                log.error("钩子函数执行处理任务时操作异常...");
            }
            log.info("钩子函数执行完毕，系统已完全关闭！");
        }));
        log.info("程序启动钩子函数监听功能完毕");
    }
}
