package app.bmc.com.BHOOMI_MRTC.api;

import com.google.gson.JsonObject;

import app.bmc.com.BHOOMI_MRTC.model.PariharaIndividualDetailsResponse;
import app.bmc.com.BHOOMI_MRTC.model.TokenRes;
import io.reactivex.Observable;
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
    Call<PariharaIndividualDetailsResponse> getMutationPendingDetails(@Path("pCensus_dist_code") int pCensus_dist_code, @Path("pCensus_Taluk_Code") int pCensus_Taluk_Code,
                                                                      @Path("pHobliCode") int pHobliCode, @Path("pVillageCode") int pVillageCode);



    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("api/values/GetMutationSummaryReport/{pCensus_dist_code}/{pCensus_Taluk_Code}/{pHobliCode}/{pVillageCode}/{pSurvey_no}")
    Call<PariharaIndividualDetailsResponse> getMutationSummeryReportDetails(@Path("pCensus_dist_code") int pCensus_dist_code, @Path("pCensus_Taluk_Code") int pCensus_Taluk_Code,
                                                                      @Path("pHobliCode") int pHobliCode, @Path("pVillageCode") int pVillageCode, @Path("pSurvey_no") int pSurvey_no);

    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("api/values/GET_Afdvt_ReqSts_BasedOnAfdvtId/{pAfdvtId}")
    Call<PariharaIndividualDetailsResponse> getLandConversionBasedOnAffidavitID(@Path("pAfdvtId") String pAfdvtId);

    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("api/values/GET_Afdvt_ReqSts_BasedOnUserId/{pUserId}")
    Call<PariharaIndividualDetailsResponse> getLandConversionBasedOnUserID(@Path("pUserId") String pUserId);

    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("api/values/GetLandConversionFinalOrders_BasedOnReqId/{PREQUESTID}")
    Call<PariharaIndividualDetailsResponse> getLandConversionFinalOrders_BasedOnReqId(@Path("PREQUESTID") String PREQUESTID);

    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("api/values/GetLandConversionFinalOrders_BasedOnSurveyNo/{pDistrictCode}/{pTalukCode}/{pHobliCode}/{pVillageCode}/{pSurveyNo}")
    Call<PariharaIndividualDetailsResponse> getLandConversionFinalOrders_BasedOnSurveyNo(
            @Path("pDistrictCode") int pDistrictCode,
            @Path("pTalukCode") int pTalukCode,
            @Path("pHobliCode") int pHobliCode,
            @Path("pVillageCode") int pVillageCode,
            @Path("pSurveyNo") String pSurveyNo
    );

    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("api/values/GetLandRestriction_RAW/")
    Call<PariharaIndividualDetailsResponse> fnGetLandRestrictionResult(
            @Body JsonObject input);

    @FormUrlEncoded
    @POST("token")
    Call<TokenRes> getToken(@Field("username") String email, @Field("password") String password, @Field("grant_type") String granttype);

}
