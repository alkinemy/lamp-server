package lamp.admin.web.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
public class UserRegisterForm {

	@NotNull
	@Pattern(regexp = "^[a-z0-9]*$|(anonymousUser)")
	private String login;

	@Email
	private String email;

	@NotNull
	private String password;

}
