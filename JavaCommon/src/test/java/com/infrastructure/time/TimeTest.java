package com.infrastructure.time;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeTest
{
    @Test
    public void test() throws Exception
    {
        LocalTime currentTime = LocalTime.now();
        LocalDate currentDate = LocalDate.now();
        LocalDateTime currentDateTime = currentDate.atTime(currentTime);
        System.out.println("currentDateTime: " + currentDateTime);
        System.out.println("year: " + currentDateTime.getYear());
        System.out.println("month: " + currentDateTime.getMonth());
        System.out.println("day: " + currentDateTime.getDayOfMonth());
        System.out.println("day of week: " + currentDateTime.getDayOfWeek());
        System.out.println("hour: " + currentDateTime.getHour());
        System.out.println("minute: " + currentDateTime.getMinute());
        System.out.println("second: " + currentDateTime.getSecond());
    }
}
