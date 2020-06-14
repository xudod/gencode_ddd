package com.xudod.gen_code.common;

import org.springframework.util.DigestUtils;

public class CreatePassWord {
	
	/**
     * 加密方法
     * @param src
     * @return
     */
	private static String md5(String src) {
        return DigestUtils.md5DigestAsHex(src.getBytes());
    }
    
    /**
     * salt
     */
	private static final String salt = "z8rTy91";
	
	public static String getPassWord(String str) {
		int length = str.length();
		return md5(str + length + salt);
	}
	
}
