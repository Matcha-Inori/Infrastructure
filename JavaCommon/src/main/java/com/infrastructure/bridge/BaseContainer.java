package com.infrastructure.bridge;

public class BaseContainer<TYPE> implements IContainer<TYPE>
{
    protected TYPE target;

    public BaseContainer()
    {
    }

    public BaseContainer(TYPE target)
    {
        this.target = target;
    }

    @Override
    public TYPE get()
    {
        return this.target;
    }

    @Override
    public TYPE set(TYPE target)
    {
        TYPE oldTarget = this.target;
        this.target = target;
        return oldTarget;
    }
}
