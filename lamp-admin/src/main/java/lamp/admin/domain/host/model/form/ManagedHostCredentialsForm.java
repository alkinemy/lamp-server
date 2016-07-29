package lamp.admin.domain.host.model.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ManagedHostCredentialsForm extends HostCredentialsForm {

	private List<String> hostId;

	private int parallelInstallCount;

}
