package lamp.admin.core.app.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AppUpdateFileForm {

	private String version;

	private String appManagementListener;

}
