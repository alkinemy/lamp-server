package lamp.server.aladin.api.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ApiAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		log.info("AuthenticationManager = {}, {}", request.getRequestURI(), authenticationManager);

		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(new ApiAuthenticationToken(AuthorityUtils.createAuthorityList("ROLE_API")));

		filterChain.doFilter(request, response);
	}
}
