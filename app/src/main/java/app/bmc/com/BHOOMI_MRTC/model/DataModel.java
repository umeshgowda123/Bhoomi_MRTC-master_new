package app.bmc.com.BHOOMI_MRTC.model;

public class DataModel {

    private String owner_name;
    private String survey_no;
    private String surnoc_no;
    private String hissa_no;

    public DataModel(String owner_name, String survey_no, String surnoc_no, String hissa_no ) {
        this.owner_name=owner_name;
        this.survey_no=survey_no;
        this.surnoc_no=surnoc_no;
        this.hissa_no=hissa_no;
    }


    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getSurvey_no() {
        return survey_no;
    }

    public void setSurvey_no(String survey_no) {
        this.survey_no = survey_no;
    }

    public String getSurnoc_no() {
        return surnoc_no;
    }

    public void setSurnoc_no(String surnoc_no) {
        this.surnoc_no = surnoc_no;
    }

    public String getHissa_no() {
        return hissa_no;
    }

    public void setHissa_no(String hissa_no) {
        this.hissa_no = hissa_no;
    }
}
