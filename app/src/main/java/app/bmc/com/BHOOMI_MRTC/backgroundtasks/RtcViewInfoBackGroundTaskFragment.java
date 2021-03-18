package app.bmc.com.BHOOMI_MRTC.backgroundtasks;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonObject;


import org.jetbrains.annotations.NotNull;

import app.bmc.com.BHOOMI_MRTC.api.PariharaIndividualReportInteface;
import app.bmc.com.BHOOMI_MRTC.api.RtcViewInformationApi;
import app.bmc.com.BHOOMI_MRTC.model.BHOOMI_API_Response;
import app.bmc.com.BHOOMI_MRTC.model.ClsReqLandID;
import app.bmc.com.BHOOMI_MRTC.model.Get_Surnoc_HissaRequest;
import app.bmc.com.BHOOMI_MRTC.model.TokenRes;
import app.bmc.com.BHOOMI_MRTC.model.ClsAppLgs;
import app.bmc.com.BHOOMI_MRTC.retrofit.PariharaIndividualreportClient;
import app.bmc.com.BHOOMI_MRTC.retrofit.AuthorizationClient;
import app.bmc.com.BHOOMI_MRTC.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RtcViewInfoBackGroundTaskFragment extends Fragment {
    public static final String TAG_HEADLESS_FRAGMENT = "headless_fragment";
    public boolean isTaskExecuting = false;
    private BackgroundCallBackRtcViewInfo backgroundCallBack;
    int count = 0;

    Call<BHOOMI_API_Response> get_rtc_data_resultCall;
    Call<BHOOMI_API_Response> get_surnoc_hissaResultCall;
    Call<BHOOMI_API_Response> getRtcResponse_call;
    Call<BHOOMI_API_Response> getLandRestrictionResultCall;
    Call<BHOOMI_API_Response> getCultivatorResponse_Call;
    Call<BHOOMI_API_Response> getBhoomiLandID_Call;
    Call<BHOOMI_API_Response> AppLgs_resultCall;

    PariharaIndividualReportInteface apiInterface;
    Call<TokenRes> callToken;

    /**
     * Called when a fragment is first attached to its activity.
     * onCreate(Bundle) will be called after this.
     */
    
//    @Override
//    public void onAttach(@NonNull Activity activity) {
//        super.onAttach(activity);
//        backgroundCallBack = (BackgroundCallBackRtcViewInfo) activity;
//    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);

        Activity activity;

        if (context instanceof Activity){
            activity=(Activity) context;
            backgroundCallBack = (BackgroundCallBackRtcViewInfo) activity;
        }

    }

    /**
     * Called to do initial creation of a fragment.
     * This is called after onAttach(Activity) and before onCreateView(LayoutInflater, ViewGroup, Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    /**
     * Called when the fragment is no longer attached to its activity. This is called after onDestroy().
     */
    @Override
    public void onDetach() {
        // TODO Auto-generated method stub
        super.onDetach();
        backgroundCallBack = null;
    }


    public void startBackgroundTask1(Get_Surnoc_HissaRequest get_surnoc_hissaRequest, String url, String token_type, String token) {
        if (!isTaskExecuting) {
            getSurnocHissaResponse(get_surnoc_hissaRequest, url, token_type, token);
        }
    }

    public void startBackgroundTask2(JsonObject input, String url,String token_type, String token) {
        if (!isTaskExecuting) {
            getRtcResponse(input, url, token_type, token);
        }
    }

    public void startBackgroundTask3(int district_id, int taluk_id, int hobli_id, int village_id, int land_no, String url, String token_type, String token) {
        if (!isTaskExecuting) {
                getMutationStatusResponse(String.valueOf(district_id), String.valueOf(taluk_id), String.valueOf(hobli_id), String.valueOf(village_id), String.valueOf(land_no), url,token_type,token);
        }
    }

    public void startBackgroundTask4(JsonObject input, String url,String token_type, String token) {
        if (!isTaskExecuting) {
            getLandRestrictionResult(input, url, token_type,token);
        }

    }

    public void startBackgroundTaskCultivatorData(JsonObject input, String url, String token_type, String token) {
        if (!isTaskExecuting) {
            getCultivatorResponse(input, url, token_type,token);
        }
    }

    public void startBackgroundTask_GetDetails_VilWise(JsonObject input, String url, String token_type, String token) {
        if (!isTaskExecuting) {
            GetDetails_VillageWise_JSON(input, url, token_type, token);
        }
    }

    public void startBackgroundTask_GenerateToken(String url){
        if (!isTaskExecuting) {
            GetToken(url);
        }
    }

    public void startBackgroundTask_GetBhoomiLandID(ClsReqLandID objClsReqLandID, String url, String token_type, String token){
        if (!isTaskExecuting) {
            GetBhoomiLandId(objClsReqLandID, url, token_type, token);
        }
    }

    public void startBackgroundTask_AppLgs(ClsAppLgs objClsAppLgs, String url, String token_type, String token) {
        if (!isTaskExecuting) {
            FnPutAppLgs(objClsAppLgs, url, token_type, token);
        }
    }

    private void GetToken(String url) {
        isTaskExecuting = true;
        if (backgroundCallBack != null)
            backgroundCallBack.onPreExecuteToken();

        apiInterface = PariharaIndividualreportClient.getClient(url).create(PariharaIndividualReportInteface.class);
        callToken = apiInterface.getToken(Constants.USERNAME_TOKEN,Constants.PASSWORD_TOKEN,Constants.GRANT_TYPE);
        callToken.enqueue(new Callback<TokenRes>() {
            @Override
            public void onResponse(@NotNull Call<TokenRes> call1, @NotNull Response<TokenRes> response) {
                String AccessToken;
                String TokenType;

                if (response.isSuccessful()) {
                    TokenRes result = response.body();
                    assert result != null;
                    AccessToken = result.getAccessToken();
                    TokenType = result.getTokenType();
                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {
                        backgroundCallBack.onPostResponseSuccessGetToken(TokenType,AccessToken);
                    }
                } else {
                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {
                        String errorResponse = response.message();
                        backgroundCallBack.onPostResponseError_Token(errorResponse);
                    }
                }
                }

                @Override
                public void onFailure(@NonNull Call<TokenRes> callToken, @NonNull Throwable error) {
                    isTaskExecuting = false;
                    error.printStackTrace();
                    if (backgroundCallBack != null) {
                        String errorResponse = error.getMessage();
                        backgroundCallBack.onPostResponseError_Token(errorResponse);
                    }
                }
            });
    }


    private void getSurnocHissaResponse(Get_Surnoc_HissaRequest get_surnoc_hissaRequest, String url, String token_type, String token) {
        isTaskExecuting = true;
        if (backgroundCallBack != null)
            backgroundCallBack.onPreExecute1();

        Retrofit retrofit = AuthorizationClient.getClient(url, token_type, token);
        RtcViewInformationApi service = retrofit.create(RtcViewInformationApi.class);
        get_surnoc_hissaResultCall = service.getSurnocHissaResponse(get_surnoc_hissaRequest);
        get_surnoc_hissaResultCall.enqueue(new Callback<BHOOMI_API_Response>() {
            @Override
            public void onResponse(@NonNull Call<BHOOMI_API_Response> call, @NonNull Response<BHOOMI_API_Response> response) {
                if (response.isSuccessful()) {
                    BHOOMI_API_Response get_surnoc_hissaResult = response.body();
                    assert get_surnoc_hissaResult != null;
                    String data = get_surnoc_hissaResult.getBhoomI_API_Response();

                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {
                        backgroundCallBack.onPostResponseSuccess1(data);
                    }
                } else {
                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {

                        String errorResponse = response.message();

                        backgroundCallBack.onPostResponseError_FORHISSA(errorResponse, count);
                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<BHOOMI_API_Response> call, @NonNull Throwable error) {
                isTaskExecuting = false;
                error.printStackTrace();
                if (backgroundCallBack != null) {

                    String errorResponse = error.getMessage();
                    Log.d("ERR_RES", "" + errorResponse);
                    backgroundCallBack.onPostResponseError_FORHISSA(errorResponse, count);
                }
            }
        });

    }
    public void terminateExecutionOfBackgroundTask1(){
        Log.d("Task1", "Entered");
//        isTaskExecuting = false;
//        if (backgroundCallBack != null) {
        if (get_surnoc_hissaResultCall != null && get_surnoc_hissaResultCall.isExecuted()) {
            get_surnoc_hissaResultCall.cancel();
//            }
        }
    }


    private void getRtcResponse(JsonObject input, String url, String token_type, String token) {
        isTaskExecuting = true;
        if (backgroundCallBack != null)
            backgroundCallBack.onPreExecute2();

        Retrofit retrofit = AuthorizationClient.getClient(url, token_type, token);
        RtcViewInformationApi service = retrofit.create(RtcViewInformationApi.class);
        getRtcResponse_call = service.getRtcResponse(input);
        getRtcResponse_call.enqueue(new Callback<BHOOMI_API_Response>() {
            @Override
            public void onResponse(@NonNull Call<BHOOMI_API_Response> call, @NonNull Response<BHOOMI_API_Response> response) {
                if (response.isSuccessful()) {
                    BHOOMI_API_Response get_rtc_data_result = response.body();
                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {
                        assert get_rtc_data_result != null;
                        backgroundCallBack.onPostResponseSuccess2(get_rtc_data_result.getBhoomI_API_Response());
                    }
                } else {
                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {
                        String errorResponse = response.message();
                        backgroundCallBack.onPostResponseError_Task2(errorResponse, count);
                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<BHOOMI_API_Response> call, @NonNull Throwable error) {
                isTaskExecuting = false;
                error.printStackTrace();
                if (backgroundCallBack != null) {
                    String errorResponse = error.getMessage();
                    Log.d("Err_msg", errorResponse + "");
                    backgroundCallBack.onPostResponseError_Task2(errorResponse, count);
                }
            }
        });

    }
    public void terminateExecutionOfBackgroundTask2(){
        Log.d("Task1", "Entered");
        if (getRtcResponse_call != null && getRtcResponse_call.isExecuted()) {
            getRtcResponse_call.cancel();
        }
    }


    private void getMutationStatusResponse(String district_id, String taluk_id, String hobli_id, String village_id, String land_no, String url, String token_type, String token) {
        isTaskExecuting = true;
        if (backgroundCallBack != null)
            backgroundCallBack.onPreExecute3();

        Retrofit retrofit = AuthorizationClient.getClient(url,token_type,token);
        RtcViewInformationApi service = retrofit.create(RtcViewInformationApi.class);
        get_rtc_data_resultCall = service.getMutationStatusResponse(Integer.parseInt(district_id),Integer.parseInt(taluk_id),Integer.parseInt(hobli_id),Integer.parseInt(village_id),Integer.parseInt(land_no));
        get_rtc_data_resultCall.enqueue(new Callback<BHOOMI_API_Response>() {
            @Override
            public void onResponse(@NonNull Call<BHOOMI_API_Response> call, @NonNull Response<BHOOMI_API_Response> response) {
                if (response.isSuccessful()) {
                    BHOOMI_API_Response viewMutationStatusResult = response.body();
                    //String get_rtc_data_result = response.body();
                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {
                        assert viewMutationStatusResult != null;
                        backgroundCallBack.onPostResponseSuccess3(viewMutationStatusResult.getBhoomI_API_Response());
                    }
                } else {
                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {
                        String errorResponse = response.message();
                        backgroundCallBack.onPostResponseError_Task3(errorResponse);
                    }
                }

            }


            @Override
            public void onFailure(@NonNull Call<BHOOMI_API_Response> call, @NonNull Throwable error) {
                isTaskExecuting = false;
                error.printStackTrace();
                if (backgroundCallBack != null) {
                    String errorResponse = error.getMessage();
                    Log.d("Err_msg", errorResponse + "");
                    backgroundCallBack.onPostResponseError_Task3(errorResponse);
                }
            }
        });

    }

    public void terminateExecutionOfBackgroundTask3(){
        Log.d("Task3", "Entered");
            if (get_rtc_data_resultCall != null && get_rtc_data_resultCall.isExecuted()) {
                get_rtc_data_resultCall.cancel();
        }
    }

    public void GetBhoomiLandId(ClsReqLandID objClsReqLandID, String url, String token_type, String token){
        isTaskExecuting = true;
        if (backgroundCallBack != null)
            backgroundCallBack.onPreExecuteGetBhoomiLandId();

        Retrofit retrofit = AuthorizationClient.getClient(url,token_type,token);
        PariharaIndividualReportInteface service = retrofit.create(PariharaIndividualReportInteface.class);
        getBhoomiLandID_Call = service.FnGet_BhoomiLandId(objClsReqLandID);
        getBhoomiLandID_Call.enqueue(new Callback<BHOOMI_API_Response>() {
            @Override
            public void onResponse(@NonNull Call<BHOOMI_API_Response> call, @NonNull Response<BHOOMI_API_Response> response) {
                if (response.isSuccessful()) {
                    BHOOMI_API_Response viewResult = response.body();
                    //String get_rtc_data_result = response.body();
                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {
                        assert viewResult != null;
                        backgroundCallBack.onPostResponseSuccessGetBhoomiLandID(viewResult.getBhoomI_API_Response());
                    }
                } else {
                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {
                        String errorResponse = response.message();
                        backgroundCallBack.onPostResponseError_BhoomiLandID(errorResponse);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BHOOMI_API_Response> call, @NonNull Throwable t) {
                isTaskExecuting = false;
                t.printStackTrace();
                if (backgroundCallBack != null) {
                    String errorResponse = t.getMessage();
                    Log.d("Err_msg", errorResponse + "");
                    backgroundCallBack.onPostResponseError_BhoomiLandID(errorResponse);
                }
            }
        });
    }

    public void terminateExecutionOfBackTaskGetBhoomiLandID(){
        if (getBhoomiLandID_Call != null && getBhoomiLandID_Call.isExecuted()) {
            getBhoomiLandID_Call.cancel();
        }
    }

    private void getLandRestrictionResult(JsonObject input, String url, String token_type, String token){
        isTaskExecuting = true;
        if (backgroundCallBack != null)
            backgroundCallBack.onPreExecute4();

        Retrofit retrofit = AuthorizationClient.getClient(url,token_type,token);
        PariharaIndividualReportInteface service = retrofit.create(PariharaIndividualReportInteface.class);
        getLandRestrictionResultCall = service.fnGetLandRestrictionResult(input);
        getLandRestrictionResultCall.enqueue(new Callback<BHOOMI_API_Response>() {
            @Override
            public void onResponse(@NonNull Call<BHOOMI_API_Response> call, @NonNull Response<BHOOMI_API_Response> response) {
                if (response.isSuccessful()) {
                    BHOOMI_API_Response viewResult = response.body();
                    //String get_rtc_data_result = response.body();
                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {
                        assert viewResult != null;
                        backgroundCallBack.onPostResponseSuccess4(viewResult.getBhoomI_API_Response());
                    }
                } else {
                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {
                        String errorResponse = response.message();
                        backgroundCallBack.onPostResponseError_Task4(errorResponse);
                    }
                }

            }


            @Override
            public void onFailure(@NonNull Call<BHOOMI_API_Response> call, @NonNull Throwable error) {
                isTaskExecuting = false;
                error.printStackTrace();
                if (backgroundCallBack != null) {
                    String errorResponse = error.getMessage();
                    Log.d("Err_msg", errorResponse + "");
                    backgroundCallBack.onPostResponseError_Task4(errorResponse);
                }
            }
        });
    }
    public void terminateExecutionOfBackgroundTask4(){
        Log.d("Task3", "Entered");
        if (getLandRestrictionResultCall != null && getLandRestrictionResultCall.isExecuted()) {
            getLandRestrictionResultCall.cancel();
        }
    }


    private void getCultivatorResponse(JsonObject input, String url, String token_type, String token) {
        isTaskExecuting = true;
        if (backgroundCallBack != null)
            backgroundCallBack.onPreExecute2();

        Retrofit retrofit = AuthorizationClient.getClient(url,token_type,token);
        RtcViewInformationApi service = retrofit.create(RtcViewInformationApi.class);
        getCultivatorResponse_Call = service.getRtcCultivator(input);
        getCultivatorResponse_Call.enqueue(new Callback<BHOOMI_API_Response>() {
            @Override
            public void onResponse(@NonNull Call<BHOOMI_API_Response> call, @NonNull Response<BHOOMI_API_Response> response) {
                if (response.isSuccessful()) {
                    BHOOMI_API_Response get_rtc_data_result = response.body();
                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {
                        assert get_rtc_data_result != null;
                        backgroundCallBack.onPostResponseSuccessCultivator(get_rtc_data_result.getBhoomI_API_Response());
                    }
                } else {
                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {
                        String errorResponse = response.message();
                        backgroundCallBack.onPostResponseErrorCultivator(errorResponse, count);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BHOOMI_API_Response> call, @NonNull Throwable error) {
                isTaskExecuting = false;
                error.printStackTrace();
                if (backgroundCallBack != null) {
                    String errorResponse = error.getLocalizedMessage();
                    backgroundCallBack.onPostResponseErrorCultivator(errorResponse, count);
                }
            }
        });
    }

    public void terminateExecutionOfGetCultivatorResponse(){
        Log.d("Task1", "Entered");
        if (getCultivatorResponse_Call != null && getCultivatorResponse_Call.isExecuted()) {
            getCultivatorResponse_Call.cancel();
        }
    }

    private void GetDetails_VillageWise_JSON(JsonObject input, String url,  String token_type, String token){
        isTaskExecuting = true;
        if (backgroundCallBack != null)
            backgroundCallBack.onPreExecute5();

        Retrofit retrofit = AuthorizationClient.getClient(url, token_type, token);
        RtcViewInformationApi service = retrofit.create(RtcViewInformationApi.class);
        getLandRestrictionResultCall = service.GetDetails_VillageWise_JSON(input);
        getLandRestrictionResultCall.enqueue(new Callback<BHOOMI_API_Response>() {
            @Override
            public void onResponse(@NonNull Call<BHOOMI_API_Response> call, @NonNull Response<BHOOMI_API_Response> response) {
                if (response.isSuccessful()) {
                    BHOOMI_API_Response get_data_result = response.body();
                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {
                        assert get_data_result != null;
                        backgroundCallBack.onPostResponseSuccess_GetDetails_VilWise(get_data_result.getBhoomI_API_Response());
                    }
                } else {
                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {
                        String errorResponse = response.message();
                        backgroundCallBack.onPostResponseError_GetDetails_VilWise(errorResponse);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BHOOMI_API_Response> call, @NonNull Throwable error) {
                isTaskExecuting = false;
                error.printStackTrace();
                if (backgroundCallBack != null) {
                    String errorResponse = error.getLocalizedMessage();
                    backgroundCallBack.onPostResponseError_GetDetails_VilWise(errorResponse);
                }
            }
        });
    }

    public void terminateExecutionOfGetDetails_VillageWise_JSONResponse(){
        if (getLandRestrictionResultCall != null && getLandRestrictionResultCall.isExecuted()) {
            getLandRestrictionResultCall.cancel();
        }
    }

    private void FnPutAppLgs(ClsAppLgs objClsAppLgs, String url, String token_type, String token){
        isTaskExecuting = true;
        if (backgroundCallBack != null)
            backgroundCallBack.onPreExecute_AppLgs();

        Retrofit retrofit = AuthorizationClient.getClient(url, token_type, token);
        PariharaIndividualReportInteface service = retrofit.create(PariharaIndividualReportInteface.class);
        AppLgs_resultCall = service.FnAppLgs(objClsAppLgs);
        AppLgs_resultCall.enqueue(new Callback<BHOOMI_API_Response>() {
            @Override
            public void onResponse(@NonNull Call<BHOOMI_API_Response> call, @NonNull Response<BHOOMI_API_Response> response) {
                if (response.isSuccessful()) {
                    BHOOMI_API_Response bhoomi_api_response = response.body();
                    assert bhoomi_api_response != null;
                    String data = bhoomi_api_response.getBhoomI_API_Response();
                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {
                        backgroundCallBack.onPostResponseSuccess_AppLgs(data);
                    }
                } else {
                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {
                        String errorResponse = response.message();
                        backgroundCallBack.onPostResponseError_AppLgs(errorResponse);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BHOOMI_API_Response> call, @NonNull Throwable t) {
                isTaskExecuting = false;
                t.printStackTrace();
                if (backgroundCallBack != null) {
                    String errorResponse = t.getMessage();
                    backgroundCallBack.onPostResponseError_AppLgs(errorResponse);
                }
            }
        });
    }

    public interface BackgroundCallBackRtcViewInfo {

        void onPreExecute1();
        void onPostResponseSuccess1(String data);
        void onPostResponseError_FORHISSA(String data, int count);


        void onPreExecute2();
        void onPostResponseSuccess2(String data);
        void onPostResponseError_Task2(String data, int count);



        void onPreExecute3();
        void onPostResponseSuccess3(String data);
        void onPostResponseError_Task3(String data);



        void onPreExecute4();
        void onPostResponseSuccess4(String data);
        void onPostResponseError_Task4(String data);


        void onPostResponseSuccessCultivator(String gettcDataResult);
        void onPostResponseErrorCultivator(String errorResponse, int count);

        void onPreExecute5();
        void onPostResponseSuccess_GetDetails_VilWise(String data);
        void onPostResponseError_GetDetails_VilWise(String data);

        void onPreExecuteToken();
        void onPostResponseSuccessGetToken(String TokenType, String AccessToken );
        void onPostResponseError_Token(String errorResponse);

        void onPreExecuteGetBhoomiLandId();
        void onPostResponseSuccessGetBhoomiLandID(String data);
        void onPostResponseError_BhoomiLandID(String data);

        void onPreExecute_AppLgs();
        void onPostResponseSuccess_AppLgs(String data);
        void onPostResponseError_AppLgs(String data);

    }
}
