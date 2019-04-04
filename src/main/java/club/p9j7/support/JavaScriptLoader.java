package club.p9j7.support;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileReader;
import java.io.IOException;

@Configuration
public class JavaScriptLoader {
    @Bean
    public Invocable invocable() throws IOException, ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        String jsFileName = "C:\\Users\\P9J7\\Desktop\\elkcity\\src\\main\\resources\\server.js";
        // 读取js文件
        FileReader reader = new FileReader(jsFileName);
        // 执行指定脚本
        engine.eval(reader);
        Invocable invocable = null;
        if (engine instanceof Invocable) {
            invocable = (Invocable) engine;
        }
        reader.close();
        return invocable;
    }
}
