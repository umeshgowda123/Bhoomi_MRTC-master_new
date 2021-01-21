package app.bmc.com.BHOOMI_MRTC.api;

import com.google.gson.JsonObject;

import app.bmc.com.BHOOMI_MRTC.model.PariharaIndividualDetailsResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PariharaIndividualReportInteface {




    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("GetMutationPendencyDetails/{pstrUserName}/{pStrPassword}/{pCensus_dist_code}/{pCensus_Taluk_Code}/{pHobliCode}/{pVillageCode}")
    Call<PariharaIndividualDetailsResponse> getMutationPendingDetails(@Path("pstrUserName") String pstrUserName, @Path("pStrPassword") String pstrPassword,
                                                                      @Path("pCensus_dist_code") int pCensus_dist_code, @Path("pCensus_Taluk_Code") int pCensus_Taluk_Code,
                                                                      @Path("pHobliCode") int pHobliCode, @Path("pVillageCode") int pVillageCode);



    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("GetMutationSummaryReport/{pstrUserName}/{pStrPassword}/{pCensus_dist_code}/{pCensus_Taluk_Code}/{pHobliCode}/{pVillageCode}/{pSurvey_no}")
    Call<PariharaIndividualDetailsResponse> getMutationSummeryReportDetails(@Path("pstrUserName") String pstrUserName, @Path("pStrPassword") String pstrPassword,
                                                                      @Path("pCensus_dist_code") int pCensus_dist_code, @Path("pCensus_Taluk_Code") int pCensus_Taluk_Code,
                                                                      @Path("pHobliCode") int pHobliCode, @Path("pVillageCode") int pVillageCode, @Path("pSurvey_no") int pSurvey_no);

    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("GET_Afdvt_ReqSts_BasedOnAfdvtId/{pstrUserName}/{pStrPassword}/{pAfdvtId}")
    Call<PariharaIndividualDetailsResponse> getLandConversionBasedOnAffidavitID(@Path("pstrUserName") String pstrUserName, @Path("pStrPassword") String pstrPassword,
                                                                         @Path("pAfdvtId") String pAfdvtId);

    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("GET_Afdvt_ReqSts_BasedOnUserId/{pstrUserName}/{pStrPassword}/{pUserId}")
    Call<PariharaIndividualDetailsResponse> getLandConversionBasedOnUserID(@Path("pstrUserName") String pstrUserName, @Path("pStrPassword") String pstrPassword,
                                                                                @Path("pUserId") String pUserId);

    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("GetLandConversionFinalOrders_BasedOnReqId/{PSTRUSERNAME}/{PSTRPASSWORD}/{PREQUESTID}")
    Call<PariharaIndividualDetailsResponse> getLandConversionFinalOrders_BasedOnReqId(@Path("PSTRUSERNAME") String PSTRUSERNAME, @Path("PSTRPASSWORD") String PSTRPASSWORD,
                                                                           @Path("PREQUESTID") String PREQUESTID);

    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("GetLandConversionFinalOrders_BasedOnSurveyNo/{PSTRUSERNAME}/{PSTRPASSWORD}/{pDistrictCode}/{pTalukCode}/{pHobliCode}/{pVillageCode}/{pSurveyNo}")
    Call<PariharaIndividualDetailsResponse> getLandConversionFinalOrders_BasedOnSurveyNo(
            @Path("PSTRUSERNAME") String PSTRUSERNAME,
            @Path("PSTRPASSWORD") String PSTRPASSWORD,
            @Path("pDistrictCode") int pDistrictCode,
            @Path("pTalukCode") int pTalukCode,
            @Path("pHobliCode") int pHobliCode,
            @Path("pVillageCode") int pVillageCode,
            @Path("pSurveyNo") String pSurveyNo
    );

    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("GetLandRestriction_RAW/{pstrUserName}/{pStrPassword}/")
    Call<PariharaIndividualDetailsResponse> fnGetLandRestrictionResult(
            @Path("pstrUserName") String username,
            @Path("pStrPassword") String password,
            @Body JsonObject input);

}
