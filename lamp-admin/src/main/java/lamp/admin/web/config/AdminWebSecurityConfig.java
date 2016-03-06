package lamp.admin.web.config;

import lamp.admin.web.security.AdminUserDetailsService;
import lamp.admin.web.security.AuthoritiesConstants;
import lamp.admin.web.security.CustomPersistentRememberMeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Order(20)
@EnableWebSecurity
public class AdminWebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsService())
			.passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers("/scripts/**/*.{js,html}")
				.antMatchers("/bower_components/**")
				.antMatchers("/i18n/**")
				.antMatchers("/assets/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf()
				.ignoringAntMatchers("/websocket/**")
			.and()
				.rememberMe()
				.rememberMeServices(rememberMeServices())
				.rememberMeParameter("remember-me")
				.key(env.getProperty("lamp.security.rememberme.key"))
			.and()
				.logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.invalidateHttpSession(true)
					.logoutSuccessUrl("/login")
			.and()
				.formLogin()
					.loginPage("/login")
	//				.successHandler(ajaxAuthenticationSuccessHandler)
	//				.failureHandler(ajaxAuthenticationFailureHandler)
					.usernameParameter("username")
					.passwordParameter("password")
					.permitAll()
			.and()
				.authorizeRequests()
					.antMatchers("/signup", "/forget").permitAll()
					.anyRequest().hasAuthority(AuthoritiesConstants.ADMIN)
		;


//			.and()
//				.logout()
//				.logoutUrl("/api/logout")
//				.logoutSuccessHandler(ajaxLogoutSuccessHandler)
//				.deleteCookies("JSESSIONID", "hazelcast.sessionId")
//				.permitAll()
//			.and()
//				.headers()
//				.frameOptions()
//				.disable()
//			.and()
//				.authorizeRequests()
//				.antMatchers("/api/register").permitAll()
//				.antMatchers("/api/activate").permitAll()
//				.antMatchers("/api/authenticate").permitAll()
//				.antMatchers("/api/account/reset_password/init").permitAll()
//				.antMatchers("/api/account/reset_password/finish").permitAll()
////				.antMatchers("/api/logs/**").hasAuthority(AuthoritiesConstants.ADMIN)
////				.antMatchers("/api/audits/**").hasAuthority(AuthoritiesConstants.ADMIN)
//				.antMatchers("/api/**").authenticated()
////				.antMatchers("/websocket/tracker").hasAuthority(AuthoritiesConstants.ADMIN)
////				.antMatchers("/websocket/**").permitAll()
////				.antMatchers("/metrics/**").hasAuthority(AuthoritiesConstants.ADMIN)
////				.antMatchers("/health/**").hasAuthority(AuthoritiesConstants.ADMIN)
////				.antMatchers("/trace/**").hasAuthority(AuthoritiesConstants.ADMIN)
////				.antMatchers("/dump/**").hasAuthority(AuthoritiesConstants.ADMIN)
////				.antMatchers("/shutdown/**").hasAuthority(AuthoritiesConstants.ADMIN)
////				.antMatchers("/beans/**").hasAuthority(AuthoritiesConstants.ADMIN)
////				.antMatchers("/configprops/**").hasAuthority(AuthoritiesConstants.ADMIN)
////				.antMatchers("/info/**").hasAuthority(AuthoritiesConstants.ADMIN)
////				.antMatchers("/autoconfig/**").hasAuthority(AuthoritiesConstants.ADMIN)
////				.antMatchers("/env/**").hasAuthority(AuthoritiesConstants.ADMIN)
////				.antMatchers("/trace/**").hasAuthority(AuthoritiesConstants.ADMIN)
////				.antMatchers("/mappings/**").hasAuthority(AuthoritiesConstants.ADMIN)
////				.antMatchers("/liquibase/**").hasAuthority(AuthoritiesConstants.ADMIN)
////				.antMatchers("/v2/api-docs/**").permitAll()
////				.antMatchers("/configuration/security").permitAll()
////				.antMatchers("/configuration/ui").permitAll()
////				.antMatchers("/swagger-ui/index.html").hasAuthority(AuthoritiesConstants.ADMIN)
//				.antMatchers("/protected/**").authenticated() ;
//
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new AdminUserDetailsService();
	}

	@Bean
	public RememberMeServices rememberMeServices() {
		return new CustomPersistentRememberMeServices(env.getProperty("lamp.security.rememberme.key"), userDetailsService());
	}


}
