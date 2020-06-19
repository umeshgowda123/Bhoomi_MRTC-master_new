package app.bmc.com.bhoomi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankPaymentCertiRasonResult {


    @SerializedName("GetPayMentCertificateForBankByRationCardNumberResult")
    @Expose
    private String getPayMentCertificateForBankByRationCardNumberResult;

    public String getGetPayMentCertificateForBankByRationCardNumberResult() {
        return getPayMentCertificateForBankByRationCardNumberResult;
    }

    public void setGetPayMentCertificateForBankByRationCardNumberResult(String getPayMentCertificateForBankByRationCardNumberResult) {
        this.getPayMentCertificateForBankByRationCardNumberResult = getPayMentCertificateForBankByRationCardNumberResult;
    }
}
