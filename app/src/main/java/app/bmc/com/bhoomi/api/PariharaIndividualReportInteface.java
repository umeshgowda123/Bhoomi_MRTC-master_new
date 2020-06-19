package app.bmc.com.bhoomi.api;

import com.google.gson.JsonObject;

import app.bmc.com.bhoomi.model.ClsLoanWaiverReportBANK_Branchwise;
import app.bmc.com.bhoomi.model.ClsLoanWaiverReportPacs_Branchwise;
import app.bmc.com.bhoomi.model.PariharaIndividualDetailsResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PariharaIndividualReportInteface {


    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("DownloadCLWSSTATUS_Aadhaar/{pstrUserName}/{pStrPassword}/{pAadhaarNo}")
    Call<PariharaIndividualDetailsResponse> getCLWSStatusByAaadharNumber(@Path("pstrUserName") String pstrUserName, @Path("pStrPassword") String pstrPassword,
                                                                      @Path("pAadhaarNo") String pAadhaarNo);

    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("DownloadCLWSSTATUS_RationCard/{pstrUserName}/{pStrPassword}/{pRationCardNumber}")
    Call<PariharaIndividualDetailsResponse> getCLWSStatusByRationCard(@Path("pstrUserName") String pstrUserName, @Path("pStrPassword") String pstrPassword,
                                                                      @Path("pRationCardNumber") String pRationCardNumber);


    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("GetPariharaPaymentDetails/{pstrUserName}/{pStrPassword}/{pAadhaarNo}/{pCalamityValue}/{pYearValue}")
    Call<PariharaIndividualDetailsResponse> getPariharaPaymentDetails(@Path("pstrUserName") String pstrUserName, @Path("pStrPassword") String pstrPassword,
                                                                      @Path("pAadhaarNo") String pAadhaarNo, @Path("pCalamityValue") String pCalamityValue,
                                                                      @Path("pYearValue") String pYearValue);


    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("GetPariharaBeneficiary/{pstrUserName}/{pStrPassword}/{pDistrictCode}/{pTalukCode}/{pHobliCode}/{pVillageCode}/{pYear}/{pSeason}/{pCalamityType}")
    Call<PariharaIndividualDetailsResponse> getVillageWisePariharaDetails(@Path("pstrUserName") String pstrUserName, @Path("pStrPassword") String pstrPassword,
                                                                      @Path("pDistrictCode") int pDistrictCode, @Path("pTalukCode") int pTalukCode,
                                                                      @Path("pHobliCode") int pHobliCode, @Path("pVillageCode") int pVillageCode,
                                                                      @Path("pYear") int pYear, @Path("pSeason") int pSeason, @Path("pCalamityType") int pCalamityType);


    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("GetPariharaBeneficiaryPaymentDetails/{pstrUserName}/{pStrPassword}/{pAadhaarNo}/{pYear}/{pSeason}/{pCalamityType}")
    Call<PariharaIndividualDetailsResponse> getBenificiaryDetailsLandWise(@Path("pstrUserName") String pstrUserName, @Path("pStrPassword") String pstrPassword,
                                                                          @Path("pAadhaarNo") String pAadhaarNo, @Path("pYear") int pYear,
                                                                          @Path("pSeason") int pSeason, @Path("pCalamityType") int pCalamityType);


    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("DownlaodVillageMap/{pstrUserName}/{pStrPassword}/{pDistrictCode}/{pTalukCode}/{pHobliCode}/{pVillageCode}")
    Call<PariharaIndividualDetailsResponse> downloadVillageMapDetails(@Path("pstrUserName") String pstrUserName, @Path("pStrPassword") String pstrPassword,
                                                                          @Path("pDistrictCode") int pDistrictCode, @Path("pTalukCode") int pTalukCode,
                                                                          @Path("pHobliCode") int pHobliCode, @Path("pVillageCode") int pVillageCode);


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
    @POST("LoanWaiverReportBANK_Farmerwise/{pstrUserName}/{pStrPassword}/{pDistrictCode}/{pTalukCode}/{pHobliCode}/{pVillageCode}")
    Call<PariharaIndividualDetailsResponse> getLoanWaiverReportFramerWise(@Path("pstrUserName") String pstrUserName, @Path("pStrPassword") String pstrPassword,
                                                                            @Path("pDistrictCode") int pDistrictCode, @Path("pTalukCode") int pTalukCode,
                                                                        @Path("pHobliCode") int pHobliCode, @Path("pVillageCode") int pVillageCode);


    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("LoanWaiverReportPACS_Farmerwise/{pstrUserName}/{pStrPassword}/{pDistrictCode}/{pTalukCode}/{pHobliCode}/{pVillageCode}")
    Call<PariharaIndividualDetailsResponse> getPacsLoanWaiverReportFramerWise(@Path("pstrUserName") String pstrUserName, @Path("pStrPassword") String pstrPassword,
                                                                          @Path("pDistrictCode") int pDistrictCode, @Path("pTalukCode") int pTalukCode,
                                                                          @Path("pHobliCode") int pHobliCode, @Path("pVillageCode") int pVillageCode);


    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("LoanWaiverReportBANK_Bankwise/{pstr_UserName}/{pStr_Password}/")
    Call<PariharaIndividualDetailsResponse> fnGetLoanWaiverReportBankWise(
            @Path("pstr_UserName") String username,
            @Path("pStr_Password") String password,
            @Body JsonObject input);


    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("LoanWaiverReportPACS_Bankwise/{pstr_UserName}/{pStr_Password}/")
    Call<PariharaIndividualDetailsResponse> fnGetLoanWaiverPacsReportBankWise(
            @Path("pstr_UserName") String username,
            @Path("pStr_Password") String password,
            @Body JsonObject input);


    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("LoanWaiverReportBANK_Branchwise/{pstrUserName}/{pStrPassword}/")
    Call<PariharaIndividualDetailsResponse> getLoanWaiverReportBranchWise(@Path("pstrUserName") String pstrUserName, @Path("pStrPassword") String pstrPassword,
                                                                          @Body ClsLoanWaiverReportBANK_Branchwise clsLoanWaiverReportBANK_branchwise);



    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("LoanWaiverReportPACS_Branchwise/{pstrUserName}/{pStrPassword}/")
    Call<PariharaIndividualDetailsResponse> getLoanWaiverReportPACS_Branchwise(@Path("pstrUserName") String pstrUserName, @Path("pStrPassword") String pstrPassword,
                                                                          @Body ClsLoanWaiverReportPacs_Branchwise clsLoanWaiverReportPacs_branchwise);


}
