package com.infrastructure.unsafe;

import com.infrastructure.model.User;
import com.infrastructure.premain.InstrumentationHolder;
import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;

public class UnsafeTest
{
    private Unsafe unsafe;

    public UnsafeTest()
    {
        try
        {
            Class<Unsafe> unsafeClass = Unsafe.class;
            Field field = unsafeClass.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            this.unsafe = (Unsafe) field.get(null);
        }
        catch (IllegalAccessException | NoSuchFieldException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test() throws Exception
    {
        int intArrayBaseOffset = this.unsafe.arrayBaseOffset(int[].class);
        int intArrayIndexScale = this.unsafe.arrayIndexScale(int[].class);

        System.out.println("==========");
        System.out.println("intArrayBaseOffset: " + intArrayBaseOffset);
        System.out.println("intArrayIndexScale: " + intArrayIndexScale);
        System.out.println("==========");

        int userArrayBaseOffset = this.unsafe.arrayBaseOffset(User[].class);
        int userArrayIndexScale = this.unsafe.arrayIndexScale(User[].class);

        System.out.println("==========");
        System.out.println("userArrayBaseOffset: " + userArrayBaseOffset);
        System.out.println("userArrayIndexScale: " + userArrayIndexScale);
        System.out.println("==========");

        int integerArrayBaseOffset = this.unsafe.arrayBaseOffset(Integer[].class);
        int integerArrayIndexScale = this.unsafe.arrayIndexScale(Integer[].class);

        System.out.println("==========");
        System.out.println("integerArrayBaseOffset: " + integerArrayBaseOffset);
        System.out.println("integerArrayIndexScale: " + integerArrayIndexScale);
        System.out.println("==========");

        InstrumentationHolder instrumentationHolder = InstrumentationHolder.getInstance();
        Instrumentation instrumentation = instrumentationHolder.getInstrumentation();

        Object object = new Object();
        long objectSize = instrumentation.getObjectSize(object);

        System.out.println("==========");
        System.out.println("objectSize: " + objectSize);
        System.out.println("==========");

        Object[] objectArray = new Object[]{new Object(), new Object(), new Object()};
        long objectArraySize = instrumentation.getObjectSize(objectArray);

        System.out.println("==========");
        System.out.println("objectArraySize: " + objectArraySize);
        System.out.println("==========");

        int[] intArray = new int[]{1, 2, 3};
        long intArraySize = instrumentation.getObjectSize(intArray);

        System.out.println("==========");
        System.out.println("intArraySize: " + intArraySize);
        System.out.println("==========");
    }
}
