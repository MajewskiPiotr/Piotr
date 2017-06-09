package core.ElementsOnPages.Task.Tab;

/**
 * Created by Piotr Majewski on 2017-06-09.
 */

public enum AssignmentsType {


    New_Editor_Work_Available("New Editor Work Available"), New_Work_Available("New Work Available");

    private String opis;

    AssignmentsType(String opis) {
        this.opis = opis;
    }

    public String getTaskType(){
        return opis;
    }
}
