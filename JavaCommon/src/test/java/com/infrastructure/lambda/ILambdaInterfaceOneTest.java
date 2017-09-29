package com.infrastructure.lambda;

import com.infrastructure.model.User;
import com.infrastructure.test.BaseTest;
import org.junit.Test;

public class ILambdaInterfaceOneTest extends BaseTest
{
    @Test
    public void methodOne() throws Exception
    {
        ILambdaInterfaceOne lambdaInterfaceOne = (User user) -> System.out.println(user);
        Class<? extends ILambdaInterfaceOne> lambdaInterfaceOneClass = lambdaInterfaceOne.getClass();
        System.out.println(lambdaInterfaceOneClass);

        //ILambdaInterfaceTwo是无法用lambda表达式的，因为里面有多个抽象方法，lambda表达式可以被当作接口使用，但是其他没有实现的方法
        //会有问题，所以不能这么用
//        ILambdaInterfaceTwo lambdaInterfaceTwo =
//                (User userOne, User userTwo) -> Integer.valueOf(userOne.getAge()).compareTo(userTwo.getAge());

        String printStr = "用户是：";
        lambdaInterfaceOne = (User user) -> System.out.println(printStr + user);
        User user = new User(1L, 22, "Riven");
        lambdaInterfaceOne.methodOne(user);
        lambdaInterfaceOne.methodTwo(user);
        //没有这种语法
//        ((User paramUser) -> System.out.println("用户" + paramUser + "为所欲为")) (user);
    }
}