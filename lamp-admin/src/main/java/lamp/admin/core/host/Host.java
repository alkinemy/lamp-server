package lamp.admin.core.host;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class Host {

	private String id;
	private String description;

	private String name;
	private String address;

	private String status;
	private long lastHealthCheck;
	private String agentVersion;
	private String agentAccessKey;
	private String agentSecretKey;

	private Long clusterId;
	private String rack;

	private List<String> roles;

	private Map<String, String> tags;

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
