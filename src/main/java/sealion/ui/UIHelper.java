package sealion.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import sealion.dao.AccountDao;
import sealion.domain.Key;
import sealion.domain.MarkedText;
import sealion.domain.TaskStatus;
import sealion.entity.Account;

@ApplicationScoped
public class UIHelper {

    private ScriptEngine scriptEngine;

    @Inject
    private AccountDao accountDao;

    @PostConstruct
    public void init() {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
    }

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
        default:
            return "";
        }
    }

    public String marked(MarkedText text) throws ScriptException, IOException {
        if (text == null) {
            return null;
        }
        ScriptContext context = new SimpleScriptContext();
        try (Reader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream(
                        "/META-INF/resources/webjars/marked/0.3.5/marked.min.js"),
                StandardCharsets.UTF_8))) {
            scriptEngine.eval(reader, context);
        }
        UnaryOperator<String> marked = (UnaryOperator<String>) scriptEngine
                .eval("var F = Java.type('" + UnaryOperator.class.getName() + "');"
                        + "var G = Java.extend(F, { apply: function(s) { return marked(s); }});"
                        + "new G()", context);
        return marked.apply(text.getValue());
    }

    public String avatar(Account account, int size) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] email = account.email.getValue().getBytes(StandardCharsets.UTF_8);
        byte[] digest = md.digest(email);
        String hash = IntStream.range(0, digest.length)
                .mapToObj(i -> String.format("%02x", digest[i] & 0xff))
                .collect(Collectors.joining());
        return avatar(hash, size);
    }

    public String avatar(Key<Account> account, int size) {
        return accountDao.selectById(account).map(a -> avatar(a, size))
                .orElseGet(() -> avatar("00000000000000000000000000000000", size));
    }

    private String avatar(String hash, int size) {
        return "http://www.gravatar.com/avatar/" + hash + "?s=" + size;
    }
}
