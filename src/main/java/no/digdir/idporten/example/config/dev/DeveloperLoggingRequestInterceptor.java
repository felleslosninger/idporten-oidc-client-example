package no.digdir.idporten.example.config.dev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Profile({"dev"}) /* Only enabled when 'dev' profile is active */
@Component
public class DeveloperLoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    private final static Logger log = LoggerFactory.getLogger(DeveloperLoggingRequestInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        printRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        printResponse(response);
        return response;
    }

    private void printRequest(HttpRequest request, byte[] body) throws IOException {
        log.debug("*** request BEGIN ***");
        log.debug("*** URI         : {}", request.getURI());
        log.debug("*** Method      : {}", request.getMethod());
        log.debug("*** Headers     : {}", request.getHeaders() );
        log.debug("*** Request body: {}", new String(body, StandardCharsets.UTF_8));
        log.debug("*** request END ***");
    }

    private void printResponse(ClientHttpResponse response) throws IOException {
        StringBuilder inputStringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8));
            String line = bufferedReader.readLine();
            while (line != null) {
                inputStringBuilder.append(line);
                inputStringBuilder.append('\n');
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            // do nothing
        }
        log.debug("*** response BEGIN ***");
        log.debug("*** Status code  : {}", response.getStatusCode());
        log.debug("*** Status text  : {}", response.getStatusText());
        log.debug("*** Headers      : {}", response.getHeaders());
        log.debug("*** Response body: {}", inputStringBuilder.toString());
        log.debug("*** response END ***");
    }

}
