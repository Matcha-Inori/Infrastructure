package com.infrastructure.time;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Period;

public class PeriodTest
{
    @Test
    public void test() throws Exception
    {
        LocalDate birthday = LocalDate.of(1993, 3, 17);
        LocalDate now = LocalDate.now();
        Period period = Period.between(now, birthday);
        System.out.println(period);
        System.out.println("years: " + period.getYears());
        System.out.println("month: " + period.getMonths());
        System.out.println("day: " + period.getDays());
        System.out.println("units: " + period.getUnits());
    }

    @Test
    public void test2() throws Exception
    {
        LocalDate now = LocalDate.now();
        LocalDate plusLocalDate = now.plus(Period.of(10, 2, 20));
        System.out.println(plusLocalDate);
    }
}
