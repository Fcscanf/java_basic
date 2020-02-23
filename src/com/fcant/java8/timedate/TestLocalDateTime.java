package com.fcant.java8.timedate;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

/**
 * TestLocalDateTime
 * <p>
 * encoding:UTF-8
 *
 * @author Fcant 下午 19:22:26 2020/2/23/0023
 */
public class TestLocalDateTime {

    // ZoneDate、ZoneTime、ZoneDateTime
    @Test
    public void zoneDateTimeTest() {
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        availableZoneIds.forEach(System.out::println);

        // 根据指定时区获取当前时间
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Africa/Nairobi"));
        System.out.println(now);

        // 获取时间并为之指定时区
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("US/Pacific"));
        System.out.println(zonedDateTime);
    }

    // DateTimeFormatter:格式化时间日期
    @Test
    public void dateTimeFormatterTest() {
        // 使用官方提供的格式
        DateTimeFormatter ISO_DATE = DateTimeFormatter.ISO_DATE;
        LocalDateTime now = LocalDateTime.now();

        String formatDate = now.format(ISO_DATE);
        System.out.println(formatDate);

        // 自定义格式
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        String selfDate = dateTimeFormatter.format(now);
        System.out.println(selfDate);

        // 字符串日期转回日期类型
        LocalDateTime parseDate = now.parse(selfDate, dateTimeFormatter);
        System.out.println(parseDate);
    }


    // TemporalAdjuster ： 时间校正器
    @Test
    public void temporalAdjusterTest() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        LocalDateTime localDateTime = now.withDayOfMonth(10);
        System.out.println(localDateTime);

        LocalDateTime friday = now.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        System.out.println(friday);

        // 自定义：下一个工作日
        LocalDateTime nextWorkDay = now.with((d) -> {
            LocalDateTime dateTime = (LocalDateTime) d;
            DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
            if (dayOfWeek.equals(DayOfWeek.FRIDAY)) {
                return dateTime.plusDays(3);
            } else if (dateTime.equals(DayOfWeek.FRIDAY)) {
                return dateTime.plusDays(2);
            } else {
                return dateTime.plusDays(1);
            }
        });
        System.out.println(nextWorkDay);
    }

    /**
     * 3.时间计算
     * Duration:计算两个时间的时间差
     * Period:计算两个日期之间的差
     */
    @Test
    public void calTimeTest() throws InterruptedException {
        System.out.println("------------计算两个时间之间的间隔-----------");
        Instant now = Instant.now();
        Thread.sleep(1000);
        Instant end = Instant.now();
        Duration duration = Duration.between(now, end);
        System.out.println(duration.toMillis());

        System.out.println("--------------------------");
        LocalTime localTime = LocalTime.now();
        Thread.sleep(1000);
        LocalTime l = LocalTime.now();
        Duration between = Duration.between(localTime, l);
        System.out.println(between.toMillis());
        System.out.println("------------计算两个日期之间的间隔-----------");

        LocalDate localDate = LocalDate.of(2025, 6, 12);
        LocalDate date = LocalDate.now();
        Period period = Period.between(date, localDate);
        System.out.println(period.getYears());
        System.out.println(period.getMonths());
        System.out.println(period.getDays());
    }

    // 2.Instant:时间戳（以Unix元年：1970年1月1日 00:00:00 到某个时间之间的毫秒值
    @Test
    public void instantTest() {
        // 默认获取UTC时区
        Instant instant = Instant.now();
        System.out.println(instant);

        // 在原来的时间上加8个小时
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.ofHours(8));
        System.out.println(offsetDateTime);

        System.out.println(instant.toEpochMilli());
        // 在Unix元年时间戳加60秒
        Instant ofEpochSecond = Instant.ofEpochSecond(60);
        System.out.println(ofEpochSecond);
    }

    // 1.LocalDateTime案例演示
    @Test
    public void localDateTimeTest() {
        // 获取当前时间
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        // 生成指定的时间日期
        LocalDateTime dateTime = LocalDateTime.of(2025, 10, 19, 13, 22, 45);
        System.out.println(dateTime);

        LocalDateTime plusYears = localDateTime.plusYears(3);
        System.out.println(plusYears);

        LocalDateTime minusMonths = localDateTime.minusMonths(2);
        System.out.println(minusMonths);

        System.out.println(localDateTime.getYear());
        System.out.println(localDateTime.getMonthValue());
        System.out.println(localDateTime.getDayOfMonth());
        System.out.println(localDateTime.getHour());
        System.out.println(localDateTime.getMinute());
        System.out.println(localDateTime.getSecond());
    }
}
