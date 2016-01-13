package lamp.server.aladin.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = AppFileType.Values.LOCAL)
@Table(name = "app_local_file")
@PrimaryKeyJoinColumn(name = "id")
public class LocalAppFile extends AppFile {

	@Column(name = "group_id")
	private String groupId;

	private String pathname;

	private String filename;

}
