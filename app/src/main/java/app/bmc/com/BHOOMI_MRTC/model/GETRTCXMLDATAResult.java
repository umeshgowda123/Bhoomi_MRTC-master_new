package app.bmc.com.BHOOMI_MRTC.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GETRTCXMLDATAResult {
    @SerializedName("RTCXMLResult")
    @Expose
    private String RTCXMLResult;

    public String getGETRTCXMLDATAResult() {
        return RTCXMLResult;
    }

    public void setGETRTCXMLDATAResult(String gETRTCXMLDATAResult) {
        this.RTCXMLResult = gETRTCXMLDATAResult;
    }

}
