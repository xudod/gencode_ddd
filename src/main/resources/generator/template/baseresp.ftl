package ${basePackageValue}.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "返回实体")
public class BaseResp<T> {
    /**
     * 返回码
     */
	@ApiModelProperty(example = "将返回状态码")
    private int code;

    /**
     * 返回信息描述
     */
	@ApiModelProperty(example = "将返回状态码说明")
    private String message;

    /**
     * 返回数据
     */
	@ApiModelProperty(example = "将返回请求要得到的数据")
    private T data;

    /**
     * 时间戳
     */
	@ApiModelProperty(example = "将返回时间戳")
    private long currentTime;
    
	public static <T> BaseResp<T> returnRes(int code, T data, String msg) {
		BaseResp<T> rtn = new BaseResp<T>();
	    rtn.setCode(code);
	    rtn.setMessage(msg);
	    rtn.setData(data);
	    rtn.setCurrentTime(System.currentTimeMillis());
	    return rtn;
	}
    
    public static <T> BaseResp<T> returnResByEnum(ResultCodeEnum resultCodeEnum) {
    	BaseResp<T> rtn = new BaseResp<T>();
	    rtn.setCode(resultCodeEnum.getCode());
	    rtn.setMessage(resultCodeEnum.getMessage());
	    rtn.setCurrentTime(System.currentTimeMillis());
	    return rtn;
	}
	
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

}