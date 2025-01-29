package io.github.easyannotation.autoconfig;

import io.github.easyannotation.processor.DefaultAnnotationProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Configuration
@Import({
        DefaultAnnotationProcessor.class,
        AnnotationPostProcessor.class
})
public class AnnotationAutoConfig {
}
