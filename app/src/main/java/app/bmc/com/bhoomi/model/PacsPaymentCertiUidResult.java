package app.bmc.com.bhoomi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PacsPaymentCertiUidResult {

    @SerializedName("GetPayMentCertificateForPacsByAadharNumberResult")
    @Expose
    private String getPayMentCertificateForPacsByAadharNumberResult;

    public String getGetPayMentCertificateForPacsByAadharNumberResult() {
        return getPayMentCertificateForPacsByAadharNumberResult;
    }

    public void setGetPayMentCertificateForPacsByAadharNumberResult(String getPayMentCertificateForPacsByAadharNumberResult) {
        this.getPayMentCertificateForPacsByAadharNumberResult = getPayMentCertificateForPacsByAadharNumberResult;
    }
}
