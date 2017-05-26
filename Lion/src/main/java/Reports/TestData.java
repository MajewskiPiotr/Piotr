package Reports;

import Tools.HelpersClass.Assigments;

import java.io.*;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-05-24.
 */
public class TestData implements Serializable {
    private static final long serialVersionUID = 1L;
    //Profile
    String availability;
    String availabilityFromUtc;
    String availabilityToUtc;
    String poc;
    String phnone;



    //FLOW
    String translator;
    String Scenariusz;
    String jobTask;
    String packageTask;
    String negociationTask;
    String translationTask;
    List<Assigments> listOfAssigments;

    boolean zakonczono = false;

    public static void saveTestData(TestData data) {
        ObjectOutputStream pl = null;
        try {
            File file = new File("c:/Lion_automatyzacja/Lion/" + "DaneTestowe/" + data.getScenariusz() + "/test.data");
            if (!file.exists()) {
                file.getParentFile().mkdir();
            }

            pl = new ObjectOutputStream(new FileOutputStream(file));
            pl.writeObject(data);
            pl.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pl != null)
                try {
                    pl.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }

    public static TestData readTestData(String nazwaPl) {

        ObjectInputStream pl2 = null;
        TestData data = null;
        String path = "C:\\Lion_automatyzacja\\Lion\\DaneTestowe\\";
        try {
            pl2 = new ObjectInputStream(new FileInputStream(path + nazwaPl + "\\test.data"));

            data = (TestData) pl2.readObject();
            System.out.println(data.toString());
        } catch (EOFException ex) {
            System.out.println("Koniec pliku");
        } catch (FileNotFoundException e) {
            {
                System.out.println("nie ma jeszcze pliku z danymi");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (pl2 != null)
                try {
                    pl2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        if (data != null) {
            System.out.println("ODCZYT DANYCH Z PLIKU: " + data.toString());
            return data;
        }
        return new TestData();
    }

    //TODO do zrobienia czyszczenie pliku
    public static void flush() {
        System.out.println("dorobic czyszczenei pliku z danymi");
    }

    public boolean isZakonczono() {
        return zakonczono;
    }

    public void setZakonczono(boolean zakonczono) {
        this.zakonczono = zakonczono;
    }

    public String getScenariusz() {
        return Scenariusz;
    }

    public void setScenariusz(String scenariusz) {
        Scenariusz = scenariusz;
    }


    public String getJobTask() {
        return jobTask;
    }

    public void setJobTask(String jobTask) {
        this.jobTask = jobTask;
    }

    public String getPackageTask() {
        return packageTask;
    }

    public void setPackageTask(String packageTask) {
        this.packageTask = packageTask;
    }

    public String getNegociationTask() {
        return negociationTask;
    }

    public void setNegociationTask(String negociationTask) {
        this.negociationTask = negociationTask;
    }

    public String getTranslationTask() {
        return translationTask;
    }

    public void setTranslationTask(String translationTask) {
        this.translationTask = translationTask;
    }

    public List<Assigments> getListOfAssigments() {
        return listOfAssigments;
    }

    public void setListOfAssigments(List<Assigments> listOfAssigments) {
        this.listOfAssigments = listOfAssigments;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getAvailabilityFromUtc() {
        return availabilityFromUtc;
    }

    public void setAvailabilityFromUtc(String availabilityFromUtc) {
        this.availabilityFromUtc = availabilityFromUtc;
    }

    public String getAvailabilityToUtc() {
        return availabilityToUtc;
    }

    public void setAvailabilityToUtc(String availabilityToUtc) {
        this.availabilityToUtc = availabilityToUtc;
    }

    public String getPoc() {
        return poc;
    }

    public void setPoc(String poc) {
        this.poc = poc;
    }

    public String getPhnone() {
        return phnone;
    }

    public void setPhnone(String phnone) {
        this.phnone = phnone;
    }

    @Override
    public String toString() {
        return "TestData{" +
                "availability='" + availability + '\'' +
                ", availabilityFromUtc='" + availabilityFromUtc + '\'' +
                ", availabilityToUtc='" + availabilityToUtc + '\'' +
                ", poc='" + poc + '\'' +
                ", phnone='" + phnone + '\'' +
                ", translator='" + translator + '\'' +
                ", Scenariusz='" + Scenariusz + '\'' +
                ", jobTask='" + jobTask + '\'' +
                ", packageTask='" + packageTask + '\'' +
                ", negociationTask='" + negociationTask + '\'' +
                ", translationTask='" + translationTask + '\'' +
                ", listOfAssigments=" + listOfAssigments +
                ", zakonczono=" + zakonczono +
                '}';
    }
}
