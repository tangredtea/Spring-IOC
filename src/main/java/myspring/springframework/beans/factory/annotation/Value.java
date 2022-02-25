package myspring.springframework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * @author Ryan
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Value {
    String value();
}
