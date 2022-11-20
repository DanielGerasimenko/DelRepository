package com.daniel.delivery.config;

import com.daniel.delivery.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import static com.daniel.delivery.entity.Role.ADMIN;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PersonService personService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests(urlConfig -> urlConfig
                        .antMatchers("/login", "/swagger-ui/index.html").permitAll()
                        .antMatchers("/swagger-ui/index.html#/person-controller/getAllPerson").hasAuthority(ADMIN.getAuthority())
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID"))
                .formLogin(login -> login
                        .defaultSuccessUrl("/swagger-ui/index.html"))
                .oauth2Login(config -> config
                        .defaultSuccessUrl("/swagger-ui/index.html")
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(oidcUserService()))
                );
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        return userRequest -> {
            String email = userRequest.getIdToken().getClaim("email");
            UserDetails userDetails = personService.loadUserByUsername(email);

            return new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken());
        };

    }
}
