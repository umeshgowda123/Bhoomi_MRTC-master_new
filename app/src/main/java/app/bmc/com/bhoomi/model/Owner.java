package app.bmc.com.bhoomi.model;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This is model class  defined for Owner.
 */
public class Owner {
    private Ownername ownername;
    private String ownerextents;
    private String khathanumber;
    private String acquisitiondetails;
    private String rights;
    private String liabilities;


    public String getOwnerextents() {
        return ownerextents == null ? "" : ownerextents;
    }

    public void setOwnerextents(String ownerextents) {
        this.ownerextents = ownerextents;
    }

    public String getKhathanumber() {
        return khathanumber == null ? "" : khathanumber;
    }

    public void setKhathanumber(String khathanumber) {
        this.khathanumber = khathanumber;
    }

    public String getAcquisitiondetails() {
        return acquisitiondetails == null ? "" : acquisitiondetails;
    }

    public void setAcquisitiondetails(String acquisitiondetails) {
        this.acquisitiondetails = acquisitiondetails;
    }

    public String getRights() {
        return rights == null ? "" : rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public String getLiabilities() {
        return liabilities == null ? "" : liabilities;
    }

    public void setLiabilities(String liabilities) {
        this.liabilities = liabilities;
    }

    public Ownername getOwnername() {
        return ownername == null ? new Ownername() : ownername;
    }

    public void setOwnername(Ownername ownername) {
        this.ownername = ownername;
    }
}
