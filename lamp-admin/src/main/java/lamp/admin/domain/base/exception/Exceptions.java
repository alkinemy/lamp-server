package lamp.admin.domain.base.exception;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.util.Objects;

@Slf4j
public abstract class Exceptions {
	
	private static final String DEFAULT_ERROR_CODE = "INTERNAL";
	private static final String DEFAULT_ERROR_MSG = "에러가 발생하였습니다";

	/**
	 * 상태값이 참(<code>true</code>)일때 에러 코드에 정의된 <code>MessageException</code>을 발생 시킨다.
	 * @param condition 상태값
	 * @param errorCode 에러 코드
	 * @param args 에러 코드 첨자
	 */
	public static void throwsException(boolean condition, ErrorCode errorCode, Object... args) {
		Objects.requireNonNull(errorCode);

		if (condition) {
			throw newException(errorCode, args);
		}
	}

	/**
	 * 상태값이 참(<code>true</code>)일때 에러 코드에 정의된 <code>MessageException</code>을 반환 한다.
	 *
	 * @param errorCode 상태값
	 * @param t 원인
	 * @param args 에러코드 첨자
	 * @return
	 */
	public static MessageException newException(ErrorCode errorCode, Throwable t, Object... args) {
		try {
			return newExceptionInstance(errorCode, t, args);
		} catch (Exception e) {
			log.error("Exception create failed.", e);
			return new MessageException(DEFAULT_ERROR_MSG, t, DEFAULT_ERROR_CODE, null);
		}
	}

	protected static MessageException newExceptionInstance(ErrorCode errorCode, Throwable t, Object... args)
		throws NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
		Class<? extends MessageException> exceptionClass = errorCode.getExceptionClass();
		Constructor<? extends MessageException> constructor = exceptionClass.getConstructor(String.class, Throwable.class, String.class,
			Object[].class);
		return constructor.newInstance(errorCode.getDefaultMessage(), t, errorCode.name(), args);
	}

	/**
	 * 상태값이 참(<code>true</code>)일때 에러 코드에 정의된 <code>MessageException</code>을 반환 한다.
	 *
	 * @param errorCode 에러 코드
	 * @param args 에러 코드 첨자
	 * @return
	 */
	public static MessageException newException(ErrorCode errorCode, Object... args) {
		try {
			return newExceptionInstance(errorCode, args);
		} catch (Exception e) {
			log.error("Exception create failed.", e);
			return new MessageException(DEFAULT_ERROR_MSG, DEFAULT_ERROR_CODE, null);
		}
	}

	protected static MessageException newExceptionInstance(ErrorCode errorCode, Object... args)
		throws NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
		Class<? extends MessageException> exceptionClass = errorCode.getExceptionClass();
		Constructor<? extends MessageException> constructor = exceptionClass.getConstructor(String.class, String.class, Object[].class);
		return constructor.newInstance(errorCode.getDefaultMessage(), errorCode.name(), args);
	}

}
