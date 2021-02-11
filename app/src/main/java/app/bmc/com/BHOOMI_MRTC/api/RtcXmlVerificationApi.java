package app.bmc.com.BHOOMI_MRTC.api;

import com.google.gson.JsonObject;

import app.bmc.com.BHOOMI_MRTC.model.GETRTCXMLDATAResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RtcXmlVerificationApi {
    @Headers({"content-type: text/json; charset=utf-8"})
//    @POST("RTCXML/{pReferenceNo}/{pPasscode}/{pSaltkey}")
    @POST("api/values/RTCXML/")
    Call<GETRTCXMLDATAResult> getStringResponse(
            @Body JsonObject input);


}
