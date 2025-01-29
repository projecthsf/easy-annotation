package io.github.easyannotation.binding;

import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.Annotation;
import java.util.List;
@Getter
@Setter
public class AnnotationFieldProperty {
    private Class<? extends Annotation> field;
    private List<Class<? extends Annotation>> dependOn;
}
