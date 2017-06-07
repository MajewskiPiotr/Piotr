package core.Tools;

import core.Tools.Configuration.Property;
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
    static String taskJob = "";


    private static void runScript(WebDriver driver, String file) throws Exception {

        if (driver instanceof JavascriptExecutor) {
            try {
                driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
                taskJob = (String) ((JavascriptExecutor) driver).executeScript(readfile(file));
            } catch (ScriptTimeoutException ex) {
                if (ex.getCause() != null) {
                    throw new Exception("Problem ze scryptem ");
                }
            }
        } else {
            throw new IllegalStateException("This driver does not support JavaScript!");
        }

    }

    private static void runScriptWithParam(WebDriver driver, String file, String param) throws Exception {

        if (driver instanceof JavascriptExecutor) {
            try {
                driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
                taskJob = (String) ((JavascriptExecutor) driver).executeScript(readfile(file), param);
            } catch (ScriptTimeoutException ex) {
                if (ex.getCause() != null) {
                    throw new Exception("Problem ze scryptem ");
                }
            }
        } else {
            throw new IllegalStateException("This driver does not support JavaScript!");
        }

    }

    public static String createTranslationJobWithManyTasks(WebDriver driver) {
        //zwiekszam timeout dla skryptów
        driver.manage().timeouts().setScriptTimeout(180, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);

        try {
            runScript(driver, "jobGeneratorManyTasks.js");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("wygenerowano job: " + taskJob);
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return taskJob;
    }

    public static String createTranslationJob(WebDriver driver) {
        //zwiekszam timeout dla skryptów
        driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

        try {
            runScript(driver, "jobGenerator.js");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("wygenerowano job: " + taskJob);
        return taskJob;
    }

    public static String createTranslationJobForDropbox(WebDriver driver) {

        try {
            runScript(driver, "jobGeneratorDropbox.js");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("wygenerowano job for Dropbox : " + taskJob);
        return taskJob;
    }

    public static void switchUser(WebDriver driver, String user) {
        try {
            runScriptWithParam(driver, "zmianaUsera.js", user);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void switchUserByLogin(WebDriver driver, String user) {
        try {
            runScriptWithParam(driver, "zmianaUseraPoLoginie.js", user);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String readfile(String filename) {
        String basePath = Property.getProperty("basePath");
        String scrypt = "";
        // Deklarowanie i tworzenie obiektu typu File
        File plikDane = new File(basePath + "\\Lion\\src\\main\\java\\core\\Tools\\" + filename);
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
