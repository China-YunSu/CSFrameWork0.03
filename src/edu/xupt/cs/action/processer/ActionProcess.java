package edu.xupt.cs.action.processer;


import edu.xupt.cs.action.abstract_.ActionBeanFactory;
import edu.xupt.cs.action.abstract_.BeanDefination;
import edu.xupt.cs.action.abstract_.IActionProcess;
import edu.xupt.cs.factory.anno.ArgumentMaker;
import edu.xupt.cs.factory.anno.NoSuchActionException;

import java.lang.reflect.Method;

public class ActionProcess implements IActionProcess {
    private ActionBeanFactory factory;

    public ActionProcess(ActionBeanFactory factory) {
        this.factory = factory;
    }

    @Override
    public Object dealRequst(String action, String paramter) throws Exception {

        BeanDefination abd = factory.getAction(action);
        if (abd == null) {
            throw new NoSuchActionException("不找不到 " + action );
        }
        Method method = abd.getMethod();
        Object object = abd.getObject();
        Object[] values = abd.getValues(method.getParameters(),paramter);
        return method.invoke(object, values);
    }

    @Override
    public void dealResponse(String action, String paramter) throws Exception {
        BeanDefination abd = factory.getAction(action);
        if (abd == null) {
            throw new NoSuchActionException("不找不到 " + action );
        }
        Method method = abd.getMethod();
        Object object = abd.getObject();
        Object value = ArgumentMaker.fromJson(paramter,method.getParameters()[0].getParameterizedType());
        method.invoke(abd.getObject(),value);
    }
}
