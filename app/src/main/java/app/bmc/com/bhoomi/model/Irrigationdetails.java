package app.bmc.com.bhoomi.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This is model class  defined for Irrigationdetails.
 */
public class Irrigationdetails {
    private List<Irrigation> irrigation;

    public List<Irrigation> getIrrigation() {
        return irrigation == null ? new ArrayList<Irrigation>() : irrigation;
    }

    public void setIrrigation(List<Irrigation> irrigation) {
        this.irrigation = irrigation;
    }
}
