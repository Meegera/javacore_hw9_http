package org.task1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;

public class Main {
    public static final String URL = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig( //используется для установки настроек запроса
                        RequestConfig.custom() //cоздает конфигурацию для HTTP-запроса
                                .setConnectTimeout(5000)
                                .setSocketTimeout(30000)
                                .setRedirectsEnabled(false) //Отключение автоматической обработки перенаправлений
                                .build())
                .build();

        HttpGet request = new HttpGet(URL);
        //устанавка заголовока запроса "Accept" с указанием типа контента, который клиент ожидает от сервера
        request.setHeader(HttpHeaders.ACCEPT, APPLICATION_JSON.getMimeType());
        CloseableHttpResponse response = httpClient.execute(request);
        //Arrays.stream(response.getAllHeaders()).forEach(System.out::println);

        List<Cat> cats = mapper.readValue(response.getEntity().getContent(), new TypeReference<List<Cat>>() {
        });
        cats.stream()
                .filter(x -> x.getUpvotes() != null && x.getUpvotes() > 0)
                .forEach(System.out::println);
    }
}