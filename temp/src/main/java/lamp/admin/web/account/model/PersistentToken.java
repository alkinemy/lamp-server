package lamp.admin.web.account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "lamp_persistent_token")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersistentToken implements Serializable {

	private static final int MAX_USER_AGENT_LEN = 255;

	@Id
	private String series;

	@JsonIgnore
	@NotNull
	@Column(name = "token_value", nullable = false)
	private String tokenValue;

	@JsonIgnore
	@Column(name = "token_date")
	private LocalDateTime tokenDate;

	//an IPV6 address max length is 39 characters
	@Size(min = 0, max = 39)
	@Column(name = "ip_address", length = 39)
	private String ipAddress;

	@Column(name = "user_agent")
	private String userAgent;

	@JsonIgnore
	@ManyToOne
	private User user;


	public void setUserAgent(String userAgent) {
		if (userAgent.length() >= MAX_USER_AGENT_LEN) {
			this.userAgent = userAgent.substring(0, MAX_USER_AGENT_LEN - 1);
		} else {
			this.userAgent = userAgent;
		}
	}


}