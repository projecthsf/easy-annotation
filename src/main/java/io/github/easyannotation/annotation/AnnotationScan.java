package io.github.easyannotation.annotation;

import io.github.easyannotation.processor.AbstractAnnotationProcessor;
import io.github.easyannotation.processor.DefaultAnnotationProcessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationScan {
    Class<? extends AbstractAnnotationProcessor> processor() default DefaultAnnotationProcessor.class;
}
