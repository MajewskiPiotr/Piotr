package Tools;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScriptTimeoutException;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by Piotr Majewski on 2017-05-16.
 */
public class JsScript {


    public static void createJob(WebDriver driver) throws Exception {
        if (driver instanceof JavascriptExecutor) {
            try {
                driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
                ((JavascriptExecutor) driver).executeAsyncScript(readfile());
            } catch (ScriptTimeoutException ex) {
                if (ex.getCause() != null) {
                    throw new Exception("Problem ze scryptem tworzacym JOB");
                }
            }
        } else {
            throw new IllegalStateException("This driver does not support JavaScript!");
        }
    }

    private static String readfile() {
        String scrypt = "";
        // Deklarowanie i tworzenie obiektu typu File
        File plikDane = new File("C:\\Lionbridge\\src\\main\\java\\Tools\\skrypt.js");
        ;
        // Utworzenie obiektu typu String, który będzie
        // przechowywał odczytany tekst
        try {
            // Utworzenie obiektu typu String
            Scanner skaner = new Scanner(plikDane);
            // Odczytywanie kolejnych linii pliku dopóki są kolejne linie
            while (skaner.hasNextLine()) {
                // Do łańcucha znaków dodawana jest zawartość kolejnej linii
                // oraz znak \n oznaczający następną linię
                scrypt = scrypt + skaner.nextLine() + "\n";
            }
            // Jeśli nie udało się odczytać pliku
        } catch (FileNotFoundException e) {
            System.out.println("Brak Pliku do odczytania!");
        }
        return scrypt;

    }

}
