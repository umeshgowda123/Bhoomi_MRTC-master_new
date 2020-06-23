package app.bmc.com.BHOOMI_MRTC.model;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This is model class  defined for Landdetails.
 */
public class Landdetails {
    private Irrigationdetails irrigationdetails;
    private Pahanidetails pahanidetails;
    private Staticinfopahani staticinfopahani;
    private Treedetails treedetails;
    private Villagedetails villagedetails;

    public Staticinfopahani getStaticinfopahani() {
        return staticinfopahani == null ? new Staticinfopahani() : staticinfopahani;
    }

    public void setStaticinfopahani(Staticinfopahani staticinfopahani) {
        this.staticinfopahani = staticinfopahani;
    }

    public Villagedetails getVillagedetails() {
        return villagedetails == null ? new Villagedetails() : villagedetails;
    }

    public void setVillagedetails(Villagedetails villagedetails) {
        this.villagedetails = villagedetails;
    }


    public Irrigationdetails getIrrigationdetails() {
        return irrigationdetails == null ? new Irrigationdetails() : irrigationdetails;
    }

    public void setIrrigationdetails(Irrigationdetails irrigationdetails) {
        this.irrigationdetails = irrigationdetails;
    }

    public Pahanidetails getPahanidetails() {
        return pahanidetails == null ? new Pahanidetails() : pahanidetails;
    }

    public void setPahanidetails(Pahanidetails pahanidetails) {
        this.pahanidetails = pahanidetails;
    }

    public Treedetails getTreedetails() {
        return treedetails == null ? new Treedetails() : treedetails;
    }

    public void setTreedetails(Treedetails treedetails) {
        this.treedetails = treedetails;
    }
}
