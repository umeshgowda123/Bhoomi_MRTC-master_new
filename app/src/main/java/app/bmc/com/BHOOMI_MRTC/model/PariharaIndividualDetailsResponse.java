package app.bmc.com.BHOOMI_MRTC.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PariharaIndividualDetailsResponse {


    @SerializedName("DownloadCLWSSTATUS_AadhaarResult")
    @Expose
    public String getDownloadCLWSSTATUS_AadhaarResult;

//    @SerializedName("GetLandRestrictionResult")
//    @Expose
//    public String GetLandRestrictionResult;

    @SerializedName("GetLandRestriction_RAWResult")
    @Expose
    public String GetLandRestrictionResult;

    public String getGetLandRestrictionResult() {
        return GetLandRestrictionResult;
    }

    @SerializedName("DownloadCLWSSTATUS_RationCardResult")
    @Expose
    public String getDownloadCLWSSTATUS_RationCardResult;

    @SerializedName("FnGetServiceStatusResult")
    @Expose
    public String FnGetServiceStatusResult;

    @SerializedName("GetDetails_VillageWise_JSONResult")
    @Expose
    public String Details_VillageWise_JSONResult;

    public void setGetLandRestrictionResult(String getLandRestrictionResult) {
        GetLandRestrictionResult = getLandRestrictionResult;
    }

    public String getDetails_VillageWise_JSONResult() {
        return Details_VillageWise_JSONResult;
    }

    public void setDetails_VillageWise_JSONResult(String details_VillageWise_JSONResult) {
        Details_VillageWise_JSONResult = details_VillageWise_JSONResult;
    }

    public String getFnGetServiceStatusResult() {
        return FnGetServiceStatusResult;
    }

    public void setFnGetServiceStatusResult(String fnGetServiceStatusResult) {
        FnGetServiceStatusResult = fnGetServiceStatusResult;
    }

    public String getGetDownloadCLWSSTATUS_RationCardResult() {
        return getDownloadCLWSSTATUS_RationCardResult;
    }

    public void setGetDownloadCLWSSTATUS_RationCardResult(String getDownloadCLWSSTATUS_RationCardResult) {
        this.getDownloadCLWSSTATUS_RationCardResult = getDownloadCLWSSTATUS_RationCardResult;
    }

    public String getGetDownloadCLWSSTATUS_AadhaarResult() {
        return getDownloadCLWSSTATUS_AadhaarResult;
    }

    public void setGetDownloadCLWSSTATUS_AadhaarResult(String getDownloadCLWSSTATUS_AadhaarResult) {
        this.getDownloadCLWSSTATUS_AadhaarResult = getDownloadCLWSSTATUS_AadhaarResult;
    }

    @SerializedName("GetPariharaPaymentDetailsResult")
    @Expose
    private String getPariharaPaymentDetailsResult;


    public String getGetPariharaPaymentDetailsResult() {
        return getPariharaPaymentDetailsResult;
    }

    public void setGetPariharaPaymentDetailsResult(String getPariharaPaymentDetailsResult) {
        this.getPariharaPaymentDetailsResult = getPariharaPaymentDetailsResult;
    }


    @SerializedName("GetPariharaBeneficiaryResult")
    @Expose
    private String getPariharaBeneficiaryResult;


    public String getGetPariharaBeneficiaryResult() {
        return getPariharaBeneficiaryResult;
    }

    public void setGetPariharaBeneficiaryResult(String getPariharaBeneficiaryResult) {
        this.getPariharaBeneficiaryResult = getPariharaBeneficiaryResult;
    }

    @SerializedName("GetPariharaBeneficiaryPaymentDetailsResult")
    @Expose
    private String getPariharaBeneficiaryPaymentDetailsResult;


    public String getGetPariharaBeneficiaryPaymentDetailsResult() {
        return getPariharaBeneficiaryPaymentDetailsResult;
    }

    public void setGetPariharaBeneficiaryPaymentDetailsResult(String getPariharaBeneficiaryPaymentDetailsResult) {
        this.getPariharaBeneficiaryPaymentDetailsResult = getPariharaBeneficiaryPaymentDetailsResult;
    }


    @SerializedName("DownloadVillageMapResult")
    @Expose
    private String downloadVillageMapResult;

    public String getDownloadVillageMapResult() {
        return downloadVillageMapResult;
    }

    public void setDownloadVillageMapResult(String downloadVillageMapResult) {
        this.downloadVillageMapResult = downloadVillageMapResult;
    }


    @SerializedName("GetMutationPendencyDetails")
    @Expose
    private String getMutationPendencyDetailsResult;


    public String getGetMutationPendencyDetailsResult() {
        return getMutationPendencyDetailsResult;
    }

    public void setGetMutationPendencyDetailsResult(String getMutationPendencyDetailsResult) {
        this.getMutationPendencyDetailsResult = getMutationPendencyDetailsResult;
    }


    @SerializedName("GetMutationSummaryReport")
    @Expose
    private String getMutationSummaryReportResult;

    public String getGetMutationSummaryReportResult() {
        return getMutationSummaryReportResult;
    }

    public void setGetMutationSummaryReportResult(String getMutationSummaryReportResult) {
        this.getMutationSummaryReportResult = getMutationSummaryReportResult;
    }


    @SerializedName("LoanWaiverReportBANK_BankwiseResult")
//    @SerializedName("GetLoanWaiverReportBANK_BankwiseResult")//---SUSMITA
    @Expose
    private String getLoanWaiverReportBANK_BankwiseResult;


    public String getGetLoanWaiverReportBANK_BankwiseResult() {
        return getLoanWaiverReportBANK_BankwiseResult;
    }

    public void setGetLoanWaiverReportBANK_BankwiseResult(String getLoanWaiverReportBANK_BankwiseResult) {
        this.getLoanWaiverReportBANK_BankwiseResult = getLoanWaiverReportBANK_BankwiseResult;
    }


    @SerializedName("LoanWaiverReportBANK_FarmerwiseResult")
    @Expose
    private String getLoanWaiverReportBANK_FarmerwiseResult;


    public String getGetLoanWaiverReportBANK_FarmerwiseResult() {
        return getLoanWaiverReportBANK_FarmerwiseResult;
    }

    public void setGetLoanWaiverReportBANK_FarmerwiseResult(String getLoanWaiverReportBANK_FarmerwiseResult) {
        this.getLoanWaiverReportBANK_FarmerwiseResult = getLoanWaiverReportBANK_FarmerwiseResult;
    }


    @SerializedName("LoanWaiverReportBANK_BranchwiseResult")
    @Expose
    private String getLoanWaiverReportBANK_BranchwiseResult;

    public String getGetLoanWaiverReportBANK_BranchwiseResult() {
        return getLoanWaiverReportBANK_BranchwiseResult;
    }

    public void setGetLoanWaiverReportBANK_BranchwiseResult(String getLoanWaiverReportBANK_BranchwiseResult) {
        this.getLoanWaiverReportBANK_BranchwiseResult = getLoanWaiverReportBANK_BranchwiseResult;
    }

    @SerializedName("LoanWaiverReportPACS_BankwiseResult")
    @Expose
    private String getLoanWaiverReportPACS_BankwiseResult;

    public String getGetLoanWaiverReportPACS_BankwiseResult() {
        return getLoanWaiverReportPACS_BankwiseResult;
    }

    public void setGetLoanWaiverReportPACS_BankwiseResult(String getLoanWaiverReportPACS_BankwiseResult) {
        this.getLoanWaiverReportPACS_BankwiseResult = getLoanWaiverReportPACS_BankwiseResult;
    }


    @SerializedName("LoanWaiverReportPACS_BranchwiseResult")
    @Expose
    private String getLoanWaiverReportPACS_BranchwiseResult;


    public String getGetLoanWaiverReportPACS_BranchwiseResult() {
        return getLoanWaiverReportPACS_BranchwiseResult;
    }

    public void setGetLoanWaiverReportPACS_BranchwiseResult(String getLoanWaiverReportPACS_BranchwiseResult) {
        this.getLoanWaiverReportPACS_BranchwiseResult = getLoanWaiverReportPACS_BranchwiseResult;
    }



    @SerializedName("LoanWaiverReportPACS_FarmerwiseResult")
    @Expose
    private String getLoanWaiverReportPACS_FarmerwiseResult;


    public String getGetLoanWaiverReportPACS_FarmerwiseResult() {
        return getLoanWaiverReportPACS_FarmerwiseResult;
    }

    public void setGetLoanWaiverReportPACS_FarmerwiseResult(String getLoanWaiverReportPACS_FarmerwiseResult) {
        this.getLoanWaiverReportPACS_FarmerwiseResult = getLoanWaiverReportPACS_FarmerwiseResult;
    }
    @SerializedName("GET_Afdvt_ReqSts_BasedOnAfdvtId")
    @Expose
    private String get_Afdvt_ReqSts_BasedOnAfdvtIdResult;

    public void setGet_Afdvt_ReqSts_BasedOnAfdvtIdResult(String get_Afdvt_ReqSts_BasedOnAfdvtIdResult) {
        this.get_Afdvt_ReqSts_BasedOnAfdvtIdResult = get_Afdvt_ReqSts_BasedOnAfdvtIdResult;
    }

    public String getGet_Afdvt_ReqSts_BasedOnAfdvtIdResult() {
        return get_Afdvt_ReqSts_BasedOnAfdvtIdResult;
    }
    @SerializedName("GET_Afdvt_ReqSts_BasedOnUserId")
    @Expose
    private String get_Afdvt_ReqSts_BasedOnUserIdResult;

    @SerializedName("GetLandConversionFinalOrders_BasedOnSurveyNo")
    @Expose
    private String getLandConversionFinalOrders_BasedOnSurveyNoResult;

    @SerializedName("GetLandConversionFinalOrders_BasedOnReqId")
    @Expose
    private String getLandConversionFinalOrders_BasedOnReqIdResult;

    public String getGet_Afdvt_ReqSts_BasedOnUserIdResult() {
        return get_Afdvt_ReqSts_BasedOnUserIdResult;
    }

    public void setGet_Afdvt_ReqSts_BasedOnUserIdResult(String get_Afdvt_ReqSts_BasedOnUserIdResult) {
        this.get_Afdvt_ReqSts_BasedOnUserIdResult = get_Afdvt_ReqSts_BasedOnUserIdResult;
    }

    public String getGetLandConversionFinalOrders_BasedOnSurveyNoResult() {
        return getLandConversionFinalOrders_BasedOnSurveyNoResult;
    }

    public void setGetLandConversionFinalOrders_BasedOnSurveyNoResult(String getLandConversionFinalOrders_BasedOnSurveyNoResult) {
        this.getLandConversionFinalOrders_BasedOnSurveyNoResult = getLandConversionFinalOrders_BasedOnSurveyNoResult;
    }

    public String getGetLandConversionFinalOrders_BasedOnReqIdResult() {
        return getLandConversionFinalOrders_BasedOnReqIdResult;
    }

    public void setGetLandConversionFinalOrders_BasedOnReqIdResult(String getLandConversionFinalOrders_BasedOnReqIdResult) {
        this.getLandConversionFinalOrders_BasedOnReqIdResult = getLandConversionFinalOrders_BasedOnReqIdResult;
    }


//-----------------------------------------SUS 12:36 P.M 29/06/20----------------------------------

    @SerializedName("GetSketchDetailsDetailsBasedOnAppNoResult")
    @Expose
    private String getSketchDetailsDetailsBasedOnAppNoResult;

    public String getGetSketchDetailsDetailsBasedOnAppNoResult() {
        return getSketchDetailsDetailsBasedOnAppNoResult;
    }

    public void setGetSketchDetailsDetailsBasedOnAppNoResult(String getSketchDetailsDetailsBasedOnAppNoResult) {
        this.getSketchDetailsDetailsBasedOnAppNoResult = getSketchDetailsDetailsBasedOnAppNoResult;
    }
//-----------------------------------------SUS 12:56 P.M 30/06/20----------------------------------
    @SerializedName("GetApplicationStatusBasedonAppNoResult")
    @Expose
    private String getApplicationStatusBasedonAppNoResult;

    public String getGetApplicationStatusBasedonAppNoResult() {
        return getApplicationStatusBasedonAppNoResult;
    }

    public void setGetApplicationStatusBasedonAppNoResult(String getApplicationStatusBasedonAppNoResult) {
        this.getApplicationStatusBasedonAppNoResult = getApplicationStatusBasedonAppNoResult;
    }

                        //-------------------- 1:06 P.M--------------

    @SerializedName("GetAllotmentDetailsBasedOnAppNoResult")
    @Expose
    private String getAllotmentDetailsBasedOnAppNoResult;

    public String getGetAllotmentDetailsBasedOnAppNoResult() {
        return getAllotmentDetailsBasedOnAppNoResult;
    }

    public void setGetAllotmentDetailsBasedOnAppNoResult(String getAllotmentDetailsBasedOnAppNoResult) {
        this.getAllotmentDetailsBasedOnAppNoResult = getAllotmentDetailsBasedOnAppNoResult;
    }

}
