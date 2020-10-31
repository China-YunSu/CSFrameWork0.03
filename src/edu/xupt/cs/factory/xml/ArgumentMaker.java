package edu.xupt.cs.factory.xml;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ArgumentMaker {
    private static final Gson gson = new GsonBuilder().create();
    private static final Type type = new TypeToken<Map<String, String>>(){}.getType();
    private Map<String,String> arugumentPool;

    public ArgumentMaker() {
        arugumentPool = new HashMap<>();
    }

    public static Object getObjcet(String paramter,Type type) {
        return gson.fromJson(paramter, type);
    }

    public static Object getObjcet(String paramter,Class<?> type) {
        return gson.fromJson(paramter, type);
    }


    public ArgumentMaker(String paramter) {
        arugumentPool = gson.fromJson(paramter,type);
    }

    public ArgumentMaker addArgument(String name,Object value) {
        arugumentPool.put(name,gson.toJson(value));
        return this;
    }

    public Object getArgument(String name,Type type) {
        String argument = arugumentPool.get(name);
        return getObjcet(argument,type);
    }

    public Object getArgument(String name,Class<?> type) {
        String argument = arugumentPool.get(name);
        return getObjcet(argument,type);
    }

    @Override
    public String toString() {
        return gson.toJson(arugumentPool);
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static Object fromJson(String para,Type type) {
        return gson.fromJson(para,type);
    }

    public static Object fromJson(String para,Class<?> type) {
        return gson.fromJson(para,type);
    }
}
