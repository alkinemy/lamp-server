package lamp.server.aladin.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "lamp_app_template")
public class AppTemplate extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String description;

	@Enumerated(EnumType.STRING)
	private AppResourceType resourceType;

	@ManyToOne
	@JoinColumn(name = "repository_id", nullable = false)
	private AppRepo appRepository;

	private String groupId;
	private String artifactId;
	private String artifactName;
	private String version;

	private String resourceUrl;

	@Enumerated(EnumType.STRING)
	private AppProcessType processType;

	private String appDirectory;
	private String workDirectory;

	private String pidFile;

	private String logFile;
	private String errorLogFile;

	private String commandShell;
	private String startCommandLine;
	private String stopCommandLine;

	private boolean preInstalled;
	private String appFilename;

	private boolean monitor;

	private String commands;

	@Column(columnDefinition = "TINYINT", nullable = false)
	private Boolean deleted;

}
