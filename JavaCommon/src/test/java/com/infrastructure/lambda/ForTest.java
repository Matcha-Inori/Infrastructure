package com.infrastructure.lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ForTest
{
    /*感觉下面两种方式其实差别不大，可能由于是数组所以耗时都在纳秒级别吧，但是确实是后一种的耗时要更少*/

    @Test
    public void testOne() throws Exception
    {
        List<Long> timeList = new ArrayList<>();
        for(int count = 0;count < 3;count++)
        {
            int[] array = new int[]{1, 2, 3, 4, 5, 6 ,7 ,8 ,9 ,10};
            long start = System.nanoTime();
            for(int index = 0;index < array.length;index++)
            {
                array[index] += 1;
            }

            for(int index = 0;index < array.length;index++)
            {
                array[index] *= 2;
            }
            long end = System.nanoTime();
            timeList.add(end - start);
        }

        Optional<Long> allTimeOptional = timeList.stream().reduce((result, time) -> result + time);
        allTimeOptional.ifPresent(allTime -> {
            System.out.println(allTime);
            System.out.println(allTime / 3);
        });
    }

    @Test
    public void testTwo() throws Exception
    {
        List<Long> timeList = new ArrayList<>();
        for(int count = 0;count < 3;count++)
        {
            int[] array = new int[]{1, 2, 3, 4, 5, 6 ,7 ,8 ,9 ,10};
            long start = System.nanoTime();
            for(int index = 0;index < array.length;index++)
            {
                array[index] += 1;
                array[index] *= 2;
            }
            long end = System.nanoTime();
            timeList.add(end - start);
        }

        long allTime = timeList.stream().mapToLong(time -> time).sum();
        System.out.println(allTime);
        timeList.stream().mapToLong(time -> time).average().ifPresent(System.out::println);
    }

    @Test
    public void testThird() throws Exception
    {
        List<Long> timeList = new ArrayList<>();
        for(int count = 0;count < 3;count++)
        {
            int[] array = new int[]{1, 2, 3, 4, 5, 6 ,7 ,8 ,9 ,10};
            long start = System.nanoTime();
            array = Arrays.stream(array).map(num -> {
                num += 1;
                num *= 2;
                return num;
            }).toArray();
            long end = System.nanoTime();
            timeList.add(end - start);
        }

        Optional<Long> allTimeOptional = timeList.stream().reduce((result, time) -> result + time);
        allTimeOptional.ifPresent(allTime -> {
            System.out.println(allTime);
            System.out.println(allTime / 3);
        });
    }
}
