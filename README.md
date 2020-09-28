# idporten-oidc-client-example
Minimalt eksempel med react frontend og backend-for-frontend for OIDC ID-porten for kunder.

### Noen punkter å tenke på:
  * Ingen feilhåndtering - trenger å oversette exceptions fra spring security/oidc-provider/idporten-api til json response (f.eks. https://github.com/zalando/problem-spring-web)
  * Applikasjonen sin sesjon er helt uavhengig idporten sin sesjon.

### Test drive
 * Legg inn api-url, client-id, client-secret og oidc provider url i src/main/resources/application.yml
 * mvn clean install -DskipTests=true && mvn spring-boot:run
 * App tilgjengelig på https://localhost:8443/
  
### Lokal utvikling
 * Legg inn api-url, client-id, client-secret og oidc provider url i src/main/resources/application-dev.yml
 * mvn clean install -DskipTests=true && mvn spring-boot:run -Dspring-boot.run.profiles=dev
 * cd src/main/react && yarn install && yarn start
 * App (via nodejs) tilgjengelig  på http://localhost:3000
 * Api tilgjengelig på https://localhost:8443/api
 

## Klientkonfigurasjon 
Dokumentasjon finnes på https://difi.github.io/felleslosninger/oidc_func_clientreg.html og selvbetjeningsløsning finnes på https://selvbetjening-samarbeid.difi.no/

For 
### Eksempel på klientkonfigurasjon:
```
{
    "client_name": "DIGITALISERINGSDIREKTORATET",
    "description": "Testklient for idporten-oidc-client-example",
    "scopes": [
        "openid",
        "profile",
        "idporten:user.log.all.read"
    ],
    "redirect_uris": [
        "http://localhost:8080/login/oauth2/code/idporten",
        "https://localhost:8443/login/oauth2/code/idporten"
    ],
    "post_logout_redirect_uris": [
        "https://localhost:8443/",
        "https://localhost:8080/"
    ],
    "authorization_lifetime": 3600,
    "access_token_lifetime": 60,
    "refresh_token_lifetime": 600,
    "refresh_token_usage": "ONETIME",
    "frontchannel_logout_session_required": false,
    "token_endpoint_auth_method": "client_secret_post",
    "grant_types": [
        "refresh_token",
        "authorization_code"
    ],
    "integration_type": "api_klient",
    "application_type": "web",
    "client_uri": "https://www.digdir.no",
    "last_updated": "2020-09-28T10:38:53.841+02:00",
    "created": "2020-09-17T12:12:36.433+02:00",
    "client_id": "idporten_oidc_client_example",
    "client_orgno": "991825827",
    "active": true
}
```