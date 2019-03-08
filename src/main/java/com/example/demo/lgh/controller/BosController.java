package com.example.demo.lgh.controller;

import com.example.demo.lgh.json.BosJson;
import com.example.demo.lgh.mapper.BosMapper;
import com.example.demo.lgh.pojo.Bos;
import com.example.demo.lgh.pojo.BosName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class BosController {
    @Resource
    BosMapper bosMapper;

    @RequestMapping("page")
    public List<Bos> ss(@RequestBody BosName bosName) throws IOException, InterruptedException, NoSuchFieldException {

        BosJson bosJson = new BosJson();
        List<Bos> bos = bosJson.getJson(bosName, bosMapper,bosName.getCurrentPage());
        return bos;
    }

    @RequestMapping("getRam")

    public Map<String, String> getRam(@RequestBody BosName bosName) throws IOException, NoSuchFieldException {
        BosJson bosJson = new BosJson();

        return bosJson.getRam(bosName.getName());
    }

}
