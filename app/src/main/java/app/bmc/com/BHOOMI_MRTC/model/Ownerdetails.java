package app.bmc.com.BHOOMI_MRTC.model;

import java.util.ArrayList;
import java.util.List;

public class Ownerdetails {
    private List<Jointowners> jointowners;

    public List<Jointowners> getJointowners() {
        return jointowners == null ? new ArrayList<>() : jointowners;
    }

    public void setJointowners(List<Jointowners> jointowners) {
        this.jointowners = jointowners;
    }
}
