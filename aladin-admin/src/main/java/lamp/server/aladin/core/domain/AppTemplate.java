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
public abstract class AppTemplate extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String description;

	@Column(name = "app_name")
	private String appName;
	@Column(name = "app_version")
	private String appVersion;

	@Column(name = "process_type")
	private AppProcessType processType;

	@Column(name = "pid_file")
	private String pidFile;

	@Column(name = "start_command_line")
	private String startCommandLine;
	@Column(name = "stop_command_ine")
	private String stopCommandLine;

	@Column(name = "pre_installed")
	private boolean preInstalled;
	@Column(name = "app_filename")
	private String appFilename;

	private boolean monitor;

	private String commands;

	@Column(name = "template_type", insertable = false, updatable = false)
	private AppFileType templateType;

}
