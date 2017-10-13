package com.infrastructure.defaultmethod;

public interface InterfaceB extends InterfaceA
{
    @Override
    default void method(String info)
    {
        String interfaceName = InterfaceB.class.getName();
        String message = String.format("%s - %s", interfaceName, info);
        System.out.println(message);
    }
}
