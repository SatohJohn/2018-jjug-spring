package jp.john.satoh.config;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.stream.Collectors;

public class ConfigAccess {
    public static void access(String url) throws IOException {
        HttpGet get = new HttpGet();
        get.setURI(URI.create(url));
        CloseableHttpClient build = HttpClientBuilder.create().build();

        try(CloseableHttpResponse response = build.execute(get);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {

            System.out.println("----------------------------------------------------------------------------------------------------");
            System.out.println(rd.lines().collect(Collectors.joining()));
            System.out.println("----------------------------------------------------------------------------------------------------");
        }

    }
}
