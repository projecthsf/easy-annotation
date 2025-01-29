package io.github.easyannotation.processor;

import io.github.easyannotation.binding.AnnotationFieldProperty;
import org.springframework.beans.factory.config.BeanDefinition;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface AnnotationProcessor {
    default AnnotationFieldProperty getAnnotationFieldProperty(){return null;}
    default void afterAllProcesses(){}
    default void processorInit(){}
    <A extends Annotation> void processClass(BeanDefinition beanDefinition, Class<?> clazz, A annotation);
    <A extends Annotation> void processField(Field field, A annotation);

}
