package app.bmc.com.BHOOMI_MRTC.model;

public class Villagedetails {
    private String talukname;
    private String hobliname;
    private String villagename;

    public String getTalukname() {
        return talukname == null ? "" : talukname;
    }

    public void setTalukname(String talukname) {
        this.talukname = talukname;
    }

    public String getHobliname() {
        return hobliname == null ? "" : hobliname;
    }

    public void setHobliname(String hobliname) {
        this.hobliname = hobliname;
    }

    public String getVillagename() {
        return villagename == null ? "" : villagename;
    }

    public void setVillagename(String villagename) {
        this.villagename = villagename;
    }
}
