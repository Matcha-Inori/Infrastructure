package com.infrastructure.defaultmethod;

public class ClassC implements InterfaceA, InterfaceC
{
    @Override
    public void method(String info)
    {
        System.out.println("ClassC");
        InterfaceA.super.method(info);
        InterfaceC.super.method(info);
    }
}
