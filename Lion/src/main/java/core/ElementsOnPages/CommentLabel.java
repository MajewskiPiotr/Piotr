package core.ElementsOnPages;

/**
 * Created by Piotr Majewski on 2017-06-26.
 */
public enum CommentLabel {
    INTERNAL("INTERNAL"),
    TO_CUSTOMER("TO CUSTOMER");


    private String label;


    CommentLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

