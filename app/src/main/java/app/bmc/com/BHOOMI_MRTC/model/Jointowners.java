package app.bmc.com.BHOOMI_MRTC.model;

import java.util.ArrayList;
import java.util.List;

public class Jointowners {
    private String OwnerCount;
    private List<Owner> owner;

    public String getOwnerCount() {
        return OwnerCount == null ? "" : OwnerCount;
    }

    public void setOwnerCount(String ownerCount) {
        OwnerCount = ownerCount;
    }

    public List<Owner> getOwner() {
        return owner == null ? new ArrayList<Owner>() : owner;
    }

    public void setOwner(List<Owner> owner) {
        this.owner = owner;
    }
}
