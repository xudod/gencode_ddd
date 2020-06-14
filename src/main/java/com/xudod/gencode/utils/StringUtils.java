package com.xudod.gencode.utils;

import com.google.common.base.CaseFormat;

/**
 * 字符串操作常用方法集 Created by zhh on 2017/08/16.
 */
public class StringUtils {


    /**
     * 4个空格串
     */
    public static final String SPACES_4 = "    ";
    
    /**
     * 8个空格串
     */
    public static final String SPACES_8 = SPACES_4 + SPACES_4;
    
    /**
     * 12个空格串
     */
    public static final String SPACES_12 = SPACES_4 + SPACES_4 + SPACES_4;
    
    /**
     * 换行
     */
    public static final String END_HR = "\n";

    /**
     * 对象是否为无效值
     * 
     * @param obj 要判断的对象
     * @return 是否为有效值(不为null 和 ""字符串)
     */
    public static boolean isNullOrEmpty(Object obj) {
        return obj == null || "".equals(obj.toString());
    }

    /**
     * 将字符串的第一位转为小写
     * 
     * @param str 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String toLowerCaseFirstOne(String str) {
        if (Character.isLowerCase(str.charAt(0))) {
            return str;
        } else {
            char[] chars = str.toCharArray();
            chars[0] = Character.toLowerCase(chars[0]);
            return new String(chars);
        }
    }

    /**
     * 将字符串的第一位转为大写
     * 
     * @param str 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String toUpperCaseFirstOne(String str) {
        if (Character.isUpperCase(str.charAt(0))) {
            return str;
        } else {
            char[] chars = str.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            return new String(chars);
        }
    }
    /**
     * Convert table name with or without underline to camel style object name.The first character is lower case<br>
     * For example:<br>
     * table to table<br>
     * my_table to myTable<br>
     * @param tableName
     * @return camel style object name with first charater lower case
     */
    public static String tableNameConvertLowerCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName.toLowerCase());
    }
    /**
     * Convert table name with or without underline to camel style object name. .The first character is lower case<br>
     * 将带有或不带下划线的表名转换为camel样式对象名。 第一个字符是小写字母
     * For example:<br>
     * table to Table<br>
     * my_table to MyTable<br>
     * @param tableName
     * @return camel style object name
     */
    public static String tableNameConvertUpperCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());
    }
    /**
     * 判断给入的字符串是否为空,null、""、" "都表示空字符串
     * 
     * @param str 待判定的字符串
     * @return 空符串返回true，否则返回false
     */
    public static boolean isEmpty(String str) {
        if (null == str || "".equals(str.trim())) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
