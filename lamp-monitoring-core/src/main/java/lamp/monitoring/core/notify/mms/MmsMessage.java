package lamp.monitoring.core.notify.mms;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class MmsMessage {

	@NonNull
	private String phoneNumber;
	@NonNull
	private String subject;
	@NonNull
	private String message;

	private long currentTimeMillis = System.currentTimeMillis();


}
