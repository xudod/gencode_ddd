package ${basePackageValue}.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusinessException extends RuntimeException {
	
    /** serialVersionUID*/  
	private static final long serialVersionUID = 1L;
	
    private Logger logger = LoggerFactory.getLogger(BusinessException.class);
	
	private Integer code;

	/**
	 * 接收状态码和错误消息
	 * @param code
	 * @param message
	 */
	public BusinessException(Integer code, String message){
		super(message);
		this.code = code;
	}

	public BusinessException(ResultCodeEnum resultCodeEnum){
		super(resultCodeEnum.getMessage());
		this.code = resultCodeEnum.getCode();
	}

	@Override
	public String toString() {
		return "GuliException{" +
				"code=" + code +
				", message=" + this.getMessage() +
				'}';
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
	
}
