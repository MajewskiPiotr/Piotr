package Tools;

import Reports.TestData;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Created by Piotr Majewski on 2017-05-25.
 */
public class TestJavaclass {

    public static void main(String[] args) {

TestData.saveTestData(new TestData());
    }
}
