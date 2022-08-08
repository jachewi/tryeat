package shop.tryit.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import shop.tryit.domain.member.entity.MemberRole;
import shop.tryit.domain.member.service.MemberSecurityService;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebSecurityConfig {

    private final MemberSecurityService memberSecurityService;

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests() //요청에 의한 보안 검사 시작
                .antMatchers("/", "/css/**", "/img/**", "/js/**", "/vendor/**", "/files/**",
                        "/members/new", "/members/login", "/members/logout", "/members/vendor/**",
                        "/items", "/items/{id}", "/items/vendor/**", "/qna", "/notices", "/notices/{noticeId}").permitAll()
                .antMatchers("/members/update", "/carts", "/carts/{cartItemId}/update", "/carts/{cartItemId}/delete",
                        "/qna/new", "/qna/{questionId}", "/qna/{questionId}/update", "/qna/{questionId}/delete",
                        "/orders", "/orders/new", "/orders/{orderId}", "/payment", "/payment/kakao").authenticated()
                .antMatchers("/items/new", "/items/{id}/update", "/items/{id}/delete",
                        "/answers", "/answers/new/{questionId}", "/answers/{answerId}/update", "/answers/delete/{answerId}",
                        "/notices/new", "/notices/{noticeId}/update", "/notices/{noticeId}/delete").hasRole(MemberRole.ADMIN.name())

                .anyRequest().authenticated() //어떤 요청에도 보안 검사를 실생
                .and()
                .formLogin()
                .loginPage("/members/login")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
