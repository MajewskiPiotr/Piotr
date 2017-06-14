package core.Tools;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by Piotr Majewski on 2017-06-08.
 * klasa pomaga zwracać elementy z listy tasków na zakładkach (np. Assignments)
 */
public class FindInTaskTab {


    //zwraca WebElement linku to tasku z WebElementu (element musi reprezentować rekord w liście z Zakładki na Stronie z taskiem)
    public static WebElement getTaskLink(WebElement element) {
        return element.findElement(By.className("issuekey"));
    }


}
