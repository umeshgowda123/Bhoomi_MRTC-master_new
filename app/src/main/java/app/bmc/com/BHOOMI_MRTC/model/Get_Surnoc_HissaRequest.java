package app.bmc.com.BHOOMI_MRTC.model;

public class Get_Surnoc_HissaRequest {

    String Bhm_dist_code, Bhm_taluk_code, Bhm_hobli_code, village_code, survey_no;

    public String getBhm_dist_code() {
        return Bhm_dist_code;
    }

    public void setBhm_dist_code(String bhm_dist_code) {
        Bhm_dist_code = bhm_dist_code;
    }

    public String getBhm_taluk_code() {
        return Bhm_taluk_code;
    }

    public void setBhm_taluk_code(String bhm_taluk_code) {
        Bhm_taluk_code = bhm_taluk_code;
    }

    public String getBhm_hobli_code() {
        return Bhm_hobli_code;
    }

    public void setBhm_hobli_code(String bhm_hobli_code) {
        Bhm_hobli_code = bhm_hobli_code;
    }

    public String getVillage_code() {
        return village_code;
    }

    public void setVillage_code(String village_code) {
        this.village_code = village_code;
    }

    public String getSurvey_no() {
        return survey_no;
    }

    public void setSurvey_no(String survey_no) {
        this.survey_no = survey_no;
    }
}
