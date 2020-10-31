package edu.xupt.cs.factory.xml;

import com.mec.util.XMLParse;
import edu.xupt.cs.action.abstract_.ActionBeanFactory;
import edu.xupt.cs.action.abstract_.BeanDefination;
import org.w3c.dom.Element;

import java.lang.reflect.Method;
import java.util.*;

public class XMLActionBeanFactory extends ActionBeanFactory {

    public BeanDefination getAction(String actionName) {
        return actionPool.get(actionName);
    }


    public void scannActionMapping(String XMLPath) {
        new XMLParse() {
            @Override
            public void dealElement(Element element, int index) {
                try {
                    String methodName = element.getAttribute("method");
                    String action = element.getAttribute("action");
                    String klassName = element.getAttribute("class");
                    Class<?> klass = Class.forName(klassName);
                    ActionBeanDefination abd = new ActionBeanDefination();
                    abd.setObject(klass.newInstance());
                    abd.setKlass(klass);
                    abd.setMethod(getMethod(klass,methodName,element,abd));
                    actionPool.put(action,abd);
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }.parseTagByDocument(XMLParse.getDocument(XMLPath), "action");
    }

    private Method getMethod(Class<?> klass, String methodName, Element element,
                                   ActionBeanDefination abd) {
        try {
            List<String> typeNames = new ArrayList<>();

            new XMLParse() {
                @Override
                public void dealElement(Element element, int index) {
                    String name = element.getAttribute("name");
                    String type = element.getAttribute("type");
                    abd.addParaName(name);
                    typeNames.add(type);
                }
            }.parseTagByElement(element, "parameter");

            Class<?>[] types = new Class[typeNames.size()];
            for (int i = 0; i < typeNames.size(); i++) {
                types[i] = MakeType.getType(typeNames.get(i));
            }

            return klass.getMethod(methodName, types);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }


}
