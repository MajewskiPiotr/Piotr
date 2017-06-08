package core.Tools;

import org.testng.Assert;

/**
 * Created by Piotr Majewski on 2017-06-07.
 */
public class LionAssert {

    public static void assertStatus(String actualStatus, String ecspectedStatus1, String ecspectedStatus2, String message) {
        Assert.assertTrue(actualStatus.equals(ecspectedStatus1) || actualStatus.equals(ecspectedStatus2), message);
    }
}