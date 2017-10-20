package com.infrastructure.lambda;

import org.junit.Test;

import java.util.Spliterator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.Consumer;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

public class ForkJoinTest
{
    @Test
    public void test() throws Exception
    {
        ForkJoinPool forkJoinPool = null;
        try
        {
            long start = System.currentTimeMillis();
            forkJoinPool = new ForkJoinPool();
            SumTask sumTask = new SumTask(0, 1000000);
            forkJoinPool.submit(sumTask);
            Long sum = sumTask.get();
            long end = System.currentTimeMillis();
            System.out.println(sum);
            System.out.println(end - start);
        }
        finally
        {
            if(null != forkJoinPool)
                forkJoinPool.shutdown();
        }
    }

    @Test
    public void testTwo() throws Exception
    {
        long start = System.currentTimeMillis();
        Spliterator<Long> splIterator = new SumSplIterator(0, 1000000);
        StreamSupport.stream(splIterator, true).reduce(Long::sum).ifPresent(System.out::println);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    @Test
    public void testThree() throws Exception
    {
        long start = System.currentTimeMillis();
        long sum = LongStream.rangeClosed(0, 1000000).sum();
        System.out.println(sum);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}

class SumSplIterator implements Spliterator<Long>
{
    private static final long THRESHOLD;

    static
    {
        THRESHOLD = 20;
    }

    private long start;
    private long end;

    private long offset;

    public SumSplIterator(long start, long end)
    {
        this.start = start;
        this.end = end;
        this.offset = 0;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Long> action)
    {
        long num = start + (offset++);
        action.accept(num);
        long nextNum = start + offset;
        return nextNum <= end;
    }

    @Override
    public Spliterator<Long> trySplit()
    {
        long currentNum = start + offset;
        long step = end - currentNum;
        if(step <= THRESHOLD)
            return null;
        long sum = end + currentNum;
        long half = sum / 2;
        Spliterator<Long> splIterator = new SumSplIterator(half + 1, end);
        start = currentNum;
        end = half;
        offset = 0;
        return splIterator;
    }

    @Override
    public long estimateSize()
    {
        long currentNum = start + offset;
        return end - currentNum + 1;
    }

    @Override
    public int characteristics()
    {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }
}

class SumTask extends RecursiveTask<Long>
{
    private static final long THRESHOLD;

    static
    {
        THRESHOLD = 20;
    }

    private long start;
    private long end;

    public SumTask(long start, long end)
    {
        if(end < start)
            throw new IllegalArgumentException("end can not smaller than start");

        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute()
    {
        long step = end - start;
        if(step <= THRESHOLD)
            return doCompute();

        long sum = end + start;
        long half = sum / 2;

        SumTask subSumTask = new SumTask(start, half);
        SumTask otherSubSumTask = new SumTask(half + 1, end);

        subSumTask.fork();
        otherSubSumTask.fork();

        Long subSum = subSumTask.join();
        Long otherSubSum = otherSubSumTask.join();
        return otherSubSum + subSum;
    }

    private Long doCompute()
    {
        return LongStream.rangeClosed(start, end).sum();
    }
}
