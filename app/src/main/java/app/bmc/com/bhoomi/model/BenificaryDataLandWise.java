package app.bmc.com.bhoomi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BenificaryDataLandWise {


    @SerializedName("ENTRYID")
    @Expose
    private String ENTRYID;

    @SerializedName("DistrictName")
    @Expose
    private String DistrictName;

    @SerializedName("TalukName")
    @Expose
    private String TalukName;

    @SerializedName("Hobliname")
    @Expose
    private String Hobliname;

    @SerializedName("VillageCircleName")
    @Expose
    private String VillageCircleName;


    @SerializedName("VillageName")
    @Expose
    private String VillageName;


    @SerializedName("SurveyNumber")
    @Expose
    private String SurveyNumber;

    @SerializedName("Surnoc")
    @Expose
    private String Surnoc;

    @SerializedName("HissaNumber")
    @Expose
    private String HissaNumber;

    @SerializedName("CropName")
    @Expose
    private String CropName;


    @SerializedName("CropCatagory")
    @Expose
    private String CropCatagory;


    @SerializedName("CropLossExtentAcre")
    @Expose
    private String CropLossExtentAcre;

    @SerializedName("CropLossExtentGunta")
    @Expose
    private String CropLossExtentGunta;

    @SerializedName("CropLossExtentFgunta")
    @Expose
    private String CropLossExtentFgunta;


    public String getENTRYID() {
        return ENTRYID;
    }

    public void setENTRYID(String ENTRYID) {
        this.ENTRYID = ENTRYID;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public String getTalukName() {
        return TalukName;
    }

    public void setTalukName(String talukName) {
        TalukName = talukName;
    }

    public String getHobliname() {
        return Hobliname;
    }

    public void setHobliname(String hobliname) {
        Hobliname = hobliname;
    }

    public String getVillageCircleName() {
        return VillageCircleName;
    }

    public void setVillageCircleName(String villageCircleName) {
        VillageCircleName = villageCircleName;
    }

    public String getVillageName() {
        return VillageName;
    }

    public void setVillageName(String villageName) {
        VillageName = villageName;
    }

    public String getSurveyNumber() {
        return SurveyNumber;
    }

    public void setSurveyNumber(String surveyNumber) {
        SurveyNumber = surveyNumber;
    }

    public String getSurnoc() {
        return Surnoc;
    }

    public void setSurnoc(String surnoc) {
        Surnoc = surnoc;
    }

    public String getHissaNumber() {
        return HissaNumber;
    }

    public void setHissaNumber(String hissaNumber) {
        HissaNumber = hissaNumber;
    }

    public String getCropName() {
        return CropName;
    }

    public void setCropName(String cropName) {
        CropName = cropName;
    }

    public String getCropCatagory() {
        return CropCatagory;
    }

    public void setCropCatagory(String cropCatagory) {
        CropCatagory = cropCatagory;
    }

    public String getCropLossExtentAcre() {
        return CropLossExtentAcre;
    }

    public void setCropLossExtentAcre(String cropLossExtentAcre) {
        CropLossExtentAcre = cropLossExtentAcre;
    }

    public String getCropLossExtentGunta() {
        return CropLossExtentGunta;
    }

    public void setCropLossExtentGunta(String cropLossExtentGunta) {
        CropLossExtentGunta = cropLossExtentGunta;
    }

    public String getCropLossExtentFgunta() {
        return CropLossExtentFgunta;
    }

    public void setCropLossExtentFgunta(String cropLossExtentFgunta) {
        CropLossExtentFgunta = cropLossExtentFgunta;
    }
}
