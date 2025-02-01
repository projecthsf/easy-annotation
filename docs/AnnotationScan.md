# AnnotationScan

Mark classes for processing. The processor will run on all classes with this annotation. You can specific a processor by param: **processor**

## How to use

### 1. Use AnnotationScan directly
```
@AnnotationScan(processor = MyAnnotationProcessor.class)
public class TestServiceWithoutMyClassAnnotation {
    // code implements
}
```

### 2. Use with your custom annotation

Create your custom annotation
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
