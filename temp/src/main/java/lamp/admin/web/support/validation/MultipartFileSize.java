package lamp.admin.web.support.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {MultipartFileNotEmptyValidator.class})
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER })
public @interface MultipartFileSize {

	String message() default "{errors.MultipartFileSize.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	long min() default 0;

	long max() default Long.MAX_VALUE;

}