package core.Tools;

import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    public static List<TranslationSchem> getTranslationSchemFromFile(String file) throws IOException {

        List<TranslationSchem> listaMapowania = TranslationSchem.getListaTranslation();
        //  String path = "C:\\Users\\Piotr Majewski\\Desktop\\tlumaczeniaFinal.txt";

        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String textLine = bufferedReader.readLine();
        do {
            String from = textLine.substring(0, textLine.indexOf(";")).trim();
            String to = textLine.substring(textLine.indexOf(";") + 1).trim();
            listaMapowania.add(new TranslationSchem(from, to));
            textLine = bufferedReader.readLine();
        } while (textLine != null);
        bufferedReader.close();
        System.out.println("Wczytano plik. Jest  " + listaMapowania.size() + " pozycji to przetłumaczenia");
        return listaMapowania;
    }
}
