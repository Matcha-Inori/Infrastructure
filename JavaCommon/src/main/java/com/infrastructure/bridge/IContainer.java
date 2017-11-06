package com.infrastructure.bridge;

public interface IContainer<TYPE>
{
    TYPE get();
    TYPE set(TYPE target);
}
