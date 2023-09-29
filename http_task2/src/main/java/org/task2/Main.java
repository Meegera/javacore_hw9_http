package org.task2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static final String URL = "https://api.nasa.gov/planetary/apod?api_key=hpe9Mx3J2xv6Nq5sUch14aqJaiCR4hsQxb57dEoT";
    public static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultRequestConfig(
                        RequestConfig.custom()
                                .setConnectTimeout(5000)
                                .setSocketTimeout(30000)
                                .setRedirectsEnabled(false)
                                .build()
                )
                .build();


        HttpGet request = new HttpGet(URL);
        CloseableHttpResponse response = client.execute(request);
        Content content = mapper.readValue(response.getEntity().getContent(), new TypeReference<Content>() {
        });
        System.out.println(content);

        HttpGet requestImage = new HttpGet(content.getUrl());
        CloseableHttpResponse responseImage = client.execute(requestImage);

        if (responseImage.getStatusLine().getStatusCode() == 200) {
            HttpEntity imageEntity = responseImage.getEntity();
            File imageFile = new File("image.jpg");

            try (FileOutputStream fos = new FileOutputStream(imageFile)) {
                imageEntity.writeTo(fos);
            }
        }
        responseImage.close();
        client.close();
    }

}