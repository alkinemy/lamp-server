package lamp.admin.api.security;

import lamp.admin.domain.support.jwt.JwtObject;
import lamp.admin.domain.support.jwt.JwtParser;
import lamp.admin.domain.support.jwt.JwtPayload;
import lamp.admin.domain.support.jwt.JwtVerifier;
import lamp.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ApiAuthenticationFilter extends OncePerRequestFilter {

	private JwtParser jwtParser = new JwtParser();
	private JwtVerifier jwtVerifier = new JwtVerifier();

	@Override protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		String token = request.getHeader("Authorization");

		try {
			if (StringUtils.isNotBlank(token)) {
				if (token.startsWith("Bearer ")) {
					token = StringUtils.substringAfter(token, "Bearer ");
				}

				JwtObject jwtObject = jwtParser.parse(token);
				if (jwtVerifier.verify(jwtObject)) {
					JwtPayload payload = jwtObject.getPayload();
					// TODO Authority 구현 필요
					User user = new User(payload.getIssuer(), "", AuthorityUtils.createAuthorityList("ROLE_API"));
					SecurityContext securityContext = SecurityContextHolder.getContext();
					securityContext.setAuthentication(new AgentAuthenticationToken(user, null, user.getAuthorities()));
				}
			}
		} catch (Exception e) {
			log.warn("ApiAuthorization failed", e);
			HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			ResponseStatus responseStatus = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);
			if (responseStatus != null) {
				httpStatus = responseStatus.value();
			}
			response.sendError(httpStatus.value(), e.getMessage());
			return;
		}

		filterChain.doFilter(request, response);
	}
}
