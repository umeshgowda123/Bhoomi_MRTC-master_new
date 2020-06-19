package app.bmc.com.bhoomi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PacsPaymentCertiRasonResult {

    @SerializedName("GetPayMentCertificateForPacsByRationCardNumberResult")
    @Expose
    private String getPayMentCertificateForPacsByRationCardNumberResult;

    public String getGetPayMentCertificateForPacsByRationCardNumberResult() {
        return getPayMentCertificateForPacsByRationCardNumberResult;
    }

    public void setGetPayMentCertificateForPacsByRationCardNumberResult(String getPayMentCertificateForPacsByRationCardNumberResult) {
        this.getPayMentCertificateForPacsByRationCardNumberResult = getPayMentCertificateForPacsByRationCardNumberResult;
    }


}
