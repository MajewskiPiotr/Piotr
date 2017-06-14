package Tests;

import org.openqa.selenium.WebElement;

/**
 * Created by Piotr Majewski on 2017-06-14.
 */
public class JavaTest {
    public static void main(String[] args) {

        int i = 1;
        String xpathForCategory = "((//*[@id='rlabs-object-list-table-values']/*)[" + i + "]//*[@class='rlabs-value-container']/a)[2]";

        for(i=1; i<4; i++){
            System.out.println(xpathForCategory);
        }
    }


}
