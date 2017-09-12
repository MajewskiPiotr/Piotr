package core.ProjectComparator;

/**
 * Created by Piotr Majewski on 2017-09-12.
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
}

