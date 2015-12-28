package lamp.server.aladin.api.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class ApiAuthenticationToken extends AbstractAuthenticationToken {

	public ApiAuthenticationToken(Collection<? extends GrantedAuthority>
		authorities) {
		super(authorities);
		super.setAuthenticated(true);
	}

	@Override public Object getCredentials() {
		return "hello";
	}

	@Override public Object getPrincipal() {
		return "world";
	}
}
