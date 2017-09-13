package core.ProjectComparator.helpers;

/**
 * Created by Piotr Majewski on 2017-09-11.
 *  klasa reprezentuje Noda MappingIssueTypes

 */
public class MappingIssueTypes {
    String issueType;
    String workflow;

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public MappingIssueTypes() {
    }

    public String getWorkflow() {
        return workflow;
    }

    public void setWorkflow(String workflow) {
        this.workflow = workflow;
    }


    @Override
    public String toString() {
        return "MappingIssueTypes{" +
                "issueType='" + issueType + '\'' +
                ", workflow='" + workflow + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MappingIssueTypes that = (MappingIssueTypes) o;

        if (!issueType.equals(that.issueType)) return false;
        return workflow.equals(that.workflow);
    }

    @Override
    public int hashCode() {
        int result = issueType.hashCode();
        result = 31 * result + workflow.hashCode();
        return result;
    }
}
