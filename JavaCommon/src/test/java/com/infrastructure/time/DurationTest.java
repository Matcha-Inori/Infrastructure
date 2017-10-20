package com.infrastructure.time;

import org.junit.Test;

import java.time.*;

public class DurationTest
{
    @Test(expected= DateTimeException.class)
    public void test() throws Exception
    {
        LocalDate birthday = LocalDate.of(1993, 3, 17);
        LocalDate now = LocalDate.now();
        //对两个LocalDate进行between会抛出异常
        try
        {
            Duration duration = Duration.between(now, birthday);
        }
        catch(Throwable throwable)
        {
            throwable.printStackTrace();
            throw throwable;
        }
    }

    @Test
    public void test2() throws Exception
    {
        LocalDateTime birthday = LocalDateTime.of(1993, 3, 17, 8, 15, 17);
        LocalDateTime current = LocalDateTime.now();
        Duration duration = Duration.between(current, birthday);
        System.out.println(duration);
        System.out.println("seconds: " + duration.getSeconds());
    }
}
