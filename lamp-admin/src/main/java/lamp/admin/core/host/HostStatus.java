package lamp.admin.core.host;

import lamp.admin.domain.host.model.HostStateCode;
import lamp.admin.domain.host.model.HostStatusCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.DecimalFormat;
import java.util.Date;

@Getter
@Setter
@ToString
public class HostStatus {

	private HostStateCode state;
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


	public int getDiskUsedPercentage() {
		if (diskTotal == 0) {
			return 0;
		}

		return (int) ((diskUsed * 1.0d) / diskTotal * 100);
	}

	public String getDiskUsedPercentageLevel() {
		int p = getDiskUsedPercentage();
		if (p > 80) {
			return "danger";
		} else if (p > 60) {
			return "warn";
		} else {
			return "info";
		}
	}

	public int getMemUsedPercentage() {
		if (memTotal == 0) {
			return 0;
		}
		return (int) ((memUsed * 1.0d) / memTotal * 100);
	}

	public String getMemUsedPercentageLevel() {
		int p = getMemUsedPercentage();
		if (p > 80) {
			return "danger";
		} else if (p > 60) {
			return "warn";
		} else {
			return "info";
		}
	}

	public String getReadableDiskTotal() {
		return readableFileSize(getDiskTotal());
	}

	public String getReadableDiskUsed() {
		return readableFileSize(getDiskUsed());
	}

	public String getReadableMemTotal() {
		return readableFileSize(getMemTotal());
	}

	public String getReadableMemUsed() {
		return readableFileSize(getMemUsed());
	}

	public static String readableFileSize(long size) {
		if(size <= 0) return "0";
		final String[] units = new String[] { "B", "KiB", "MiB", "GiB", "TiB" };
		int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
		return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}

}
