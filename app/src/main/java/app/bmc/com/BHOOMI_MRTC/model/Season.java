package app.bmc.com.BHOOMI_MRTC.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This is model class  defined for Season Data.
 */
public class Season {
    private List<Cultivator> cultivators;
    private String seasonname;
    private String year;

    public List<Cultivator> getCultivators() {
        return cultivators == null ? new ArrayList<Cultivator>() : cultivators;
    }

    public void setCultivators(List<Cultivator> cultivators) {
        this.cultivators = cultivators;
    }

    public String getSeasonname() {
        return seasonname == null ? "" : seasonname;
    }

    public void setSeasonname(String seasonname) {
        this.seasonname = seasonname;
    }

    public String getYear() {
        return year == null ? "" : year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
