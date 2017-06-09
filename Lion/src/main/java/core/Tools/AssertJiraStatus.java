package core.Tools;

import core.ElementsOnPages.Task.TaskStatus;
import org.testng.Assert;

/**
 * Created by Piotr Majewski on 2017-06-07.
 */
public class AssertJiraStatus {
    //Funkcja przyjmuje status i dwie oczekiwane statusy

    public static void assertStatus(TaskStatus actualStatus, TaskStatus expectedStatus1, TaskStatus expectedStatus2, String message) {
        if (expectedStatus2==null){
            Assert.assertTrue(actualStatus.equals(expectedStatus1));
        }
        else {
            Assert.assertTrue(actualStatus.equals(expectedStatus1) || actualStatus.equals(expectedStatus2), message);
        }

    }
}