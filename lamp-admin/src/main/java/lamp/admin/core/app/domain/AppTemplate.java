package lamp.admin.core.app.domain;

import lamp.admin.core.base.domain.AbstractAuditingEntity;
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
	private String logDirectory;

	private String pidFile;

	private String stdOutFile;
	private String stdErrFile;

	private String commandShell;
	private String startCommandLine;
	private String stopCommandLine;

	private boolean preInstalled;
	private String appFilename;

	private boolean monitor;

	private ParametersType parametersType;
	private String parameters;

	private String commands;

	@Column(columnDefinition = "TINYINT", nullable = false)
	private Boolean deleted;

}
