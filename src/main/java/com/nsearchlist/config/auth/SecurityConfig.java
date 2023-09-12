package com.nsearchlist.config.auth;

import com.nsearchlist.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity //스프링 시큐어리티 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**","/libs/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);

    }

}

// .csrf().disable().headers().frameOptions().disable() : h2-console 화면을 사용하기 위한 해당옵션 disale
// authorizeRequests : URL별 권한 관리를 설정하는 옵션의 시작점. 이게 선언되어야 antMatchers 옵션 사용 가능
// antMatchers : 권한 관리 지정 옵션. URL, HTTP 메소드별 관리 가능
// anyRequest 설정된 값들 이외 나머지 URL

//oauth2Login() oauth2 로그인 설정 진입점
// userInfoEndpoint : 로그인 성공 이후 사용자 정보 가져올 때 설정 담당
// userService 소셜 로그인 성공 시 후속조치를 진행할 UserService 인터페이스의 구현체를 등록
// 소셜 서비스에서 사용자 정보를 가져온 후 추가로 진행하고자 하는 작업 명시