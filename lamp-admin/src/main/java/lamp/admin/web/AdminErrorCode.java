package lamp.admin.web;


import lamp.admin.core.base.exception.ErrorCode;
import lamp.admin.core.base.exception.FlashMessageException;
import lamp.admin.core.base.exception.MessageException;

public enum AdminErrorCode implements ErrorCode {
	INTERNAL("에러가 발생하였습니다."),
	APP_NOT_FOUND("애플리케이션이 존재하지 않습니다."),
	INVALID_MOUNT_POINT_NAME("잘못된 마운트 포인트 이름입니다."),
	INVALID_MOUNT_POINT_PARENT("잘못된 마운트 포인트 부모입니다."),
	FILE_DOWNLOAD_FAILED("파일을 다운로드 하는 중 에러가 발생하였습니다."),
	APP_IS_ALREADY_DEPLOYED("애플리케이션이 이미 설치되어 있습니다."),
	APP_IS_ALREADY_RUNNING("애플리케이션이 이미 실행중입니다."),
	APP_IS_RUNNING("애플리케이션이 실행중입니다."),
	APP_IS_NOT_RUNNING("애플리케이션이 실행중이지 않습니다."),
	APP_START_FAILED("애플리케이션을 시작할 수 없습니다."),
	APP_STOP_FAILED("애플리케이션을 중지 할 수 없습니다."),
	APP_MANIFEST_SAVE_FAILED("애플리케이션 설정 정보를 저장하는 중 에러가 발생하였습니다."),
	APP_MANIFEST_READ_FAILED("애플리케이션 설정 정보를 읽어오는 중 에러가 발생하였습니다."),
	CANNOT_CONNECT_TO_AGENT_SERVER("에이전트 서버에 접속할 수 없습니다."),
	ARTIFACT_REPOSITORY_CONNECT_FAILED("Artifact Repository에 접속할수 없습니다."),
	ARTIFACT_REPOSITORY_CLEAN_FAILED("Artifact Repository를 정리하는 중 에러가 발생하였습니다."),
	ARTIFACT_RESOLVE_FAILED("Artifact 정보를 가져올 수 없스니다."),
	ARTIFACT_VERSION_RESOLVE_FAILED("Artifact 버전 정보를 가져올 수 없스니다."),
	DOWNLOAD_ARTIFACT_FAILED("Artifact를 다운로드 할 수 없습니다."),
	AGENT_INSTALLER_NOT_FOUND("서비스 인스톨러를 찾을 수 없습니다."),
	AGENT_UPDATER_FILE_NOT_FOUND("에이전트 업데이터 파일을 찾을 수 없습니다"),
	AGENT_UPDATER_START_FAILED("에이전트 업데이터를 실행하는 중 에러가 발생하였습니다."),
	AGENT_PID_FILE_READ_FAILED("에이전트 PID 정보를 가져오는 중 에러가 발생하였습니다."),
	SERVICE_PROPERTIES_WRITE_FAILED("서비스 프로퍼티즈 파일을 생성하는 중 에러가 발생하였습니다."),
	AGENT_SYSTEM_LOG_FILE_NOT_FOUND("에이전트 시스템 로그 파일을 찾을 수 없습니다.")
	, SECRET_KEY_GENERATION_FAILED("비밀키 생성을 실패하였습니다.")
	, APP_DEPLOY_FAILED("애클리케이션을 디플로이 하는 중 문제가 발생하였습니다.")
	, INVALID_APP_REPOSITORY_TYPE("잘못된 AppRepositoryType입니다.")
	, INVALID_APP_TEMPLATE_TYPE("잘못된 AppTemplateType입니다.")
	, LOCAL_APP_FILE_UPLOAD_FAILED("파일 업로드를 실패하였습니다.")
	, INSERT_SUCCESS("성공적으로 등록하였습니다.", FlashMessageException.class)
	, UPDATE_SUCCESS("성공적으로 수정하였습니다.", FlashMessageException.class)
	, DELETE_SUCCESS("성공적으로 삭제하였습니다.", FlashMessageException.class)
	, USER_REGISTER_SUCCESS("성공적으로 유저가 등록되었습니다. 승인 이후에 사용가능합니다.", FlashMessageException.class)
	, DUPLICATED_USER_LOGIN("중복된 아이디입니다.")
	, DUPLICATED_USER_EMAIL("중복된 이메일주소입니다.")
	;

	private String defaultMessage;
	private Class<? extends MessageException> exceptionClass;

	AdminErrorCode(String defaultMessage) {
		this(defaultMessage, MessageException.class);
	}

	AdminErrorCode(String defaultMessage, Class<? extends MessageException> exceptionClass) {
		this.defaultMessage = defaultMessage;
		this.exceptionClass = exceptionClass;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}

	public Class<? extends MessageException> getExceptionClass() {
		return exceptionClass;
	}
}
