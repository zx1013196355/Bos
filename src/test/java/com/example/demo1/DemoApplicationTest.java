package com.example.demo1;

import com.example.demo.lgh.annotation.FiledAnnotation;
import com.example.demo.lgh.pojo.Actions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.sf.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplicationTest.class)
public class DemoApplicationTest {
//    @Test
//    public void city() throws IOException, NoSuchFieldException {
//        String url = "https://hapi.bos.eosrio.io/v1/history/get_actions";
//        Document document = getResponse(url);
//        FiledAnnotation province = getFiledAnnotation("province");
//        FiledAnnotation city = getFiledAnnotation("city");
//        Elements elements = null;
//        int i = 1;
//        if (province != null) {
//            elements = document.select(province.selector());
//            for (Element e : elements) {
//                System.out.print(e.text() + " ");
//                Document documentCity = getResponse(url + e.attr("href"));
//                sas(documentCity, city.selector(), i);
//                System.out.println();
//            }
//        }

//        Runtime runtime = Runtime.getRuntime();
//        String exec = "D:\\phantomjs\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe D:\\phantomjs\\phantomjs-2.1.1-windows\\bin\\code.js https://bos.eospark.com/account/monitorstbos";
//        Process p = runtime.exec(exec);
//        InputStream is = p.getInputStream();
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//        StringBuffer sbf = new StringBuffer();
//        String tmp = "";
//        while((tmp = br.readLine())!=null){
//            sbf.append(tmp);
//        }
//        System.out.println(sbf);
//        Document document = Jsoup.parse(is,"UTF-8","https://bos.eospark.com/account/monitorstbos");
//        System.out.println(document.html());

    //    }
    @Test
    public void getResponse() throws IOException {
//        Connection connection = Jsoup.connect("https://hapi.bos.eosrio.io/v1/history/get_actions")
//                .ignoreContentType(true)
//                .userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36") // User-Agent of Chrome 55
//                .referrer("http://bluetata.com/")
//                .header("Content-Type", "application/json; charset=UTF-8")
//                .header("Accept", "text/plain, */*; q=0.01")
//                .header("Accept-Encoding", "gzip,deflate,sdch")
//                .header("Accept-Language", "es-ES,es;q=0.8")
//                .header("Connection", "keep-alive")
//                .header("X-Requested-With", "XMLHttpRequest")
//
//                .requestBody("{\"account_name\":\"redpacket\",\"pos\":1,\"offset\":250}")
//                .timeout(999999999)
//                .method(Connection.Method.POST);
//        Connection.Response response = connection.execute();
//        Document document = response.parse();
//        String jsonStr = document.body().text();
//        JsonObject object = new JsonParser().parse(jsonStr).getAsJsonObject();
//        Actions actions = new Gson().fromJson(object,Actions.class);
//        System.out.println();
//        return document;
    }
//    @Test
//    public FiledAnnotation getFiledAnnotation(String field) throws NoSuchFieldException {
//        Field field1 = City.class.getDeclaredField(field);
//        FiledAnnotation filedAnnotation = null;
//        if (field1 != null) {
//            filedAnnotation = field1.getAnnotation(FiledAnnotation.class);
//        }
//        return filedAnnotation;
//    }
//
//    //递归
//    @Test
//    public void sas(Document document, String cssQuery, int j) throws IOException, NoSuchFieldException {
//        Elements elementsCity = null;
//        FiledAnnotation city = null;
//        try {
//            city = getFiledAnnotation("city" + j);
//        } catch (Exception e) {
//
//        }
//        elementsCity = document.select(cssQuery);
//        if (city != null)
//            for (int i = 1; i < elementsCity.size(); i = i + 2) {
//                Element element = elementsCity.get(i);
//                System.out.print(element.text() + " ");
//                String url = element.baseUri();
//                Document e = getResponse(url.substring(0, url.lastIndexOf("/") + 1) + element.attr("href"));
//                sas(e, city.selector(), ++j);
//            }
//        else {
//            for (int i = 2; i < elementsCity.size(); i = i + 3) {
//                Element element = elementsCity.get(i);
//                System.out.print(element.text() + " ");
//            }
//        }
//        System.out.println("\n");
//    }
}
