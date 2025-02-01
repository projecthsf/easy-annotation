# Overview

Easy-Annotation provides 3 annotations:

- **@AnnotationScan**: Mark classes for processing. The processor will run on all classes with this annotation. You can specific a processor by param: **processor**
```
@AnnotationScan(processor = MyAnnotationProcessor.class)
public class TestServiceWithoutMyClassAnnotation {
    // code implements
}
```
Or you can use this annotation to your custom annotation:
```
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@AnnotationScan(processor = MyAnnotationProcessor.class)
public @interface MyClassAnnotation {
}
```
And use **@MyClassAnnotation** to mark your classes:
```
@MyClassAnnotation
public class TestServiceWithMyClassAnnotation {
    // code implements
}
```

- **@AnnotationConfigurationScan**: Mark classes for field-level processing. The processor will run on specific annotated fields (marked by **fieldAnnotation** within these classes.
Same. you can use **@AnnotationConfigurationScan** directly on your class or use on your custom annotation
```
@AnnotationConfigurationScan(processor = MyAnnotationProcessor.class, fieldAnnotation = MyFieldAnnotation.class)
public class AutoConfigWithoutMyConfigAnnotation {
    @MyFieldAnnotation
    private TestServiceWithoutMyConfigAnnotation testClass2Service;
}
```
Or you can use this annotation to your custom annotation:
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

As above, the field with annotation *@MyFieldAnnotation* will be scanned and will process at MyAnnotationProcessor.processField
- **@Processor**: Mark a class as processor.
Work same as @Component/@Service, to marke your class as a bean. However, autowired other beans to proccessor will not work, b/c the processor run at the time bean create.

