package no.digdir.idporten.example.api;

import lombok.RequiredArgsConstructor;
import no.digdir.idporten.example.integration.IdportenUserLogClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiEndpoint {

    private final IdportenUserLogClient idportenUserLogClient;

    @GetMapping("/authcheck")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> isAuthenticated() {
        return ResponseEntity.ok("{\"status\": \"Great success!\"}");
    }

    @GetMapping("/userlog")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> getEventlog() {
        return ResponseEntity.ok(idportenUserLogClient.getUserlog());
    }

}