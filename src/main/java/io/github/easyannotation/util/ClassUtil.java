package io.github.easyannotation.util;

public class ClassUtil {
    public static Class<?> getClass(String className) {
        try {
            return Class.forName(className);
        } catch (Exception e) {
            return null;
        }
    }
}
