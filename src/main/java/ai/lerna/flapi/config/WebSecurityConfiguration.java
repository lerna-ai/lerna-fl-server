package ai.lerna.flapi.config;

import ai.lerna.flapi.api.AdminApi;
import ai.lerna.flapi.api.ConfigApi;
import ai.lerna.flapi.api.RecommendationApi;
import ai.lerna.flapi.api.TestApi;
import ai.lerna.flapi.api.TrainingApi;
import ai.lerna.flapi.api.TrainingApiV2;
import ai.lerna.flapi.api.UserApi;
import ai.lerna.flapi.config.jwt.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private JwtRequestFilter jwtRequestFilter;

	public WebSecurityConfiguration(JwtRequestFilter jwtRequestFilter) {
		this.jwtRequestFilter = jwtRequestFilter;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable()
				// dont authenticate this particular request
				.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				.antMatchers(UserApi.path + "/authenticate").permitAll()
				.antMatchers(AdminApi.path + "/**").permitAll()
				.antMatchers(TestApi.path + "/**").permitAll()
				.antMatchers(TrainingApi.path + "/**").permitAll()
				.antMatchers(TrainingApiV2.path + "/**").permitAll()
				.antMatchers(ConfigApi.path + "/**").permitAll()
				.antMatchers(RecommendationApi.path + "/**").permitAll()
				// authenticate all other particular request
				.anyRequest().authenticated()
				.and().exceptionHandling().authenticationEntryPoint((req, res, ex) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED : " + ex.getMessage()))
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				// Add a filter to validate the tokens with every request
				.and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
