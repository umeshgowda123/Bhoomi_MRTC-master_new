package app.bmc.com.BHOOMI_MRTC.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This is model class  defined for get rtc data result.
 */
public class Get_Rtc_Data_Result {

    @SerializedName("get_rtc_dataResult")
    @Expose
    private String getRtcDataResult;

    @SerializedName("get_tc_dataResult")
    @Expose
    private String gettcDataResult;

    public String getGetRtcDataResult() {
        return getRtcDataResult;
    }

    public void setGetRtcDataResult(String getRtcDataResult) {
        this.getRtcDataResult = getRtcDataResult;
    }

    public String getGettcDataResult() {
        return gettcDataResult;
    }

    public void setGettcDataResult(String gettcDataResult) {
        this.gettcDataResult = gettcDataResult;
    }
}
