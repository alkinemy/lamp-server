package lamp.admin.core.app.base;

public enum AppInstanceStatus {

	UNKNOWN, PENDING, DEPLOYING, DEPLOY_FAILED,
	STARTING, RUNNING, STOPPING, STOPPED,
	START_FAILED, UNDEPLOYING

}
