package com.fcant.java8.timedate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateFormatThreadLocal
 * <p>
 * encoding:UTF-8
 *
 * @author Fcant 下午 18:03:53 2020/2/23/0023
 */
public class DateFormatThreadLocal {
    public static final ThreadLocal<DateFormat> DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<DateFormat>(){
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("YYYY-mm-dd");
        }
    };

    public static Date convert(String source) throws ParseException {
        return DATE_FORMAT_THREAD_LOCAL.get().parse(source);
    }
}
