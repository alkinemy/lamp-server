package lamp.admin.domain.app.base.model.form;

import lamp.admin.domain.host.model.form.AwsEc2HostsForm;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AppInstanceClusterDeployForm extends AwsEc2HostsForm {

	private String clusterId;

}
