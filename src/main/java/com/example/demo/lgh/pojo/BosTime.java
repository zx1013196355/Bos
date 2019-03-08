package com.example.demo.lgh.pojo;

import java.util.List;
import java.util.Map;

public class BosTime {
    private String block_time;//time
    private String account_action_seq;//id
    private BosAct action_trace;

    public String getBlock_time() {
        return block_time;
    }

    public void setBlock_time(String block_time) {
        this.block_time = block_time;
    }

    public String getAccount_action_seq() {
        return account_action_seq;
    }

    public void setAccount_action_seq(String account_action_seq) {
        this.account_action_seq = account_action_seq;
    }

    public BosAct getAction_trace() {
        return action_trace;
    }

    public void setAction_trace(BosAct action_trace) {
        this.action_trace = action_trace;
    }
}
