package lamp.server.aladin.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "app_template")
public class AppTemplate extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String description;

	@Column(name = "resource_type")
	@Enumerated(EnumType.STRING)
	private AppResourceType resourceType;

	@ManyToOne
	@JoinColumn(name = "repository_id", nullable = false)
	private AppRepo appRepository;

	@Column(name = "app_group_id")
	private String appGroupId;
	@Column(name = "app_id")
	private String appId;
	@Column(name = "app_name")
	private String appName;
	@Column(name = "app_version")
	private String appVersion;
	@Column(name = "app_url")
	private String appUrl;

	@Column(name = "process_type")
	@Enumerated(EnumType.STRING)
	private AppProcessType processType;

	@Column(name = "app_directory")
	private String appDirectory;
	@Column(name = "work_directory")
	private String workDirectory;

	@Column(name = "pid_file")
	private String pidFile;

	@Column(name = "command_shell")
	private String commandShell;
	@Column(name = "start_command_line")
	private String startCommandLine;
	@Column(name = "stop_command_line")
	private String stopCommandLine;

	@Column(name = "pre_installed")
	private boolean preInstalled;
	@Column(name = "app_filename")
	private String appFilename;

	private boolean monitor;

	private String commands;

	@Column(name = "deleted", columnDefinition = "TINYINT", nullable = false)
	private Boolean deleted;


}
