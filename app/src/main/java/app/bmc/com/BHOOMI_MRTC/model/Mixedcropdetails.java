package app.bmc.com.BHOOMI_MRTC.model;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This is model class  defined for Mixedcropdetails.
 */
public class Mixedcropdetails {
    private String mixedcropname;
    private String mixedcropextents;

    public String getMixedcropname() {
        return mixedcropname == null ? "" : mixedcropname;
    }

    public void setMixedcropname(String mixedcropname) {
        this.mixedcropname = mixedcropname;
    }

    public String getMixedcropextents() {
        return mixedcropextents == null ? "" : mixedcropextents;
    }

    public void setMixedcropextents(String mixedcropextents) {
        this.mixedcropextents = mixedcropextents;
    }
}
