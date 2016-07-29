package lamp.admin.domain.host.model.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class ScannedHostCredentialsForm extends HostCredentialsForm {

	private List<String> scannedHostAddress;

	private int parallelInstallCount;

}
