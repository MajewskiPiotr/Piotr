package core.ProjectComparator.helpers;

/**
 * Created by Piotr Majewski on 2017-09-12.
 * klasa reprezentuje Noda mappingScreenSchemesIssueTypes
 */
public class MappingScreenSchemesIssueTypes {
    String issueType;
    String screenScheme;

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getScreenScheme() {
        return screenScheme;
    }

    public void setScreenScheme(String screenScheme) {
        this.screenScheme = screenScheme;
    }

    @Override
    public String toString() {
        return "MappingScreenSchemesIssueTypes{" +
                "issueType='" + issueType + '\'' +
                ", screenScheme='" + screenScheme + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MappingScreenSchemesIssueTypes that = (MappingScreenSchemesIssueTypes) o;

        if (!issueType.equals(that.issueType)) return false;
        return screenScheme.equals(that.screenScheme);
    }

    @Override
    public int hashCode() {
        int result = issueType.hashCode();
        result = 31 * result + screenScheme.hashCode();
        return result;
    }
}

