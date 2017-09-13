package core.ProjectComparator.helpers;

/**
 * Created by Piotr Majewski on 2017-09-12.
 */
public class Component {
    String name;
    String desc;
    String defaultAssignee;
    String componentLead;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        if(desc==null){this.desc="";}
        this.desc = desc;
    }

    public String getDefaultAssignee() {
        return defaultAssignee;
    }

    public void setDefaultAssignee(String defaultAssignee) {
        this.defaultAssignee = defaultAssignee;
    }

    public String getComponentLead() {
        return componentLead;
    }

    public void setComponentLead(String componentLead) {
        this.componentLead = componentLead;
    }

    @Override
    public String toString() {
        return "Component{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", defaultAssignee='" + defaultAssignee + '\'' +
                ", componentLead='" + componentLead + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Component component = (Component) o;

        if (name != null ? !name.equals(component.name) : component.name != null) return false;
        if (desc != null ? !desc.equals(component.desc) : component.desc != null) return false;
        if (defaultAssignee != null ? !defaultAssignee.equals(component.defaultAssignee) : component.defaultAssignee != null)
            return false;
        return componentLead != null ? componentLead.equals(component.componentLead) : component.componentLead == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (defaultAssignee != null ? defaultAssignee.hashCode() : 0);
        result = 31 * result + (componentLead != null ? componentLead.hashCode() : 0);
        return result;
    }
}
