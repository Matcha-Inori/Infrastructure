package com.infrastructure.lambda;

import com.infrastructure.model.User;

public interface ILambdaInterfaceTwo extends ILambdaInterfaceOne
{
    int methodThread(User userOne, User userTwo);
}
