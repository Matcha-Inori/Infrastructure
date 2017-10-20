package com.infrastructure.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class WorkTemporalAdjuster implements TemporalAdjuster
{
    @Override
    public Temporal adjustInto(Temporal temporal)
    {
        LocalDate date = LocalDate.from(temporal);
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        long plus;
        switch(dayOfWeek)
        {
            case MONDAY:
            case THURSDAY:
            case WEDNESDAY:
            case TUESDAY:
            case SUNDAY:
                plus = 1L;
                break;
            case FRIDAY:
            case SATURDAY:
                int dayOfWeekValue = dayOfWeek.getValue();
                plus = 7L - dayOfWeekValue + 1L;
                break;
            default:
                plus = 0L;
                break;
        }
        return temporal.plus(plus, ChronoUnit.DAYS);
    }
}
