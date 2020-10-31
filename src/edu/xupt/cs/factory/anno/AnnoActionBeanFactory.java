package edu.xupt.cs.factory.anno;

import com.mec.util.PackageScan;
import edu.xupt.cs.action.abstract_.ActionBeanFactory;
import edu.xupt.cs.action.abstract_.BeanDefination;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class AnnoActionBeanFactory extends ActionBeanFactory {

    public BeanDefination getAction(String action) {
        BeanDefination beanDefination = actionPool.get(action);
        return beanDefination;
    }

    @Override
    public void scannActionMapping(String path) {
        try {
            new PackageScan() {
                @Override
                protected void dealClass(Class<?> klass) {
                    try {
                        if (klass.isAnnotationPresent(AActionClass.class)) {
                            Object object = klass.newInstance();
                            Method[] methods = klass.getDeclaredMethods();
                            for (Method method : methods) {
                                if (method.isAnnotationPresent(AActionMethod.class)) {
                                    ActionBeanDefination abd = new ActionBeanDefination();
                                    abd.setKlass(klass);
                                    abd.setMethod(method);
                                    abd.setObject(object);
                                    String action = method.getAnnotation(AActionMethod.class).action();
                                    Parameter[] parameters = method.getParameters();
                                    for (Parameter parameter : parameters) {
                                        if (parameter.isAnnotationPresent(AActionType.class)) {
                                            String name = parameter.getAnnotation(AActionType.class).parameterName();
                                            ParameterInfo parameterInfo = new ParameterInfo().
                                                    setName(name).
                                                    setParameter(parameter);
                                            abd.addParameterInfo(parameterInfo);
                                        } else {
                                            try {
                                                throw new ParameterAnnoExption("参数未注解名字");
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    actionPool.put(action, abd);
                                }
                            }
                        }
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }.scanPackage(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
