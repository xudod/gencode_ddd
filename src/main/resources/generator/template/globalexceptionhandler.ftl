package ${basePackageValue}.common;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class GlobalExceptionHandler<T> {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public BaseResp<T> error(Exception e) {
		e.printStackTrace();
		return BaseResp.returnRes(100001, null, "");
	}
	
	@ExceptionHandler(BadSqlGrammarException.class)
	@ResponseBody
	public BaseResp<T> error(BadSqlGrammarException e) {
		e.printStackTrace();
		return BaseResp.returnResByEnum(ResultCodeEnum.BAD_SQL_GRAMMAR);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	public BaseResp<T> error(HttpMessageNotReadableException e) {
		e.printStackTrace();
		return BaseResp.returnResByEnum(ResultCodeEnum.JSON_PARSE_ERROR);
	}
	
	@ExceptionHandler(InvalidFormatException.class)
	@ResponseBody
	public BaseResp<T> error(InvalidFormatException e) {
		e.printStackTrace();
		return BaseResp.returnResByEnum(ResultCodeEnum.INVALID_FORMAT_EXCEPTION);
	}
	
	@ExceptionHandler(BusinessException.class)
	@ResponseBody
	public BaseResp<T> error(BusinessException e) {
		e.printStackTrace();
		return BaseResp.returnRes(e.getCode(), null, e.getMessage());
	}
	
}
