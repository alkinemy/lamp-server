package lamp.admin.core.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = AppResourceType.Values.LOCAL)
@Table(name = "lamp_app_install_file_remove_command")
@PrimaryKeyJoinColumn(name = "id")
public class AppInstallFileRemoveCommand extends AppInstallCommand {

	private String filename;

}
