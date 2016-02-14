package lamp.admin.core.app.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {MultipartFileNotEmptyValidator.class})
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER })
public @interface MultipartFileNotEmpty {

	String message() default "{errors.MultipartFileNotEmpty}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}