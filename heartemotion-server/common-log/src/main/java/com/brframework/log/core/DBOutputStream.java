package com.brframework.log.core;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson.JSON;
import com.brframework.commonweb.core.SpringContext;
import com.brframework.log.appender.DBAppender;
import com.brframework.log.service.LogMessageService;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xu
 * @date 2020/5/28 15:55
 */
public class DBOutputStream extends OutputStream {

    List<DBAppender.LogInfo> writeCache = Lists.newArrayList();
    ReentrantLock lock = new ReentrantLock();

    public DBOutputStream() {
        timerFlush();
    }

    @SneakyThrows
    private void timerFlush(){
        ThreadUtil.excAsync(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ThreadUtil.sleep(20000);
                flush();
                timerFlush();
            }

        }, true);
    }

    @Override
    public void write(int b) throws IOException {
        throw new IOException("该方法不支持");
    }

    @Override
    public void write(byte[] b) throws IOException {
        lock.lock();
        try{
            DBAppender.LogInfo logInfo = JSON.parseObject(new String(b), DBAppender.LogInfo.class);
            writeCache.add(logInfo);

            if(logInfo.getMessage().contains("Shutting down")){
                flush();
            }

        } finally {
            lock.unlock();
        }

    }

    @Override
    public void close() throws IOException {
        flush();
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        throw new IOException("该方法不支持");
    }


    @Override
    public void flush() throws IOException {
        if(SpringContext.getApplicationContext() == null || writeCache.size() == 0){
            return;
        }

        List<DBAppender.LogInfo> doFlushCache = Lists.newArrayList();
        LogMessageService logMessageService = SpringContext.getBean(LogMessageService.class);

        lock.lock();
        try {
            doFlushCache.addAll(writeCache);
            writeCache.clear();
        } finally {
            lock.unlock();
        }

        logMessageService.writeLogs(doFlushCache);
    }
}
