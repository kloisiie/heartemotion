# 架构介绍

## 项目采用框架技术

* Spring Boot
* Spring MVC
* Spring Security
* Hibernate
* QueryDSL

## 项目依赖中间

* Redis

## 开发环境

* Java 8（必须）
* Intellij IDEA 2018.3（必须）
* Gradle 4.10（必须）
* IDEA 插件 lombok（必须）
* IDEA 插件 Alibaba Java Coding Guideline（选装，代码规范检查）
* IDEA 插件 GenerateAllSetter （选装，自动生成对象的所有Set方法）

## module约定

module为一个独立的组件，目前架构中存在两种module
* boot-xxxx  
可启动module，该module可运行
* common-xxxx
不可启动module，可理解为boot-xxxx的通用依赖

## QueryDSL环境

当你执行boot-xxx的时候发现编译错误，缺少entity.Qxxxx文件的时候，不是因为缺少代码，而是因为生成
querymodels，需要在gradle中执行querymodels的job，特别注意一定要执行根项目的querymodels。

# 模块介绍

## common

项目最基础的公用组件，依赖内容包括
* guava 28.0-jre(谷歌Java开发工具包)
* commons-lang3 3.9(工具类)
* fastjson 1.2.60(阿里巴巴开源的JSON处理框架)
* hutool 4.6.8(工具集)
* vavr 0.10.2(函数式编程工具)

## common-api-doc

接口文档组件，基于Swagger2，UI基于Knife4j的二次开发使接口拥有天然的MOCK能力

依赖
* common-web
* common-db

## common-app-push

app的推送组件，目前使用的是友盟的app推送支持，该项目需要依赖
* common-web-base(Web开发基础组件)

## common-cache

分布式缓存组件，基于spring boot cache 并使用了Redisson的支持

依赖
* common(基础组件)
* spring-boot-starter-data-redis 2.1.7.RELEASE(Spring对Redis操作的支持)
* spring-boot-starter-cache 2.1.7.RELEASE(Spring对缓存的支持)
* commons-pool2 2.7.0(对象池化技术，如果使用cache需要依赖)
* redisson 3.11.3(架设在Redis基础上的一个Java驻内存数据网格（In-Memory Data Grid）)

## common-cms

自研的后端管理自动生成组件，可通过简单的注解自动生成管理端界面，CMS的设计初衷是为了高度模板化的界面
所以在重复率很高的界面上，CMS可以起到非常重要的作用。

依赖
* common-web
* common-security
* poi-ooxml 3.17（apache poi 用于excel导出）

## common-db

数据库组件，目前支持MYSQL数据库，ORM框架采用Hibernate，使用JPA及QueryDSL来提升效率。  

QueryDSL的数据库操作需要依赖QueryModels，所以在项目启动前，要注意QueryModels是否生成
如果没有生成的情况下，需要在gradle里执行querymodels进行生成。  

依赖
* common-web
* spring-boot-starter-data-jpa 2.1.7.RELEASE(Spring JPA支持)
* mysql-connector-java 6.0.6(Java Mysql连接驱动)
* hibernate-core 5.4.4.Final(Hibernate)
* hibernate-java8 5.4.4.Final(Hibernate)
* hibernate-types-5 2.4.2(Hibernate)
* querydsl-core(QueryDSL)
* querydsl-sql(QueryDSL)
* querydsl-jpa(QueryDSL)


## common-distributed

分布式调度组件，包括分布式锁  

使用示例  

```java
@DistributedLock(key = "'updateShopSaleQuantity' + #orderId")
```

原理基于Redisson的RLock实现的，核心实现代码  

```java
//加锁，为避免死锁，60秒后将自动解锁
lock(locks, 60, TimeUnit.SECONDS);
try {
    return pjp.proceed();
} finally {
    //解锁
    unlock(locks);
}
```
依赖
* common-cache
* common-web

## common-generate

代码生成器，目前支持的代码生成方式为
* 基于processOn导出的数据库设计文件(pos)进行识别生成，可生成设计图内所有的表对应的Entity和Dao
* 基于已有生成的Entity和Dao生成指定功能的CRUD功能

## common-log

日志组件，收集系统的所有运行日志，并存储至数据库  

实现原理基于Logback的Appender  

```java
public class DBAppender extends OutputStreamAppender<LoggingEvent> {

    ......
    
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

    ......

}
```
为了保证日志的吞吐量，每20000毫秒定时批量持久化至数据库，核心实现: DBOutputStream  
```java
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
```
## common-mq

消息队列组件，使用Redis实现消息队列  

