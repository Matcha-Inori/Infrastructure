package com.infrastructure.lambda;

import com.infrastructure.model.User;

public interface ILambdaInterfaceOne
{
    default void methodOne(User user)
    {
        System.out.println("this is method one");
        System.out.println(user);
    }

    void methodTwo(User user);
}
