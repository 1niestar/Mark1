package com.iniestar.mark1.config;

import com.iniestar.mark1.config.handler.CustomAccessDeniedHandler;
import com.iniestar.mark1.config.handler.CustomLoginFailureHandler;
import com.iniestar.mark1.config.handler.CustomLoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

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

    /**
     * 스프링 시큐리티가 사용자를 인증하는 방법이 담긴 객체를 설정정하는 함수
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        	auth.inMemoryAuthentication()
			.withUser("admin")
			// In spring security 5 must determine the algorithm that
			// your passwords are stored in and prefix ass passwords with {id}
			.password(passwordEncoder().encode("admin123")) // To migrate plain text password by prefixing them with {noop}
			.roles("ADMIN");
    }



    /**
     * 스프링 시큐리티 룰을 무시하게 되는 URL
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/img/**", "/scss/**", "/js/**");
    }

    /**
     * 스프링 시큐리티의 접근 권한 및 기타 세션 설정
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.csrf().disable()// 보안설정을 비활성화
               .authorizeRequests()// 그외 리소스들은 인증을해야 접근 가능
               .antMatchers("/api/**", "/login", "/register", "resources") .permitAll()
               .anyRequest().authenticated()
               .and()

               .formLogin()
               .loginPage("/login") // 별도 로그인 페이지 사용
               .loginProcessingUrl("/doLogin")
               .usernameParameter("username")
               .passwordParameter("password")
           //    .anyRequest().authenticated() // 그외 리소스들은 인증을해야 접근 가능
               .successHandler(SuccessHandler()) // 로그인 성공 시 별도 핸들러 사용
               .failureHandler(FailureHandler()) // 로그인 실패 시 별도 핸들러 사용

               .and()
               .logout()
               .logoutUrl("/doLogout")
               .invalidateHttpSession(true).deleteCookies("JSESSIONID")
//               //<=> same codes
//               .addLogoutHandler((httpServletRequest, httpServletResponse, authentication)-> {
//                   HttpSession session = httpServletRequest.getSession();
//                   session.invalidate();
//               })
               .logoutSuccessUrl("/login") // 로그아웃 성공 시 URL 리턴

               .and()
               .exceptionHandling()
               .accessDeniedHandler(AccessDeniedHandler()) // 별도 Access Denied 핸들러 사용

               .and()
               .sessionManagement()
               .maximumSessions(1) // 같은 아이디로 1명만 로그인할 수 있음
               .maxSessionsPreventsLogin(true) // 신규 로그인 사용자의 로그인 허용되고, 기존 사용자는 로그아웃
                ;
    }

}
