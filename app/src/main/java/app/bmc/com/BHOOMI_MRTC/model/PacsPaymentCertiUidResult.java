package app.bmc.com.BHOOMI_MRTC.model;

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
