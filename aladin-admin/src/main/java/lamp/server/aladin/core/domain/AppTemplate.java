package lamp.server.aladin.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "app_template")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "template_type", discriminatorType = DiscriminatorType.STRING)
public class AppTemplate extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String description;

	private String appName;
	private String appVersion;

	private AppProcessType processType;

	private String pidFile;

	private String startCommandLine;
	private String stopCommandLine;

	private boolean preInstalled;
	private String appFilename;

	private boolean monitor;

	private String commands;
}
