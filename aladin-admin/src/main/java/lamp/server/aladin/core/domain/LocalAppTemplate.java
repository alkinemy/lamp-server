package lamp.server.aladin.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = AppFileType.Values.LOCAL)
@Table(name = "app_local_template")
@PrimaryKeyJoinColumn(name = "id")
public class LocalAppTemplate extends AppTemplate {

	private LocalAppRepo appRepository;

	@Column(name = "group_id")
	private String groupId;

}
