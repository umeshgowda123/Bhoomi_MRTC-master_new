package app.bmc.com.BHOOMI_MRTC.api;

import com.google.gson.JsonObject;

import app.bmc.com.BHOOMI_MRTC.model.ClsKnowID_Get_Surnoc_Hissa;
import app.bmc.com.BHOOMI_MRTC.model.Get_Surnoc_HissaRequest;
import app.bmc.com.BHOOMI_MRTC.model.BHOOMI_API_Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RtcViewInformationApi {

    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("api/values/Get_Surnoc_Hissa/")
    Call<BHOOMI_API_Response> getSurnocHissaResponse(
            @Body Get_Surnoc_HissaRequest get_surnoc_hissaRequest
    );

    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("api/values/GetSurnocNo/")
    Call<BHOOMI_API_Response> GetSurnocNo(
            @Body ClsKnowID_Get_Surnoc_Hissa input
    );

    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("api/values/GetHissaNo/")
    Call<BHOOMI_API_Response> GetHissaNo(
            @Body ClsKnowID_Get_Surnoc_Hissa input
    );

    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("api/values/get_rtc_data/")
    Call<BHOOMI_API_Response> getRtcResponse(
            @Body JsonObject input
    );


    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("api/values/ViewMutationStatus/{District_Code}/{Taluk_Code}/{Hobli_Code}/{Village_Code}/{Land_Code}")
    Call<BHOOMI_API_Response> getMutationStatusResponse(
            @Path("District_Code") int District_Code,
            @Path("Taluk_Code") int Taluk_Code,
            @Path("Hobli_Code") int Hobli_Code,
            @Path("Village_Code") int Village_Code,
            @Path("Land_Code") int Land_Code
    );


    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("api/values/get_tc_data/")
    Call<BHOOMI_API_Response> getRtcCultivator(
            @Body JsonObject input
    );

    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("api/values/GetDetails_VillageWise_JSON/")
    Call<BHOOMI_API_Response> GetDetails_VillageWise_JSON(
            @Body JsonObject input
    );



}
