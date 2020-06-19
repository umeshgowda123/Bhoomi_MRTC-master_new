package app.bmc.com.bhoomi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PariharaEntry {

    @SerializedName("HobliName")
    @Expose
    private String hobliName;

    @SerializedName("DistrictName")
    @Expose
    private String districtName;

    @SerializedName("VillageName")
    @Expose
    private String villageName;

    @SerializedName("TalukName")
    @Expose
    private String talukName;

    @SerializedName("AadhaarNo")
    @Expose
    private String aadhaarNo;

    @SerializedName("CropCategory")
    @Expose
    private String cropCategory;

    @SerializedName("SurveyNumber")
    @Expose
    private String surveyNumber;

    @SerializedName("CropName")
    @Expose
    private String cropName;

    @SerializedName("SlNo")
    @Expose
    private String slNo;

    @SerializedName("EntryID")
    @Expose
    private String entryID;

    @SerializedName("CropLossExtent")
    @Expose
    private String cropLossExtent;

    public String getHobliName() {
        return hobliName;
    }

    public void setHobliName(String hobliName) {
        this.hobliName = hobliName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getTalukName() {
        return talukName;
    }

    public void setTalukName(String talukName) {
        this.talukName = talukName;
    }

    public String getAadhaarNo() {
        return aadhaarNo;
    }

    public void setAadhaarNo(String aadhaarNo) {
        this.aadhaarNo = aadhaarNo;
    }

    public String getCropCategory() {
        return cropCategory;
    }

    public void setCropCategory(String cropCategory) {
        this.cropCategory = cropCategory;
    }

    public String getSurveyNumber() {
        return surveyNumber;
    }

    public void setSurveyNumber(String surveyNumber) {
        this.surveyNumber = surveyNumber;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getSlNo() {
        return slNo;
    }

    public void setSlNo(String slNo) {
        this.slNo = slNo;
    }

    public String getEntryID() {
        return entryID;
    }

    public void setEntryID(String entryID) {
        this.entryID = entryID;
    }

    public String getCropLossExtent() {
        return cropLossExtent;
    }

    public void setCropLossExtent(String cropLossExtent) {
        this.cropLossExtent = cropLossExtent;
    }
}
