package app.bmc.com.BHOOMI_MRTC.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Get_Surnoc_HissaResult {

    @SerializedName("Get_Surnoc_HissaResult")
    @Expose
    private String getSurnocHissaResult;

    public String getGetSurnocHissaResult() {
        return getSurnocHissaResult;
    }

    public void setGetSurnocHissaResult(String getSurnocHissaResult) {
        this.getSurnocHissaResult = getSurnocHissaResult;
    }

}