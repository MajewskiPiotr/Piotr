package Tools;

import org.openqa.selenium.WebElement;

/**
 * Created by Piotr Majewski on 2017-05-17.
 */
public class Task {

    private WebElement key;
    private String status;

    public Task(String status){
        this.status= status;
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
