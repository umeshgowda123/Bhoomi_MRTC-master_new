package app.bmc.com.BHOOMI_MRTC.api;

import com.google.gson.JsonObject;

import app.bmc.com.BHOOMI_MRTC.model.Get_Rtc_Data_Result;
import app.bmc.com.BHOOMI_MRTC.model.Get_Surnoc_HissaRequest;
import app.bmc.com.BHOOMI_MRTC.model.Get_Surnoc_HissaResult;
import app.bmc.com.BHOOMI_MRTC.model.Get_ViewMutationStatusResult;
import app.bmc.com.BHOOMI_MRTC.model.PariharaIndividualDetailsResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RtcViewInformationApi {

    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("api/values/Get_Surnoc_Hissa/")
    Call<Get_Surnoc_HissaResult> getSurnocHissaResponse(
            @Body Get_Surnoc_HissaRequest get_surnoc_hissaRequest
    );

    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("api/values/get_rtc_data/")
    Call<Get_Rtc_Data_Result> getRtcResponse(
            @Body JsonObject input
    );


    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("api/values/ViewMutationStatus/{District_Code}/{Taluk_Code}/{Hobli_Code}/{Village_Code}/{Land_Code}")
    Call<Get_ViewMutationStatusResult> getMutationStatusResponse(
            @Path("District_Code") int District_Code,
            @Path("Taluk_Code") int Taluk_Code,
            @Path("Hobli_Code") int Hobli_Code,
            @Path("Village_Code") int Village_Code,
            @Path("Land_Code") int Land_Code
    );


    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("get_tc_data/{pstrUserName}/{pStrPassword}/")
    Call<Get_Rtc_Data_Result> getRtcCultivator(
            @Path("pstrUserName") String pstrUserName,
            @Path("pStrPassword") String pStrPassword,
            @Body JsonObject input
    );

    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("api/values/GetDetails_VillageWise_JSON/")
    Call<PariharaIndividualDetailsResponse> GetDetails_VillageWise_JSON(
            @Body JsonObject input
    );



}
