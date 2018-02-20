package Automation;

import core.BaseTestClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Piotr Majewski on 2017-12-06.
 */
public class RaportOfert extends BaseTestClass {

    List<WebElement> listaPrzetargow;

    public void next() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.xpath(".//*[@id='middle-column']//*[text()='Następny'][1]")).click();

    }


    @Test
    public void getRaport() throws InterruptedException, IOException {
        wyszukaj();

        PrintWriter plik = new PrintWriter("r.txt");
        try {


            for (int pageNumber = 1; pageNumber <= 227; pageNumber++) {
                System.out.println("wykonuje " + pageNumber);
                listaPrzetargow = driver.findElements(By.xpath(".//*[@id='notice']/tbody/*"));
                int iterator;

                for (iterator = 1; iterator <= listaPrzetargow.size(); iterator++) {
                    Zamówienie zamówienie = new Zamówienie();
                    zamówienie.setLink(getText("//*[@id='notice']/tbody/tr[" + iterator + "]/td[2]/a"));
                    zamówienie.setNumer(getText("//*[@id='notice']/tbody/tr[" + iterator + "]/td[2]/a"));
                    zamówienie.setOpis(getText("//*[@id='notice']/tbody/tr[" + iterator + "]/td[3]"));
                    zamówienie.setKraj(getText("//*[@id=\"notice\"]/tbody/tr[" + iterator + "]/td[4]"));
                    zamówienie.setData(getText(".//*[@id=\"notice\"]/tbody/tr[" + iterator + "]/td[5]"));
                    zamówienie.setWartosc(getWartoscZamowienia(".//*[@id='notice']/tbody/tr[" + iterator + "]/td[2]/a"));

                    plik.println(zamówienie.toString());

                    System.out.println("Page : " + pageNumber + " iterator : " + iterator + "  " + zamówienie.toString());
                }
                next();
            }
        } catch (Exception e) {
            System.out.println("złapałem błąd " + e.getMessage());
            plik.close();
        } finally {
            plik.close();
        }


    }

    public String getWartoscZamowienia(String s) {
        click(s);
        String wartosc;
        try {
            String temp = driver.findElement(By.xpath(".//*[@id='fullDocument']//*[contains(span,'Sekcja II')]/../../div[contains(.,'wartość')][1]/div[contains(.,'wartość')] [last()]/div")).getText();
            wartosc = temp.replace("Bez VAT", "").replace("ś", "s")
                    .replace("ć", "c")
                    .replace("ń", "n")
                    .replace("ż", "z")
                    .replace("ę", "e");
        } catch (org.openqa.selenium.NoSuchElementException e) {
            wartosc = "Brak wartosci w ofercie";
        }
        driver.navigate().back();
        return wartosc;
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
        addText(".//*[@id='searchCriteria.cpvCodeList']", "48610000, 48611000, 48612000, 48613000, 48614000, 48960000, 48110000, 48331000, 48332000, 48333000, 48150000, 48160000, 48170000, 48430000, 48440000, 48812000, 48450000, 48813000, 48460000, 48814000, 48470000, 48480000, 48490000, 48221000, 48222000, 48920000, 48310000, 48930000, 48312000");
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
        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            String temp = "http://ted.europa.eu/udl?uri=TED:NOTICE:" + link + ":TEXT:PL:HTML&src=0";
            this.link = temp;
        }

        String link;
        String numer;
        String opis;

        @Override
        public String toString() {
            return link + "; " + numer + "; " + opis + "; " + kraj + "; " + data + "; " + wartosc;
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
