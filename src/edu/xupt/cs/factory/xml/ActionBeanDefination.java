package edu.xupt.cs.factory.xml;

import edu.xupt.cs.action.abstract_.BeanDefination;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class ActionBeanDefination extends BeanDefination {
    private final List<String> paraNames = new ArrayList<>();
    private int index = 0;

    public void addParaName(String name) {
        if (!paraNames.contains(name)) {
            paraNames.add(name);
        }
    }

    public boolean hasNext() {
        boolean hasNext = index < paraNames.size();
        if (!hasNext) {
            index = 0;
        }

        return hasNext;
    }

    public String next() {
        return paraNames.get(index++);
    }

    @Override
    public Object[] getValues(Parameter[] parameters,String strValues) {
        ArgumentMaker argumentMaker = new ArgumentMaker(strValues);
        Object[] values = new Object[parameters.length];
        for (int i = 0; hasNext(); i++) {
            String argumentName = next();
            values[i] = argumentMaker.getArgument(argumentName, parameters[i].getParameterizedType());
        }

        return values;
    }

}
