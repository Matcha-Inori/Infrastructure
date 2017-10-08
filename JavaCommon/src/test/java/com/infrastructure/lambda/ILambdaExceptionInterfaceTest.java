package com.infrastructure.lambda;

import com.infrastructure.exception.BaseRuntimeException;
import com.infrastructure.test.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ILambdaExceptionInterfaceTest extends BaseTest
{
    private static final Logger logger = LoggerFactory.getLogger(ILambdaExceptionInterface.class);

    @Test
    public void throwsException() throws Exception
    {
        ILambdaExceptionInterface iLambdaExceptionInterface = () -> {throw new BaseRuntimeException();};
        try
        {
            iLambdaExceptionInterface.throwsException();
        }
        catch(BaseRuntimeException exception)
        {
            logger.error("ILambdaExceptionInterfaceTest: ", exception);
        }

        logger.info("--------");

        iLambdaExceptionInterface = () -> System.out.println("not throw exception");
        iLambdaExceptionInterface.throwsException();

        ILambdaNotExceptionInterface iLambdaNotExceptionInterface = () -> System.out.println("not throw exception");
        iLambdaNotExceptionInterface.notThrowException();

        try
        {
            iLambdaNotExceptionInterface = () -> {throw new BaseRuntimeException();};
            iLambdaNotExceptionInterface.notThrowException();
        }
        catch (BaseRuntimeException exception)
        {
            logger.error("ILambdaNotExceptionInterfaceTest: ", exception);
        }

        //下面这一句编译不过的
        /*iLambdaNotExceptionInterface = () -> {throw new BaseException();};*/
    }
}