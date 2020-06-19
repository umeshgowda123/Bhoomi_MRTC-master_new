package app.bmc.com.bhoomi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentDetail {

    @SerializedName("PaymentStatus")
    @Expose
    private String paymentStatus;

    @SerializedName("Season")
    @Expose
    private String season;

    @SerializedName("Year")
    @Expose
    private String year;

    @SerializedName("Amount")
    @Expose
    private String amount;

    @SerializedName("BankName")
    @Expose
    private String bankName;

    @SerializedName("CalamityType")
    @Expose
    private String calamityType;

    @SerializedName("DistrictCode")
    @Expose
    private String districtCode;

    @SerializedName("PaymentDate")
    @Expose
    private String paymentDate;

    @SerializedName("SlNo")
    @Expose
    private String slNo;

    @SerializedName("AC_HolderName")
    @Expose
    private String aCHolderName;

    @SerializedName("BankAccountNumber")
    @Expose
    private String bankAccountNumber;


    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCalamityType() {
        return calamityType;
    }

    public void setCalamityType(String calamityType) {
        this.calamityType = calamityType;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getSlNo() {
        return slNo;
    }

    public void setSlNo(String slNo) {
        this.slNo = slNo;
    }

    public String getaCHolderName() {
        return aCHolderName;
    }

    public void setaCHolderName(String aCHolderName) {
        this.aCHolderName = aCHolderName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }
}
