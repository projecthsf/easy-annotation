# AnnotationConfigurationScan 

Mark classes for field-level processing. The processor will run on specific annotated fields  within these classes.
```
@AnnotationConfigurationScan(processor = MyAnnotationProcessor.class, fieldAnnotation = MyFieldAnnotation.class)
```
With this config:

- Scan on fields have annotation: **MyFieldAnnotation**
- The processor is: **MyAnnotationProcessor** and method **MyAnnotationProcessor.processField** will be invoked.

## How to use

### 1. Use @AnnotationConfigurationScan directly

```
@AnnotationConfigurationScan(processor = MyAnnotationProcessor.class, fieldAnnotation = MyFieldAnnotation.class)
public class AutoConfigWithoutMyConfigAnnotation {
    @MyFieldAnnotation
    private TestServiceWithoutMyConfigAnnotation testClass2Service;
}
```
=> will invoke MyAnnotationProcessor.processField(TestServiceWithoutMyConfigAnnotation.class, \<Instance of MyFieldAnnotation\>)
### 2. Use on your custom annotation

Create your custom annotation
```
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@AnnotationConfigurationScan(processor = MyAnnotationProcessor.class, fieldAnnotation = MyFieldAnnotation.class)
public @interface MyConfigAnnotation {
}
```
And then use **MyConfigAnnotation** to your class:
```
@MyConfigAnnotation
public class AutoConfigWithMyConfigAnnotation {
    @MyFieldAnnotation
    private TestServiceWithMyConfigAnnotation testClass1Service;
}
```
=> will invoke MyAnnotationProcessor.processField(AutoConfigWithMyConfigAnnotation.class, \<Instance of MyFieldAnnotation\>)
### 3. Use with original Spring class annotations: @Configuration

You can also scan your field anntation with @Configuration by update **AnnotationFieldProperty getAnnotationFieldProperty()** from your processor **MyAnnotationProcessor**

```
@Configuration
public class AutoConfigWithOriginalSpringConfiguration {
    @MyFieldAnnotation
    private TestServiceWithOriginalSpringConfiguration testServiceWithOriginalSpringConfiguration;
}
```

And you have to update your **MyAnnotationProcessor.getAnnotationFieldProperty()**:

```
@Processor
@Slf4j
public class MyAnnotationProcessor extends AbstractAnnotationProcessor {
    @Override
    public AnnotationFieldProperty getAnnotationFieldProperty() {
        AnnotationFieldProperty property = new AnnotationFieldProperty();
        property.setField(MyFieldAnnotation.class);
        property.setDependOn(Collections.singletonList(Configuration.class));

        return property;
    }
    //....
}
```
=> will invoke MyAnnotationProcessor.processField(TestServiceWithOriginalSpringConfiguration.class, \<Instance of MyFieldAnnotation\>)

