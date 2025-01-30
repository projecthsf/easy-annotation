# easy-annotation

Library for inspecting and processing class and field annotations, simplifying the creation of custom annotations.

Let says, you want to create a custom annotation on class level
```
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyClassAnnotation {}
```

And another custom annotation on field level
```
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyFieldAnnotation {
}
```

And if you put your annotation to a class
```
@MyClassAnnotation
public class TestServiceWithMyClassAnnotation {
    public String getString() {
        return TestServiceWithMyClassAnnotation.class.getName();
    }
}
```

Or 
```
public class AutoConfigWithMyConfigAnnotation {
    @MyFieldAnnotation
    private TestServiceWithMyConfigAnnotation testClass1Service;
}
```
Nothing will happens, b/c your annotation is not processed. To make your annotations work, you have to do something with it:

- Find all classes are using @MyClassAnnotation and @MyFieldAnnotation
- Do something with it, example register the class or the field to bean,....

And to do that you will need to:
- Implement BeanDefinitionRegistryPostProcessor or some other PostProcessor....
- Scan all your code to find classes/fields that are using  @MyClassAnnotation/@MyFieldAnnotation

This library support to implement BeanDefinitionRegistryPostProcessor and scan your code to find classes/fields and it will call a custom processor to process the class or field. So what u need to do is: create a new processor for your annotations.

```
@Processor
public class YourProcessor extends AbstractAnnotationProcessor {
    @Override
    public <A extends Annotation> void processClass(BeanDefinition beanDefinition, Class<?> clazz, A annotation) {
        // do something with class
    }

    @Override
    public void processField(Field field, Annotation annotation) {
        // do something with field 
    }
}

// we aready implement DefaultAnnotationProcessor which is registering classes/fields to bean
```

And use @AnnotationScan(processor=YourProcessor.class) in to your annotation

```
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@AnnotationScan(processor = MyAnnotationProcessor.class)
public @interface MyClassAnnotation {
}
```

And then use it your annotation on your class
```
@MyClassAnnotation
public class TestServiceWithMyClassAnnotation {
    public String getString() {
        return TestServiceWithMyClassAnnotation.class.getName();
    }
}
```

Or use @AnnotationScan directly on your class
```
@AnnotationScan(processor = MyAnnotationProcessor.class)
public class TestServiceWithoutMyClassAnnotation {
    public String getString() {
        return TestServiceWithoutMyClassAnnotation.class.getName();
    }
}
```

Let try [demo](https://github.com/projecthsf/easy-annotation-demo) to know how to use
