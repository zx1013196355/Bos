package com.example.demo12306;

import com.example.demo1.DemoApplicationTest;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplicationTest.class)
public class Test {
    @org.junit.Test
    public void test() throws IOException {

        Runtime rt = Runtime.getRuntime();
        Process p = rt.exec("C:\\Users\\HP-PC\\Desktop\\phantomjs\\bin\\phantomjs.exe C:\\Users\\HP-PC\\Desktop\\phantomjs\\bin\\parser.js " + "https://kyfw.12306.cn/otn/leftTicket/init?linktypeid=dc&fs=%E5%8C%97%E4%BA%AC,BJP&ts=%E6%88%90%E9%83%BD,CDW&date=2019-01-16&flag=N,Y,Y");
        InputStream is = p.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuffer sbf = new StringBuffer();
        String tmp = "";
        while ((tmp = br.readLine()) != null) {
            sbf.append(tmp);
        }
        Document document1 = Jsoup.parse(sbf.toString());
        System.out.println(document1.html());


//        Runtime runtime = Runtime.getRuntime();
//
//        Connection.Response connection1 = Jsoup.connect("https://kyfw.12306.cn/otn/leftTicket/init").ignoreContentType(true).header("User-Agent",
//                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0").postDataCharset("utf-8").method(Connection.Method.GET)
//                .data("linktypeid", "dc", "fs", "北京,BJP", "ts", "成都,CDW", "date", "2019-01-16", "flag", "N,Y,Y").execute();
//
//        Document document = connection1.parse();
//        Process process = runtime.exec("C:\\Users\\HP-PC\\Desktop\\phantomjs\\bin\\phantomjs.exe C:\\Users\\HP-PC\\Desktop\\phantomjs\\bin\\parser.js");
//        InputStream is = process.getInputStream();
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//
//        StringBuffer sbf = new StringBuffer();
//
//        String tmp = "";
//
//        while ((tmp = br.readLine()) != null) {
//
//            sbf.append(tmp);
//
//        }
//
//        System.out.println(sbf.toString());

//        System.out.println(document.baseUri());
    }
}
