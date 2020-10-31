package edu.xupt.cs.factory.anno;

import edu.xupt.cs.action.abstract_.BeanDefination;

import java.lang.reflect.Parameter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ActionBeanDefination extends BeanDefination {
    private final List<ParameterInfo> parameterInfos;

    {
        parameterInfos = new LinkedList<>();
    }

    @Override
    public Object[] getValues(Parameter[] parameters, String strValues) {
        ArgumentMaker argumentMaker = new ArgumentMaker(strValues);
        Iterator<ParameterInfo> iter = getParameterInfosIterator();
        Object[] values = new Object[getParameterListSize()];
        for (int i = 0; iter.hasNext(); i++) {
            ParameterInfo parameterInfo = iter.next();
            Object value = argumentMaker.getArgument(parameterInfo.getName(),
                    parameterInfo.getParameter().getParameterizedType());
            values[i] = value;
        }
        return values;
    }

    public void addParameterInfo(ParameterInfo parameterInfo) {
        if (parameterInfos.contains(parameterInfo)) {
            return;
        }
        parameterInfos.add(parameterInfo);
    }

    public void removeParameterInfo(ParameterInfo parameterInfo) {
            parameterInfos.remove(parameterInfo);
    }

    public Iterator<ParameterInfo> getParameterInfosIterator() {
        return parameterInfos.iterator();
    }

    public int getParameterListSize() {
        return parameterInfos.size();
    }


}
