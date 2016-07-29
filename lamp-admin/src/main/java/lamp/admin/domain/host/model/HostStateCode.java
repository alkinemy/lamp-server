package lamp.admin.domain.host.model;

import com.amazonaws.services.ec2.model.InstanceState;
import com.amazonaws.services.ec2.model.InstanceStateName;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum HostStateCode {

	UNKNOWN, PENDING, RUNNING, SHUTTING_DOWN, TERMINATED, STOPPING, STOPPED;


	public static HostStateCode of(InstanceState instanceState) {
		try {
			InstanceStateName instanceStateName = InstanceStateName.fromValue(instanceState.getName());
			if (InstanceStateName.Pending.equals(instanceStateName)) {
				return HostStateCode.PENDING;
			} else if (InstanceStateName.Running.equals(instanceStateName)) {
				return HostStateCode.RUNNING;
			} else if (InstanceStateName.ShuttingDown.equals(instanceStateName)) {
				return HostStateCode.SHUTTING_DOWN;
			} else if (InstanceStateName.Terminated.equals(instanceStateName)) {
				return HostStateCode.TERMINATED;
			} else if (InstanceStateName.Stopping.equals(instanceStateName)) {
				return HostStateCode.STOPPING;
			} else if (InstanceStateName.Stopped.equals(instanceStateName)) {
				return HostStateCode.STOPPED;
			} else {
				log.warn("Unknown InstanceStateName", instanceStateName);
				return HostStateCode.UNKNOWN;
			}
		} catch (Exception e) {
			log.warn("Unknown InstanceState", e);
			return HostStateCode.UNKNOWN;
		}

	}
}
