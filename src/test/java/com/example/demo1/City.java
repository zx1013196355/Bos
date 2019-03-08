package com.example.demo1;

import com.example.demo.lgh.annotation.FiledAnnotation;

public class City {
    @FiledAnnotation(selector = "body > table > tbody > tr > td > table> tbody > tr > td > table> tbody > tr > td > table > tbody > tr.provincetr > td > a")
    private String province;
    @FiledAnnotation(selector = "body > table > tbody > tr > td > table> tbody > tr > td > table> tbody > tr > td > table > tbody > tr.citytr > td > a")
    private String city;
    @FiledAnnotation(selector = "body > table > tbody > tr > td > table> tbody > tr > td > table> tbody > tr > td > table > tbody > tr.countytr > td > a")
    private String city1;
    @FiledAnnotation(selector = "body > table > tbody > tr > td > table> tbody > tr > td > table> tbody > tr > td > table > tbody > tr.towntr > td > a")
    private String city2;
    @FiledAnnotation(selector = "body > table > tbody > tr > td > table> tbody > tr > td > table> tbody > tr > td > table > tbody > tr.villagetr > td")
    private String city3;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity1() {
        return city1;
    }

    public void setCity1(String city1) {
        this.city1 = city1;
    }

    public String getCity2() {
        return city2;
    }

    public void setCity2(String city2) {
        this.city2 = city2;
    }

    public String getCity3() {
        return city3;
    }

    public void setCity3(String city3) {
        this.city3 = city3;
    }
}
