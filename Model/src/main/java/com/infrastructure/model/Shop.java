package com.infrastructure.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Shop
{
    private String name;
    private Map<String, Double> priceMap;

    private ReadWriteLock readWriteLock;
    private Lock readLock;
    private Lock writeLock;

    public Shop(String name)
    {
        this(name, new HashMap<>());
    }

    public Shop(String name, Map<String, Double> priceMap)
    {
        this.name = name;
        this.priceMap = priceMap;
        init();
    }

    private void init()
    {
        this.readWriteLock = new ReentrantReadWriteLock(false);
        this.readLock = this.readWriteLock.readLock();
        this.writeLock = this.readWriteLock.writeLock();
    }

    private void delay()
    {
        delay(1L, TimeUnit.SECONDS);
    }

    private void delay(long time, TimeUnit timeUnit)
    {
        LockSupport.parkNanos(timeUnit.toNanos(time));
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Optional<Double> getPrice(String product)
    {
        delay();
        this.readLock.lock();
        try
        {
            Double price = this.priceMap.get(product);
            return Optional.ofNullable(price);
        }
        finally
        {
            this.readLock.unlock();
        }
    }

    public Shop registerProduct(String product, double price)
    {
        this.writeLock.lock();
        try
        {
            this.priceMap.put(product, price);
            return this;
        }
        finally
        {
            this.writeLock.unlock();
        }
    }
}
