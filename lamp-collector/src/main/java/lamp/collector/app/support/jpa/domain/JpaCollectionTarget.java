package lamp.collector.app.support.jpa.domain;

import lamp.common.collection.CollectionTarget;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "lamp_collection_target")
public class JpaCollectionTarget implements CollectionTarget {

	@Id
	private String id;
	private String name;

	private String hostname;
	private String address;

	private String groupId;
	private String artifactId;
	private String version;

	@Column(columnDefinition = "TINYINT")
	private Boolean healthCollectionEnabled;
	private String healthType;
	private String healthUrl;
	private String healthExportPrefix;

	@Column(columnDefinition = "TINYINT")
	private Boolean metricsCollectionEnabled;
	private String metricsType;
	private String metricsUrl;
	private String metricsExportPrefix;

}
