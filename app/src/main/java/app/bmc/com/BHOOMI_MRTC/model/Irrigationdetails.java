package app.bmc.com.BHOOMI_MRTC.model;

import java.util.ArrayList;
import java.util.List;

public class Irrigationdetails {
    private List<Irrigation> irrigation;

    public List<Irrigation> getIrrigation() {
        return irrigation == null ? new ArrayList<>() : irrigation;
    }

    public void setIrrigation(List<Irrigation> irrigation) {
        this.irrigation = irrigation;
    }
}
