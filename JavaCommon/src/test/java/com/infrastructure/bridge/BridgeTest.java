package com.infrastructure.bridge;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class BridgeTest
{
    @Test
    public void test() throws Exception
    {
        IContainer<String> stringContainer = new StringContainer("A");
        String strA = stringContainer.get();
        Assert.assertEquals(strA, "A");
        String oldStr = stringContainer.set("B");
        Assert.assertEquals(oldStr, "A");
        String strB = stringContainer.get();
        Assert.assertEquals(strB, "B");

        //这样编译不通过
//        stringContainer.set(new Object());
    }

    @Test(expected = ClassCastException.class)
    public void test2() throws Exception
    {
        IContainer stringContainer = new StringContainer("A");
        try
        {
            //下面这个虽然不会编译报错，但是运行期会报错
            //堆栈如下
            //java.lang.ClassCastException: java.base/java.lang.Object cannot be cast to java.base/java.lang.String
            //  at com.infrastructure.bridge.StringContainer.set(StringContainer.java:5)
            //  at com.infrastructure.bridge.BridgeTest.test2(BridgeTest.java:30)
            //可以看到是StringContainer的第五行，这个应该是调用了桥接方法
            stringContainer.set(new Object());
        }
        catch (ClassCastException exception)
        {
            exception.printStackTrace();
            throw exception;
        }
    }

    @Test
    public void testInvoke() throws Exception
    {
        IContainer<String> stringContainer = new StringContainer("A");

        Class<StringContainer> stringContainerClass = StringContainer.class;
        Method[] stringContainerMethods = stringContainerClass.getDeclaredMethods();
        //这里可以看到，其实StringContainer中声明的有四个方法，有两个参数和返回值是Object类型的就是桥接方法
        Arrays.stream(stringContainerMethods).forEach(System.out::println);

        //找到返回值类型为Object的get方法
        Optional<Method> getBridgeMethodOptional = Arrays.stream(stringContainerMethods)
                .filter(method -> "get".equals(method.getName()) && Object.class.equals(method.getReturnType()))
                .findAny();
        Assert.assertTrue(getBridgeMethodOptional.isPresent());
        Method getBridgeMethod = getBridgeMethodOptional.get();
        Assert.assertTrue(getBridgeMethod.isBridge());
        Assert.assertEquals("A", getBridgeMethod.invoke(stringContainer));

        //找到参数类型为String类型的set方法
        Method setMethod = stringContainerClass.getDeclaredMethod("set", Object.class);
        Assert.assertTrue(setMethod.isBridge());
        Assert.assertEquals("A", setMethod.invoke(stringContainer, "B"));
        //下面这个方法会抛出类型转换异常
        //java.lang.reflect.InvocationTargetException
        //  at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        //  at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        //  ...
        //Caused by: java.lang.ClassCastException: java.base/java.lang.Object cannot be cast to java.base/java.lang.String
        //  at com.infrastructure.bridge.StringContainer.set(StringContainer.java:5)
        //  ...
//        setMethod.invoke(stringContainer, new Object());

        //通过javap命令反编译StringContainer类，可以得到document/StringContainerP.txt中的结果，可以看到
        //编译器增加了两个方法，Object get()和Object set(Object)
        //Object set(Object)方法会首先校验参数类型是否是String，然后会调用String set(String)方法

        Assert.assertEquals("B", stringContainer.get());
    }
}
