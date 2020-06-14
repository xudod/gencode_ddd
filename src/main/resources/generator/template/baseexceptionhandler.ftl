package ${basePackageValue}.config;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alibaba.fastjson.JSONObject;
import com.cgr.common.exception.BusinessException;
import com.cgr.common.result.BaseResp;

@RestControllerAdvice
public class BaseExceptionHandler {
	
    private Logger logger = LoggerFactory.getLogger(BaseExceptionHandler.class);
	
	/*代码分界head TODO*/
	
    /**
     * 所有验证框架异常捕获处理
     * @return
     */
    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    public BaseResp<?> validationExceptionHandler(Exception exception) {
        BindingResult bindResult = null;
        if (exception instanceof BindException) {
            bindResult = ((BindException) exception).getBindingResult();
        } else if (exception instanceof MethodArgumentNotValidException) {
            bindResult = ((MethodArgumentNotValidException) exception).getBindingResult();
        }
        String msg;
        if (bindResult != null && bindResult.hasErrors()) {
            msg = bindResult.getAllErrors().get(0).getDefaultMessage();
            if (msg.contains("NumberFormatException")) {
                msg = "参数类型错误！";
            }
        }else {
            msg = "系统繁忙，请稍后重试...";
        }
        return new BaseResp<Object>(445, msg, null);
    }
 
    /**
     * 业务异常
     * @param exception
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public BaseResp<?> allBusinessExceptionHandler(BusinessException exception) {
        return new BaseResp<Object>(exception.getCode(), exception.getMsg(), null);
    }
 
    /**
     * 未知的异常捕获处理
     * @param exception
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public BaseResp<?> allUnknowExceptionHandler(HttpServletRequest request, Exception exception) {
        String error = logError(request, exception);
        //可以发送邮件通知开发
        return new BaseResp<Object>(445, error, null);
    }
 
    private String logError(HttpServletRequest request, Exception exception) {
    	logger.error("发生未知异常:", exception);
        StringWriter sw = new StringWriter();
        sw.append(String.format("Date:{%s};\n", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())));
        sw.append(String.format("url:{%s}产生错误;\n", request.getRequestURI()));
        sw.append(String.format("请求IP:{%s};\n", request.getRemoteAddr()));
        sw.append(String.format("type:{%s};\n", request.getMethod()));
        sw.append(String.format("请求参数:{%s};\n", JSONObject.toJSONString(request.getParameterMap())));
        exception.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
	
	/*代码分界end TODO*/
	
}

