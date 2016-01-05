package lamp.server.aladin.api.security;

import lamp.server.aladin.api.support.jwt.JwtObject;
import lamp.server.aladin.api.support.jwt.JwtParser;
import lamp.server.aladin.api.support.jwt.JwtPayload;
import lamp.server.aladin.api.support.jwt.JwtVerifier;
import lamp.server.aladin.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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

	private JwtParser jwtParser = new JwtParser();
	private JwtVerifier jwtVerifier = new JwtVerifier();

	@Override protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		log.info("AuthenticationManager = {}, {}", request.getRequestURI(), authenticationManager);

		String token = request.getHeader("Authorization");
		if (token.startsWith("Bearer ")) {
			token = StringUtils.substringAfter(token, "Bearer ");
		}

		if (StringUtils.isNoneBlank(token)) {
			JwtObject jwtObject = jwtParser.parse(token);
			if (jwtVerifier.verify(jwtObject)) {
				JwtPayload payload = jwtObject.getPayload();
				User user = new User(payload.getIssuer(), "", AuthorityUtils.createAuthorityList("ROLE_API"));
				SecurityContext securityContext = SecurityContextHolder.getContext();
				securityContext.setAuthentication(new ApiAuthenticationToken(user, null, user.getAuthorities()));
			}
		}

		filterChain.doFilter(request, response);
	}
}
