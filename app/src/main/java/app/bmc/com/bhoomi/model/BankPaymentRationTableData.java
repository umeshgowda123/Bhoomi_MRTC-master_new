package app.bmc.com.bhoomi.model;

public class BankPaymentRationTableData {


    private String PCUST_ID;
    private String CustomerName;
    private String LoanAccount;
    private String LoanType;
    private String MaxAmount;
    private String PayingAmount;
    private String ProrataAmount;
    private String PPMT_Status;
    private String PAYMENT_DT;

    public String getPCUST_ID() {
        return PCUST_ID;
    }

    public void setPCUST_ID(String PCUST_ID) {
        this.PCUST_ID = PCUST_ID;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getLoanAccount() {
        return LoanAccount;
    }

    public void setLoanAccount(String loanAccount) {
        LoanAccount = loanAccount;
    }

    public String getLoanType() {
        return LoanType;
    }

    public void setLoanType(String loanType) {
        LoanType = loanType;
    }

    public String getMaxAmount() {
        return MaxAmount;
    }

    public void setMaxAmount(String maxAmount) {
        MaxAmount = maxAmount;
    }

    public String getPayingAmount() {
        return PayingAmount;
    }

    public void setPayingAmount(String payingAmount) {
        PayingAmount = payingAmount;
    }

    public String getProrataAmount() {
        return ProrataAmount;
    }

    public void setProrataAmount(String prorataAmount) {
        ProrataAmount = prorataAmount;
    }

    public String getPPMT_Status() {
        return PPMT_Status;
    }

    public void setPPMT_Status(String PPMT_Status) {
        this.PPMT_Status = PPMT_Status;
    }

    public String getPAYMENT_DT() {
        return PAYMENT_DT;
    }

    public void setPAYMENT_DT(String PAYMENT_DT) {
        this.PAYMENT_DT = PAYMENT_DT;
    }
}
