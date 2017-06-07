package core.Listeners;

import com.itextpdf.text.DocumentException;
import core.Reports.PdfCreator;
import core.Reports.RaporPDF;
import core.Reports.TestData;
import Tests.BaseTest.BaseTestClass;
import core.Tools.Configuration.Property;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-05-23.
 */
public class Listeners implements ITestListener {

    List<ITestResult> listabledow = new ArrayList();

    private String getPath(ITestResult result) {
        String basePath = Property.getProperty("basePath");

        String path = "";
        LocalDateTime time = LocalDateTime.now(ZoneId.systemDefault());
        String currentTimeStamp = time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).
                replace("T", "_").replace(":", "-").substring(0, 13);

        if (result.isSuccess()) {
            path = basePath + "\\Lion\\DaneTestowe\\" +
                    result.getInstance().getClass().getSimpleName() + "\\ScreenShots" + "\\PASS_" + result.getMethod().getMethodName() + ".jpg";

        } else {
            path = basePath + "\\Lion\\DaneTestowe\\" +
                    result.getInstance().getClass().getSimpleName() + "\\ScreenShots" + "\\Fail_" + result.getMethod().getMethodName() + ".jpg";
        }
        return path;
    }


    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        try {
            BaseTestClass.takeSnapShot(BaseTestClass.getDriver(), getPath(result));
            TestData.saveTestData(BaseTestClass.getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
        listabledow.add(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            BaseTestClass.takeSnapShot(BaseTestClass.getDriver(), getPath(result));
              TestData.saveTestData(BaseTestClass.getData());

        } catch (Exception e) {
            e.printStackTrace();
        }
        listabledow.add(result);

    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Result : " +listabledow.toString());
        RaporPDF raporPDF = new RaporPDF();
        try {
            raporPDF.create(listabledow);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }
}
