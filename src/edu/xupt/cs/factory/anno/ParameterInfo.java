package edu.xupt.cs.factory.anno;

import java.lang.reflect.Parameter;

public class ParameterInfo {
    private String name;
    private Parameter parameter;

    public String getName() {
        return name;
    }

    public ParameterInfo setName(String name) {
        this.name = name;
        return this;
    }

    public Parameter getParameter() {
        return parameter;
    }


    public ParameterInfo setParameter(Parameter parameter) {
        this.parameter = parameter;
        return this;
    }

}
