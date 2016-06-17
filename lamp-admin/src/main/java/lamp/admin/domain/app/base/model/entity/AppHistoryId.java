package lamp.admin.domain.app.base.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class AppHistoryId implements Serializable {

	private String id;
	private String version;

}
