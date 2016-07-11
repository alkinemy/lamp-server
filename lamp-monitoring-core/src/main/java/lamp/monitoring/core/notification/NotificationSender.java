package lamp.monitoring.core.notification;

import java.io.IOException;

public interface NotificationSender<M extends NotificationMessage> {

	void send(M message) throws IOException;
}
