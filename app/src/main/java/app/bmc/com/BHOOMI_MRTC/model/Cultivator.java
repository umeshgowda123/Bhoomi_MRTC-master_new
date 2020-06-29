package app.bmc.com.BHOOMI_MRTC.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This is model class  defined for Cultivator xml data.
 */
public class Cultivator {
    private Cultivatorname cultivatorname;
    private String cultivationtype;
    private String cultivationextent;
    private String tenantamount;
    private Landutilisation landutilisation;
    private List<Cropdetails> cropdetails;
    private Mixedcropdetails mixedcropdetails;

    public Cultivatorname getCultivatorname() {
        return cultivatorname == null ? new Cultivatorname() : cultivatorname;
    }

    public void setCultivatorname(Cultivatorname cultivatorname) {
        this.cultivatorname = cultivatorname;
    }

    public String getCultivationtype() {
        return cultivationtype == null ? "" : cultivationtype;
    }

    public void setCultivationtype(String cultivationtype) {
        this.cultivationtype = cultivationtype;
    }

    public String getCultivationextent() {
        return cultivationextent == null ? "" : cultivationextent;
    }

    public void setCultivationextent(String cultivationextent) {
        this.cultivationextent = cultivationextent;
    }

    public String getTenantamount() {
        return tenantamount == null ? "" : tenantamount;
    }

    public void setTenantamount(String tenantamount) {
        this.tenantamount = tenantamount;
    }

    public Landutilisation getLandutilisation() {
        return landutilisation == null ? new Landutilisation() : landutilisation;
    }

    public void setLandutilisation(Landutilisation landutilisation) {
        this.landutilisation = landutilisation;
    }


    public List<Cropdetails> getCropdetails() {
        return cropdetails == null ? new ArrayList<>() : cropdetails;
    }

    public void setCropdetails(List<Cropdetails> cropdetails) {
        this.cropdetails = cropdetails;
    }

    public Mixedcropdetails getMixedcropdetails() {
        return mixedcropdetails == null ? new Mixedcropdetails() : mixedcropdetails;
    }

    public void setMixedcropdetails(Mixedcropdetails mixedcropdetails) {
        this.mixedcropdetails = mixedcropdetails;
    }
}
