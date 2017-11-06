package com.infrastructure.bridge;

import java.util.concurrent.atomic.AtomicStampedReference;

public class StringContainer implements IContainer<String>
{
    private AtomicStampedReference<String> targetReference;

    public StringContainer()
    {
        this(null);
    }

    public StringContainer(String target)
    {
        this.targetReference = new AtomicStampedReference<>(target, 0);
    }

    @Override
    public String get()
    {
        return this.targetReference.getReference();
    }

    @Override
    public String set(String target)
    {
        int[] stampHolder = new int[1];
        String oldTarget;
        int stamp;
        do
        {
            oldTarget = this.targetReference.get(stampHolder);
            stamp = stampHolder[0];
        } while(!this.targetReference.compareAndSet(oldTarget, target, stamp, stamp + 1));
        return oldTarget;
    }
}
