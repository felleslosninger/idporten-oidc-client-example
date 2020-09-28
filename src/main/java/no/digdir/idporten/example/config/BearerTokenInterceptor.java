package no.digdir.idporten.example.config;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Clock;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class BearerTokenInterceptor implements ClientHttpRequestInterceptor {

    private final OAuth2AuthorizedClientService clientService;
    private final OAuth2AuthorizedClientManager clientManager;


    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] bytes, ClientHttpRequestExecution execution) throws IOException {

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if(clientService != null && oauthToken != null) {
            String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
            OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(clientRegistrationId, oauthToken.getName());

            if (hasTokenExpired(client.getAccessToken())) { // token expired - attempt to re-authorize with refresh_token
                OAuth2AuthorizeRequest authzRequest = OAuth2AuthorizeRequest.withClientRegistrationId(clientRegistrationId).principal(oauthToken).build();
                OAuth2AuthorizedClient reauthorizedClient = clientManager.authorize(authzRequest);
                if(reauthorizedClient != null) {
                    clientService.saveAuthorizedClient(reauthorizedClient, oauthToken);
                    client = reauthorizedClient;
                }
            }

            String accessToken = client.getAccessToken().getTokenValue();
            request.getHeaders().set("Authorization", "Bearer " + accessToken);
        }

        return execution.execute(request, bytes);
    }


    private Duration clockSkew = Duration.ofSeconds(10);
    private Clock clock = Clock.systemUTC();
    private boolean hasTokenExpired(AbstractOAuth2Token token) {
        return this.clock.instant().isAfter(token.getExpiresAt().minus(this.clockSkew));
    }

}
