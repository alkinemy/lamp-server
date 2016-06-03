package lamp.admin.domain.old.app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AppUpdateFileForm {

	private String version;

	private String appManagementListener;

	private Long installScriptId;


}
