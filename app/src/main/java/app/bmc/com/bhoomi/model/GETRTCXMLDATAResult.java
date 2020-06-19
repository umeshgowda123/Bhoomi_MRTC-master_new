package app.bmc.com.bhoomi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This is model class  defined for Rtc Xml Verification.
 */
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
