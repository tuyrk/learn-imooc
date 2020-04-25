package com.tuyrk.jdk11;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

/**
 * 3-4 标准 HTTP 客户端
 *
 * @author tuyrk
 */
public class HttpClientExample {
    /**
     * 同步Get方法
     *
     * @param uri uri
     * @throws Exception 方法异常
     */
    private static void syncGet(String uri) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

    /**
     * 异步Get方法
     *
     * @param uri uri
     * @throws Exception 方法异常
     */
    private static void asyncGet(String uri) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();
        CompletableFuture<HttpResponse<String>> future = client.sendAsync(request,
                HttpResponse.BodyHandlers.ofString());
        future.whenComplete((response, e) -> {
            if (e != null) {
                e.printStackTrace();
            } else {
                System.out.println(response.statusCode());
                System.out.println(response.body());
            }
        }).join();
    }

    public static void main(String[] args) throws Exception {
        String uri = "http://t.weather.sojson.com/api/weather/city/101270101";
        syncGet(uri);
        asyncGet(uri);
    }
}
