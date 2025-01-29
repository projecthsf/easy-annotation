package io.github.easyannotation.annotation;


import io.github.easyannotation.processor.AbstractAnnotationProcessor;
import io.github.easyannotation.processor.DefaultAnnotationProcessor;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationConfigurationScan {
    Class<? extends AbstractAnnotationProcessor> processor() default DefaultAnnotationProcessor.class;
    Class<? extends Annotation> fieldAnnotation();
}
