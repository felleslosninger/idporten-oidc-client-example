# idporten-oidc-client-example
Minimalt eksempel med react frontend og backend-for-frontend for OIDC ID-porten for kunder.

### Noen punkter å tenke på:
  * Ingen feilhåndtering - trenger å oversette exceptions fra spring security/oidc-provider/idporten-api til json response (f.eks. https://github.com/zalando/problem-spring-web)

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