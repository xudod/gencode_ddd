package ${basePackageValue}.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtil {
	
	/**
	 * 根据操作数据库时，影响行数得到一boolean结果
	 * 影响行数大于0时返回true
	 * 无影响时返回false
	 */
	public static boolean resultIntToBoolean(int resStatus) {
		boolean res = false;
		if(resStatus > 0) {
			res = true;
		}
		return res;
	}
	
	/**
	 * 返回一个时间日期处理对象
	 */
	public static SimpleDateFormat YMDHMS()
    {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
	
	/**
	 * 返回一个时间日期处理对象
	 */
	public static SimpleDateFormat YMD()
    {
		return new SimpleDateFormat("yyyy-MM-dd");
    }
	
	/**
	 * 返回一个时间日期处理对象
	 */
	public static SimpleDateFormat HMS()
    {
		return new SimpleDateFormat("HH:mm:ss");
    }
	
	/**
     * 功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return long 
     * @author xudod
     */
    public static long getDaySub(String beginDateStr,String endDateStr)
    {
        long day=0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");    
        java.util.Date beginDate;
        java.util.Date endDate;
        try
        {
            beginDate = format.parse(beginDateStr);
            endDate= format.parse(endDateStr);    
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);    
            //System.out.println("相隔的天数="+day);   
        } catch (ParseException e)
        {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }   
        return day;
    }
    
    /**
     * 功能描述：时间相减得到天数
     * @param beginDate
     * @param endDate
     * @author xudod
     */
    public static long getDaySub(Date beginDate,Date endDate)
    {
        long day=0;
        day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);    
        //System.out.println("相隔的天数="+day);   
        return day;
    }
    
    /**
     * 功能描述：时间相减得到分钟数
     * @param beginDate
     * @param endDate
     * @author xudod
     */
    public static int getMinSub(Date beginDate,Date endDate)
    {
    	int min = 0;
    	long a = endDate.getTime() - beginDate.getTime();
    	long b = (a)/(60*1000);
    	if(a > 0 && b == 0) {
    		b = 1;
    	}
        min = (int) (b);
        //System.out.println("相隔的天数="+day);   
        return min;
    }
    
    /**
     * 功能描述：时间相减得到秒数
     * @param beginDate
     * @param endDate
     * @author xudod
     */
    public static int getSecSub(Date beginDate,Date endDate)
    {
    	int sec = 0;
    	long a = endDate.getTime() - beginDate.getTime();
    	long b = (a)/(1000);
        sec = (int) (b); 
        return sec;
    }
    
    /**
     * 功能描述：时间相减得到分钟数,取绝对值并tostring
     * @param beginDate
     * @param endDate
     * @author xudod
     */
    public static String getMinSubToString(Date beginDate,Date endDate)
    {
    	int minSub = getMinSub(beginDate, endDate);
    	String abs = "" + Math.abs(minSub);
        return abs;
    }
    
    /**
     * 日期转日期字符串，yyyy-MM-dd格式
     * @param beginDateStr
     * @param endDateStr
     * @return
     */
    public static String dateToDateStr(Date date)
    {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String dateStr = sdf.format(date);
    	return dateStr;
    }
    
    /**
     * 用考勤数据中的时间和当前时间的日期级别做比较获取比较结果
     * <p>Title: dateCompareNewDate</p>  
     * <p>Description: </p>  
     * @param date
     * @return int
     */
    public static int dateCompareNewDate(Date date) {
    	return date.compareTo(dateStrToDate(dateToDateStr(new Date())));
	}
    
    /**
     * 日期字符串转日期，yyyy-MM-dd格式
     * @param beginDateStr
     * @param endDateStr
     * @return
     */
    public static Date dateStrToDate(String dateStr)
    {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return date;
    }
    
    /**
     * 日期时间字符串转日期时间，yyyy-MM-dd HH:mm:ss格式
     * @param beginDateStr
     * @param endDateStr
     * @return
     */
    public static Date dateStrToDateTime(String dateStr)
    {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return date;
    }
    
    /**
     * 获取当前月的周六天的天数
     * <p>Title: getInMonthRestDay</p>  
     * <p>Description: </p>  
     * @return
     */
    public static int getInMonthRestDay(int month) {
    	Calendar cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        // 获取前月的最后一天  
		int lastDay = cale.getActualMaximum(Calendar.DATE);
        
        Date startTime = CommonUtil.dateStrToDate(year + "-" + month + "-1");
        Date endTime = CommonUtil.dateStrToDate(year + "-" + month + "-" + lastDay);
        
    	long daySub = CommonUtil.getDaySub(startTime, endTime);
		int j = 0;
        cale.setTime(startTime);
		for (int i = 0; i <= daySub; i++) {
			Date date = cale.getTime();
			cale.add(Calendar.DAY_OF_MONTH, 1);
			int dateToWeek = CommonUtil.dateToWeek(date);
        	if(dateToWeek == 0 || dateToWeek == 6) {
        		j++;
        	}
		}
		return j;
	}
    /**
     * 两个日期比对，如果第一个日期大于第二个日期则，第二个日期加一天
     * <p>Title: getNewDate</p>  
     * <p>Description: </p>  
     * @param time1
     * @param time2
     * @return
     */
    public static Date getNewDate(Date time1, Date time2)
    {
    	boolean a = time1.compareTo(time2) > 0;
    	if(a) {
    		time2 = dayAdd(time2, 1);
    	}
    	return time2;
    }
    
    /**
     * 日期加上指定天数
     * <p>Title: dayAddOne</p>  
     * <p>Description: </p>  
     * @param time2 date
     * @param num int
     * @return
     */
    public static Date dayAdd(Date time2, int num) {
		Calendar c = Calendar.getInstance();
		c.setTime(time2);
		c.add(Calendar.DAY_OF_MONTH, num);
		time2 = c.getTime();
		return time2;
	}
    
    /**
     * 日期加上指定天数
     * <p>Title: dayAddOne</p>  
     * <p>Description: </p>  
     * @param time2 date
     * @param num string
     * @return
     */
    public static Date dayAddStr(Date time2, String numStr) {
		int num = Integer.parseInt(numStr);
    	return dayAdd(time2, num);
	}
    
    /**
     * 判断日期是否是同一天
     * @param day1
     * @param day2
     * @return
     */
    public static boolean isSameDay(Date day1, Date day2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String ds1 = sdf.format(day1);
        String ds2 = sdf.format(day2);
        if (ds1.equals(ds2)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 由日期计算周几
     */
    public static int dateToWeek(Date date) {
    	Calendar cal = Calendar.getInstance(); // 获得一个日历
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return w;
    }
    
    /**
     * 判断是否是周六天，是则返回TRUE
     */
    public static boolean weekJudge(Date date) {
    	boolean res = false;
    	int week = dateToWeek(date);
    	if(week == 0 || week == 6) {
    		res = true;
    	}
        return res;
    }
    
    /**
     * 获取某个日期的日期数，仅有日期数值
     * <p>Title: getDay</p>  
     * <p>Description: </p>  
     * @param date
     * @return
     */
    public static int getOnlyDay(Date date) {
    	Calendar cale1 = Calendar.getInstance();
		cale1.setTime(date);
	    int day = cale1.get(Calendar.DATE);
	    return day;
	}
    
    /**
     * 对象转json的string在cotroller层后用
     * @param object
     * @return
     */
    public static String objToJson(Object object) {
    	/**
         * ObjectMapper是JSON操作的核心，Jackson的所有JSON操作都是在ObjectMapper中实现。
         * ObjectMapper有多个JSON序列化的方法，可以把JSON字符串保存File、OutputStream等不同的介质中。
         * writeValue(File arg0, Object arg1)把arg1转成json序列，并保存到arg0文件中。
         * writeValue(OutputStream arg0, Object arg1)把arg1转成json序列，并保存到arg0输出流中。
         * writeValueAsBytes(Object arg0)把arg0转成json序列，并把结果输出成字节数组。
         * writeValueAsString(Object arg0)把arg0转成json序列，并把结果输出成字符串。
         */
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        //User类转JSON
        try {
			json = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return json;
	}
    
    /**
     * 在字符串中通过json的key获取json串中对应的value值
     * @param jsonStr
     * @param jsonKey
     * @return
     */
    public static String getStrJsonVelueByStrJsonKey(String jsonStr, String jsonKey){
    	if(jsonStr.indexOf("ChuChaiTongXingRenYuan_ZSJG") > 0 && (jsonKey.equals("MDMCode") || jsonKey.equals("empMdmCode"))) {
    		jsonKey = "ChuChaiTongXingRenYuan_ZSJG";
    	}
    	String JsonValue = "";
    	if(jsonStr.indexOf("MDMCode") >= 0 || jsonStr.indexOf("empMdmCode") >= 0) {
    		int bpmSerialNumStartIndexOf = jsonStr.indexOf(jsonKey);
    		if(bpmSerialNumStartIndexOf != -1) {
    			int bpmSerialNumEndIndexOf = bpmSerialNumStartIndexOf + jsonKey.length() + 3;
    			int indexOf = jsonStr.indexOf(",",bpmSerialNumEndIndexOf) - 1;
    			if(indexOf <= 0) {
    				indexOf = jsonStr.length() - 3;
    			}
    			JsonValue = jsonStr.substring(bpmSerialNumEndIndexOf,indexOf);
    		}
    	}else {
    		JsonValue = jsonStr;
    	}
        return JsonValue;
    }
    
    /**
     * 根据排班的时间和标记，输出排班的
     * <p>Title: createDate</p>  
     * <p>Description: </p>  
     * @param date
     * @param mark
     * @param time
     * @return
     */
    public static Date createDate(Date date, String mark, String time) {
		SimpleDateFormat sdf1 = CommonUtil.YMDHMS();
		Date dayAddRes = CommonUtil.dayAddStr(date, mark);
		String dateStr = CommonUtil.YMD().format(dayAddRes);
		Date parse = null;
		try {
			 parse = sdf1.parse(dateStr + " " + time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parse;
	}
}
