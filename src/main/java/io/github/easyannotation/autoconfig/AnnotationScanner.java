package io.github.easyannotation.autoconfig;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;

public class AnnotationScanner extends ClassPathScanningCandidateComponentProvider {
    public AnnotationScanner(boolean useDefaultFilters) {
        super(useDefaultFilters);
    }
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return super.isCandidateComponent(beanDefinition) || beanDefinition.getMetadata().isAbstract();
    }
}
