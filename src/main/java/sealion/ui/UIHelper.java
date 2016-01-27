package sealion.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.function.UnaryOperator;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import sealion.domain.MarkedText;
import sealion.domain.TaskStatus;

public class UIHelper {

    public String cssClass(TaskStatus status) {
        switch (status) {
        case OPEN:
            return "aui-lozenge aui-lozenge-success";
        case DOING:
            return "aui-lozenge aui-lozenge-current";
        case DONE:
            return "aui-lozenge aui-lozenge-error";
        case CLOSED:
            return "aui-lozenge aui-lozenge-default";
        }
        return "";
    }

    public String marked(MarkedText text) throws ScriptException {
        if (text == null) {
            return null;
        }
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
        ScriptContext context = new SimpleScriptContext();
        Reader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream(
                        "/META-INF/resources/webjars/marked/0.3.5/marked.min.js"),
                StandardCharsets.UTF_8));
        scriptEngine.eval(reader, context);
        UnaryOperator<String> marked = (UnaryOperator<String>) scriptEngine
                .eval("var F = Java.type('" + UnaryOperator.class.getName() + "');"
                        + "var G = Java.extend(F, { apply: function(s) { return marked(s); }});"
                        + "new G()", context);
        return marked.apply(text.getValue());
    }
}
