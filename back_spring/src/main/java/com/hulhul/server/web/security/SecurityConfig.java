package com.hulhul.server.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtTokenProvider jwtTokenProvider;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable() // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
				.csrf().disable() // rest api이므로 csrf 보안이 필요없으므로 disable처리.
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증할것이므로
																							// 세션필요없으므로 생성안함.
				.and().authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
				.requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // CORS preflight 요청
				.antMatchers("/**/login", "/**/signup").permitAll() // 가입 가능 / 로그인 가능
				.antMatchers(HttpMethod.GET, "/exception/**", "/api/**").permitAll() // 등록된 Get요청 가능
				.antMatchers("/api/v2/**").hasRole("USER") // v2에 대해서는 나머지 요청은 모두 인증된 회원만 접근 가능
				.anyRequest().permitAll() // 일단은 나머지에 대해 다 권한 허용
				.and().cors().configurationSource(corsConfigurationSource()) // ORIGIN있으면 CORS 허용
				.and().exceptionHandling().accessDeniedHandler(new AccessDeniedHandlerImpl()).and().exceptionHandling()
				.authenticationEntryPoint(new AuthenticationEntryPointImpl()).and().addFilterBefore(
						new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // jwt
																													// token
																													// 필터를
																													// id/password
																													// 인증
																													// 필터
																													// 전에넣기
	}

	@Override // ignore swagger security config
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**",
				"/swagger/**");
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		// - (3)
		configuration.addAllowedOrigin("*");
		configuration.addAllowedMethod("*");
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(360000L);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}