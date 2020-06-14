package com.xudod.gen_code.common;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {

    public static final String DATE_FORMAT     = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME        = "yyyyMMddHHmmss";

    
    /**
     * 获取指定月的第一天的日期
     * <p>Title: getMonthFirstDate</p>  
     * <p>Description: </p>  
     * @return
     */
    public static Date getMonthFirstDate(String monthStr) {
        Calendar cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
    	Date dateStrToDate = CommonUtil.dateStrToDate(year + "-" + monthStr + "-01");
    	return dateStrToDate;
	}
    
    /**
     * 获取当前月的第一天的日期
     * <p>Title: getMonthFirstDate</p>  
     * <p>Description: </p>  
     * @return
     */
    public static Date getMonthFirstDate() {
        Calendar cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;
    	Date dateStrToDate = CommonUtil.dateStrToDate(year + "-" + month + "-01");
    	return dateStrToDate;
	}
    /**
     * 获取指定月的第一天的日期
     * <p>Title: getMonthFirstDate</p>  
     * <p>Description: </p>  
     * @return
     */
    public static Date getMonthLastDate(String monthStr) {
        Calendar cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
    	// 获取某月最大天数
    	int lastDay = cale.getActualMaximum(Calendar.DATE);
    	Date dateStrToDate = CommonUtil.dateStrToDate(year + "-" + monthStr + "-" + lastDay);
    	return dateStrToDate;
	}
    
    /**
     * 获取当前月的第一天的日期
     * <p>Title: getMonthFirstDate</p>  
     * <p>Description: </p>  
     * @return
     */
    public static Date getMonthLastDate() {
        Calendar cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;
    	// 获取某月最大天数
    	int lastDay = cale.getActualMaximum(Calendar.DATE);
    	Date dateStrToDate = CommonUtil.dateStrToDate(year + "-" + month + "-" + lastDay);
    	return dateStrToDate;
	}
    
    

	
    /**
     * 计算两个日期之间相差的天数
     * 
     * @param smdate 较小的时间
     * @param bdate 较大的时间
     * @return 相差天数
     * @throws ParseException
     * @throws Exception
     */
    public static int daysBetween(Date smdate, Date bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return new BigDecimal(String.valueOf(between_days)).abs().intValue();
    }

    /**
     * @description 将时间字符串转化为Date
     * @author Anan
     * @time 2013年7月26日 下午7:50:32
     * @param time 时间字符串
     * @param formatStr 时间格式 如"2013-7-26 19:52:47"、"2013-7-26"
     * @return
     */
    public static Date toDate(String time, String formatStr) {
        Date date = null;
        DateFormat dateFormat = new SimpleDateFormat(formatStr);
        try {
            date = dateFormat.parse(time);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date toDatebyday(String time, String formatStr) {
        Date date = null;
        DateFormat dateFormat = new SimpleDateFormat(formatStr, Locale.ENGLISH);
        try {
            date = dateFormat.parse(time);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String toDatebydaytoString(String time, String formatStr) throws java.text.ParseException {
        Date date = null;
        String dateString = "";
        DateFormat dateFormat = new SimpleDateFormat(formatStr, Locale.ENGLISH);

        date = dateFormat.parse(time);
        dateString = formateDate(date);

        return dateString;
    }

    public static Date toDatebytime(Date time, String formatStr) throws java.text.ParseException {
        Date date = null;
        String dateString = "";
        //DateFormat dateFormat = new SimpleDateFormat(formatStr, Locale.ENGLISH);

        dateString = formateDate(time);
        date = toDate(dateString);

        return date;
    }

    /**
     * @description 将日期转化为字符串
     * @author Anan
     * @time 2013年7月30日 下午4:32:30
     * @param date
     * @param formatStr
     * @return
     */
    public static String toString(Date date, String formatStr) {
        if (null == date || StringUtil.isEmpty(formatStr)) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(date);
    }

    /**
     * @description 将日期转化为字符串
     * @author Anan
     * @time 2013年7月30日 下午4:32:30
     * @param date
     * @param formatStr
     * @return
     */
    public static String toString(Date date) {
        if (null == date) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
    
    /**
     * @description 将年月日转化为日期
     * @author Anan
     * @time 2013年7月30日 下午5:00:33
     * @param year
     * @param month
     * @param day
     * @return
     * @throws java.text.ParseException
     */
    public static Date toDate(int year, int month, int day) throws java.text.ParseException {
        Date date = null;
        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.YEAR, year);
        calender.set(Calendar.MONTH, month - 1);
        calender.set(Calendar.DATE, day);
        calender.set(Calendar.HOUR_OF_DAY, 0);
        calender.set(Calendar.MINUTE, 0);
        calender.set(Calendar.SECOND, 0);
        date = calender.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = sdf.parse(sdf.format(date));
        return date;
    }

    /**
     * @description 结束日期属于开始日期后的第几个月的日期
     * @author Anan
     * @time 2013年8月27日 下午10:00:33
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    public static int monthsFromStartDate(Date startDate, Date endDate) {
        int result = 0;
        Date temp = null;
        startDate = toDate(toString(startDate, "yyyy-MM-dd"), "yyyy-MM-dd");
        endDate = toDate(toString(endDate, "yyyy-MM-dd"), "yyyy-MM-dd");
        // 开始日期 大于 结束日期 两个日期互换 例如： startDate 2013-05-21 endDate = 2013-04-20
        if (startDate.after(endDate)) {
            temp = startDate;
            startDate = endDate;
            endDate = temp;
        }
        Date tempEndDate1 = null;
        Date tempEndDate2 = null;
        int a = getDayOfMonth(startDate);
        int b = getDayOfMonth(endDate);
        int c = a - b;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(startDate);
        c2.setTime(endDate);
        c2.set(Calendar.DAY_OF_MONTH, a);
        tempEndDate2 = c2.getTime();
        int i = 0;
        while (true) {
            tempEndDate1 = addToMonth(startDate, i);
            if (tempEndDate1.compareTo(tempEndDate2) == 0) {
                result = i;
                break;
            }
            i++;
            if (i == 999999999) {// 防止死循环
                break;
            }
        }
        if (c < 0) {
            result = result + 1;
        }
        return result;
    }

    /**
     * 获取开始时间与结束时间之间间隔的月数
     * 
     * @author yansong
     * @param startDate
     * @param endDate
     * @return
     */
    public static int monthsBetween(Date startDate, Date endDate) {
        int iMonth = 0;
        try {
            Calendar objCalendarDateStart = Calendar.getInstance();
            objCalendarDateStart.setTime(startDate);
            Calendar objCalendarDateEnd = Calendar.getInstance();
            objCalendarDateEnd.setTime(endDate);
            if (objCalendarDateEnd.equals(objCalendarDateStart) || objCalendarDateStart.after(objCalendarDateEnd)) {
                return 0;
            } else {
                if (objCalendarDateEnd.get(Calendar.YEAR) > objCalendarDateStart.get(Calendar.YEAR)) {
                    iMonth = (objCalendarDateEnd.get(Calendar.YEAR) - objCalendarDateStart.get(Calendar.YEAR)) * 12
                             + objCalendarDateEnd.get(Calendar.MONTH) - objCalendarDateStart.get(Calendar.MONTH);
                } else {
                    iMonth = objCalendarDateEnd.get(Calendar.MONTH) - objCalendarDateStart.get(Calendar.MONTH);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return iMonth;
    }

    /**
     * 获取输入日期所在月份的第一天
     * 
     * @author yansong
     * @param date
     * @return
     */
    public static Date getFristDateForCurrentMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(GregorianCalendar.DAY_OF_MONTH, 1);

        return cal.getTime();
    }

    /**
     * 获取输入日期所在月份的最后一天
     * 
     * @author yansong
     * @param date
     * @return
     */
    public static Date getLastDateForCurrentMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.DATE, 1);
        cal.roll(Calendar.DATE, -1);

        return cal.getTime();
    }

    /**
     * @description 获取某年某月的第一天
     * @author Anan
     * @time 2013年7月30日 下午4:27:53
     * @param year 某年
     * @param month 某月
     * @return
     */
    public static Date getMonthBegin(int year, int month) {
        Date _month_begin = null;
        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.YEAR, year);
        calender.set(Calendar.MONTH, month - 1);
        calender.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        calender.set(Calendar.HOUR_OF_DAY, 0);
        calender.set(Calendar.MINUTE, 0);
        calender.set(Calendar.SECOND, 0);
        _month_begin = calender.getTime();
        return _month_begin;
    }

    /**
     * @description 获取某年某月的最后一天
     * @author Anan
     * @time 2013年7月30日 下午4:28:59
     * @param year 某年
     * @param month 某月
     * @return
     */
    public static Date getMonthEnd(int year, int month) {
        Date month_end = null;
        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.YEAR, year);
        calender.set(Calendar.MONTH, month - 1);
        calender.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        calender.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        calender.set(Calendar.HOUR_OF_DAY, 0);
        calender.set(Calendar.MINUTE, 0);
        calender.set(Calendar.SECOND, 0);
        month_end = calender.getTime();
        return month_end;
    }

    /**
     * @description 得到指定月的天数
     * @author Anan
     * @time 2013年7月30日 下午4:48:00
     * @param year 某年
     * @param month 某月
     * @return
     */
    public static int getMonthLastDay(int year, int month) {
        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.YEAR, year);
        calender.set(Calendar.MONTH, month - 1);
        calender.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        calender.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        int maxDate = calender.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * @description 得到当前日期月的天数
     * @author Anan
     * @time 2013年9月1日 下午1:01:44
     * @param date
     * @return
     */
    public static int getMonthLastDay(Date date) {
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        calender.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        calender.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        int maxDate = calender.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * @description 得到日期中的月份
     * @author William
     * @time 2013年10月24日 下午1:01:44
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    /**
     * @description 当月的第几天
     * @author Anan
     * @time 2013年8月22日 下午9:24:30
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * @description 获得当前日期 + N个月 之后的日期
     * @author Anan
     * @time 2013年8月23日 上午12:26:53
     * @param oldDate
     * @param n
     * @return
     */
    public static Date addToMonth(Date oldDate, int n) {
        Date newDate = null;
        Calendar calOld = Calendar.getInstance();
        calOld.setTime(oldDate);
        int month = calOld.get(Calendar.MONTH);
        Calendar calNew = Calendar.getInstance();
        calNew.setTime(oldDate);
        calNew.set(Calendar.MONTH, n + month);
        newDate = calNew.getTime();
        return newDate;
    }

    /**
     * @description 获得当前日期 减去 N月 之后的日期
     * @author Anan
     * @time 2013年9月1日 上午12:26:53
     * @param oldDate
     * @param n
     * @return
     */
    public static Date removeMonths(Date oldDate, int n) {
        Date newDate = null;
        Calendar calOld = Calendar.getInstance();
        calOld.setTime(oldDate);
        int month = calOld.get(Calendar.MONTH);
        Calendar calNew = Calendar.getInstance();
        calNew.setTime(oldDate);
        calNew.set(Calendar.MONTH, month - n);
        newDate = calNew.getTime();
        return newDate;
    }

    /**
     * @description 获得当前日期 减去 N天 之后的日期
     * @author Anan
     * @time 2013年8月23日 上午12:26:53
     * @param oldDate
     * @param n
     * @return
     */
    public static Date removeDays(Date oldDate, int n) {
        Date newDate = null;
        Calendar calOld = Calendar.getInstance();
        calOld.setTime(oldDate);
        int day = calOld.get(Calendar.DAY_OF_YEAR);
        Calendar calNew = Calendar.getInstance();
        calNew.setTime(oldDate);
        calNew.set(Calendar.DAY_OF_YEAR, day - n);
        newDate = calNew.getTime();
        return newDate;
    }

    /**
     * @description 获得当前日期 加上 N天 之后的日期
     * @author Anan
     * @time 2013年8月23日 上午12:26:53
     * @param oldDate
     * @param n
     * @return
     */
    public static Date addDays(Date oldDate, int n) {
        Date newDate = null;
        Calendar calOld = Calendar.getInstance();
        calOld.setTime(oldDate);
        int day = calOld.get(Calendar.DAY_OF_YEAR);
        Calendar calNew = Calendar.getInstance();
        calNew.setTime(oldDate);
        calNew.set(Calendar.DAY_OF_YEAR, day + n);
        newDate = calNew.getTime();
        return newDate;
    }

    /**
     * @description 获取两个年份之间的差值
     * @author Anan
     * @time 2013年8月23日 上午2:28:29
     * @param startDate
     * @param endDate
     * @return
     */
    public static int yearsBetween(Date startDate, Date endDate) {
        int iYears = 0;
        Calendar calS = Calendar.getInstance();
        calS.setTime(startDate);
        Calendar calE = Calendar.getInstance();
        calE.setTime(endDate);
        int i = startDate.compareTo(endDate);
        if (i == 1) {
            iYears = calS.get(Calendar.YEAR) - calE.get(Calendar.YEAR);
        } else if (i == -1) {
            iYears = calE.get(Calendar.YEAR) - calS.get(Calendar.YEAR);
        }
        return iYears;
    }

    /**
     * @param date 日期
     * @param offset 偏移量，0为周日 单位为日
     * @return WeekOfYear
     */
    public static int getWeekOfYear(Date date, int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime() - offset * 24 * 3600 * 1000L);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    // public static void main(String[] args) {
    // Date now = toDate("2013-1-12", "yyyy-MM-dd");
    // System.out.println(DateUtil.toString(DateUtil.addDays(now, 2),"yyyy-MM-dd"));
    // }

    /**
     * 标准格式化date
     * 
     * @param date
     * @return
     */
    public static String formateDate(Date date) {
        if (date == null) {
            return StringUtil.EMPTY_STRING;
        }

        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }

    /**
     * 标准格式化datetime
     * 
     * @param date
     * @return
     */
    public static String formateDatetime(Date date) {
        if (date == null) {
            return StringUtil.EMPTY_STRING;
        }

        return new SimpleDateFormat(DATETIME_FORMAT).format(date);
    }

    /**
     * 按照"yyyy-MM-dd"的格式转换日期字符串为Date类型
     * 
     * @param dateStr 日期字符串
     * @return
     */
    public static Date toDate(String dateStr) {
        Date date = null;
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            date = dateFormat.parse(dateStr);
        } catch (java.text.ParseException e) {
            return null;
        }
        return date;
    }

    public static Date toDateTimes(String dateStr) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String result = null;
        Date date = null;
        try {
            Date strToDate = sdf.parse(dateStr);
            result = toString(strToDate, DATETIME_FORMAT);
            date = toDate(result, DATETIME_FORMAT);
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;

    }

    public static String toDateTimeCompara(String dateStr) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String result = null;

        try {
            Date strToDate = sdf.parse(dateStr);
            result = toString(strToDate, DATETIME_FORMAT);

        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;

    }

    /**
     * 按照"yyyy-MM-dd HH:mm:ss"的格式转换日期时间字符串为Date类型
     * 
     * @param dateTimeStr 日期时间字符串
     * @return
     */
    public static Date toDateTime(String dateTimeStr) {
        Date date = null;
        DateFormat dateFormat = new SimpleDateFormat(DATETIME_FORMAT);
        try {
            date = dateFormat.parse(dateTimeStr);
        } catch (java.text.ParseException e) {
            return null;
        }
        return date;
    }

    public static String transferLongToDate(Long millSec) {

        SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT);

        Date date = new Date(millSec);

        return sdf.format(date);

    }
    
    /**
     * 判断时间是否在两个时间中间
     * <p>Title: timeInTwoTimeMiddle</p>  
     * <p>Description: </p>  
     * @param judgeTime
     * @param sTime
     * @param eTime
     * @return
     */
    public static boolean timeInTwoTimeMiddle(Date judgeTime, Date sTime, Date eTime) {
		boolean res = false;
		boolean after = judgeTime.after(sTime);
		boolean before = judgeTime.before(eTime);
		if(after && before) {
			res = true;
		}
		return res;
	}
    
    /**
     * 判断时间是否在两个时间中间
     * <p>Title: timeInTwoTimeMiddle</p>  
     * <p>Description: </p>  
     * @param judgeTime
     * @param sTime
     * @param eTime
     * @return
     */
    public static boolean timeInTwoTimeMiddle(Date judgeTime, String sTime, String eTime) {
    	boolean res = false;
    	boolean after = judgeTime.after(DateUtil.toDateTime(sTime));
		boolean before = judgeTime.before(DateUtil.toDateTime(eTime));
		if(after && before) {
			res = true;
		}
		return res;
	}

    /**
     * 日期加上指定分钟数，如果是负数则为减去
     * <p>Title: dayAddOne</p>  
     * <p>Description: </p>  
     * @param time2 date
     * @param num int
     * @return
     */
    public static Date minuteAdd(Date time, int num) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(Calendar.MINUTE, num);
		time = c.getTime();
		return time;
	}
    
    /**
     * 日期加上指定分钟数，如果是负数则为减去
     * <p>Title: dayAddOne</p>  
     * <p>Description: </p>  
     * @param time2 date
     * @param num int
     * @return
     */
    public static Date minuteAdd(String timeStr, String num) {
		Calendar c = Calendar.getInstance();
		c.setTime(toDateTime(timeStr));
		c.add(Calendar.MINUTE, Integer.parseInt(num));
		Date time = c.getTime();
		return time;
	}
    
    /**
     * 校验日期格式是否满足yyyyMMddHHmmss这种格式
     * 
     * @param time
     * @return
     */
    public static boolean checkValidDate(String time) {
        boolean ret = true;
        try {
            int year = new Integer(time.substring(0, 4)).intValue();
            int month = new Integer(time.substring(4, 6)).intValue();
            int date = new Integer(time.substring(6, 8)).intValue();
            int hourOfDay = new Integer(time.substring(8, 10)).intValue();
            int minute = new Integer(time.substring(10, 12)).intValue();
            int second = new Integer(time.substring(12, 14)).intValue();
            Calendar cal = Calendar.getInstance();
            cal.setLenient(false); // 允许严格检查日期格式
            cal.set(year, month - 1, date);
            cal.set(year, month - 1, date, hourOfDay, minute, second);
            cal.getTime();// 该方法调用就会抛出异常
        } catch (Exception e) {
            e.printStackTrace();
            ret = false;
        }
        return ret;
    }

//    public static void main(String[] args) {
//        String format = "20150819202020";
//        String datestr = "09090909090909";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//        System.out.println("456" + checkValidDate(datestr));
//        System.out.println("789" + toDateTimes(datestr));
//        System.out.println("123" + toString(toDateTimes(datestr), DATETIME_FORMAT));
//        try {
//            Date strToDate = sdf.parse(format);
//            String result = toString(strToDate, DATETIME_FORMAT);
//            System.out.println("strToDate" + strToDate);
//            System.out.println("strToDate" + result);
//
//        } catch (java.text.ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }

    /**
     * 获得指定日期的后一天
     * 
     * @param specifiedDay
     * @return
     */
    public static Date getSpecifiedDayAfter(Date date) {
        Calendar c = Calendar.getInstance();

        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        Date newdate = toDate(dayAfter);
        return newdate;
    }
}
