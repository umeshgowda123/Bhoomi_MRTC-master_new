package app.bmc.com.bhoomi.model;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This is model class  defined for Staticinfopahani Data.
 */
public class Staticinfopahani {
    private String formname;
    private String villagetype;
    private String talukseal;
    private String formnumber;
    private String villageaccountformnumber;
    private String extentmeasurement;
    private String landactrule;
    private String validfrom;
    private String validto;

    public String getFormname() {
        return formname == null ? "" : formname;
    }

    public void setFormname(String formname) {
        this.formname = formname;
    }

    public String getVillagetype() {
        return villagetype == null ? "" : villagetype;
    }

    public void setVillagetype(String villagetype) {
        this.villagetype = villagetype;
    }

    public String getTalukseal() {
        return talukseal == null ? "" : talukseal;
    }

    public void setTalukseal(String talukseal) {
        this.talukseal = talukseal;
    }

    public String getFormnumber() {
        return formnumber == null ? "" : formnumber;
    }

    public void setFormnumber(String formnumber) {
        this.formnumber = formnumber;
    }

    public String getVillageaccountformnumber() {
        return villageaccountformnumber == null ? "" : villageaccountformnumber;
    }

    public void setVillageaccountformnumber(String villageaccountformnumber) {
        this.villageaccountformnumber = villageaccountformnumber;
    }

    public String getExtentmeasurement() {
        return extentmeasurement == null ? "" : extentmeasurement;
    }

    public void setExtentmeasurement(String extentmeasurement) {
        this.extentmeasurement = extentmeasurement;
    }

    public String getLandactrule() {
        return landactrule == null ? "" : landactrule;
    }

    public void setLandactrule(String landactrule) {
        this.landactrule = landactrule;
    }

    public String getValidfrom() {
        return validfrom == null ? "" : validfrom;
    }

    public void setValidfrom(String validfrom) {
        this.validfrom = validfrom;
    }

    public String getValidto() {
        return validto == null ? "" : validto;
    }

    public void setValidto(String validto) {
        this.validto = validto;
    }
}