对消息的基本操作
```java
/**
 * 发送消息
 * @param topic  主题
 * @param message   消息内容
 * @return 消息ID
 */
String publish(String topic, String message);

/**
 * 发送延时消息
 * @param topic  主题
 * @param message   消息内容
 * @param delay   延时时间
 * @param unit   延时单位
 * @return 消息ID
 */
String publish(String topic, String message, long delay, TimeUnit unit);

/**
 * 发送定时消息
 * @param topic  主题
 * @param message   消息内容
 * @param time      发送时间
 * @return 消息ID
 */
String publish(String topic, String message, LocalDateTime time);


/**
 * 事务提交后发送消息
 * @param topic    主题
 * @param message  消息内容
 * @return 消息ID
 */
String publishByTxAfterCommit(String topic, String message);

/**
 * 事务提交后发送延时消息
 * @param topic  主题
 * @param message   消息内容
 * @param delay   延时时间
 * @param unit   延时单位
 * @return 消息ID
 */
String publishByTxAfterCommit(String topic, String message, long delay, TimeUnit unit);


/**
 * 事务提交后发送定时消息
 * @param topic  主题
 * @param message   消息内容
 * @param time      发送时间
 * @return 消息ID
 */
String publishByTxAfterCommit(String topic, String message, LocalDateTime time);

/**
 * 删除消息
 * @param messageId  消息ID
 */
void remove(String messageId);

/**
 * 添加MQ消息监听
 * @param topic      主题
 * @param listener   监听事件
 */
void addListener(String topic, MQMessageListener listener);
```

监听示例，消息监听方法必须由两个String类型的参数组成，分别是topic和message，否则消息无法监听，注意：类一定要加上@MQConfig注解，否则无法收到消息。  

```java
@MQListener(topic = MqTopics.MESSAGE_LISTENER)
@Transactional(rollbackFor = Exception.class)
public void messageListener(String topic, String message) {
    // handle message
}
```
消息发送原理，基于Redisson的BlockingQueue实现消息队列，基于Redisson的DelayedQueue实现延时队列  
```java
private void publishMessage(MQData mqData , long delay, TimeUnit unit){
    try {
        if(delay <= 0){
            logService.addQueueInsertLog(mqData.getMessageId(), mqData.getTopic()
                    , mqData.getMessage(), null);
            client.getBlockingQueue(MQ_KEY).put(JSON.toJSONString(mqData));
        } else {
            RQueue<String> distinationQueue = client.getBlockingQueue(MQ_KEY);
            logService.addQueueInsertLog(mqData.getMessageId(), mqData.getTopic(), mqData.getMessage()
                    , unit.toSeconds(delay));
            client.getDelayedQueue(distinationQueue).offer(JSON.toJSONString(mqData), delay, unit);
        }
    } catch (InterruptedException e) {
        log.error("线程切换错误", e);
    }
}
```
消息监听原理，监听消息，切换线程同步处理消息。这里说的为什么需要切换线程呢？如果只用一个线程处理消息的话会有一个问题，
每个线程对应一条数据库连接，线程长时间运行数据库连接也会一直持有，长时间没有消息的情况下，mysql会主动断掉连接，导致
主动断开后的第一条消息执行超时。  
```java
RBlockingQueue<Object> blockingQueue = client.getBlockingQueue(MQ_KEY);
Object value = blockingQueue.take();
MQData mqData = JSON.parseObject(value.toString(), MQData.class);
CompletableFuture<Void> future = CompletableFuture.runAsync(new HandleMessage(mqData));
future.get();
```
消息监控，如果MQ少了监控，就会让程序执行过程中我们变成一个瞎子，监控对于MQ来说至关中，我们通过Redis实现的MQ功能无法
实现像RocketMQ类似的强大监控功能，所以我们对监控进行了简单的实现，可监听所有消息的运行情况及错误日志，失败可重新发送消息。  

为了让监控日志打点不影响到原有mq的性能，所以日志状态打点采用Redis的ScoredSortedSet进行存储，异步处理。  
```java
JSONObject json = new JSONObject();
json.put("status", LogStatus.AWAIT);
json.put("messageId", messageId);
json.put("topic", topic);
json.put("message", message);
json.put("time", System.currentTimeMillis());
json.put("delayedSeconds", delayedSeconds);
json.put("pushInfo", IPUtils.getLocalIp());

client.getScoredSortedSet(MQ_LOG_QUEUE_KEY).add(
        System.currentTimeMillis() ,json.toJSONString()
```
数据也是通过异步的方式进行处理的，有兴趣可以直接看MqLogServiceImpl


## common-oss

对象存储组件，使用aliyun oss作为底层支持

## common-security

安全组件，使用spring security实现安全控制

使用JWT进行用户的权限认证，验证实现过程  
```java
String auth = ServletUtils.request().getHeader("Authorization");
//不存在Authorization
if(StringUtils.isEmpty(auth)) {
    return;
}

SecurityUserDetails user = (SecurityUserDetails) userDetailsService.loadUserByUsername(auth);

//持有subject
SecurityContextHolder.setSubject(user.getSubject());

UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
token.setDetails(user);
//设置security权限
org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(token);
```


## common-web

Web组件， 使用Spring boot 和 Spring MVC实现

## common-web-admin

管理后台及管理员管理组件

## common-web-app-patch

APP热更新解决方案

## common-web-base

Web开发的一些基础组件


