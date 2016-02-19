package lamp.admin.core.app.domain;

import lamp.admin.core.script.domain.Script;
import lamp.admin.core.script.domain.ScriptType;
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
	private Long templateId;

}
