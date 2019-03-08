package com.example.demo.lgh.pojo;

public class BosName {
    private String name;
    private Integer status;
    private Integer currentPage;
    private String startTime;
    private String endTime;
    private String symbol;
    private String transactionMeans;
    private TransactionPrice transactionPrice;
    private String sortType;
    private String othername;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTransactionMeans() {
        return transactionMeans;
    }

    public void setTransactionMeans(String transactionMeans) {
        this.transactionMeans = transactionMeans;
    }

    public TransactionPrice getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(TransactionPrice transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getOthername() {
        return othername;
    }

    public void setOthername(String othername) {
        this.othername = othername;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
