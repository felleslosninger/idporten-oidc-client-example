package no.digdir.oidcclientexample.config.dev;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;

@Profile({"dev"}) /* Only enabled when 'dev' profile is active */
@Component("corsConfigurationSource")
public class DeveloperCorsConfigurationSource extends UrlBasedCorsConfigurationSource {

    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest httpServletRequest) {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader("Authorization");
        configuration.addExposedHeader("Link");
        configuration.addExposedHeader("X-Total-Count");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(1800L);

        this.registerCorsConfiguration("/**", configuration);
        return configuration;
    }
}
