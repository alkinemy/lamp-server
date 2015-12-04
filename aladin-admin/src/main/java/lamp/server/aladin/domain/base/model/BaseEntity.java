package lamp.server.aladin.domain.base.model;

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
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@SuppressWarnings("PMD.UnusedPrivateField")
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

	@CreatedBy
	@Column(name = "createdBy")
	private String createdBy;

	@CreatedDate
	@Column(name = "createdDate")
	private Date createdDate;

	@LastModifiedBy
	@Column(name = "lastModifiedBy")
	private String lastModifiedBy;

	@LastModifiedDate
	@Column(name = "lastModifiedDate")
	private Date lastModifiedDate;

}
