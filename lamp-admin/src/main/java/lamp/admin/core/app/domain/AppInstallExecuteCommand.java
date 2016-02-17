package lamp.admin.core.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = AppResourceType.Values.LOCAL)
@Table(name = "lamp_app_install_execute_command")
@PrimaryKeyJoinColumn(name = "id")
public class AppInstallExecuteCommand extends AppInstallCommand {

	@Enumerated(EnumType.STRING)
	private CommandShell commandShell;

	private String commandLine;

}
