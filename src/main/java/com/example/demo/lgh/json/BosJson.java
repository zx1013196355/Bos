package com.example.demo.lgh.json;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.lgh.annotation.FiledAnnotation;
import com.example.demo.lgh.annotation.impl.BosAnnotation;
import com.example.demo.lgh.mapper.BosMapper;
import com.example.demo.lgh.pojo.Actions;
import com.example.demo.lgh.pojo.Bos;
import com.example.demo.lgh.pojo.BosName;
import com.example.demo.lgh.pojo.BosTime;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BosJson {

    public List<Bos> getJson(BosName bosName, BosMapper bosMapper, Integer currentPage) throws IOException, InterruptedException, NoSuchFieldException {
        String name = bosName.getName();
        //设置网络代理
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 2333));
        int sum = 0;
        while (true) {
            //查询这个用户的最后一条数据
            Wrapper<Bos> wrapperx = new QueryWrapper<>(new Bos()).eq("bosname", name);
            Bos count = bosMapper.getOne(wrapperx);
            if (count == null) {
                count = new Bos();
                count.setId(1);
            }
            Thread.sleep(10);
            int x = count.getId() + 1;
            Connection connection = Jsoup.connect("https://hapi.bos.eosrio.io/v1/history/get_actions")
                    .ignoreContentType(true)
                    .proxy(proxy)
                    .userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36") // User-Agent of Chrome 55
                    .referrer("http://bluetata.com/")
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .header("Accept", "text/plain, */*; q=0.01")
                    .header("Accept-Encoding", "gzip,deflate,sdch")
                    .header("Accept-Language", "es-ES,es;q=0.8")
                    .header("Connection", "keep-alive")
                    .header("X-Requested-With", "XMLHttpRequest")
                    .ignoreHttpErrors(true)

                    .requestBody("{\"account_name\":\"" + name + "\",\"pos\":" + x + ",\"offset\":" + 190 + "}")
                    .timeout(999999999)
                    .method(Connection.Method.POST);
            Connection.Response response = connection.execute();
            Document document = response.parse();
            String jsonStr = document.body().text();
            JsonObject object = new JsonParser().parse(jsonStr).getAsJsonObject();
            Actions actions = new Gson().fromJson(object, Actions.class);
            if ((actions.getActions().size() == 2 && sum == 1) || actions.getActions().size() == 0) {
                break;
            }
            if (actions.getActions().size() == 2) {
                sum = 1;
            }
            Bos bos = new Bos();
            for (BosTime bosTime : actions.getActions()) {
                bos.setId(Integer.parseInt(bosTime.getAccount_action_seq()));
                int end = bosTime.getBlock_time().indexOf(".");
                String st = bosTime.getBlock_time().substring(0, end);

                bos.setTime(getTime(st));
                if (bosTime.getAction_trace().getAct().getData().getQuantity() != null) {
                    bos.setQuantity(bosTime.getAction_trace().getAct().getData().getQuantity().split(" ")[0]);
                    bos.setSymbol(bosTime.getAction_trace().getAct().getData().getQuantity().split(" ")[1]);
                }

                bos.setFromname(bosTime.getAction_trace().getAct().getData().getFrom());
                bos.setToname(bosTime.getAction_trace().getAct().getData().getTo());
                bos.setMemo(bosTime.getAction_trace().getAct().getData().getMemo());
                bos.setBosname(name);
                bos.setTrxid(bosTime.getAction_trace().getTrx_id());
                if (bos.getFromname() == null || bos.getFromname().equals("") || bosTime.getAction_trace().getAct().getData().getQuantity() == null) {
                    bos.setStatus(2);
                    bos.setFromname("11");
                    bos.setMemo("as");
                    bos.setQuantity("Sa");
                    bos.setToname("Sa");
                    bos.setSymbol(" ");
                } else if (bos.getFromname().equals(name)) {
                    bos.setStatus(0);
                    insert(bos, bosMapper, name);
                } else {
                    bos.setStatus(1);
                    insert(bos, bosMapper, name);
                }
            }
        }
        IPage<Bos> ceIPage = null;
        Page<Bos> iPage = new Page<>(currentPage, 100);
        Wrapper<Bos> wrapper2 = null;
        if (bosName.getSymbol() == null || bosName.getSymbol().equals("")) {
            bosName.setSymbol("bos");
        }
        bosName.setSymbol(bosName.getSymbol().toUpperCase());
        if (bosName.getStartTime() == null || bosName.getStartTime().equals("")) {
            bosName.setStartTime("2000-03-04 22:53:44");
            bosName.setEndTime("2100-03-04 22:53:44");
        }
        if (bosName.getTransactionPrice().getMax() == null || bosName.getTransactionPrice().getMax().equals("")) {
            bosName.getTransactionPrice().setMin("0");
            bosName.getTransactionPrice().setMax("1000000000");
        }
        if (bosName.getStatus() == 0) {
            wrapper2 = new QueryWrapper<>(new Bos())
                    .eq("bosname", name).orderByDesc("time")
                    .eq("symbol", bosName.getSymbol().toUpperCase());
        } else {
            if (bosName.getSortType().equals("dateUp")) {
                if (bosName.getTransactionMeans().equals("transferIn")) {
                    wrapper2 = new QueryWrapper<>(new Bos())
                            .eq("bosname", name).orderByAsc("time")
                            .eq("symbol", bosName.getSymbol().toUpperCase())
                            .between(true, "time", bosName.getStartTime(), bosName.getEndTime())
                            .between(true, "quantity", bosName.getTransactionPrice().getMin(), bosName.getTransactionPrice().getMax())
                            .eq("toname", name);
                } else if (bosName.getTransactionMeans().equals("transferDown")) {
                    if (bosName.getOthername() == null || bosName.getOthername().equals("")) {
                        wrapper2 = new QueryWrapper<>(new Bos())
                                .eq("bosname", name).orderByDesc("time")
                                .eq("symbol", bosName.getSymbol().toUpperCase())
                                .between(true, "time", bosName.getStartTime(), bosName.getEndTime())
                                .between(true, "quantity", bosName.getTransactionPrice().getMin(), bosName.getTransactionPrice().getMax())
                                .eq("fromname", name);
                    } else {
                        wrapper2 = new QueryWrapper<>(new Bos())
                                .eq("bosname", name).orderByDesc("time")
                                .eq("symbol", bosName.getSymbol().toUpperCase())
                                .between(true, "time", bosName.getStartTime(), bosName.getEndTime())
                                .between(true, "quantity", bosName.getTransactionPrice().getMin(), bosName.getTransactionPrice().getMax())
                                .eq("toname", bosName.getOthername());
                    }

                } else {
                    wrapper2 = new QueryWrapper<>(new Bos())
                            .eq("bosname", name).orderByAsc("time")
                            .eq("symbol", bosName.getSymbol().toUpperCase())
                            .between(true, "time", bosName.getStartTime(), bosName.getEndTime())
                            .between(true, "quantity", bosName.getTransactionPrice().getMin(), bosName.getTransactionPrice().getMax());
                }


            } else if (bosName.getSortType().equals("dateDown")) {

                if (bosName.getTransactionMeans().equals("transferIn")) {
                    wrapper2 = new QueryWrapper<>(new Bos())
                            .eq("bosname", name).orderByDesc("time")
                            .eq("symbol", bosName.getSymbol().toUpperCase())
                            .between(true, "time", bosName.getStartTime(), bosName.getEndTime())
                            .between(true, "quantity", bosName.getTransactionPrice().getMin(), bosName.getTransactionPrice().getMax())
                            .eq("toname", name);
                } else if (bosName.getTransactionMeans().equals("transferDown")) {
                    if (bosName.getOthername() == null || bosName.getOthername().equals("")) {
                        wrapper2 = new QueryWrapper<>(new Bos())
                                .eq("bosname", name).orderByDesc("time")
                                .eq("symbol", bosName.getSymbol().toUpperCase())
                                .between(true, "time", bosName.getStartTime(), bosName.getEndTime())
                                .between(true, "quantity", bosName.getTransactionPrice().getMin(), bosName.getTransactionPrice().getMax())
                                .eq("fromname", name);
                    } else {
                        wrapper2 = new QueryWrapper<>(new Bos())
                                .eq("bosname", name).orderByDesc("time")
                                .eq("symbol", bosName.getSymbol().toUpperCase())
                                .between(true, "time", bosName.getStartTime(), bosName.getEndTime())
                                .between(true, "quantity", bosName.getTransactionPrice().getMin(), bosName.getTransactionPrice().getMax())
                                .eq("toname", bosName.getOthername());
                    }

                } else {
                    wrapper2 = new QueryWrapper<>(new Bos())
                            .eq("bosname", name).orderByDesc("time")
                            .eq("symbol", bosName.getSymbol().toUpperCase())
                            .between(true, "time", bosName.getStartTime(), bosName.getEndTime())
                            .between(true, "quantity", bosName.getTransactionPrice().getMin(), bosName.getTransactionPrice().getMax());
                }


            } else if (bosName.getSortType().equals("priceUp")) {
                if (bosName.getTransactionMeans().equals("transferIn")) {
                    wrapper2 = new QueryWrapper<>(new Bos())
                            .eq("bosname", name).orderByAsc("quantity")
                            .eq("symbol", bosName.getSymbol().toUpperCase())
                            .between(true, "time", bosName.getStartTime(), bosName.getEndTime())
                            .between(true, "quantity", bosName.getTransactionPrice().getMin(), bosName.getTransactionPrice().getMax())
                            .eq("toname", name);
                } else if (bosName.getTransactionMeans().equals("transferDown")) {
                    if (bosName.getOthername() == null || bosName.getOthername().equals("")) {
                        wrapper2 = new QueryWrapper<>(new Bos())
                                .eq("bosname", name).orderByAsc("quantity")
                                .eq("symbol", bosName.getSymbol().toUpperCase())
                                .between(true, "time", bosName.getStartTime(), bosName.getEndTime())
                                .between(true, "quantity", bosName.getTransactionPrice().getMin(), bosName.getTransactionPrice().getMax())
                                .eq("fromname", name);
                    } else {
                        wrapper2 = new QueryWrapper<>(new Bos())
                                .eq("bosname", name).orderByAsc("quantity")
                                .eq("symbol", bosName.getSymbol().toUpperCase())
                                .between(true, "time", bosName.getStartTime(), bosName.getEndTime())
                                .between(true, "quantity", bosName.getTransactionPrice().getMin(), bosName.getTransactionPrice().getMax())
                                .eq("toname", bosName.getOthername());
                    }

                } else {
                    wrapper2 = new QueryWrapper<>(new Bos())
                            .eq("bosname", name).orderByAsc("quantity")
                            .eq("symbol", bosName.getSymbol().toUpperCase())
                            .between(true, "time", bosName.getStartTime(), bosName.getEndTime())
                            .between(true, "quantity", bosName.getTransactionPrice().getMin(), bosName.getTransactionPrice().getMax());
                }

            } else if (bosName.getSortType().equals("priceDown")) {

                if (bosName.getTransactionMeans().equals("transferIn")) {
                    wrapper2 = new QueryWrapper<>(new Bos())
                            .eq("bosname", name).orderByDesc("quantity")
                            .eq("symbol", bosName.getSymbol().toUpperCase())
                            .between(true, "time", bosName.getStartTime(), bosName.getEndTime())
                            .between(true, "quantity", bosName.getTransactionPrice().getMin(), bosName.getTransactionPrice().getMax())
                            .eq("toname", name);
                } else if (bosName.getTransactionMeans().equals("transferDown")) {
                    if (bosName.getOthername() == null || bosName.getOthername().equals("")) {
                        wrapper2 = new QueryWrapper<>(new Bos())
                                .eq("bosname", name).orderByDesc("quantity")
                                .eq("symbol", bosName.getSymbol().toUpperCase())
                                .between(true, "time", bosName.getStartTime(), bosName.getEndTime())
                                .between(true, "quantity", bosName.getTransactionPrice().getMin(), bosName.getTransactionPrice().getMax())
                                .eq("fromname", name);
                    } else {
                        wrapper2 = new QueryWrapper<>(new Bos())
                                .eq("bosname", name).orderByDesc("quantity")
                                .eq("symbol", bosName.getSymbol().toUpperCase())
                                .between(true, "time", bosName.getStartTime(), bosName.getEndTime())
                                .between(true, "quantity", bosName.getTransactionPrice().getMin(), bosName.getTransactionPrice().getMax())
                                .eq("toname", bosName.getOthername());
                    }

                } else {
                    wrapper2 = new QueryWrapper<>(new Bos())
                            .eq("bosname", name).orderByDesc("quantity")
                            .eq("symbol", bosName.getSymbol().toUpperCase())
                            .between(true, "time", bosName.getStartTime(), bosName.getEndTime())
                            .between(true, "quantity", bosName.getTransactionPrice().getMin(), bosName.getTransactionPrice().getMax());
                }

            }

        }


        ceIPage = bosMapper.selectPage(iPage, wrapper2);


        return ceIPage.getRecords();
    }

    public FiledAnnotation getFiledAnnotation(Class clazz, String filed) throws NoSuchFieldException {
        Field field = clazz.getDeclaredField(filed);
        FiledAnnotation filedAnnotation = null;
        if (field != null) {
            filedAnnotation = field.getAnnotation(FiledAnnotation.class);
        }
        return filedAnnotation;
    }

    public void insert(Bos bos, BosMapper bosMapper, String name) {
        Wrapper<Bos> wrapper = new QueryWrapper<>(new Bos())
                .eq("trxid", bos.getTrxid());
        if (bosMapper.selectOne(wrapper) == null) {
            bosMapper.insert(bos);
        }
    }

    public Map<String, String> getRam(String name) throws IOException, NoSuchFieldException {

        Connection connection = Jsoup.connect("https://bos.eospark.com/account/" + name)
                .userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36"); // User-Agent of Chrome 55
        Document document = connection.execute().parse();
        String[] bosBlance = document.select(getFiledAnnotation(BosAnnotation.class, "bosBlance").selector()).text().split(" ");
        Map<String, String> map = new HashMap<>();
        if (bosBlance.length < 6) {
            map.put("status", "没有当前账户");
            return map;
        }

        map.put("bosBlance", bosBlance[0]);
        map.put("cpuMortgage", bosBlance[1]);
        map.put("netMortgage", bosBlance[2]);
        map.put("mortgageToOthers", bosBlance[3]);
        map.put("redemption", bosBlance[5]);
        map.put("bosSumBlance", bosBlance[6]);
        String[] ram = document.select(getFiledAnnotation(BosAnnotation.class, "ram").selector()).text().split(": ");
        map.put("ram", ram[1]);
        map.put("cpu", ram[2]);
        map.put("net", ram[3]);
        String[] bosSumMortgage = document.select(getFiledAnnotation(BosAnnotation.class, "bosSumMortgage").selector()).text().split(" ");
        map.put("cpuSumMortgage", bosSumMortgage[0]);
        map.put("cpuSelfMortgage", bosSumMortgage[1]);
        map.put("cpuOthersMortgage", bosSumMortgage[2]);
        map.put("cpuRedemption", bosSumMortgage[3]);
        map.put("netSumMortgage", bosSumMortgage[4]);
        map.put("netSelfMortgage", bosSumMortgage[5]);
        map.put("netOthersMortgage", bosSumMortgage[6]);
        map.put("netRedemption", bosSumMortgage[7]);
        return map;
    }

    public String getTime(String time) {
        String stringDate = time;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.CHINA);
        Date date = null;
        try {
            date = sdf.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        return sdf1.format(calendar.getTime());
    }
}