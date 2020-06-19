package app.bmc.com.bhoomi.api;

import app.bmc.com.bhoomi.model.GETRTCXMLDATAResult;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This interface defined to access web services using retrofit. here we calling rtc xml verification
 * services.
 */
public interface RtcXmlVerificationApi {
    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("RTCXML/{pReferenceNo}/{pPasscode}/{pSaltkey}")
    Call<GETRTCXMLDATAResult> getStringResponse(@Path("pReferenceNo") String pReferenceNo, @Path("pPasscode") String pPasscode, @Path("pSaltkey") String pSaltkey);


}
