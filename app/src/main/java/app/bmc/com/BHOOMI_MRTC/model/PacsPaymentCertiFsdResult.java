package app.bmc.com.BHOOMI_MRTC.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PacsPaymentCertiFsdResult {

    @SerializedName("GetPayMentCertificateForPacsByCustomerID_newResult")
    @Expose
    private String getPayMentCertificateForPacsByCustomerIDNewResult;

    public String getGetPayMentCertificateForPacsByCustomerIDNewResult() {
        return getPayMentCertificateForPacsByCustomerIDNewResult;
    }

    public void setGetPayMentCertificateForPacsByCustomerIDNewResult(String getPayMentCertificateForPacsByCustomerIDNewResult) {
        this.getPayMentCertificateForPacsByCustomerIDNewResult = getPayMentCertificateForPacsByCustomerIDNewResult;
    }

}
