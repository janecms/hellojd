package com.hellojd.guava;

import com.google.common.cache.*;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CacheTest {
    static class KeyHolder{
        private KeyHolder(){}
        static KeyHolder instance= new KeyHolder();
        AtomicInteger key = new AtomicInteger();
        public static KeyHolder getInstance(){return instance;}
        public int get(){
            return key.incrementAndGet();
        }
    }
    static class MyRemovalListener implements RemovalListener {
        @Override
        public void onRemoval(RemovalNotification removalNotification) {
            System.out.println("remove:"+removalNotification.getKey()+","+removalNotification.getCause().name());
        }
    }
    public static void main(String[] args) {
        LoadingCache<String, AgentInfo> agentCache = CacheBuilder.newBuilder()
                .maximumSize(10000)
                .concurrencyLevel(10)
                .expireAfterWrite(1000, TimeUnit.SECONDS)
                .removalListener(new MyRemovalListener())
                .build(
                        new CacheLoader<String, AgentInfo>() {
                            public AgentInfo load(String key) throws Exception {
                                AgentInfo agentInfo = new AgentInfo();
                                agentInfo.setKey(key);
                                int agentId = KeyHolder.getInstance().get();
                                agentInfo.setAgentId(agentId);
                                agentInfo.setDate(new Date());
                                agentInfo.setName("agent:"+agentId);
//                                agentInfo.setAgentId(aid++);
                                return agentInfo;
                            }
                        });
        try {
            Random random = new Random();
            for(int i=0;i<10000;i++){
                AgentInfo agentInfo = agentCache.get(""+i);
//                TimeUnit.SECONDS.sleep(10);
                agentCache.get(""+random.nextInt(100));
            }

            TimeUnit.MINUTES.sleep(10);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
