package app.bmc.com.BHOOMI_MRTC.model;

public class Landutilisation {
    private String classification;
    private String utilisationextents;

    public String getClassification() {
        return classification == null ? "" : classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getUtilisationextents() {
        return utilisationextents == null ? "" : utilisationextents;
    }

    public void setUtilisationextents(String utilisationextents) {
        this.utilisationextents = utilisationextents;
    }
}
