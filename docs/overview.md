# Overview

Easy-Annotation provides 3 annotations:

- **@AnnotationScan**: Mark classes for processing. The processor will run on all classes with this annotation. You can specific a processor by param: **processor**
- **@AnnotationConfigurationScan**: Mark classes for field-level processing. The processor will run on specific annotated fields (marked by **fieldAnnotation** within these classes.
- **@Processor**: Mark a class as a processor.  The processor will be invoked when @AnnotationScan/@AnnotationConfigurationScan/\<Field annotaion\> have been processed.


