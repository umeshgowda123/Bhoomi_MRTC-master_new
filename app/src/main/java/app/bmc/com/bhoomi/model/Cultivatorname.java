package app.bmc.com.bhoomi.model;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This is model class  defined for Cultivator name.
 */
public class Cultivatorname {
    private String name;
    private String relationship;
    private String relativename;

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationship() {
        return relationship == null ? "" : relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getRelativename() {
        return relativename == null ? "" : relativename;
    }

    public void setRelativename(String relativename) {
        this.relativename = relativename;
    }
}
