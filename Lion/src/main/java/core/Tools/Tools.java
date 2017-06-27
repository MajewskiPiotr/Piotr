package core.Tools;

import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Piotr Majewski on 2017-06-21.
 */
public class Tools {

    public static void waitForProcesing(long wait){
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //metoda wypisujaca zawartość listy
    public static void wypiszListe(List<WebElement> elements) {
        for (WebElement w : elements) {
            System.out.println(w.getText());
        }

    }}
