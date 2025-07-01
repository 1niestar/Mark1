package com.iniestar.mark1.config;

import com.iniestar.mark1.config.handler.CustomAccessDeniedHandler;
import com.iniestar.mark1.config.handler.CustomLoginFailureHandler;
import com.iniestar.mark1.config.handler.CustomLoginSuccessHandler;
import com.iniestar.mark1.config.handler.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationSuccessHandler SuccessHandler()
    {
        return new CustomLoginSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler FailureHandler()
    {
        return new CustomLoginFailureHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder(); // 스프링 시큐리티에서 제공하는 패스워드 해싱 함수
    }

    @Bean
    public AccessDeniedHandler AccessDeniedHandler()
    {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable).httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests().antMatchers("/api/admin/").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin(form -> form.loginPage("/login")
                        .successHandler(SuccessHandler())
                        .failureHandler(FailureHandler()))
                .sessionManagement(session -> session.maximumSessions(1))
                .exceptionHandling(ex -> ex.accessDeniedHandler(AccessDeniedHandler()))
                .logout(Customizer.withDefaults())
                .build();
    }

    @Bean // 인증제공자 인증처리
    protected AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;

    }

    @Bean // 인증매니저 스프링 문서 참조, global 설정 복사해옴
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean // 동시 로그인수 제어을 위해 필요
    protected HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean // webSecurity는 전체적인 설정(구성)에,httpSecurity는 구체적인 세부URL에
    protected WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.debug(false).ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico");
    }

    @Bean // 사용자 정의 UserDetailsService
    protected UserDetailsService customUserDetailsService() {
        return new CustomUserDetailService();
    }
}
