package com.xudod.gencode;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xudod.gencode.data.GenCodeInfo;
import com.xudod.gencode.service.GenCodeService;

@RunWith(SpringJUnit4ClassRunner.class) 
public class DateTest {
	
	
	/**
     * 日期转星期
     * 
     * @param datetime  "2017-01-01"
     * @return
     */
	@Test
    public void dateToWeek() {
    	
    	String str = "d:/aaa/bbb/ccc";
    	char ch = '/';
    	int length = str.split("/").length;
    	int lastIndexOf = str.lastIndexOf(ch);
        int index1 = str.indexOf(String.valueOf(ch));
        int index2 = str.indexOf(String.valueOf(ch), length);  //如果没有加一的话，2个参数是两都相同，不小于包含等于
        String s = str.substring(0, lastIndexOf);
        System.out.println(s);
    }
	
	
	
	
}