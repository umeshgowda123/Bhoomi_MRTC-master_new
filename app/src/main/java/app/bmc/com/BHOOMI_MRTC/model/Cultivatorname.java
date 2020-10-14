package app.bmc.com.BHOOMI_MRTC.model;

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
