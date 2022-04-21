package com.acs.core.services;

import lombok.NonNull;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Service for getting Job Details by ID.
 */
@Component(service = FastlyPurger.class, immediate = true)
@Designate(ocd = FastlyPurgerConfiguration.class)
public class FastlyPurger {

    private static final String METHOD_PURGE = "PURGE";
    private static final int CONNECTION_TIMEOUT = 20;
    public static final String X_AEM_PURGE_KEY = "x-aem-purge-key";

    FastlyPurgerConfiguration config;

    HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(CONNECTION_TIMEOUT))
            .build();

    /**
     * Init config
     * @param config
     */
    @Activate
    @Modified
    protected void activate(FastlyPurgerConfiguration config) {
        this.config = config;
    }

    public String getHost() {
        return Optional.ofNullable(config)
                .map(FastlyPurgerConfiguration::fastlyHost)
                .orElse(null);
    }

    public String getKey() {
        return Optional.ofNullable(config)
                .map(FastlyPurgerConfiguration::fastlyPurgeKey)
                .orElse(null);
    }

    /**
     * Purge with default host
     * @param path the real published path relative to the fastly domain
     * @return
     */
    public CompletableFuture<HttpResponse<String>> purgeAsync(String path) {
        return purgeAsync(getHost(), path);
    }


    /**
     * Purge with default host
     * @param path the real published path relative to the fastly domain
     * @return
     */
    public CompletableFuture<HttpResponse<String>> purgeAsync(@NonNull String host, @NonNull String path) {
        // TOTO: validate that host is not blank, and that path starts with "/"
        HttpRequest request = getRequest(host, path, getKey());
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> purge(@NonNull String path) throws IOException, InterruptedException {
        return purge(getHost(), path);
    }

    public HttpResponse<String> purge(@NonNull String host, @NonNull String path) throws IOException, InterruptedException {
        HttpRequest request = getRequest(host, path, getKey());
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static HttpRequest getRequest(String host, String path, String key) {
        return HttpRequest.newBuilder()
                .uri(URI.create(host + path))
                .header(X_AEM_PURGE_KEY, key)
                .method(METHOD_PURGE, HttpRequest.BodyPublishers.noBody())
                .build();
    }
}
