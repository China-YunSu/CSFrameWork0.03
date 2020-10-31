package edu.xupt.cs.factory.xml;

public class MakeType {
    public static Class<?> getType(String typeName) {
        if (typeName.equalsIgnoreCase("int")) {
            return int.class;
        }

        if (typeName.equalsIgnoreCase("long")) {
            return long.class;
        }

        if (typeName.equalsIgnoreCase("short")) {
            return short.class;
        }

        if (typeName.equalsIgnoreCase("char")) {
            return char.class;
        }

        if (typeName.equalsIgnoreCase("double")) {
            return double.class;
        }

        if (typeName.equalsIgnoreCase("float")) {
            return float.class;
        }

        if (typeName.equalsIgnoreCase("byte")) {
            return byte.class;
        }

        if (typeName.equalsIgnoreCase("String")) {
            return String.class;
        }

        try {
            return Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
