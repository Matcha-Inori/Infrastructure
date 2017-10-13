package com.infrastructure.defaultmethod;

public interface InterfaceC
{
    default void method(String info)
    {
        String interfaceName = InterfaceC.class.getName();
        String message = String.format("%s - %s", interfaceName, info);
        System.out.println(message);
    }
}
