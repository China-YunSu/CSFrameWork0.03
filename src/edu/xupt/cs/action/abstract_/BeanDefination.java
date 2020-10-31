package edu.xupt.cs.action.abstract_;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public abstract class BeanDefination {
    protected Method method;
    protected Object object;
    protected Class<?> klass;

    protected Class<?> getKlass() {
        return klass;
    }

    public void setKlass(Class<?> klass) {
        this.klass = klass;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getObject() {
        return object;
    }

    public BeanDefination setObject(Object object) {
        this.object = object;
        return this;
    }

    public abstract Object[] getValues(Parameter[] parameters,String strValues);
 }
