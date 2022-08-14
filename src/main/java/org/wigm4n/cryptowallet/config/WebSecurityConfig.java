package org.wigm4n.cryptowallet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Here we have done the following configurations:
     * 1. cors() — Adds a CORS filter
     * 2. csrf().disable() — Disable CSRF protection as we won’t be needing that.
     * 3. sessionManagement() — We set the session management policy to STATELESS
     * because we are not going to use sessions (state) in this case.
     * 4. authorizeRequests() — Allow “/api/auth/.." endpoints to be accessed without authentication
     * and marks all the requests to other endpoints to as authentication required.
     * 5. oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt) — Configures the spring boot application
     * as an OAuth2 Resource Server which authenticates all the incoming requests (except the ones excluded above)
     * using JWT authentication. That is, all the requests should have an authorization header as below
     * which contains a valid JWT.
     * link: https://medium.com/swlh/stateless-jwt-authentication-with-spring-boot-a-better-approach-1f5dbae6c30f
     */

    @Value("${web-security.config.ant-pattern}")
    private String antPattern;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests(
                        configurer -> configurer
                                .antMatchers(antPattern)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }
}
