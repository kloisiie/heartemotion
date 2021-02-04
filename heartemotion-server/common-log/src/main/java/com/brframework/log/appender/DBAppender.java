package com.brframework.log.appender;

import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.OutputStreamAppender;
import ch.qos.logback.core.encoder.Encoder;
import ch.qos.logback.core.encoder.EncoderBase;
import ch.qos.logback.core.status.Status;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.commonweb.utils.IPUtils;
import com.brframework.log.core.DBOutputStream;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author xu
 * @date 2020/5/28 16:09
 */
public class DBAppender extends OutputStreamAppender<LoggingEvent> {

    @Override
    public void start() {
        setOutputStream(new DBOutputStream());
        setEncoder(new DBAppenderEncoder());
        super.start();
    }

    @SneakyThrows
    @Override
    public void stop() {
        getOutputStream().flush();
        super.stop();
    }

    public static class DBAppenderEncoder extends EncoderBase<LoggingEvent> {
        @Override
        public byte[] headerBytes() {
            return new byte[0];
        }

        @Override
        public byte[] encode(LoggingEvent event) {

            LogInfo logInfo = new LogInfo();
            logInfo.setTimeStamp(event.getTimeStamp());
            logInfo.setThreadName(event.getThreadName());
            logInfo.setLoggerName(event.getLoggerName());
            logInfo.setLevel(event.getLevel().toString());
            logInfo.setIp(IPUtils.getLocalIp());
            if(event.getThrowableProxy() != null){
                ThrowableProxy throwableProxy = (ThrowableProxy) event.getThrowableProxy();
                logInfo.setMessage(event.getFormattedMessage() + "\n" +
                        ExceptionUtils.getStackTrace(throwableProxy.getThrowable()));
            } else {
                logInfo.setMessage(event.getFormattedMessage());
            }


            return JSON.toJSONString(logInfo).getBytes();
        }

        @Override
        public byte[] footerBytes() {
            return new byte[0];
        }
    }

    @Data
    public static class LogInfo{
        /**
         * 时间戳
         */
        private Long timeStamp;
        /**
         * 线程名字
         */
        private String threadName;
        /**
         * 本地ip
         */
        private String ip;
        /**
         * 日志写入名称
         */
        private String loggerName;
        /**
         * 日志等级
         */
        private String level;
        /**
         * 日志消息
         */
        private String message;
    }

}
