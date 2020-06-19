package app.bmc.com.bhoomi.api;

import app.bmc.com.bhoomi.model.BankPaymentCertiFsdResult;
import app.bmc.com.bhoomi.model.BankPaymentCertiRasonResult;
import app.bmc.com.bhoomi.model.BankPaymentCertiUidResult;
import app.bmc.com.bhoomi.model.PacsPaymentCertiFsdResult;
import app.bmc.com.bhoomi.model.PacsPaymentCertiRasonResult;
import app.bmc.com.bhoomi.model.PacsPaymentCertiUidResult;
import app.bmc.com.bhoomi.model.PariharaIndividualDetailsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {


      @Headers({"content-type: text/json; charset=utf-8"})
      @GET("GetPayMentCertificateForPacsByAadharNumber/{aadhar_no}")
      Call<PacsPaymentCertiUidResult> getPacsCertificationPaymentDetails(@Path("aadhar_no") String aadharNumber);


    @Headers({"content-type: text/json; charset=utf-8"})
    @GET("GetPayMentCertificateForPacsByRationCardNumber/{rasoncard_no}")
    Call<PacsPaymentCertiRasonResult> getPacsCertificationPaymentByRasonCard(@Path("rasoncard_no") String rasoncardno);


    @Headers({"content-type: text/json; charset=utf-8"})
    @GET("GetPayMentCertificateForPacsByCustomerID_new/{fsd_id}")
    Call<PacsPaymentCertiFsdResult> getPacsCertificationPaymentByFsdid(@Path("fsd_id") String fsdid);


    // Methods for Bank Payment Certificate Service

    @Headers({"content-type: text/json; charset=utf-8"})
    @GET("GetPayMentCertificateForBankByAadharNumber/{aadhar_no}")
    Call<BankPaymentCertiUidResult> getBankCertificationPaymentDetails(@Path("aadhar_no") String aadharNumber);



    @Headers({"content-type: text/json; charset=utf-8"})
    @GET("GetPayMentCertificateForBankByRationCardNumber/{rasoncard_no}")
    Call<BankPaymentCertiRasonResult> getBankCertificationPaymentByRasonCard(@Path("rasoncard_no") String rasoncardno);


    @Headers({"content-type: text/json; charset=utf-8"})
    @GET("GetPayMentCertificateForBankByFsdIdNumber/{fsd_id}")
    Call<BankPaymentCertiFsdResult> getBankCertificationPaymentByFsdid(@Path("fsd_id") String fsdid);




}
