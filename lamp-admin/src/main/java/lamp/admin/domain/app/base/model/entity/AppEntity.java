package lamp.admin.domain.app.base.model.entity;

import lamp.admin.core.app.base.AppContainer;
import lamp.admin.domain.base.model.AbstractAuditingEntity;
import lamp.admin.domain.old.app.model.ParametersType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "lamp_app")
public class AppEntity extends AbstractAuditingEntity {

	@Id
	private String id;
	private String groupId;

	private int cpu;
	private int memory;
	private int diskSpace;
	private int instances;

	private String data;

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
	private String ptql;

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

	@Column(columnDefinition = "TINYINT", nullable = false)
	private Boolean deleted;

}
