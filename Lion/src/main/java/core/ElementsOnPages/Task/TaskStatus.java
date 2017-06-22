package core.ElementsOnPages.Task;

/**
 * Created by Piotr Majewski on 2017-06-09.
 */
public enum TaskStatus {


    NEW("NEW"),
    CLOSED("CLOSED"),
    WAITING_FOR_CUSTOMER("WAITING FOR CUSTOMER"),
    WAITING_FOR_SUPPORT("WAITING FOR SUPPORT");

    private String opis;
    TaskStatus(String opis) {
        this.opis = opis;
    }

    public String getStatus(){
        return opis;
    }
}
