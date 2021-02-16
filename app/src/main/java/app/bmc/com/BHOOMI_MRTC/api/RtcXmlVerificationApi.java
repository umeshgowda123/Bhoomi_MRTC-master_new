package app.bmc.com.BHOOMI_MRTC.api;

import com.google.gson.JsonObject;

import app.bmc.com.BHOOMI_MRTC.model.BHOOMI_API_Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RtcXmlVerificationApi {
    @Headers({"content-type: text/json; charset=utf-8"})
//    @POST("RTCXML/{pReferenceNo}/{pPasscode}/{pSaltkey}")
    @POST("api/values/RTCXML/")
    Call<BHOOMI_API_Response> getStringResponse(
            @Body JsonObject input);


}
