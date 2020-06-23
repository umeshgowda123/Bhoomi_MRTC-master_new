package app.bmc.com.BHOOMI_MRTC.model;

public class PacsPaymentTableData {

    private String PCUST_ID;
    private String PCUST_BNK_ID;
    private String PCUST_NME;
    private String LoanType;
    private String PCUST_LINKED_SAVED_ACNT_NO;
    private String PCS_LIAB_10072018;
    private String NeedtoPay;
    private String PayingAmount;
    private String PAYMENT_DT;
    private String PPMT_Status;

    public String getLoanType() {
        return LoanType;
    }

    public void setLoanType(String loanType) {
        LoanType = loanType;
    }

    public String getPCUST_ID() {
        return PCUST_ID;
    }

    public void setPCUST_ID(String PCUST_ID) {
        this.PCUST_ID = PCUST_ID;
    }

    public String getPCUST_BNK_ID() {
        return PCUST_BNK_ID;
    }

    public void setPCUST_BNK_ID(String PCUST_BNK_ID) {
        this.PCUST_BNK_ID = PCUST_BNK_ID;
    }

    public String getPCUST_NME() {
        return PCUST_NME;
    }

    public void setPCUST_NME(String PCUST_NME) {
        this.PCUST_NME = PCUST_NME;
    }

    public String getPCUST_LINKED_SAVED_ACNT_NO() {
        return PCUST_LINKED_SAVED_ACNT_NO;
    }

    public void setPCUST_LINKED_SAVED_ACNT_NO(String PCUST_LINKED_SAVED_ACNT_NO) {
        this.PCUST_LINKED_SAVED_ACNT_NO = PCUST_LINKED_SAVED_ACNT_NO;
    }

    public String getPCS_LIAB_10072018() {
        return PCS_LIAB_10072018;
    }

    public void setPCS_LIAB_10072018(String PCS_LIAB_10072018) {
        this.PCS_LIAB_10072018 = PCS_LIAB_10072018;
    }

    public String getNeedtoPay() {
        return NeedtoPay;
    }

    public void setNeedtoPay(String needtoPay) {
        NeedtoPay = needtoPay;
    }

    public String getPayingAmount() {
        return PayingAmount;
    }

    public void setPayingAmount(String payingAmount) {
        PayingAmount = payingAmount;
    }

    public String getPAYMENT_DT() {
        return PAYMENT_DT;
    }

    public void setPAYMENT_DT(String PAYMENT_DT) {
        this.PAYMENT_DT = PAYMENT_DT;
    }

    public String getPPMT_Status() {
        return PPMT_Status;
    }

    public void setPPMT_Status(String PPMT_Status) {
        this.PPMT_Status = PPMT_Status;
    }
}
