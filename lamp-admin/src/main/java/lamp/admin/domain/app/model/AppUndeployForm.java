package lamp.admin.domain.app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AppUndeployForm {

    private String appManagementListener;

    private boolean forceStop;
}
