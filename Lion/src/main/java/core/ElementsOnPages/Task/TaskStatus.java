package core.ElementsOnPages.Task;

/**
 * Created by Piotr Majewski on 2017-06-09.
 */
public enum TaskStatus {

    ACCEPTED("ACCEPTED"),
    UNAVAILABLE("UNAVAILABLE"),
    NEW("NEW"),
    WAITING_FOR_PACKAGING("WAITING FOR PACKAGING"),
    IN_PROCESSING("IN PROCESSING"),
    WAITING_FOR_ASSIGMENT("WAITING FOR ASSIGNMENT"),
    ASSIGNED_TO_TRANSLATOR("ASSIGNED TO TRANSLATOR"),
    ASSIGNED_TO_AG("ASSIGNED TO AG"),
    IN_PROGRESS("IN PROGRESS"),
    SELF_QA("SELF QA"),
    QA("QA"),
    COMPLETED("COMPLETED"),
    READY_TO_VERIFY("READY TO VERIFY"),
    ASSIGNED_TO_EDITOR("ASSIGNED TO EDITOR"),
    REJECTED("REJECTED"),
    AUTOMATICALLY_ACCEPTED("AUTOMATICALLY ACCEPTED");

    private String opis;
    TaskStatus(String opis) {
        this.opis = opis;
    }

    public String getStatus(){
        return opis;
    }
}
