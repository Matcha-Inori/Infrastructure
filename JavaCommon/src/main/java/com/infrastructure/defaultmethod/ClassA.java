package com.infrastructure.defaultmethod;

public class ClassA extends ClassB implements InterfaceC
{
    @Override
    public void method(String info)
    {
        //这个地方必须显示的重写函数。否则编译不过
        System.out.println("ClassA");
        InterfaceC.super.method(info);
        //这个编译不过
//        ClassB.super.method(info);
        //这个也编译不过
//        InterfaceA.super.method(info);
        super.method(info);
    }
}
