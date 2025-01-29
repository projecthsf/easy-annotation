package io.github.easyannotation.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClassUtilTest {

    @Test
    void testGetClass() {
        Class<?> clazz = ClassUtil.getClass(AnnotationUtil.class.getName());
        assertEquals(clazz, AnnotationUtil.class);
    }

    @Test
    void testGetClass_NonExistedClass() {
        Class<?> clazz = ClassUtil.getClass("non_existed_class");
        assertNull(clazz);
    }
}