package br.com.climb.commons.utils;

import com.google.common.reflect.ClassPath;

//import javax.enterprise.inject.se.SeContainer;
//import javax.enterprise.inject.se.SeContainerInitializer;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;


public class ReflectionUtils {

    public static final String JAVA_TYPE_STRING = "class java.lang.String";
    public static final String JAVA_TYPE_LONG = "class java.lang.Long";
    public static final String JAVA_TYPE_INTEGER = "class java.lang.Integer";
    public static final String JAVA_TYPE_DOUBLE = "class java.lang.Double";
    public static final String JAVA_TYPE_FLOAT = "class java.lang.Float";
    public static final String JAVA_TYPE_BOOLEAN = "class java.lang.Boolean";

    public static final String PRIMITIVE_TYPE_LONG = "long";
    public static final String PRIMITIVE_TYPE_INTEGER = "integer";
    public static final String PRIMITIVE_TYPE_INT = "int";
    public static final String PRIMITIVE_TYPE_DOUBLE = "double";
    public static final String PRIMITIVE_TYPE_FLOAT = "float";
    public static final String PRIMITIVE_TYPE_BOOLEAN = "boolean";

    public static final String CLIMB_TYPE_NUMBER = "class java.lang.Number";

    public synchronized static Set<Class<?>> getAnnotedClass(Class annotation, String packag) throws IOException {

        final Set<Class<?>> classes = new HashSet<>();
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();

        ClassPath.from(loader).getTopLevelClasses().forEach((classInfo -> {
            if (classInfo.getName().startsWith(packag)) {
                final Class<?> clazz = classInfo.load();
                Object returnObject = clazz.getAnnotation(annotation);

                if (returnObject != null) {
                    classes.add(clazz);
                }
            }
        }));

        return classes;
    }

    public synchronized static boolean isJavaType(String value) {

        if (JAVA_TYPE_LONG.equals(value)) {
            return true;
        }

        if (JAVA_TYPE_INTEGER.equals(value)) {
            return true;
        }

        if (JAVA_TYPE_INTEGER.equals(value)) {
            return true;
        }

        if (JAVA_TYPE_DOUBLE.equals(value)) {
            return true;
        }

        if (JAVA_TYPE_FLOAT.equals(value)) {
            return true;
        }

        if (JAVA_TYPE_STRING.equals(value)) {
            return true;
        }

        if (JAVA_TYPE_BOOLEAN.equals(value)) {
            return true;
        }

        return false;

    }

    public synchronized static String getTypeClimbString(Class clazz) {

        if (JAVA_TYPE_STRING.equals(clazz.toString())) {
            return JAVA_TYPE_STRING;
        }

        if (JAVA_TYPE_BOOLEAN.equals(clazz.toString())) {
            return JAVA_TYPE_BOOLEAN;
        }

        if (JAVA_TYPE_LONG.equals(clazz.toString())) {
            return CLIMB_TYPE_NUMBER;
        }

        if (JAVA_TYPE_INTEGER.equals(clazz.toString())) {
            return CLIMB_TYPE_NUMBER;
        }

        if (JAVA_TYPE_DOUBLE.equals(clazz.toString())) {
            return CLIMB_TYPE_NUMBER;
        }

        if (JAVA_TYPE_FLOAT.equals(clazz.toString())) {
            return CLIMB_TYPE_NUMBER;
        }

        if (PRIMITIVE_TYPE_BOOLEAN.equals(clazz.toString())) {
            return JAVA_TYPE_BOOLEAN;
        }

        if (PRIMITIVE_TYPE_LONG.equals(clazz.toString())) {
            return CLIMB_TYPE_NUMBER;
        }

        if (PRIMITIVE_TYPE_INT.equals(clazz.toString())) {
            return CLIMB_TYPE_NUMBER;
        }

        if (PRIMITIVE_TYPE_INTEGER.equals(clazz.toString())) {
            return CLIMB_TYPE_NUMBER;
        }

        if (PRIMITIVE_TYPE_DOUBLE.equals(clazz.toString())) {
            return CLIMB_TYPE_NUMBER;
        }

        if (PRIMITIVE_TYPE_FLOAT.equals(clazz.toString())) {
            return CLIMB_TYPE_NUMBER;
        }

        throw new Error("Tipo não suportado: " + clazz.toString());
    }

    public synchronized static boolean isNumeric(String strNum) {

        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        if (strNum == null) {
            return false;
        }

        return pattern.matcher(strNum).matches();
    }


}
