package lamp.admin.core.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = AppResourceType.Values.LOCAL)
@Table(name = "lamp_app_install_file_create_command")
@PrimaryKeyJoinColumn(name = "id")
public class AppInstallFileCreateCommand extends AppInstallCommand {

	private String filename;

	private String content;

	private boolean read = true;
	private boolean write = true;
	private boolean execute = false;

}
