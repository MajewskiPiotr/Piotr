package core.Tools;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * Created by Piotr Majewski on 2017-06-08.
 * klasa pomaga zwracać elementy z listy tasków na zakładkach (np. Assignments)
 */
public class FindInTaskList {


    //zwraca WebElement linku to tasku z WebElementu (element musi reprezentować rekord w liście z Zakładki na Stronie z taskiem)
    public static String getTaskLink(WebElement element) {
        WebElement e = element.findElement(By.className("issuekey"));
        return e.getText();
    }


    public static WebElement getProductClass(WebElement element) {
        return element.findElement(By.className("rlabs-customfield-viewdialog-object-info"));
    }

    public static WebElement getStatusFromRelatedIssue(WebElement element, int iterator){
        return element.findElement(By.xpath("//dd["+iterator+"]//*[@class='link-snapshot']"));
    }

}
