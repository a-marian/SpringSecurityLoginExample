package ar.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	/** As this is an example we will set up users in memory,
	 *  not safe for production */
	@Bean
	public InMemoryUserDetailsManager userDetailsManager() {
		UserDetails user = User.withDefaultPasswordEncoder()
						.username("marian").password("12345").roles("USER").build();
		UserDetails admin = User.withDefaultPasswordEncoder()
						.username("admin").password("11111").roles("ADMIN").build();
		UserDetails dba = User.withDefaultPasswordEncoder()
						.username("dbadmin").password("22222").roles("DBA").build();
		return new InMemoryUserDetailsManager(user, admin, dba);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

		return http
				.csrf().disable()
				.authorizeHttpRequests((authz) -> authz
								.requestMatchers("/").permitAll()
								.requestMatchers("/", "/home").hasRole("USER")
								.requestMatchers("/admin/**", "/dba/**").hasRole("ADMIN")
								.requestMatchers("/dba/**").hasRole("DBA")
								.anyRequest().authenticated())
				.formLogin(Customizer.withDefaults())
				.sessionManagement((sessionMngmt) -> sessionMngmt.maximumSessions(1)).build();
	}

	
}
