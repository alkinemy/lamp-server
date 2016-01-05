package lamp.server.aladin.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@CreatedBy
	@NotNull
	@Column(name = "created_by", nullable = false, length = 50, updatable = false)
	@JsonIgnore
	private String createdBy;

	@CreatedDate
	@NotNull
	@Column(name = "created_date", nullable = false, updatable = false)
	@JsonIgnore
	private LocalDateTime createdDate;

	@LastModifiedBy
	@Column(name = "last_modified_by", length = 50)
	@JsonIgnore
	private String lastModifiedBy;

	@LastModifiedDate
	@Column(name = "last_modified_date")
	@JsonIgnore
	private LocalDateTime lastModifiedDate;

}