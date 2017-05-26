package Tools.Configuration;

/**
 * Created by Piotr Majewski on 2017-05-25.
 */
public class TestJavaclass {

    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");
        Property pt = new Property();
        pt.loadProperties();

    }

}
