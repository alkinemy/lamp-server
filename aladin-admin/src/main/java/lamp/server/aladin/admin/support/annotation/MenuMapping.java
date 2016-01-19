package lamp.server.aladin.admin.support.annotation;


import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MenuMapping {

	String value() default "";

	String title() default "";

}