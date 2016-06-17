package lamp.admin.domain.host.model.entity;

import lamp.admin.domain.host.model.HostStatusCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "lamp_host_status")
public class HostStatusEntity {

	@Id
	private String id;

	@Enumerated(EnumType.STRING)
	private HostStatusCode status;
	private Date lastStatusTime;

	// 로드 평균
	private double cpuUser;
	private double cpuNice;
	private double cpuSys;

	// 디스크 사용량
	private long diskTotal;
	private long diskUsed;
	private long diskFree;

	// 물리적 메모리
	private long memTotal;
	private long memUsed;
	private long memFree;

	// 스왑 공간
	private long swapTotal;
	private long swapUsed;
	private long swapFree;

}