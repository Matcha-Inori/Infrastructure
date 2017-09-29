package com.infrastructure.lambda;

import com.infrastructure.exception.BaseRuntimeException;

@FunctionalInterface
public interface ILambdaExceptionInterface
{
    void throwsException() throws BaseRuntimeException;
}
