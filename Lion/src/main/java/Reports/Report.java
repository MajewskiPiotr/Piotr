package Reports;

import org.testng.ITestResult;

import java.io.File;
import java.util.Date;

/**
 * Created by Piotr Majewski on 2017-05-24.
 */
public class Report {

    public static String getPath(ITestResult result) {
        String path = "";

        long currentTimeStamp = new Date().getTime();

        File f = new File("Lion/DaneTestowe/ScreenShots/" + currentTimeStamp);
        if (f.exists() && f.isDirectory()) {
            System.out.println("Problem z tworzeniem katalogow");
        } else {
            if (result.isSuccess()) {

                path = "C:\\Lion_automatyzacja\\Lion\\DaneTestowe\\" + result.getInstance().getClass().getSimpleName() +"\\ScreenShots\\"+ currentTimeStamp + "/PASS_" +  result.getMethod().getMethodName() + ".jpg";
                System.out.println(path);

            } else {
                System.out.println("fail");
                path = "C:\\Lion_automatyzacja\\Lion\\DaneTestowe\\" + result.getInstance().getClass().getSimpleName() +"\\ScreenShots\\"+ currentTimeStamp + "/FAIL" +  result.getMethod().getMethodName() + ".jpg";
                System.out.println(path);
            }
        }
        return path;
    }

}
