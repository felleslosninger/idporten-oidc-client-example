package no.digdir.idporten.example.integration;

import lombok.RequiredArgsConstructor;
import no.digdir.idporten.example.config.BearerTokenInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class IdportenUserLogClient {

    @Value("${digdir.userlog-api}")
    private String userlogApi;

    private final RestTemplate restTemplate = new RestTemplate();
    private final BearerTokenInterceptor bearerTokenInterceptor;

    @PostConstruct
    public void init() {
        restTemplate.setInterceptors(Collections.singletonList(bearerTokenInterceptor));
    }

    public String getUserlog() {
        // https://docs.digdir.no/docs/idporten/oidc/oidc_api_logghistorikk
        return restTemplate.exchange(userlogApi + "?maxhits=100", HttpMethod.GET, httpEntity(), String.class).getBody();
    }


    private HttpEntity httpEntity() {
        return new HttpEntity(headers());
    }

    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

}
