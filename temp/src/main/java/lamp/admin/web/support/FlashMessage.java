package lamp.admin.web.support;

import lamp.admin.domain.base.exception.ErrorCode;
import lamp.admin.domain.base.exception.MessageException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor(staticName = "of")
public class FlashMessage {

	private FlashMessageLevel level;
	private String message;
	private String code;
	private Object[] parameters;


	public String getLevelClass() {
		return "alert-" + level.name().toLowerCase();
	}

	public static FlashMessage ofSuccess(ErrorCode code) {
		return of(FlashMessageLevel.SUCCESS, code.getDefaultMessage(), code.name(), null);
	}

	public static FlashMessage ofError(String message, String code, Object[] parameters) {
		return of(FlashMessageLevel.ERROR, message, code, parameters);
	}

	public static FlashMessage ofError(MessageException e) {
		return of(FlashMessageLevel.ERROR, e.getMessage(), e.getCode(), e.getArgs());
	}


	public static FlashMessage ofInfo(String message) {
		return of(FlashMessageLevel.INFO, message, null, null);
	}
}
