package PageObjects.TaskPage.TaskPage_Tab;

import PageObjects.Elements.Task.TaskStatus;
import PageObjects.TaskPage.JobTaskPage;
import core.Tools.HelpersClass.Assigments;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-05-18.
 * Klasa odzwierciedla zakladke ASSIGMENT
 */
public class AssigmentsTabPage extends JobTaskPage {

    @FindBy(xpath = "//*[@id='customfield_12000-val']//tbody/tr")
    private List<WebElement> negotiations;


    public AssigmentsTabPage(WebDriver driver) {
        super(driver);
    }

    public int getNegociationCount() {
        return negotiations.size();
    }

    private List<Assigments> getAssigments() {
        List<Assigments> assigmentsList = new ArrayList<>();
        for (int i = 0; i <= negotiations.size() - 1; i++) {
            String key = driver.findElement(By.xpath("//*[@id='negotiation-tasks-template-field']/table/tbody/tr[" + (i + 1) + "]/td[1]/a")).getText();
            if (key == "") {
                Assert.fail("Blad w taskach negocjacyjnych");
            }
            String status = driver.findElement(By.xpath("//*[@id='negotiation-tasks-template-field']/table/tbody/tr[" + (i + 1) + "]/td[4]/span")).getText();
            String translator = driver.findElement(By.xpath("//*[@id='negotiation-tasks-template-field']/table/tbody/tr[" + (i + 1) + "]/td[5]")).getText();
            String type = driver.findElement(By.xpath("//*[@id='negotiation-tasks-template-field']/table/tbody/tr[" + (i + 1) + "]/td[2]")).getText();
            assigmentsList.add(new Assigments(key, status, translator, type));
        }
        return assigmentsList;

    }


    public List<Assigments> getNegotiations() {
        List<Assigments> negociationList = new ArrayList<>();
        for (Assigments editor : getAssigments()) {
            if (editor.getType().equals("New Work Available")) {
                negociationList.add(editor);
            }
        }
        System.out.println("Lista New Editor Work : " + negociationList.toString());
        return negociationList;
    }

    public List<Assigments> getNewEditorWork() {
        List<Assigments> editorWork = new ArrayList<>();
        for (Assigments editor : getAssigments()) {
            if (editor.getType().equals("New Editor Work Available")) {
                editorWork.add(editor);
            }
        }
        System.out.println("Lista New Editor Work : " + editorWork.toString());
        return editorWork;
    }

    public boolean checkIsTaskRejected() {
        int rejected = 0;

        List<Assigments> list = getNegotiations();
        for (Assigments assigments : list) {
            if (assigments.getStatus().equals(TaskStatus.REJECTED)) {
                rejected++;
            } //else (assigments.getStatus().equals(TaskStatus.NEW))
          //  )
        }
        return rejected >= 0;
    }

    public boolean checkIsStatusChange() {
        int accepted = 0;
        int unavailable = 0;
        List<Assigments> list = getNegotiations();
        for (Assigments assigments : list) {
            if (assigments.getStatus().equals(TaskStatus.ACCEPTED)) {
                accepted++;
            } else if (assigments.getStatus().equals(TaskStatus.UNAVAILABLE)) {
                unavailable++;
            }
        }
        if (accepted > 1) {
            Assert.fail("Negocjacje zaakceptowaly co najmniej 2 osoby!");
        }
        return accepted + unavailable == list.size();
    }

    public boolean checkEditorWorkIsPresent() {
        boolean flag = false;
        List<Assigments> list = getAssigments();

        for (Assigments assigments : list) {
            if (assigments.getType().equals("New Editor Work Available")) {
                flag = true;
                break;
            }
        }
        return flag;
    }


}
