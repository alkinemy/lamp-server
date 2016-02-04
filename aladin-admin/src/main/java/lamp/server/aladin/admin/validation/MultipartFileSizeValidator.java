package lamp.server.aladin.admin.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultipartFileSizeValidator implements ConstraintValidator<MultipartFileSize, MultipartFile> {

	private long min;
	private long max;

	@Override public void initialize(MultipartFileSize constraintAnnotation) {
		min = constraintAnnotation.min();
		max = constraintAnnotation.min();
	}

	@Override public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		if (value == null || value.isEmpty()) {
			return true;
		}
		long size = value.getSize();
		return min <= size && size <= max;
	}

}
