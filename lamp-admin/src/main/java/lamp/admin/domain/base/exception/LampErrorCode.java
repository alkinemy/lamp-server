package lamp.admin.domain.base.exception;

public enum LampErrorCode implements ErrorCode {
	INTERNAL("에러가 발생하였습니다.")
	, ENTITY_NOT_FOUND("대상을 찾을 수가 없습니다.", EntityNotFoundException.class)
	, DUPLICATED_HOSTNAME("이미 등록되어 있는 호스트네임 입니다.", DuplicatedException.class)
	, DUPLICATED_AGENT_ID("이미 등록되어 있는 에이전트 아이디 입니다.", DuplicatedException.class)

	, DUPLICATED_APP_REPO_NAME("중복된 앱 리파지토리 이름입니다.", DuplicatedException.class)

	,
	APP_NOT_FOUND("애플리케이션이 존재하지 않습니다."),
	MANAGED_APP_NOT_FOUND("애플리케이션이 존재하지 않습니다."),
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
	, AGENT_NOT_FOUND("에이전트가 존재하지 않습니다.")
	, AGENT_NOT_FOUND_BY_TARGET_SERVER("에이전트가 존재하지 않습니다.")
	, APP_TEMPLATE_NOT_FOUND("앱 템플릿이 존재하지 않습니다.")
	, APP_RESOURCE_NOT_FOUND("앱 리소스를 찾을 수 없습니다.")
	, UNSUPPORTED_APP_RESOURCE_TYPE("지원하지 않는 앱 템플릿 타입입니다.")
	, UNSUPPORTED_APP_REPOSITORY_TYPE("지원하지 않는 앱 리파지토리 타입입니다.")
	, AGENT_INSTALL_FAILED("에이전트 설치를 실패하였습니다.")
	, APP_REPOSITORY_NOT_FOUND("앱 저장소를 찾을 수 없습니다.")
	, HOST_NOT_FOUND("타켓 서버를 찾을 수 없습니다")
	, TARGET_SERVER_DELETE_FAILED_AGENT_EXIST("에이전트가 존재하기 때문에, 타겟 서버를 삭제할 수 없습니다.", FlashMessageException.class)
	// Monitoring
	, WATCH_TARGET_NOT_FOUND("타켓를 찾을 수 없습니다")
	//
	, DUPLICATED_LOCAL_APP_FILE("이미 등록되어 있습니다.")
	, LOCAL_APP_FILE_UPLOAD_FAILED("파일 업로드를 실패하였습니다")
	, INVALID_AUTH_TOKEN("인증 토큰이 유효하지 않습니다")
	, APP_INSTALL_COMMAND_NOT_FOUND("명령어가 존재하지 않습니다")
	, UNSUPPORTED_SCRIPT_COMMAND_TYPE("지원하지 않는 명령어 타입입니다")
	, INVALID_SCRIPT_COMMANDS("스크립트 명령어가 잘못되었습니다.")
	, JSON_PROCESS_FAILED("Json Processing failed")
	, SCRIPT_COMMAND_EXECUTION_FAILED("스크립트 명령어 실행을 실패하였습니다")
	, SSH_KEY_NOT_FOUND("SSH Key를 찾을 수 없습니다.")
	, INVALID_PARAMETERS("유효하지 않은 파라메터 입니다.")
	, SHELL_FILE_NOT_FOUND("쉘 파일을 찾을 수 없습니다.")
	, APP_CONTAINER_CREATE_FAILED("앱 컨테이서를 생성할 수 없습니다."), APP_INSTANCE_NOT_FOUND("앱 인스턴스를 찾을 수 없습니다.");

	private String defaultMessage;
	private Class<? extends MessageException> exceptionClass;

	LampErrorCode(String defaultMessage) {
		this(defaultMessage, MessageException.class);
	}

	LampErrorCode(String defaultMessage, Class<? extends MessageException> exceptionClass) {
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
