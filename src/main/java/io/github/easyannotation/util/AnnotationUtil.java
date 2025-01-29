package io.github.easyannotation.util;

import io.github.easyannotation.autoconfig.AnnotationScanner;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class AnnotationUtil {
    public static Set<BeanDefinition> getBeanDefinitions(String packageName, Class<? extends Annotation> annotationType) {
        return getBeanDefinitions(packageName, Collections.singletonList(annotationType), null);
    }

    public static Set<BeanDefinition> getBeanDefinitions(String packageName, List<Class<? extends Annotation>> annotationTypes) {
        return getBeanDefinitions(packageName, annotationTypes, null);
    }

    public static Set<BeanDefinition> getBeanDefinitions(String packageName, List<Class<? extends Annotation>> annotationTypes, Class<?> classType) {
        AnnotationScanner provider = new AnnotationScanner(false);
        if (!annotationTypes.isEmpty()) {
            for (Class<? extends Annotation> annotationType: annotationTypes) {
                provider.addIncludeFilter(new AnnotationTypeFilter(annotationType));
            }
        }

        if (classType != null) {
            provider.addIncludeFilter(new AssignableTypeFilter(classType));
        }

        return provider.findCandidateComponents(packageName);
    }

    @SuppressWarnings("unchecked")
    public static  <T extends Annotation> T getAnnotationScan(Class<T> annotationClazz, Class<?> clazz) {
        for (Annotation annotation: clazz.getAnnotations()) {

            if (annotationClazz.getName().equals(annotation.annotationType().getName())) {
                return (T) annotation;
            }

            T a = annotation.annotationType().getAnnotation(annotationClazz);
            if (a == null) {
                continue;
            }

            return a;

        }

        return null;
    }
}
