package io.github.easyannotation.processor;

import io.github.easyannotation.annotation.Processor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultBeanNameGenerator;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Slf4j
@Processor
public class DefaultAnnotationProcessor extends AbstractAnnotationProcessor {
    @Override
    public <A extends Annotation> void processClass(BeanDefinition beanDefinition, Class<?> clazz, A annotation) {
        String beanName = DefaultBeanNameGenerator.INSTANCE.generateBeanName(beanDefinition, registry);
        registry.registerBeanDefinition(beanName, beanDefinition);
    }

    @Override
    public void processField(Field field, Annotation annotation) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(field.getType());

        String beanName = DefaultBeanNameGenerator.INSTANCE.generateBeanName(beanDefinition, registry);
        registry.registerBeanDefinition(beanName, beanDefinition);
    }
}
