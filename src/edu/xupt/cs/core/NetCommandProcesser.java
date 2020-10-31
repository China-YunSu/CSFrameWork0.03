package edu.xupt.cs.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NetCommandProcesser {

    public static void dealNetMessage(Object object,NetMessage netMessage) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        ECammand cammand = netMessage.getCammand();
        String[] subStrings = cammand.name().split("_");
        StringBuffer methodName = new StringBuffer("deal");
        for (String subString : subStrings) {
            methodName.append(subString.substring(0,1))
                        .append(subString.substring(1).toLowerCase());
        }
        Method method = object.getClass().getDeclaredMethod(methodName.toString(), NetMessage.class);
        method.invoke(object,netMessage);
    }
}
