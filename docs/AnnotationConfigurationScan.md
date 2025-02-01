# AnnotationConfigurationScan 

Mark classes for field-level processing. The processor will run on specific annotated fields  within these classes.

## How to use

### 1. Use @AnnotationConfigurationScan directly

```
@AnnotationConfigurationScan(processor = MyAnnotationProcessor.class, fieldAnnotation = MyFieldAnnotation.class)
public class AutoConfigWithoutMyConfigAnnotation {
    @MyFieldAnnotation
    private TestServiceWithoutMyConfigAnnotation testClass2Service;
}
```

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


