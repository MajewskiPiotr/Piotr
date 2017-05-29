package core.Tools.HelpersClass;

import org.openqa.selenium.WebElement;

import java.io.Serializable;

/**
 * Created by Piotr Majewski on 2017-05-17.
 */
public class Task implements Serializable {

    private WebElement key;
    private String status;

    public Task(String status) {
        this.status = status;
    }

    public Task(WebElement key, String status) {
        this.key = key;
        this.status = status;
    }

    public WebElement getKey() {
        return key;
    }

    public void setKey(WebElement key) {
        this.key = key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
