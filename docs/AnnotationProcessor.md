# AnnotationProcessor

Mark a class as a processor.  The processor will be invoked when @AnnotationScan/@AnnotationConfigurationScan/\<Field annotaion\> have been processed.

## How to mark a class as processor?

- Your class have to extends from io.github.easyannotation.processor.AbstractAnnotationProcessor
- Use annotation: @Processor/@Component/@Service on your class

```
@Processor
public class MyAnnotationProcessor extends AbstractAnnotationProcessor {
// code implements
}
```

## How to assign a processor to annotation

Set AnnotationScan/AnnotationConfigurationScan processor param
```
@AnnotationScan(processor = MyAnnotationProcessor.class)
```

Or

```
@AnnotationConfigurationScan(processor = MyAnnotationProcessor.class, ...)
```

## Initiated properties

When a processor has been created, there some properties have been initiated.
```
public abstract class AbstractAnnotationProcessor implements AnnotationProcessor {
    protected BeanFactory beanFactory;
    protected ApplicationContext applicationContext;
    protected DefaultListableBeanFactory registry;
    protected Environment environment;
}
```
So you can use these field in your code, let see the DefaultAnnotationProcessor as example:
```
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
```
=> property registry.registerBeanDefinition is working b/c registry has been created already. 

## Abstract methods

```
public interface AnnotationProcessor {
    default AnnotationFieldProperty getAnnotationFieldProperty(){return null;}
    default void afterAllProcesses(){}
    default void processorInit(){}
    <A extends Annotation> void processClass(BeanDefinition beanDefinition, Class<?> clazz, A annotation);
    <A extends Annotation> void processField(Field field, A annotation);
}
```
### - getAnnotationFieldProperty();
Run when processor initated. See [AnnotationConfigurationScan](https://github.com/projecthsf/easy-annotation/blob/main/docs/AnnotationConfigurationScan.md)

### - processorInit():
Run right after processor initiated

### - afterAllProcesses():
Run after all processor.processClass and processor.processField finished.

### - processClass():
Run when a class been processed

### - processField():
Run when a field been processed

 
