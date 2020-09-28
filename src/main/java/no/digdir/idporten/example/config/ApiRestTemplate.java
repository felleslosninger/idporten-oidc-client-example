package no.digdir.idporten.example.config;

import no.digdir.idporten.example.config.dev.DeveloperLoggingRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("restTemplate")
public class ApiRestTemplate extends RestTemplate {

    public ApiRestTemplate(@Autowired(required = false) DeveloperLoggingRequestInterceptor loggingInterceptor,
                           BearerTokenInterceptor bearerTokenInterceptor) {
        this.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        this.getInterceptors().add(bearerTokenInterceptor);
        if(loggingInterceptor != null) { // only enabled in 'dev' profile
            this.getInterceptors().add(loggingInterceptor);
        }
    }
}