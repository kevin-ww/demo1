package com.example.demo;

import java.lang.reflect.Method;

/**
 * Created by kevin on 04/06/2018.
 */
public class Test {
    public static void main(String[] args) throws Exception{
        Class[] parameterTypes = new Class[1];
        parameterTypes[0] = String.class;
        Method method1 = Test.class.getMethod("method1", parameterTypes);

        Test demo = new Test();
        demo.method2(demo, method1, "Hello World method2");

        //

        demo.method3(demo,method1,"hello world method3");
    }

    public void method1(String message) {
        System.out.println(message);
    }

    public void method2(Object object, Method method, String message) throws Exception {
        Object[] parameters = new Object[1];
        parameters[0] = message;
        method.invoke(object, parameters);
    }

    public void method3(Object object, Method method, Object... parameters) throws Exception{
//        Object[] parameters = new Object[1];
//        parameters[0] = message;
        method.invoke(object, parameters);
    }

}
