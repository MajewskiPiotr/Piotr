package core.JSscripts;

import core.Tools.Configuration.Property;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScriptTimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by Piotr Majewski on 2017-05-16.
 */
public class JsScript {
    static String taskJob = "";


    private static void runScript(WebDriver driver, String file) {

        if (driver instanceof JavascriptExecutor) {
            try {
                driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
                taskJob = (String) ((JavascriptExecutor) driver).executeScript(readfile(file));
                if (taskJob == null) {
                    Assert.fail("Nie udało sie wygenerować JOB-a");
                }
            } catch (ScriptTimeoutException ex) {
                if (ex.getCause() != null) {
                    Assert.fail("Problem ze scryptem ");
                }
            }
        } else {
            throw new IllegalStateException("This driver does not support JavaScript!");
        }
    }

    private static void runScriptWithParam(WebDriver driver, String file, String param) {

        if (driver instanceof JavascriptExecutor) {
            try {
                driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
                Boolean isSuccess = (Boolean) ((JavascriptExecutor) driver).executeScript(readfile(file), param);
                System.out.println("czy udalo sie przelaczyć: " + isSuccess);
            } catch (ScriptTimeoutException ex) {
                if (ex.getCause() != null) {
                    Assert.fail("Problem ze scryptem ");
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

        runScript(driver, "jobGeneratorManyTasks.js");
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
        runScript(driver, "jobGenerator.js");

        System.out.println("wygenerowano job: " + taskJob);
        return taskJob;
    }

    public static String createTranslationJobForDropbox(WebDriver driver) {

        runScript(driver, "jobGeneratorDropbox.js");
        System.out.println("wygenerowano job for Dropbox : " + taskJob);
        return taskJob;
    }

    public static void switchUser(WebDriver driver, String user) {
        runScriptWithParam(driver, "zmianaUsera.js", user);
    }

    public static void switchUserByLogin(WebDriver driver, String user) {
        runScriptWithParam(driver, "zmianaUseraPoLoginie.js", user);
        driver.navigate().refresh();
    }

    public static String readfile(String filename) {
        String basePath = Property.getProperty("basePath");
        String scrypt = "";
        // Deklarowanie i tworzenie obiektu typu File
        File plikDane = new File(basePath + "\\Lion\\src\\main\\java\\core\\JSscripts\\" + filename);

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
            Assert.fail("Brak Pliku ze skryptem do odczytania!");
        }
        return scrypt;

    }

}