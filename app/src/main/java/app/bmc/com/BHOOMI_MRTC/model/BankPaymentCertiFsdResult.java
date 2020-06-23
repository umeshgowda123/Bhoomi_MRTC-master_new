package app.bmc.com.BHOOMI_MRTC.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankPaymentCertiFsdResult {


    @SerializedName("GetPayMentCertificateForBankByFsdIdNumberResult")
    @Expose
    private String getPayMentCertificateForBankByFsdIdNumberResult;

    public String getGetPayMentCertificateForBankByFsdIdNumberResult() {
        return getPayMentCertificateForBankByFsdIdNumberResult;
    }

    public void setGetPayMentCertificateForBankByFsdIdNumberResult(String getPayMentCertificateForBankByFsdIdNumberResult) {
        this.getPayMentCertificateForBankByFsdIdNumberResult = getPayMentCertificateForBankByFsdIdNumberResult;
    }


}
