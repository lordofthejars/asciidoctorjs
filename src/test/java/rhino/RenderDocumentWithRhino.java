package rhino;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.script.*;


import org.junit.Test;

public class RenderDocumentWithRhino {

    @Test
    public void testInvokable() throws ScriptException, NoSuchMethodException {

        ScriptEngineManager engineManager =
                new ScriptEngineManager();
        ScriptEngine engine =
                engineManager.getEngineByName("nashorn");


        SimpleScriptContext simpleScriptContext = new SimpleScriptContext();
        Bindings bindings = new SimpleBindings();

        simpleScriptContext.setBindings(bindings, ScriptContext.ENGINE_SCOPE);

        try {
            engine.eval(new InputStreamReader(RenderDocumentWithRhino.class.getResourceAsStream("/opal.js")), simpleScriptContext);
            engine.eval(new InputStreamReader(RenderDocumentWithRhino.class.getResourceAsStream("/asciidoctor.js")), simpleScriptContext);
            engine.eval(new InputStreamReader(RenderDocumentWithRhino.class.getResourceAsStream("/asciidoctor_extensions.js")), simpleScriptContext);
            engine.eval(new InputStreamReader(RenderDocumentWithRhino.class.getResourceAsStream("/asciidoctorjava.js")), simpleScriptContext);
        } catch (ScriptException e) {
            throw new IllegalArgumentException(e);
        }

        String content = "*Hello World*";
        Map<String, Object> options = new HashMap<>();
        options.put("header_footer", true);

        simpleScriptContext.getBindings(ScriptContext.ENGINE_SCOPE).put("listOptions", options.keySet().toArray());
        simpleScriptContext.getBindings(ScriptContext.ENGINE_SCOPE).put("options", options);

        Invocable invocable = (Invocable) engine;

        Object eval = engine.eval("Opal.hash2(listOptions, options)", simpleScriptContext);

        engine.setContext(simpleScriptContext);

        AsciidoctorJs asciidoctorJs =
                invocable.getInterface(
                        AsciidoctorJs.class);
        System.out.println(content);
        System.out.println(eval);
        System.out.println(asciidoctorJs);
        System.out.println(
                asciidoctorJs.render(content, eval));


    }

}
