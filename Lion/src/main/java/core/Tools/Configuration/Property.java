package core.Tools.Configuration;

import java.io.*;
import java.util.Properties;

/**
 * Created by Piotr Majewski on 2017-05-26.
 */
public class Property {
    //Plik z konfiguracją
    private File f = new File("C:\\Automaty\\Piotr\\Lion\\lion.properties");
    //przyszły obiekt Property
    private Properties properties = new Properties();

    public static String getProperty(String property) {
        Property prop = new Property();
        prop.loadProperties();
        return prop.properties.getProperty(property);
    }

    public void loadProperties() {
        //Strumień wejściowy
        InputStream is;
        try {
            is = new FileInputStream(f);
            //ładujemy nasze ustawienia
            properties.load(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
