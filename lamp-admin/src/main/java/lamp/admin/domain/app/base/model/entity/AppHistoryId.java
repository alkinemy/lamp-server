package lamp.admin.domain.app.base.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AppHistoryId implements Serializable {

	private String id;
	private String version;

}
