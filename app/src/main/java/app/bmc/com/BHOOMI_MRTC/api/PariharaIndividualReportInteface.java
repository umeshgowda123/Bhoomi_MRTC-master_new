package app.bmc.com.BHOOMI_MRTC.api;

import com.google.gson.JsonObject;

import app.bmc.com.BHOOMI_MRTC.model.BHOOMI_API_Response;
import app.bmc.com.BHOOMI_MRTC.model.TokenRes;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PariharaIndividualReportInteface {




    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("api/values/GetMutationPendencyDetails/{pCensus_dist_code}/{pCensus_Taluk_Code}/{pHobliCode}/{pVillageCode}")
    Call<BHOOMI_API_Response> getMutationPendingDetails(@Path("pCensus_dist_code") int pCensus_dist_code, @Path("pCensus_Taluk_Code") int pCensus_Taluk_Code,
                                                        @Path("pHobliCode") int pHobliCode, @Path("pVillageCode") int pVillageCode);



    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("api/values/GetMutationSummaryReport/{pCensus_dist_code}/{pCensus_Taluk_Code}/{pHobliCode}/{pVillageCode}/{pSurvey_no}")
    Call<BHOOMI_API_Response> getMutationSummeryReportDetails(@Path("pCensus_dist_code") int pCensus_dist_code, @Path("pCensus_Taluk_Code") int pCensus_Taluk_Code,
                                                              @Path("pHobliCode") int pHobliCode, @Path("pVillageCode") int pVillageCode, @Path("pSurvey_no") int pSurvey_no);

//    @Headers({"content-type: text/json; charset=utf-8"})
//    @POST("api/values/GET_Afdvt_ReqSts_BasedOnAfdvtId/{pAfdvtId}")
//    Call<BHOOMI_API_Response> getLandConversionBasedOnAffidavitID(@Path("pAfdvtId") String pAfdvtId);

    @POST("api/values/GET_LandConv_ReqSts_BasedOnId/1/{pId}")
    Call<BHOOMI_API_Response> getLandConversionBasedOnReqID(@Path("pId") String pReqId);

    @POST("api/values/GET_LandConv_ReqSts_BasedOnId/2/{pId}")
    Call<BHOOMI_API_Response> getLandConversionBasedOnAffidavitID(@Path("pId") String pAfdvtId);

    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("api/values/GET_Afdvt_ReqSts_BasedOnUserId/{pUserId}")
    Call<BHOOMI_API_Response> getLandConversionBasedOnUserID(@Path("pUserId") String pUserId);

    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("api/values/GetLandConversionFinalOrders_BasedOnReqId/{PREQUESTID}")
    Call<BHOOMI_API_Response> getLandConversionFinalOrders_BasedOnReqId(@Path("PREQUESTID") String PREQUESTID);

    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("api/values/GetLandConversionFinalOrders_BasedOnSurveyNo/{pDistrictCode}/{pTalukCode}/{pHobliCode}/{pVillageCode}/{pSurveyNo}")
    Call<BHOOMI_API_Response> getLandConversionFinalOrders_BasedOnSurveyNo(
            @Path("pDistrictCode") int pDistrictCode,
            @Path("pTalukCode") int pTalukCode,
            @Path("pHobliCode") int pHobliCode,
            @Path("pVillageCode") int pVillageCode,
            @Path("pSurveyNo") String pSurveyNo
    );

    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("api/values/GetLandRestriction_RAW/")
    Call<BHOOMI_API_Response> fnGetLandRestrictionResult(
            @Body JsonObject input);

    @FormUrlEncoded
    @POST("token")
    Call<TokenRes> getToken(@Field("username") String email, @Field("password") String password, @Field("grant_type") String granttype);

}
