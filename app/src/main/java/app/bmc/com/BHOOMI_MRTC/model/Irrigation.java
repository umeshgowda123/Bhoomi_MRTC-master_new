package app.bmc.com.BHOOMI_MRTC.model;

public class Irrigation {
    private String serialnumber;
    private String watersource;
    private String kharifextents;
    private String rabiextents;
    private String gardenextents;
    private String totalextents;

    public String getSerialnumber() {
        return serialnumber == null ? "" : serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public String getWatersource() {
        return watersource == null ? "" : watersource;
    }

    public void setWatersource(String watersource) {
        this.watersource = watersource;
    }

    public String getKharifextents() {
        return kharifextents == null ? "" : kharifextents;
    }

    public void setKharifextents(String kharifextents) {
        this.kharifextents = kharifextents;
    }

    public String getRabiextents() {
        return rabiextents == null ? "" : rabiextents;
    }

    public void setRabiextents(String rabiextents) {
        this.rabiextents = rabiextents;
    }

    public String getGardenextents() {
        return gardenextents == null ? "" : gardenextents;
    }

    public void setGardenextents(String gardenextents) {
        this.gardenextents = gardenextents;
    }

    public String getTotalextents() {
        return totalextents == null ? "" : totalextents;
    }

    public void setTotalextents(String totalextents) {
        this.totalextents = totalextents;
    }
}
