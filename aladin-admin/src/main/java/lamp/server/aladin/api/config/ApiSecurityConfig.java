package lamp.server.aladin.api.config;

import lamp.server.aladin.api.security.ApiAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Order(10)
@EnableWebSecurity
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.antMatcher("/api/**")
			.authorizeRequests().anyRequest().hasAuthority("ROLE_API")
			.and()
			.addFilterAfter(new ApiAuthenticationFilter(), BasicAuthenticationFilter.class)
			.exceptionHandling()
			.authenticationEntryPoint(http403ForbiddenEntryPoint());
		//
	}

	@Bean Http403ForbiddenEntryPoint http403ForbiddenEntryPoint() {
		return new Http403ForbiddenEntryPoint();
	}

}
