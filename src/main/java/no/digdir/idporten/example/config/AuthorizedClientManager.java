package no.digdir.idporten.example.config;

import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.stereotype.Component;

@Component("OAuth2AuthorizedClientManager")
public class AuthorizedClientManager implements OAuth2AuthorizedClientManager  {

    private final DefaultOAuth2AuthorizedClientManager authorizedClientManager;

    public AuthorizedClientManager(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientRepository authorizedClientRepository) {
        this.authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository);
        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .authorizationCode()
                        .refreshToken()
                        .build();
        this.authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
    }


    @Override
    public OAuth2AuthorizedClient authorize(OAuth2AuthorizeRequest authorizeRequest) {
        return authorizedClientManager.authorize(authorizeRequest);
    }
}
