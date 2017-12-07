package Automation;

import core.BaseTestClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-12-06.
 */
public class RaportOfert extends BaseTestClass {

    List<WebElement> listaPrzetargow;


    @Test
    public void getRaport() throws InterruptedException, IOException {
        wyszukaj();
        List<Zamówienie> listaZamówien = new ArrayList<>();
        listaPrzetargow = driver.findElements(By.xpath(".//*[@id='notice']/tbody/*"));
        System.out.println("TEST " + driver.findElement(By.xpath(".//*[@id='notice']/tbody/tr[1]/td[3]")).getText());
        // Tworzenie obiektu typu PrintWriter, jako argument
        // zostaje podana nazwa pliku
        PrintWriter plik = new PrintWriter("Test2.txt");

        int iterator = 1;
        for (iterator = 1; iterator <= listaPrzetargow.size(); iterator++) {
            Zamówienie zamówienie = new Zamówienie();
            zamówienie.setNumer(getText("//*[@id='notice']/tbody/tr[" + iterator + "]/td[2]/a"));
            zamówienie.setOpis(getText("//*[@id='notice']/tbody/tr[" + iterator + "]/td[3]"));
            zamówienie.setKraj(getText("//*[@id=\"notice\"]/tbody/tr[" + iterator + "]/td[4]"));
            zamówienie.setData(getText(".//*[@id=\"notice\"]/tbody/tr[" + iterator + "]/td[5]"));
            zamówienie.setWartosc(getWartoscZamowienia(".//*[@id='notice']/tbody/tr[" + iterator + "]/td[2]/a"));

            listaZamówien.add(zamówienie);
            // po kolei zapisywane są kolejne linijki tekstu
            plik.println(zamówienie.toString());
        }


        plik.close();


    }

    public String getWartoscZamowienia(String s) {
        driver.findElement(By.xpath(s))

        return wartoscZamowienia;
    }


    public void wyszukaj() {
        driver.navigate().to("http://ted.europa.eu/TED/search/searchResult.do");
        click("//*[@id='menu']/div/ul/li[18]/a");
        //Wyszukiwanie
        click(".//*[@id='left-column']/ul[2]/li[1]/a");
        //Warunki wyszukiwania
        //Klik w Archiwka button
        click(".//*[@id='scope-fs']/label[4]");
        //Zamówienie
        addText(".//*[@id='searchCriteria.contractList']", "'Dostawy'");
        addText(".//*[@id='searchCriteria.contractList']", ",'Usługi'");
        //Rodzaj dokumentu
        addText(".//*[@id='searchCriteria.documentTypeList']", "'Ogłoszenie o udzieleniu zamówienia'");
        //Więcej kryteriów
        click(".//*[@id='link_searchShowHideBlock']");
        //Kod CPV
        addText(".//*[@id='searchCriteria.cpvCodeList']", "30200000, 72000000, 30000000, 30100000, 48000000");
        //szukaj
        click(".//*[@id='scope-fs']/div[2]/button");
    }

    public String getText(String path) {
        return driver.findElement(By.xpath(path)).getText();
    }

    public void click(String element) {
        driver.findElement(By.xpath(element)).click();
    }

    public void addText(String element, String inputText) {
        driver.findElement(By.xpath(element)).sendKeys(inputText);

    }


    public class Zamówienie {
        String numer;
        String opis;

        @Override
        public String toString() {
            return numer + "; " + opis + "; " + kraj + "; " + data + "; " + wartosc;
        }

        String kraj;
        String data;
        String wartosc;

        public String getOpis() {
            return opis;
        }

        public void setOpis(String opis) {
            this.opis = opis;
        }

        public String getKraj() {
            return kraj;
        }

        public void setKraj(String kraj) {
            this.kraj = kraj;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getWartosc() {
            return wartosc;
        }

        public void setWartosc(String wartosc) {
            this.wartosc = wartosc;
        }

        public String getNumer() {
            return numer;
        }

        public void setNumer(String numer) {
            this.numer = numer;
        }
    }

}
