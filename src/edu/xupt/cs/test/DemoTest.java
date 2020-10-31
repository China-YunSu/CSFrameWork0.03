package edu.xupt.cs.test;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class Student {
    String id;
    String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


public class DemoTest {

    @Test
    public void reflect() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        Student student = new Student();
        student.setName("wang");
        Method method = Student.class.getDeclaredMethod("getName");
        Object[] values = new Object[0];
        Object invoke = method.invoke(student, values);
        System.out.println(invoke);
    }
}
