package jp.john.satoh.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Iterator;

public class ConfigTestForHttpClient {

    static NextServerInfo accessToEureka() throws IOException {
        HttpGet eurekaAccess = new HttpGet();
        eurekaAccess.setURI(URI.create("http://localhost:11801/eureka/apps/config-test"));
        eurekaAccess.setHeader("Accept", "application/json");
        ObjectMapper mapper = new ObjectMapper();
        try (CloseableHttpResponse response = HttpClientBuilder.create()
                .setRetryHandler(new DefaultHttpRequestRetryHandler())
                .build()
                .execute(eurekaAccess)) {
            Iterator<JsonNode> propertySources = mapper.readTree(response.getEntity().getContent())
                    .findValue("instance").elements();

            String content = propertySources.next().toString();
            return mapper.readValue(content, NextServerInfo.class);
        }
    }

    public static void main(String[] args) throws IOException {
        NextServerInfo info = accessToEureka();
        ConfigAccess.access(info.buildNextServerHostUrl() + "/my-id/prod/master");
    }
}
