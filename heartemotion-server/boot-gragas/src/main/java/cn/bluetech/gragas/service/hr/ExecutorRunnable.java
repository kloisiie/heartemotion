package cn.bluetech.gragas.service.hr;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程池中执行
 * 解决异常时无异常日志打印的问题
 * @author xu
 * @date 2020/8/20 9:10
 */
@Slf4j
public abstract class ExecutorRunnable implements Runnable {

    /**
     * 运行
     */
    protected abstract void doRun();

    /**
     * 异常处理
     * @param e  异常
     */
    protected void exception(Throwable e){
        log.error("ExecutorRunnable", e);
    }

    @Override
    public void run() {
        try {
            doRun();
        } catch (Throwable e){
            exception(e);
        }
    }
}
