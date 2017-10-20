package com.infrastructure.time;

import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

public class LocalDateTest
{
    @Test
    public void test() throws Exception
    {
        LocalDate birthday = LocalDate.of(1993, 3, 17);
        System.out.println("birthday: " + birthday);
        System.out.println("year: " + birthday.getYear());
        System.out.println("month: " + birthday.getMonth());
        System.out.println("day: " + birthday.getDayOfMonth());
        System.out.println("day of week: " + birthday.getDayOfWeek());
        System.out.println("length of year: " + birthday.lengthOfYear());
        System.out.println("length of month: " + birthday.lengthOfMonth());
        System.out.println("is leap year: " + birthday.isLeapYear());

        System.out.println("===============");

        LocalDate now = LocalDate.now();
        System.out.println("now: " + now);
        System.out.println("year: " + now.get(ChronoField.YEAR));
        System.out.println("month: " + now.get(ChronoField.MONTH_OF_YEAR));
        System.out.println("day: " + now.get(ChronoField.DAY_OF_MONTH));
        System.out.println("day of week: " + now.get(ChronoField.DAY_OF_WEEK));
        System.out.println("day of year: " + now.get(ChronoField.DAY_OF_YEAR));
    }

    @Test
    public void change() throws Exception
    {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        LocalDate newLocalDate = localDate.withYear(2020);
        System.out.println(newLocalDate);
    }
}
