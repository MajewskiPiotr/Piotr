package core.Tools;

import org.openqa.selenium.By;

/**
 * Created by Piotr Majewski on 2017-06-08.
 */
public class JiraBy {

    public static By getTaskLink() {
        return By.partialLinkText("GO");
    }

    public static By getTaskType() {
        System.out.println("szukam");
        return By.tagName("a");
    }

}
