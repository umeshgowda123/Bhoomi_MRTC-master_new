package app.bmc.com.bhoomi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoanWaiverBranchResponseData {


    @SerializedName("DISTRICT_CODE")
    @Expose
    private String DISTRICT_CODE;

    @SerializedName("DISTRICT_NAME_KN")
    @Expose
    private String DISTRICT_NAME_KN;


    @SerializedName("DISTRICT_NAME")
    @Expose
    private String DISTRICT_NAME;

    @SerializedName("bnk_nme_en")
    @Expose
    private String bnk_nme_en;


    @SerializedName("BNK_BRNCH_NME")
    @Expose
    private String BNK_BRNCH_NME;

    @SerializedName("loantype")
    @Expose
    private String loantype;

    @SerializedName("Total")
    @Expose
    private String Total;


    @SerializedName("LoanAmount")
    @Expose
    private String LoanAmount;

    @SerializedName("CoopCommon")
    @Expose
    private String CoopCommon;


    @SerializedName("AuthenticationFailed")
    @Expose
    private String AuthenticationFailed;


    @SerializedName("EligibleLoan")
    @Expose
    private String EligibleLoan;

    @SerializedName("EligibleLoanAmount")
    @Expose
    private String EligibleLoanAmount;

    @SerializedName("GreenListLoans")
    @Expose
    private String GreenListLoans;

    @SerializedName("GreenListAmount")
    @Expose
    private String GreenListAmount;

    @SerializedName("PaidLoans")
    @Expose
    private String PaidLoans;

    @SerializedName("PaidLoanAmount")
    @Expose
    private String PaidLoanAmount;


    @SerializedName("Perc")
    @Expose
    private String Perc;

    public String getDISTRICT_CODE() {
        return DISTRICT_CODE;
    }

    public void setDISTRICT_CODE(String DISTRICT_CODE) {
        this.DISTRICT_CODE = DISTRICT_CODE;
    }

    public String getDISTRICT_NAME_KN() {
        return DISTRICT_NAME_KN;
    }

    public void setDISTRICT_NAME_KN(String DISTRICT_NAME_KN) {
        this.DISTRICT_NAME_KN = DISTRICT_NAME_KN;
    }

    public String getDISTRICT_NAME() {
        return DISTRICT_NAME;
    }

    public void setDISTRICT_NAME(String DISTRICT_NAME) {
        this.DISTRICT_NAME = DISTRICT_NAME;
    }

    public String getBnk_nme_en() {
        return bnk_nme_en;
    }

    public void setBnk_nme_en(String bnk_nme_en) {
        this.bnk_nme_en = bnk_nme_en;
    }

    public String getLoantype() {
        return loantype;
    }

    public void setLoantype(String loantype) {
        this.loantype = loantype;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getLoanAmount() {
        return LoanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        LoanAmount = loanAmount;
    }

    public String getCoopCommon() {
        return CoopCommon;
    }

    public void setCoopCommon(String coopCommon) {
        CoopCommon = coopCommon;
    }

    public String getAuthenticationFailed() {
        return AuthenticationFailed;
    }

    public void setAuthenticationFailed(String authenticationFailed) {
        AuthenticationFailed = authenticationFailed;
    }

    public String getEligibleLoan() {
        return EligibleLoan;
    }

    public void setEligibleLoan(String eligibleLoan) {
        EligibleLoan = eligibleLoan;
    }

    public String getEligibleLoanAmount() {
        return EligibleLoanAmount;
    }

    public void setEligibleLoanAmount(String eligibleLoanAmount) {
        EligibleLoanAmount = eligibleLoanAmount;
    }

    public String getGreenListLoans() {
        return GreenListLoans;
    }

    public void setGreenListLoans(String greenListLoans) {
        GreenListLoans = greenListLoans;
    }

    public String getGreenListAmount() {
        return GreenListAmount;
    }

    public void setGreenListAmount(String greenListAmount) {
        GreenListAmount = greenListAmount;
    }

    public String getPaidLoans() {
        return PaidLoans;
    }

    public void setPaidLoans(String paidLoans) {
        PaidLoans = paidLoans;
    }

    public String getPaidLoanAmount() {
        return PaidLoanAmount;
    }

    public void setPaidLoanAmount(String paidLoanAmount) {
        PaidLoanAmount = paidLoanAmount;
    }

    public String getPerc() {
        return Perc;
    }

    public void setPerc(String perc) {
        Perc = perc;
    }

    public String getBNK_BRNCH_NME() {
        return BNK_BRNCH_NME;
    }

    public void setBNK_BRNCH_NME(String BNK_BRNCH_NME) {
        this.BNK_BRNCH_NME = BNK_BRNCH_NME;
    }
}


