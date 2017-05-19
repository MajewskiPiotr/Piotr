package PageObjects.TaskPage.TaskPage_Tab;

import Elements.Task.TaskStatus;
import PageObjects.TaskPage.JobTaskPage;
import Tools.Negotiations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-05-18.
 */
public class AssigmentsTabPage extends JobTaskPage {
    WebDriver driver;

    @FindBy(xpath = "//*[@id='customfield_12000-val']//tbody/tr")
    private List<WebElement> negotiations;


    public AssigmentsTabPage(WebDriver driver) {
        super(driver);
    }

    public int getNegociationCount() {
        return negotiations.size();
    }

    public List<Negotiations> getNegotiations() {
        List<Negotiations> negotiationsList = new ArrayList<>();
        for (int i = 0; i <= negotiations.size() - 1; i++) {
            String key = negotiations.get(i).findElement(By.xpath("//*[@id='negotiation-tasks-template-field']/table/tbody/tr[" + (i + 1) + "]/td[1]/a")).getText();
            String status = negotiations.get(i).findElement(By.xpath("//*[@id='negotiation-tasks-template-field']/table/tbody/tr[" + (i + 1) + "]/td[4]/span")).getText();
            String translator = negotiations.get(i).findElement(By.xpath("//*[@id='negotiation-tasks-template-field']/table/tbody/tr[" + (i + 1) + "]/td[5]")).getText();
            negotiationsList.add(new Negotiations(key, status, translator));
        }
        return negotiationsList;
    }

    public boolean checkIsStatusChange() {
        int accepted = 0;
        int unavailable = 0;
        List<Negotiations> list = getNegotiations();
        for (Negotiations negotiations : list) {
            if (negotiations.getStatus().equals(TaskStatus.ACCEPTED)) {
                accepted++;
            } else if (negotiations.getStatus().equals(TaskStatus.UNAVAILABLE)) {
                unavailable++;
            }
        }
        if (accepted>1){
            Assert.fail("Negocjacje zaakceptowaly co najmniej 2 osoby!");}

        return accepted+unavailable==list.size();
    }


}
