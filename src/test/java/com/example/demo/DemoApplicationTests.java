package com.example.demo;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
//@WebAppConfiguration
public class DemoApplicationTests {
    @Test
    public void contextLoads() throws NoSuchMethodException, NoSuchFieldException, IOException, IllegalAccessException, URISyntaxException, JSONException {
        Connection connection = Jsoup.connect("https://eos.greymass.com/v1/chain/get_table_rows")
                .ignoreContentType(true)
                .userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36") // User-Agent of Chrome 55
                .referrer("http://bluetata.com/")
                .header("Content-Type", "application/json; charset=UTF-8")
                .header("Accept", "text/plain, */*; q=0.01")
                .header("Accept-Encoding", "gzip,deflate,sdch")
                .header("Accept-Language", "es-ES,es;q=0.8")
                .header("Connection", "keep-alive")
                .header("X-Requested-With", "XMLHttpRequest")
                .requestBody("{\n" +
                        "    \"scope\": \"eosio\",\n" +
                        "    \"code\": \"eosio\",\n" +
                        "    \"table\": \"producers\",\n" +
                        "    \"json\": \"true\",\n" +
                        "    \"lower_bound\": \"starteosiobp\",\n" +
                        "    \"upper_bound\": \"starteosiobp\",\n" +
                        "    \"limit\": 1000,\n" +
                        "    \"index_position\": \"1\",\n" +
                        "    \"key_type\": \"name\"\n" +
                        "}")
                .method(Connection.Method.POST);
        Response response = connection.execute();

        String json = response.parse().body().html();
        System.out.println(json.substring(json.indexOf("total_votes") + 14, json.indexOf("producer_key") - 3));
//        JSONObject jsonObject = new JSONObject(response.parse().body().html());
//        System.out.println(jsonObject);

    }
}