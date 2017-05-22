package Tools;

/**
 * Created by Piotr Majewski on 2017-05-18.
 */
public class Negotiations {

    private String translator;
    private String key;
    private String status;
    public Negotiations(String key, String status, String translator) {
        this.status=status;
        this.translator = translator;
        this.key=key;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


