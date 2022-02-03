# idporten-oidc-client-example
Minimalt eksempel med react frontend og backend-for-frontend for OIDC ID-porten for kunder. Dette eksempelprosjektet viser innlogging via ID-porten, og bruk av tredjeparts-api beskyttet av ID-porten. API:et lister opp innlogget bruker sin innloggingshistorikk (https://docs.digdir.no/docs/idporten/oidc/oidc_api_logghistorikk) 

Full dokumentasjon finner du her: https://docs.digdir.no/docs/idporten/idporten/idporten_overordnet

### Noen punkter å tenke på:
  * Applikasjonen sin sesjon er helt uavhengig ID-porten sin sesjon. 
  * Ingen feilhåndtering er konfigurert i eksempelprosjektet

### Test drive
 * Legg inn api-url, client-id, client-secret og oidc provider url i src/main/resources/application.yml
 * mvn clean package spring-boot:run
 * App tilgjengelig på https://localhost:8443/

## Klientkonfigurasjon 
Dokumentasjon finnes på https://docs.digdir.no/docs/idporten/oidc/oidc_func_clientreg, og selvbetjeningsløsning finnes på https://selvbetjening-samarbeid.difi.no/

### Eksempel på klientkonfigurasjon:
```
{
    "client_name": "DIGITALISERINGSDIREKTORATET",
    "description": "Testklient for idporten-oidc-client-example",
    "scopes": [
        "openid",
        "profile",
        "idporten:user.log.read"
    ],
    "redirect_uris": [
        "https://localhost:8443/login/oauth2/code/idporten"        
    ],
    "post_logout_redirect_uris": [
        "https://localhost:8443/"
    ],
    "authorization_lifetime": 3600,
    "access_token_lifetime": 60,
    "refresh_token_lifetime": 3600,
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