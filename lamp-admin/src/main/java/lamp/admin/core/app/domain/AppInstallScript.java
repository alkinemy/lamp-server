package lamp.admin.core.app.domain;

import lamp.admin.core.base.domain.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "lamp_app_install_script")
public class AppInstallScript extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(updatable = false)
	private Long templateId;

	private String name;
	private String description;
	private String version;

	@OneToMany(mappedBy = "appInstallScript")
	private List<AppInstallCommand> commands;

}
