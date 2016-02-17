package lamp.admin.core.app.domain;

import lamp.admin.core.base.domain.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "lamp_app_install_command")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "command_type", discriminatorType = DiscriminatorType.STRING)
public class AppInstallCommand extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "app_install_script_id")
	private AppInstallScript appInstallScript;

	private int seq;
	private String description;

}
