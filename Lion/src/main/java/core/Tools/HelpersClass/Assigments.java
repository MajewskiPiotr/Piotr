package core.Tools.HelpersClass;

import java.io.Serializable;

/**
 * Created by Piotr Majewski on 2017-05-18.
 */
public class Assigments implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type;
    private String translator;
    private String key;
    private String status;

    public Assigments(String key, String status, String translator, String type) {
        this.status = status;
        this.translator = translator;
        this.key = key;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Assigments{" +
                "type='" + type + '\'' +
                ", translator='" + translator + '\'' +
                ", key='" + key + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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


