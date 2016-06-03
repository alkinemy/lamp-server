package lamp.admin.domain.old.app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class LogFile {

	private String name;
	private long size;
	private Date lastModified;

}
