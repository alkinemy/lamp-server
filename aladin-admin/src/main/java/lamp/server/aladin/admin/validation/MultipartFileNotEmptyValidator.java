package lamp.server.aladin.admin.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class MultipartFileNotEmptyValidator implements ConstraintValidator<MultipartFileNotEmpty, MultipartFile> {

	@Override public void initialize(MultipartFileNotEmpty constraintAnnotation) {

	}

	@Override public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		return value != null && !value.isEmpty();
	}
}
