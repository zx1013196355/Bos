package com.example.demo.lgh.annotation.impl;

import com.example.demo.lgh.annotation.FiledAnnotation;

public class BosAnnotation {
    @FiledAnnotation(selector = "div.line-justify > div > span")
    private String bosBlance;//bos可用余额
    private String cpuMortgage;//CPU质押
    private String netMortgage;//NET质押
    private String mortgageToOthers;//抵押给别人
    private String redemption;//正在赎回
    private String bosSumBlance;//bos总余额
    @FiledAnnotation(selector = "div.resource-tooltip > span.c-black")
    private String ram;//剩余
    private String cpu;
    private String net;
    @FiledAnnotation(selector = "div.lh-2.mt-15.c-045 > div > span")
    private String bosSumMortgage;

    public String getBosSumMortgage() {
        return bosSumMortgage;
    }

    public void setBosSumMortgage(String bosSumMortgage) {
        this.bosSumMortgage = bosSumMortgage;
    }

    public String getBosBlance() {
        return bosBlance;
    }

    public void setBosBlance(String bosBlance) {
        this.bosBlance = bosBlance;
    }

    public String getCpuMortgage() {
        return cpuMortgage;
    }

    public void setCpuMortgage(String cpuMortgage) {
        this.cpuMortgage = cpuMortgage;
    }

    public String getNetMortgage() {
        return netMortgage;
    }

    public void setNetMortgage(String netMortgage) {
        this.netMortgage = netMortgage;
    }

    public String getMortgageToOthers() {
        return mortgageToOthers;
    }

    public void setMortgageToOthers(String mortgageToOthers) {
        this.mortgageToOthers = mortgageToOthers;
    }

    public String getRedemption() {
        return redemption;
    }

    public void setRedemption(String redemption) {
        this.redemption = redemption;
    }

    public String getBosSumBlance() {
        return bosSumBlance;
    }

    public void setBosSumBlance(String bosSumBlance) {
        this.bosSumBlance = bosSumBlance;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }
}
