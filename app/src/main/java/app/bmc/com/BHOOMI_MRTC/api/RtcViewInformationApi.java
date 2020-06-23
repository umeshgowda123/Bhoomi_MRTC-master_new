package app.bmc.com.BHOOMI_MRTC.api;

import app.bmc.com.BHOOMI_MRTC.model.Get_Rtc_Data_Result;
import app.bmc.com.BHOOMI_MRTC.model.Get_Surnoc_HissaResult;
import app.bmc.com.BHOOMI_MRTC.model.Get_ViewMutationStatusResult;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This interface defined to access web services using retrofit. here we calling rtc view information
 * services.
 */
public interface RtcViewInformationApi {

    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("Get_Surnoc_Hissa/{Bhm_dist_code},{Bhm_taluk_code},{Bhm_hobli_code},{village_code},{survey_no}")
    Call<Get_Surnoc_HissaResult> getSurnocHissaResponse(@Path("Bhm_dist_code") String Bhm_dist_code, @Path("Bhm_taluk_code") String Bhm_taluk_code, @Path("Bhm_hobli_code") String Bhm_hobli_code, @Path("village_code") String village_code, @Path("survey_no") String survey_no);

    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("get_rtc_data/{BHM_DIST_CODE},{BHM_TALUK_CODE},{BHM_HOBLI_CODE},{VILLAGE_CODE},{LAND_CODE}")
    Call<Get_Rtc_Data_Result> getRtcResponse(@Path("BHM_DIST_CODE") String BHM_DIST_CODE, @Path("BHM_TALUK_CODE") String BHM_TALUK_CODE, @Path("BHM_HOBLI_CODE") String BHM_HOBLI_CODE, @Path("VILLAGE_CODE") String VILLAGE_CODE, @Path("LAND_CODE") String LAND_CODE);


    @Headers({"content-type: text/json; charset=utf-8"})
    @POST("ViewMutationStatus/{pstrUserName}/{pStrPassword}/{District_Code}/{Taluk_Code}/{Hobli_Code}/{Village_Code}/{Land_Code}")
    Call<Get_ViewMutationStatusResult> getMutationStatusResponse(@Path("pstrUserName") String pstrUserName, @Path("pStrPassword") String pStrPassword, @Path("District_Code") int District_Code, @Path("Taluk_Code") int Taluk_Code, @Path("Hobli_Code") int Hobli_Code, @Path("Village_Code") int Village_Code, @Path("Land_Code") int Land_Code);
}
