package app.bmc.com.BHOOMI_MRTC.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class BHOOMI_API_Response {


    @SerializedName("bhoomI_API_Response")
    @Expose
    public String bhoomI_API_Response;

    public String getBhoomI_API_Response() {
        return bhoomI_API_Response;
    }

    public void setBhoomI_API_Response(String bhoomI_API_Response) {
        this.bhoomI_API_Response = bhoomI_API_Response;
    }
}