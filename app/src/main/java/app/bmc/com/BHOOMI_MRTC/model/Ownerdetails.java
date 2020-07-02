package app.bmc.com.BHOOMI_MRTC.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This is model class  defined for Ownerdetails Data.
 */
public class Ownerdetails {
    private List<Jointowners> jointowners;

    public List<Jointowners> getJointowners() {
        return jointowners == null ? new ArrayList<>() : jointowners;
    }

    public void setJointowners(List<Jointowners> jointowners) {
        this.jointowners = jointowners;
    }
}
