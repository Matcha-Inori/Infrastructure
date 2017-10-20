package com.infrastructure.time;

import org.junit.Assert;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;

public class WorkTemporalAdjusterTest
{
    @Test
    public void adjustInto() throws Exception
    {
        TemporalAdjuster workTemporalAdjuster = new WorkTemporalAdjuster();
        LocalDate currentDate = LocalDate.now();
        LocalDate resultDate = currentDate.with(workTemporalAdjuster);
        System.out.println(resultDate);
        DayOfWeek resultDateDayOfWeek = resultDate.getDayOfWeek();
        Assert.assertTrue(resultDateDayOfWeek.getValue() < 6);
        Assert.assertTrue(resultDateDayOfWeek.getValue() >= 1);
    }
}