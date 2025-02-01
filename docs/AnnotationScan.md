# AnnotationScan

Mark classes for processing. The processor will run on all classes with this annotation. You can specific a processor by **AnnotationScan.processor**

```
@AnnotationScan(processor = MyAnnotationProcessor.class)
```

With this config, the processor is: **MyAnnotationProcessor** and the method **MyAnnotationProcessor.processClass** will be invoked.
## How to use

### 1. Use AnnotationScan directly
```
@AnnotationScan(processor = MyAnnotationProcessor.class)
public class TestServiceWithoutMyClassAnnotation {
    // code implements
}
```
=> will invoke MyAnnotationProcessor.processClass(TestServiceWithoutMyClassAnnotation.class, \<Instance of AnnotationScan\>)
### 2. Use with your custom annotation 

Create your custom annotation
```
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@AnnotationScan(processor = MyAnnotationProcessor.class)
public @interface MyClassAnnotation {
    String name() default "";
}
```
And use **@MyClassAnnotation** to mark your classes:
```
@MyClassAnnotation(name="ABC")
public class TestServiceWithMyClassAnnotation {
    // code implements
}
```

=> will invoke MyAnnotationProcessor.processClass(TestServiceWithMyClassAnnotation.class, \<Instance of MyClassAnnotation\>)
