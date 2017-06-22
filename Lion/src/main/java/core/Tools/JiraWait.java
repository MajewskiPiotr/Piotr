package core.Tools;

/**
 * Created by Piotr Majewski on 2017-06-21.
 */
public class JiraWait {

    public static void waitForProcesing(long wait){
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
