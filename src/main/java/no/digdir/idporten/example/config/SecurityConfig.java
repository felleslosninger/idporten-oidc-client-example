package no.digdir.idporten.example.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ClientRegistrationRepository clientRegistrationRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionFixation().migrateSession()
            .and()
                .cors(withDefaults())
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .and()
                .logout()
                .logoutSuccessHandler(this.oidcLogoutSuccessHandler())
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // needed to allow GET if CSRF is configured
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)) // translate unauthenticated exception to a http status 401 response
            .and()
                .headers()
                // check with online CSP evaluator at https://csp-evaluator.withgoogle.com/
                .contentSecurityPolicy("base-uri 'self'; object-src 'none'; default-src 'self'; connect-src 'self' localhost; script-src 'self' 'unsafe-inline'; style-src 'self' 'unsafe-inline' ; font-src  'self' ; img-src 'self' data: ;  frame-ancestors 'none'; child-src 'self'; frame-src 'self';")
            .and()
                .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.SAME_ORIGIN)
            .and()
            .and()
                .authorizeRequests().antMatchers("/api/**").authenticated()
            .and()
                .oauth2Login()
                .successHandler(new SimpleUrlAuthenticationSuccessHandler("/"))
        ;
    }

    private LogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler = new OidcClientInitiatedLogoutSuccessHandler(this.clientRegistrationRepository);
        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}/"); // for now, post logout uri is just the front page
        return oidcLogoutSuccessHandler;
    }

}
