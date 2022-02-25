package myspring.springframework.stereotype;

import java.lang.annotation.*;

/**
 * @author Ryan
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String value() default "";
}
