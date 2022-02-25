package myspring.springframework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * @author Ryan
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Qualifier {
    String value() default "";
}
