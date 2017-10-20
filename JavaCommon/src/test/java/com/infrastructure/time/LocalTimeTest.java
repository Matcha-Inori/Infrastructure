package com.infrastructure.time;

import org.junit.Test;

import java.time.LocalTime;

public class LocalTimeTest
{
    @Test
    public void test() throws Exception
    {
        LocalTime current = LocalTime.of(15, 52, 9);
        System.out.println("current: " + current);
        System.out.println("hour: " + current.getHour());
        System.out.println("minute: " + current.getMinute());
        System.out.println("second: " + current.getSecond());

        System.out.println("==================");

        LocalTime otherTime = LocalTime.parse("12:33:05");
        System.out.println("otherTime: " + otherTime);
        System.out.println("hour: " + otherTime.getHour());
        System.out.println("minute: " + otherTime.getMinute());
        System.out.println("second: " + otherTime.getSecond());

        System.out.println("==================");

        LocalTime now = LocalTime.now();
        System.out.println(now);
    }
}
