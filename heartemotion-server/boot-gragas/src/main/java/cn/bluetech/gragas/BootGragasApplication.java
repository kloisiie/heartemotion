package cn.bluetech.gragas;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * @author  xu
 */
@SpringBootApplication
@EnableSwagger2
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.brframework", "cn.bluetech"})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableScheduling
@Slf4j
public class BootGragasApplication {

    public static void main(String[] args) {

        //设置ForkJoinPool线程数量
        setForkJoinPoolParallelism();

        //处理参数
        args = handlerArgs(args);
        SpringApplication.run(BootGragasApplication.class, args);

    }

    private static void setForkJoinPoolParallelism(){
        int parallelism = Runtime.getRuntime().availableProcessors();
        if(parallelism <= 4){
            parallelism = 8;
        } else {
            parallelism = parallelism * 2;
        }
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", parallelism + "");
    }

    /**
     * 处理参数
     * @param args
     * @return
     */
    private static String[] handlerArgs(String[] args){
        boolean defActive = false;
        for (String arg : args) {
            if(arg.startsWith("--spring.profiles.active=")){
                defActive = true;
                break;
            }
        }

        if(!defActive){
            //默认使用dev启动
            List<String> argList = Lists.newArrayList(args);
            argList.add("--spring.profiles.active=dev");
            args = argList.toArray(new String[]{});
        }

        return args;
    }

}
