package app.bmc.com.bhoomi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BenificaryDataVlgWise {


    @SerializedName("AadhaarNumber")
    @Expose
    private String AadhaarNumber;

    @SerializedName("BankName")
    @Expose
    private String BankName;

    @SerializedName("PaymentSTATUS")
    @Expose
    private String PaymentSTATUS;

    @SerializedName("PaymentDate")
    @Expose
    private String PaymentDate;

    @SerializedName("BeneficiaryName")
    @Expose
    private String BeneficiaryName;


    @SerializedName("BankAccountNumber")
    @Expose
    private String BankAccountNumber;


    @SerializedName("Amount")
    @Expose
    private String Amount;


    public String getAadhaarNumber() {
        return AadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        AadhaarNumber = aadhaarNumber;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getPaymentSTATUS() {
        return PaymentSTATUS;
    }

    public void setPaymentSTATUS(String paymentSTATUS) {
        PaymentSTATUS = paymentSTATUS;
    }

    public String getPaymentDate() {
        return PaymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        PaymentDate = paymentDate;
    }

    public String getBeneficiaryName() {
        return BeneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        BeneficiaryName = beneficiaryName;
    }

    public String getBankAccountNumber() {
        return BankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        BankAccountNumber = bankAccountNumber;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
