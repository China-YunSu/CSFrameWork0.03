package edu.xupt.cs.action.abstract_;

import java.util.HashMap;
import java.util.Map;

public abstract class ActionBeanFactory {
    protected static Map<String,BeanDefination> actionPool;

    static {
        actionPool = new HashMap<>();
    }

    public void setObject(String action, Object object) {
        actionPool.get(action).setObject(object);
    }

    public abstract BeanDefination getAction(String name);
    public abstract void scannActionMapping(String Path);
}
