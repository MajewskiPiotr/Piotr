package core.Tools;

import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Piotr Majewski on 2017-06-21.
 */
public class Tools {

    public static void waitForProcesing(long wait) {
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //metoda wypisujaca zawartość listy
    public static void wypiszListe(List elements) {
        if (elements.equals(WebElement.class)) {
            List<WebElement> lista = (List<WebElement>) elements;
            for (WebElement w : lista) {
                System.out.println(w.getText());
            }
        } else {
            List<String> lista = (List<String>) elements;
            for (String w : lista) {
                System.out.println(w);
            }
        }

    }
}
