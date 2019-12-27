package priv.xzhi.globaleye.exception;

import priv.xzhi.globaleye.bean.GlobalResponse;
import priv.xzhi.globaleye.enums.ResultCode;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Desc: 404异常控制器
 * Created by 陈冠志 on 2019-09-29 12:42.
 */
@RestController
public class ExceptionController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @GetMapping("/error")
    public GlobalResponse error() {
        return GlobalResponse.success(ResultCode.NOT_FOUND);
    }
}
