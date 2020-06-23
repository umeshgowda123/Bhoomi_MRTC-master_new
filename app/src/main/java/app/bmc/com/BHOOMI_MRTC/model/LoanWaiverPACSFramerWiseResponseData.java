package app.bmc.com.BHOOMI_MRTC.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoanWaiverPACSFramerWiseResponseData {

    @SerializedName("lnd_villgnme")
    @Expose
    private String lnd_villgnme;

    @SerializedName("customerName")
    @Expose
    private String customerName;


    @SerializedName("CustomerRelativeName")
    @Expose
    private String CustomerRelativeName;

    @SerializedName("bnk_nme_en")
    @Expose
    private String bnk_nme_en;

    @SerializedName("BNK_BRNCH_NME")
    @Expose
    private String BNK_BRNCH_NME;

    @SerializedName("LoanAccount")
    @Expose
    private String LoanAccount;


    @SerializedName("Loantype")
    @Expose
    private String Loantype;

    @SerializedName("AmountLIAB20171231")
    @Expose
    private String AmountLIAB20171231;


    @SerializedName("IngreenList")
    @Expose
    private String IngreenList;


    @SerializedName("REason")
    @Expose
    private String REason;

    @SerializedName("LoanWaiverDisbursed")
    @Expose
    private String LoanWaiverDisbursed;

    @SerializedName("Paidamt")
    @Expose
    private String Paidamt;

    @SerializedName("LoanWaiverdisbursedCompleted")
    @Expose
    private String LoanWaiverdisbursedCompleted;

    @SerializedName("RationCardNum")
    @Expose
    private String RationCardNum;

    public String getLnd_villgnme() {
        return lnd_villgnme;
    }

    public void setLnd_villgnme(String lnd_villgnme) {
        this.lnd_villgnme = lnd_villgnme;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerRelativeName() {
        return CustomerRelativeName;
    }

    public void setCustomerRelativeName(String customerRelativeName) {
        CustomerRelativeName = customerRelativeName;
    }

    public String getBnk_nme_en() {
        return bnk_nme_en;
    }

    public void setBnk_nme_en(String bnk_nme_en) {
        this.bnk_nme_en = bnk_nme_en;
    }

    public String getBNK_BRNCH_NME() {
        return BNK_BRNCH_NME;
    }

    public void setBNK_BRNCH_NME(String BNK_BRNCH_NME) {
        this.BNK_BRNCH_NME = BNK_BRNCH_NME;
    }

    public String getLoanAccount() {
        return LoanAccount;
    }

    public void setLoanAccount(String loanAccount) {
        LoanAccount = loanAccount;
    }

    public String getLoantype() {
        return Loantype;
    }

    public void setLoantype(String loantype) {
        Loantype = loantype;
    }

    public String getAmountLIAB20171231() {
        return AmountLIAB20171231;
    }

    public void setAmountLIAB20171231(String amountLIAB20171231) {
        AmountLIAB20171231 = amountLIAB20171231;
    }

    public String getIngreenList() {
        return IngreenList;
    }

    public void setIngreenList(String ingreenList) {
        IngreenList = ingreenList;
    }

    public String getREason() {
        return REason;
    }

    public void setREason(String REason) {
        this.REason = REason;
    }

    public String getLoanWaiverDisbursed() {
        return LoanWaiverDisbursed;
    }

    public void setLoanWaiverDisbursed(String loanWaiverDisbursed) {
        LoanWaiverDisbursed = loanWaiverDisbursed;
    }

    public String getPaidamt() {
        return Paidamt;
    }

    public void setPaidamt(String paidamt) {
        Paidamt = paidamt;
    }

    public String getLoanWaiverdisbursedCompleted() {
        return LoanWaiverdisbursedCompleted;
    }

    public void setLoanWaiverdisbursedCompleted(String loanWaiverdisbursedCompleted) {
        LoanWaiverdisbursedCompleted = loanWaiverdisbursedCompleted;
    }

    public String getRationCardNum() {
        return RationCardNum;
    }

    public void setRationCardNum(String rationCardNum) {
        RationCardNum = rationCardNum;
    }
}


