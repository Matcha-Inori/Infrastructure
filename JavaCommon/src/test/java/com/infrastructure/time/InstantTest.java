package com.infrastructure.time;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class InstantTest
{
    @Test
    public void test() throws Exception
    {
        long currentMillis = System.currentTimeMillis();
        Instant instant = Instant.ofEpochSecond(TimeUnit.MILLISECONDS.toSeconds(currentMillis));
        Instant current = Instant.now();
        System.out.println(instant);
        System.out.println(current);
    }

    @Test
    public void change() throws Exception
    {
        Instant now = Instant.now();
        System.out.println(now);
        Instant newInstant = now.plus(Duration.of(10, ChronoUnit.HOURS));
        System.out.println(newInstant);
        ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
        System.out.println(zonedDateTime);
    }
}
