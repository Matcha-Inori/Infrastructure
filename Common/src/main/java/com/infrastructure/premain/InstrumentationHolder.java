package com.infrastructure.premain;

import java.lang.instrument.Instrumentation;

public class InstrumentationHolder
{
    private static volatile InstrumentationHolder instance;

    public static InstrumentationHolder getInstance()
    {
        if(null == instance)
            createInstance();
        return instance;
    }

    private static synchronized void createInstance()
    {
        if(null != instance) return;
        instance = new InstrumentationHolder();
    }

    public static void premain(String agentArgs, Instrumentation inst)
    {
        InstrumentationHolder instrumentationHolder = InstrumentationHolder.getInstance();
        instrumentationHolder.setInstrumentation(inst);
    }

    private Instrumentation instrumentation;

    private InstrumentationHolder()
    {
        this.instrumentation = null;
    }

    public Instrumentation getInstrumentation()
    {
        return instrumentation;
    }

    private void setInstrumentation(Instrumentation instrumentation)
    {
        this.instrumentation = instrumentation;
    }
}
