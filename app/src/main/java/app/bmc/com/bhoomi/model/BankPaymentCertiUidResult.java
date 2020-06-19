package app.bmc.com.bhoomi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankPaymentCertiUidResult {


    @SerializedName("GetPayMentCertificateForBankByAadharNumberResult")
    @Expose
    private String getPayMentCertificateForBankByAadharNumberResult;

    public String getGetPayMentCertificateForBankByAadharNumberResult() {
        return getPayMentCertificateForBankByAadharNumberResult;
    }

    public void setGetPayMentCertificateForBankByAadharNumberResult(String getPayMentCertificateForBankByAadharNumberResult) {
        this.getPayMentCertificateForBankByAadharNumberResult = getPayMentCertificateForBankByAadharNumberResult;
    }


}
