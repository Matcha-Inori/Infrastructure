package com.infrastructure.defaultmethod;

public interface InterfaceA
{
    default void method(String info)
    {
        String interfaceName = InterfaceA.class.getName();
        String message = String.format("%s - %s", interfaceName, info);
        System.out.println(message);
    }
}
