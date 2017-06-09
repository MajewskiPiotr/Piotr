

import PageObjects.ElementsOnPages.Task.Tab.AssignmentsType;
import com.sun.jna.platform.win32.Sspi;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Piotr Majewski on 2017-05-25.
 */
public class TestJavaclass {

    public static void main(String[] args) throws IOException, MessagingException {
        System.out.println(AssignmentsType.New_Editor_Work_Available.getTaskType());


    }

}
