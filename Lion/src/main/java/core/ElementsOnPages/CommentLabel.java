package core.ElementsOnPages;

/**
 * Created by Piotr Majewski on 2017-06-26.
 */
public enum CommentLabel {
    INTERNAL("INTERNAL");

    private String opis;


    CommentLabel(String opis) {
        this.opis = opis;
    }

    public String getOpis() {
        return opis;
    }
}

