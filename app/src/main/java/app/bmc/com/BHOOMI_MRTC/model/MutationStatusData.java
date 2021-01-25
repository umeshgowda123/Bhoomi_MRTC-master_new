package app.bmc.com.BHOOMI_MRTC.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MutationStatusData {
    @SerializedName("MR_NO")
    @Expose
    private String MR_NO;

    @SerializedName("APPL_NO")
    @Expose
    private String APPL_NO;

    @SerializedName("TRAN_NO")
    @Expose
    private String TRAN_NO;

    @SerializedName("MUTAION_STATUS_ENG")
    @Expose
    private String MUTAION_STATUS_ENG;

    @SerializedName("VILLAGE_CODE")
    @Expose
    private String VILLAGE_CODE;

    @SerializedName("MUTAION_STATUS_KN")
    @Expose
    private String MUTAION_STATUS_KN;


    @SerializedName("YEAR")
    @Expose
    private String YEAR;


    @SerializedName("LAND_CODE")
    @Expose
    private String LAND_CODE;


    @SerializedName("MUTATION_TYPE")
    @Expose
    private String MUTATION_TYPE;

    @SerializedName("HOBLI_CODE")
    @Expose
    private String HOBLI_CODE;

    @SerializedName("ACQUISION_TYPE")
    @Expose
    private String ACQUISION_TYPE;

    @SerializedName("SURVEY_NO")
    @Expose
    private String SURVEY_NO;

    @SerializedName("HobliName")
    @Expose
    private String HobliName;


    @SerializedName("VillageName")
    @Expose
    private String VillageName;

    public String getMR_NO() {
        return MR_NO;
    }

    public void setMR_NO(String MR_NO) {
        this.MR_NO = MR_NO;
    }

    public String getAPPL_NO() {
        return APPL_NO;
    }

    public void setAPPL_NO(String APPL_NO) {
        this.APPL_NO = APPL_NO;
    }

    public String getTRAN_NO() {
        return TRAN_NO;
    }

    public void setTRAN_NO(String TRAN_NO) {
        this.TRAN_NO = TRAN_NO;
    }

    public String getHobliName() {
        return HobliName;
    }

    public void setHobliName(String hobliName) {
        HobliName = hobliName;
    }

    public String getVillageName() {
        return VillageName;
    }

    public void setVillageName(String villageName) {
        VillageName = villageName;
    }

    public String getMUTAION_STATUS_ENG() {
        return MUTAION_STATUS_ENG;
    }

    public void setMUTAION_STATUS_ENG(String MUTAION_STATUS_ENG) {
        this.MUTAION_STATUS_ENG = MUTAION_STATUS_ENG;
    }

    public String getVILLAGE_CODE() {
        return VILLAGE_CODE;
    }

    public void setVILLAGE_CODE(String VILLAGE_CODE) {
        this.VILLAGE_CODE = VILLAGE_CODE;
    }

    public String getMUTAION_STATUS_KN() {
        return MUTAION_STATUS_KN;
    }

    public void setMUTAION_STATUS_KN(String MUTAION_STATUS_KN) {
        this.MUTAION_STATUS_KN = MUTAION_STATUS_KN;
    }

    public String getYEAR() {
        return YEAR;
    }

    public void setYEAR(String YEAR) {
        this.YEAR = YEAR;
    }

    public String getLAND_CODE() {
        return LAND_CODE;
    }

    public void setLAND_CODE(String LAND_CODE) {
        this.LAND_CODE = LAND_CODE;
    }

    public String getMUTATION_TYPE() {
        return MUTATION_TYPE;
    }

    public void setMUTATION_TYPE(String MUTATION_TYPE) {
        this.MUTATION_TYPE = MUTATION_TYPE;
    }

    public String getHOBLI_CODE() {
        return HOBLI_CODE;
    }

    public void setHOBLI_CODE(String HOBLI_CODE) {
        this.HOBLI_CODE = HOBLI_CODE;
    }

    public String getACQUISION_TYPE() {
        return ACQUISION_TYPE;
    }

    public void setACQUISION_TYPE(String ACQUISION_TYPE) {
        this.ACQUISION_TYPE = ACQUISION_TYPE;
    }

    public String getSURVEY_NO() {
        return SURVEY_NO;
    }

    public void setSURVEY_NO(String SURVEY_NO) {
        this.SURVEY_NO = SURVEY_NO;
    }
}
