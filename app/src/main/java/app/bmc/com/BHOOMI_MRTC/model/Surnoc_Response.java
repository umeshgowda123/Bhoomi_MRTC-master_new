package app.bmc.com.BHOOMI_MRTC.model;

import androidx.annotation.NonNull;

public class Surnoc_Response {
    private String surnoc;
    private String survey_no;

    public String getSurnoc() {
        return surnoc;
    }

    public void setSurnoc(String surnoc) {
        this.surnoc = surnoc;
    }

    public String getSurvey_no() {
        return survey_no;
    }

    public void setSurvey_no(String survey_no) {
        this.survey_no = survey_no;
    }

    @NonNull
    @Override
    public String toString() {
        return surnoc + "";
    }
}
