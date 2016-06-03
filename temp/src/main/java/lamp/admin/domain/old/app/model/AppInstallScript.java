package lamp.admin.domain.old.app.model;

import lamp.admin.domain.script.model.Script;
import lamp.admin.domain.script.model.ScriptType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = ScriptType.Values.INSTALL)
@Table(name = "lamp_app_install_script")
@PrimaryKeyJoinColumn(name = "id")
public class AppInstallScript extends Script {

	@Column(updatable = false)
	private String templateId;

}
