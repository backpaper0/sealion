package sealion.ui;

import java.util.function.Function;

public final class UIResponse {

    public final String template;
    public final Object model;

    private UIResponse(String template, Object model) {
        this.template = template;
        this.model = model;
    }

    public static UIResponse render(String template, Object model) {
        return new UIResponse(template, model);
    }

    public static UIResponse render(String template) {
        return new UIResponse(template, null);
    }

    public static <T> Function<? super T, ? extends UIResponse> factory(String template) {
        return model -> render(template, model);
    }
}
