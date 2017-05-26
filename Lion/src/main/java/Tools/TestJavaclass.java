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
         LocalDateTime time = LocalDateTime.now(ZoneId.systemDefault());



        String currentTimeStamp = time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace("T", "_").replace(":", "-").substring(0, 19);
        System.out.println(currentTimeStamp);


    }
}
