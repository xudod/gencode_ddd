package com.xudod.gen_code.common;

public enum ResultCodeEnum {

	SUCCESS(20000,"成功"),
	UNKNOWN_REASON(20001, "未知错误"),
	BAD_SQL_GRAMMAR(21001, "sql语法错误"),
	JSON_PARSE_ERROR(21002, "json解析异常"),
	PARAM_ERROR(21003, "参数不正确"),
	FILE_UPLOAD_ERROR(21004, "文件上传错误"),
	EXCEL_DATA_IMPORT_ERROR(21005, "Excel数据导入错误"),
	INVALID_FORMAT_EXCEPTION(21006, "输入数据格式异常"),
	EXIST_REPEAT_DATA(21007, "存在重复数据"),
	
	BU_NO_ATT_SCHEDULE(60001, "没有排班信息！");

	private Integer code;//返回码
	private String message;//返回消息

	ResultCodeEnum(Integer code, String message) {
		this.setCode(code);
		this.setMessage(message);
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
