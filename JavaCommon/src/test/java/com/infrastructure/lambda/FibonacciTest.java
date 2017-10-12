package com.infrastructure.lambda;

import org.junit.Test;

import java.util.Random;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FibonacciTest
{
    @Test
    public void test() throws Exception
    {
        Stream.iterate(new int[]{1, 1}, array -> new int[]{array[1], array[0] + array[1]})
                .limit(20)
                .map(array -> array[1])
                .forEach(System.out::println);
    }

    @Test
    public void test2() throws Exception
    {
        IntStream.generate(new IntSupplier()
        {
            private int firstValue = 0;
            private int secondValue = 1;

            @Override
            public int getAsInt()
            {
                int value = firstValue + secondValue;
                firstValue = secondValue;
                secondValue = value;
                return value;
            }
        }).limit(20).forEach(System.out::println);

        System.out.println("=========");

        IntStream.generate(new IntSupplier()
        {
            private int firstValue = 0;
            private int secondValue = 1;

            @Override
            public int getAsInt()
            {
                int value = firstValue + secondValue;
                firstValue = secondValue;
                secondValue = value;
                return value;
            }
        }).parallel()
                .limit(20)/*对于无限流排序无效.sorted()*/
                .forEach(System.out::println);
    }

    @Test
    public void testGenerate() throws Exception
    {
        Random random = new Random();
        Stream.generate(random::nextInt)
                .limit(30)
                .forEach(System.out::println);
    }
}
