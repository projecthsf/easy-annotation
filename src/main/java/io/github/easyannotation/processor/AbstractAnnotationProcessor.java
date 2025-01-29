package io.github.easyannotation.processor;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@Setter
@Slf4j
public abstract class AbstractAnnotationProcessor implements AnnotationProcessor {
    protected BeanFactory beanFactory;
    protected ApplicationContext applicationContext;
    protected DefaultListableBeanFactory registry;
    protected Environment environment;
}
