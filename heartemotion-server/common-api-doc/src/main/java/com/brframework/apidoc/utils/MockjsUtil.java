package com.brframework.apidoc.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.script.JavaScriptEngine;
import cn.hutool.script.ScriptUtil;
import lombok.extern.slf4j.Slf4j;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.util.Objects;

/**
 * mockjs调用工具
 * http://mockjs.com
 * @author xu
 * @date 2020/4/20 14:31
 */
@Slf4j
public class MockjsUtil {
    /** mockjs文件名 */
    private static final String MOCKJS_FILE_NAME = "mock.js";

    /** javascript 执行引擎 */
    private static ScriptEngine engine;

    /**
     * 获取运行了mockjs的javascript引擎
     * @return 运行了mockjs的javascript引擎
     */
    private static synchronized ScriptEngine getEngine(){
        if(engine == null){
            String mockjs = IoUtil.read(Objects.requireNonNull(MockjsUtil.class.getClassLoader()
                            .getResourceAsStream(MOCKJS_FILE_NAME)),"UTF-8");
            JavaScriptEngine scriptEngine = ScriptUtil.getJavaScriptEngine();
            try {
                scriptEngine.eval(mockjs);
            } catch (ScriptException e) {
                log.error("mockjs初始化失败", e);
                throw new RuntimeException(e);
            }
            engine = scriptEngine;
        }

        return engine;
    }

    /**
     * 通过模板生成MOCK数据
     * @param template    MOCK模板
     * @return MOCK数据
     */
    public static String mock(String template){
        ScriptEngine engine = getEngine();
        try {
            return (String) engine.eval("JSON.stringify(Mock.mock(" + template + "))");
        } catch (ScriptException e) {
            log.error("mock执行失败", e);
            throw new RuntimeException(e);
        }
    }

}
