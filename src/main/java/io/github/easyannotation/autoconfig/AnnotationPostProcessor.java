package io.github.easyannotation.autoconfig;

import io.github.easyannotation.annotation.AnnotationConfigurationScan;
import io.github.easyannotation.annotation.AnnotationScan;
import io.github.easyannotation.processor.AbstractAnnotationProcessor;
import io.github.easyannotation.util.AnnotationUtil;
import io.github.easyannotation.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class AnnotationPostProcessor implements BeanDefinitionRegistryPostProcessor, BeanFactoryAware, EnvironmentAware, ApplicationContextAware {
    private BeanFactory beanFactory;
    private ApplicationContext applicationContext;
    private DefaultListableBeanFactory registry;
    private Environment environment;
    private Map<String, AbstractAnnotationProcessor> processors = new HashMap<>();


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        this.registry = (DefaultListableBeanFactory) registry;
        initProcessors();
        for (AbstractAnnotationProcessor processor: processors.values()) {
            processor.processorInit();
        }

        List<String> packageNames = AutoConfigurationPackages.get(beanFactory);
        for (String packageName: packageNames) {
            log.info("* Package {} > Scan AnnotationScan start ...", packageName);
            postProcessAnnotationScan(packageName);
            log.info("* Package {} > Scan AnnotationScan finished ...", packageName);
            log.info("* Package {} > Scan AnnotationFieldConfigScan start ...", packageName);
            postProcessAnnotationConfigurationScan(packageName);
            log.info("* Package {} > Scan AnnotationFieldConfigScan finished ...", packageName);
            postProcessAnnotationFieldProperties(packageName);
        }

        for (AbstractAnnotationProcessor processor: processors.values()) {
            processor.afterAllProcesses();
        }
    }

    private void postProcessAnnotationFieldProperties(String packageName) {
        for (AbstractAnnotationProcessor processor: processors.values()) {
            if (processor.getAnnotationFieldProperty() == null) {
                continue;
            }

            postProcessAnnotationFieldProperty(packageName, processor);
        }
    }

    private void postProcessAnnotationFieldProperty(String packageName, AbstractAnnotationProcessor processor) {
        Set<BeanDefinition> beans = AnnotationUtil.getBeanDefinitions(packageName, processor.getAnnotationFieldProperty().getDependOn());
        for (BeanDefinition bean: beans) {
            Class<?> clazz = ClassUtil.getClass(bean.getBeanClassName());
            if (clazz == null) {
                continue;
            }

            for (Field field: clazz.getDeclaredFields()) {
                processField(processor, field, processor.getAnnotationFieldProperty().getField());
            }

        }
    }

    private void postProcessAnnotationScan(String packageName) {
        Set<BeanDefinition> beans = AnnotationUtil.getBeanDefinitions(packageName, AnnotationScan.class);
        for (BeanDefinition bean: beans) {
            Class<?> clazz = ClassUtil.getClass(bean.getBeanClassName());
            if (clazz == null) {
                continue;
            }

            if (clazz.isAnnotation()) {
                continue;
            }

            AnnotationScan annotationScan = AnnotationUtil.getAnnotationScan(AnnotationScan.class, clazz);
            if (annotationScan == null) {
                continue;
            }

            AbstractAnnotationProcessor processor = getPostProcessor(annotationScan.processor());
            if (processor == null) {
                continue;
            }

            processor.processClass(bean, clazz, clazz.getAnnotations()[0]);
        }
    }

    private void postProcessAnnotationConfigurationScan(String packageName) {
        Set<BeanDefinition> beans = AnnotationUtil.getBeanDefinitions(packageName, AnnotationConfigurationScan.class);
        for (BeanDefinition bean: beans) {
            Class<?> clazz = ClassUtil.getClass(bean.getBeanClassName());
            if (clazz == null) {
                continue;
            }

            AnnotationConfigurationScan annotationScan = AnnotationUtil.getAnnotationScan(AnnotationConfigurationScan.class, clazz);
            if (annotationScan == null) {
                continue;
            }

            if (annotationScan.fieldAnnotation() == null) {
                continue;
            }

            AbstractAnnotationProcessor processor = getPostProcessor(annotationScan.processor());
            if (processor == null) {
                continue;
            }

            for (Field field: clazz.getDeclaredFields()) {
                processField(processor, field, annotationScan.fieldAnnotation());
            }
        }
    }

    private void processField(AbstractAnnotationProcessor processor, Field field, Class<? extends Annotation> fieldAnnotation) {
        Annotation[] annotations = field.getAnnotationsByType(fieldAnnotation);
        if (annotations.length == 0) {
            return;
        }

        processor.processField(field, annotations[0]);
    }

    private void initProcessors() {
        Map<String, AbstractAnnotationProcessor> beans = applicationContext.getBeansOfType(AbstractAnnotationProcessor.class);
        for (AbstractAnnotationProcessor processor: beans.values()) {
            processor.setRegistry(registry);
            processor.setEnvironment(environment);
            processor.setBeanFactory(beanFactory);
            processor.setApplicationContext(applicationContext);
            processors.put(processor.getClass().getName(), processor);
        }
    }

    private AbstractAnnotationProcessor getPostProcessor(Class<? extends AbstractAnnotationProcessor> clazz) {
        if (processors.containsKey(clazz.getName())) {
            return processors.get(clazz.getName());
        }

        return null;
    }
}
