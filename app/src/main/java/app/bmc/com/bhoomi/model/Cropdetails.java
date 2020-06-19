package app.bmc.com.bhoomi.model;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This is model class  defined for Crop xml data.
 */
public class Cropdetails {
    private String landclassification;
    private String cropname;
    private String singlecropextents;
    private String mixedcropextent;
    private String totalcropextents;
    private String watersource;
    private String yieldperacre;

    public String getLandclassification() {
        return landclassification == null ? "" : landclassification;
    }

    public void setLandclassification(String landclassification) {
        this.landclassification = landclassification;
    }

    public String getCropname() {
        return cropname == null ? "" : cropname;
    }

    public void setCropname(String cropname) {
        this.cropname = cropname;
    }

    public String getSinglecropextents() {
        return singlecropextents == null ? "" : singlecropextents;
    }

    public void setSinglecropextents(String singlecropextents) {
        this.singlecropextents = singlecropextents;
    }

    public String getMixedcropextent() {
        return mixedcropextent;
    }

    public void setMixedcropextent(String mixedcropextent) {
        this.mixedcropextent = mixedcropextent;
    }

    public String getTotalcropextents() {
        return totalcropextents;
    }

    public void setTotalcropextents(String totalcropextents) {
        this.totalcropextents = totalcropextents;
    }

    public String getWatersource() {
        return watersource;
    }

    public void setWatersource(String watersource) {
        this.watersource = watersource;
    }

    public String getYieldperacre() {
        return yieldperacre;
    }

    public void setYieldperacre(String yieldperacre) {
        this.yieldperacre = yieldperacre;
    }
}
