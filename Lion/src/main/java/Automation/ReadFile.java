package Automation;

import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Piotr Majewski on 2017-12-08.
 */
public class ReadFile {
    Map<String, BigDecimal> kursWalut = new HashMap<>();

    @Test
    public void getFromFile() throws FileNotFoundException {
        File plikDane = new File("C:\\Automaty\\Piotr\\Lion\\Dane.txt");
        // Utworzenie obiektu typu String, który będzie
        // przechowywał odczytany tekst
        String odczyt = "";

        try {
            PrintWriter plik = new PrintWriter("wynik.txt");

            // Utworzenie obiektu typu String
            Scanner skaner = new Scanner(plikDane);
            // Odczytywanie kolejnych linii pliku dopóki są kolejne linie
            while (skaner.hasNextLine()) {
                // Do łańcucha znaków dodawana jest zawartość kolejnej linii
                // oraz znak \n oznaczający następną linię
                odczyt = skaner.nextLine();
                if (odczyt.contains("http://")) {
                    String[] split = odczyt.split(";");
                    String link = split[0];
                    String numer = split[1];
                    String opis = split[2];
                    String kraj = split[3];
                    String data = split[4];
                    String opisWartosci = split[5];

                    Zamówienie zamówienie = new Zamówienie();
                    zamówienie.setLink(link);
                    zamówienie.setNumer(numer);
                    zamówienie.setOpis(opis);
                    zamówienie.setKraj(kraj);
                    zamówienie.setData(data);
                    zamówienie.setWartosc(opisWartosci);
                    //zapisać to do pliku
                    System.out.println(zamówienie.numer + "  " + zamówienie.minCena );
                    plik.println(zamówienie.toString());
                }

            }
            plik.close();
            // Jeśli nie udało się odczytać pliku
        } catch (FileNotFoundException e) {
            System.out.println("Brak Pliku do odczytania!");
        }

    }



    public class Zamówienie {

        String separator = " ; ";

        String link = "";
        String numer = "";
        String opis;
        String kraj;
        String data;
        String opisWartosci;

        String vat = "";
        String minCena = "";
        String waluta = "";


        @Override
        public String toString() {

            String o = link + separator + numer + separator + opis + separator + kraj + separator + data;
            String wartosciOpisow = "";


            wartosciOpisow = separator + minCena + separator + waluta + separator + vat;

            return o + wartosciOpisow + separator + opisWartosci;
        }


        public void setWartosc(String w) {
            opisWartosci = w;

            String wartosc;
            if (w.contains("Łącznie")) {
                wartosc = w.substring(0, w.indexOf("Ł"));
            } else if (w.contains("Jezeli")) {
                wartosc = w.substring(0, w.indexOf("Jezeli"));

            } else wartosc = w;

            if (wartosc.contains("Brak wartosci w ofercie")) {
                vat = "Brak wartosci w ofercie";
                minCena = "0";
            }

            if (wartosc.toLowerCase().contains("bez vat")) {
                vat = "bez Vat";
            } else {
                vat = "z Vat";
            }

            if (wartosc.contains("Wartosc")) {
                String temp = wartosc.substring(wartosc.indexOf(":") + 1).trim();
                minCena = temp.substring(0, temp.length() - 3).trim().replace(",", ".").replace(" ", "");
            }

            if (wartosc.contains("Najtansza")) {
                String minTemp = wartosc.substring(wartosc.indexOf(":") + 2, wartosc.indexOf("/")).trim();
                minCena = minTemp.substring(0, minTemp.length() - 3).trim().replace(",", ".").replace(" ", "");
                String temp = wartosc.substring(wartosc.indexOf("/") + 20).replace("brana pod uwage", "").trim();
            }
            ustalWalute(wartosc);


        }


        public void ustalWalute(String s) {

            if (s.contains("LTL")) {
                waluta = "LTL";
            }
            if (s.contains("UK")) {
                waluta = "GBP";
            }
            if (s.contains("RON")) {
                waluta = "RON";
            }
            if (s.contains("NOK")) {
                waluta = "NOK";
            }
            if (s.contains("EUR")) {
                waluta = "EUR";
            }
            if (s.contains("PLN")) {
                waluta = "PLN";
            }
            if (s.contains("CHF")) {
                waluta = "CHF";
            }

            if (s.contains("SEK")) {
                waluta = "SEK";
            }
            if (s.contains("GBP")) {
                waluta = "GBP";
            }
            if (s.contains("CZK")) {
                waluta = "CZK";
            }
            if (s.contains("HRK")) {
                waluta = "HRK";
            }
            if (s.contains("HUF")) {
                waluta = "HUF";
            }
            if (s.contains("DKK")) {
                waluta = "DKK";
            }
            if (s.contains("MKD")) {
                waluta = "MKD";
            }
            if (s.contains("BGN")) {
                waluta = "BGN";
            }
            if (s.contains("USD")) {
                waluta = "USD";
            }

        }



        public void setLink(String link) {

            this.link = link;
        }

        public void setOpis(String opis) {
            this.opis = opis;
        }


        public void setKraj(String kraj) {
            this.kraj = kraj;
        }


        public void setData(String data) {
            this.data = data;
        }


        public void setNumer(String numer) {
            this.numer = numer;
        }


    }

}
