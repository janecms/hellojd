package com.hellojd.guava;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhaoguoyu on 2017/5/4.
 */
public class AgentInfo {
    DateFormat df= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
    private int agentId;
    private String key;
    String name;
    Date date;
    public AgentInfo(){
        System.out.println("constract agent!");
    }
    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "AgentInfo{" +
                "agentId=" + agentId +
                ", name='" + name + '\'' +
                ", date=" + df.format(date) +
                '}';
    }
}
