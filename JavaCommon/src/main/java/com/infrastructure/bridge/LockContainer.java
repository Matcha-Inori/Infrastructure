package com.infrastructure.bridge;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockContainer<TYPE> extends BaseContainer<TYPE>
{
    private ReadWriteLock lock;
    private Lock readLock;
    private Lock writeLock;

    public LockContainer()
    {
        this(null);
    }

    public LockContainer(TYPE target)
    {
        super(target);
        this.lock = new ReentrantReadWriteLock(false);
        this.readLock = this.lock.readLock();
        this.writeLock = this.lock.writeLock();
    }

    @Override
    public TYPE get()
    {
        this.readLock.lock();
        try
        {
            return super.get();
        }
        finally
        {
            this.readLock.unlock();
        }
    }

    @Override
    public TYPE set(TYPE target)
    {
        this.writeLock.lock();
        try
        {
            return super.set(target);
        }
        finally
        {
            this.writeLock.unlock();
        }
    }
}
