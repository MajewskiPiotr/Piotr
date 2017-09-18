package core.ProjectComparator.helpers;

/**
 * Created by Piotr Majewski on 2017-09-12.
 */
public class IndividualFieldConfig {
    String fieldName;
    boolean hidden;
    boolean required;
    String renderer;

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getRenderer() {
        return renderer;
    }

    public void setRenderer(String renderer) {
        this.renderer = renderer;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndividualFieldConfig that = (IndividualFieldConfig) o;

        if (hidden != that.hidden) return false;
        if (required != that.required) return false;
        if (!fieldName.equals(that.fieldName)) return false;
        return renderer.equals(that.renderer);
    }

    @Override
    public int hashCode() {
        int result = fieldName.hashCode();
        result = 31 * result + (hidden ? 1 : 0);
        result = 31 * result + (required ? 1 : 0);
        result = 31 * result + renderer.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "IndividualFieldConfig{" +
                "fieldName='" + fieldName + '\'' +
                ", hidden=" + hidden +
                ", required=" + required +
                ", renderer='" + renderer + '\'' +
                '}';
    }
}
