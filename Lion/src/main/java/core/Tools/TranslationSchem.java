package core.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-08-24.
 * Klasa pomocnicza
 * reprezentuje jeden text to tłumaczenia
 */
public class TranslationSchem {
    private String from;
    private String to;
    public static List<TranslationSchem> listaTranslation = new ArrayList<>();

    public TranslationSchem(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public static List<TranslationSchem> getListaTranslation() {
        return listaTranslation;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }


}
