package Automation;


import PageObjects.ServiceDesk.MainPage.AddLenguageMappingPage;
import PageObjects.ServiceDesk.MainPage.DashboardPage;
import PageObjects.ServiceDesk.MainPage.ProjectSettingsPage;
import PageObjects.ServiceDesk.MainPage.ServiceDeskLoginPage;
import core.BaseTestClass;
import core.Tools.Tools;
import core.Tools.TranslationSchem;

import java.util.List;

/**
 * Created by Piotr Majewski on 2017-06-08.
 */

// Automat ma za zadanie wprowadzenie tłumaczenia w ramach wtyczki do tłumaczenia.
//nie jest to test do uruchamiania wraz z innymi
// do działania należy podac ściezkę do pliku w którym definiujemy mapowanie from;to np. en;pl
public class Automat_Biznesowy_wprowadzenie_tlumaczenia_SD extends BaseTestClass {

    //adres do ustawień wtyczki translator w modyfikowanym projekcie
    private static final String VPN = "/secure/project/TranslationPortal.jspa?pid=10101";
    private static final String PROD = "/secure/project/TranslationPortal.jspa?pid=19207";

    //@Test
    public void test() throws Exception {
        //załaduj plik i wczytaj mapowanie en/pl
        List<TranslationSchem> listaMapowania = Tools.getTranslationSchemFromFile("C:\\Users\\Piotr Majewski\\Desktop\\tlumaczeniaFinal.txt");

        ServiceDeskLoginPage loginPage = new ServiceDeskLoginPage(driver);
        DashboardPage dashboardPage = loginPage.loginAsAdminOnProd();
        //podać adres dostepu do ustawień wtyczki w projekcie
        dashboardPage.goToUrl(PROD);

        for (int i = 0; i < listaMapowania.size(); i++) {
            ProjectSettingsPage projectSettingsPage = new ProjectSettingsPage(driver);
            AddLenguageMappingPage add = projectSettingsPage.addNewTranslation();
            add.setLanguage("pl");
            add.setKey(listaMapowania.get(i).getFrom());
            add.setValue(listaMapowania.get(i).getTo());
            add.setShared();
            add.addMapping();
        }
    }
}
