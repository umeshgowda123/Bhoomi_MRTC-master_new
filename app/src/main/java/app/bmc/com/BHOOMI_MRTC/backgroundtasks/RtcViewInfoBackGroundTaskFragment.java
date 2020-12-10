package app.bmc.com.BHOOMI_MRTC.backgroundtasks;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.api.PariharaIndividualReportInteface;
import app.bmc.com.BHOOMI_MRTC.api.RtcViewInformationApi;
import app.bmc.com.BHOOMI_MRTC.model.Get_Rtc_Data_Result;
import app.bmc.com.BHOOMI_MRTC.model.Get_Surnoc_HissaResult;
import app.bmc.com.BHOOMI_MRTC.model.Get_ViewMutationStatusResult;
import app.bmc.com.BHOOMI_MRTC.model.PariharaIndividualDetailsResponse;
import app.bmc.com.BHOOMI_MRTC.retrofit.RtcViewInfoClient;
import app.bmc.com.BHOOMI_MRTC.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RtcViewInfoBackGroundTaskFragment extends Fragment {
    public static final String TAG_HEADLESS_FRAGMENT = "headless_fragment";
    public boolean isTaskExecuting = false;
    private BackgroundCallBackRtcViewInfo backgroundCallBack;
    private int count = 0;

    Call<Get_ViewMutationStatusResult> get_rtc_data_resultCall;
    Call<Get_Surnoc_HissaResult> get_surnoc_hissaResultCall;
    Call<Get_Rtc_Data_Result> getRtcResponse_call;
    Call<PariharaIndividualDetailsResponse> getLandRestrictionResultCall;
    Call<Get_Rtc_Data_Result> getCultivatorResponse_Call;

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


    public void startBackgroundTask1(JsonObject jsonObject, String url) {
        if (!isTaskExecuting) {
            getSurnocHissaResponse(jsonObject, url);
        }
    }

    public void startBackgroundTask2(JsonObject input, String url) {
        if (!isTaskExecuting) {
            getRtcResponse(input, url);
        }
    }

    public void startBackgroundTask3(int district_id, int taluk_id, int hobli_id, int village_id, int land_no, String url) {
        if (!isTaskExecuting) {
                getMutationStatusResponse(String.valueOf(district_id), String.valueOf(taluk_id), String.valueOf(hobli_id), String.valueOf(village_id), String.valueOf(land_no), url);
        }
    }

    public void startBackgroundTask4(JsonObject input, String url) {
        if (!isTaskExecuting) {
            getLandRestrictionResult(input, url);
        }

    }

    public void startBackgroundTaskCultivatorData(JsonObject input, String url) {
        if (!isTaskExecuting) {
            getCultivatorResponse(input, url);
        }
    }

    public void startBackgroundTask_GetDetails_VilWise(JsonObject input, String url) {
        if (!isTaskExecuting) {
            GetDetails_VillageWise_JSON(input, url);
        }
    }


    private void getSurnocHissaResponse(JsonObject jsonObject, String url) {
        isTaskExecuting = true;
        if (backgroundCallBack != null)
            backgroundCallBack.onPreExecute1();

        Retrofit retrofit = RtcViewInfoClient.getClient(url);
        RtcViewInformationApi service = retrofit.create(RtcViewInformationApi.class);
        get_surnoc_hissaResultCall = service.getSurnocHissaResponse(Constants.CLWS_REST_SERVICE_USER_NAME,Constants.CLWS_REST_SERVICE_PASSWORD, jsonObject);
        get_surnoc_hissaResultCall.enqueue(new Callback<Get_Surnoc_HissaResult>() {
            @Override
            public void onResponse(@NonNull Call<Get_Surnoc_HissaResult> call, @NonNull Response<Get_Surnoc_HissaResult> response) {
                if (response.isSuccessful()) {
                    Get_Surnoc_HissaResult get_surnoc_hissaResult = response.body();
                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {
                        assert get_surnoc_hissaResult != null;
                        backgroundCallBack.onPostResponseSuccess1(get_surnoc_hissaResult.getGetSurnocHissaResult());
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
            public void onFailure(@NonNull Call<Get_Surnoc_HissaResult> call, @NonNull Throwable error) {
                isTaskExecuting = false;
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


    private void getRtcResponse(JsonObject input, String url) {
        isTaskExecuting = true;
        if (backgroundCallBack != null)
            backgroundCallBack.onPreExecute2();

        Retrofit retrofit = RtcViewInfoClient.getClient(url);
        RtcViewInformationApi service = retrofit.create(RtcViewInformationApi.class);
        getRtcResponse_call = service.getRtcResponse(Constants.CLWS_REST_SERVICE_USER_NAME,Constants.CLWS_REST_SERVICE_PASSWORD, input);
        getRtcResponse_call.enqueue(new Callback<Get_Rtc_Data_Result>() {
            @Override
            public void onResponse(@NonNull Call<Get_Rtc_Data_Result> call, @NonNull Response<Get_Rtc_Data_Result> response) {
                if (response.isSuccessful()) {
                    Get_Rtc_Data_Result get_rtc_data_result = response.body();
                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {
                        assert get_rtc_data_result != null;
                        backgroundCallBack.onPostResponseSuccess2(get_rtc_data_result.getGetRtcDataResult());
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
            public void onFailure(@NonNull Call<Get_Rtc_Data_Result> call, @NonNull Throwable error) {
                isTaskExecuting = false;
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


    private void getMutationStatusResponse(String district_id, String taluk_id, String hobli_id, String village_id, String land_no, String url) {
        isTaskExecuting = true;
        if (backgroundCallBack != null)
            backgroundCallBack.onPreExecute3();

        Retrofit retrofit = RtcViewInfoClient.getClient(url);
        RtcViewInformationApi service = retrofit.create(RtcViewInformationApi.class);
        get_rtc_data_resultCall = service.getMutationStatusResponse(Constants.CLWS_REST_SERVICE_USER_NAME,Constants.CLWS_REST_SERVICE_PASSWORD,Integer.parseInt(district_id),Integer.parseInt(taluk_id),Integer.parseInt(hobli_id),Integer.parseInt(village_id),Integer.parseInt(land_no));
        get_rtc_data_resultCall.enqueue(new Callback<Get_ViewMutationStatusResult>() {
            @Override
            public void onResponse(@NonNull Call<Get_ViewMutationStatusResult> call, @NonNull Response<Get_ViewMutationStatusResult> response) {
                if (response.isSuccessful()) {
                    Get_ViewMutationStatusResult viewMutationStatusResult = response.body();
                    //String get_rtc_data_result = response.body();
                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {
                        assert viewMutationStatusResult != null;
                        backgroundCallBack.onPostResponseSuccess3(viewMutationStatusResult.getViewMutationStatusResult());
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
            public void onFailure(@NonNull Call<Get_ViewMutationStatusResult> call, @NonNull Throwable error) {
                isTaskExecuting = false;
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

    private void getLandRestrictionResult(JsonObject input, String url){
        isTaskExecuting = true;
        if (backgroundCallBack != null)
            backgroundCallBack.onPreExecute4();

        Retrofit retrofit = RtcViewInfoClient.getClient(url);
        PariharaIndividualReportInteface service = retrofit.create(PariharaIndividualReportInteface.class);
        getLandRestrictionResultCall = service.fnGetLandRestrictionResult(Constants.CLWS_REST_SERVICE_USER_NAME,Constants.CLWS_REST_SERVICE_PASSWORD, input);
        getLandRestrictionResultCall.enqueue(new Callback<PariharaIndividualDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<PariharaIndividualDetailsResponse> call, @NonNull Response<PariharaIndividualDetailsResponse> response) {
                if (response.isSuccessful()) {
                    PariharaIndividualDetailsResponse viewResult = response.body();
                    //String get_rtc_data_result = response.body();
                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {
                        assert viewResult != null;
                        backgroundCallBack.onPostResponseSuccess4(viewResult.getGetLandRestrictionResult());
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
            public void onFailure(@NonNull Call<PariharaIndividualDetailsResponse> call, @NonNull Throwable error) {
                isTaskExecuting = false;
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


    private void getCultivatorResponse(JsonObject input, String url) {
        isTaskExecuting = true;
        if (backgroundCallBack != null)
            backgroundCallBack.onPreExecute2();

        Retrofit retrofit = RtcViewInfoClient.getClient(url);
        RtcViewInformationApi service = retrofit.create(RtcViewInformationApi.class);
        getCultivatorResponse_Call = service.getRtcCultivator(Constants.CLWS_REST_SERVICE_USER_NAME, Constants.CLWS_REST_SERVICE_PASSWORD, input);
        getCultivatorResponse_Call.enqueue(new Callback<Get_Rtc_Data_Result>() {
            @Override
            public void onResponse(@NonNull Call<Get_Rtc_Data_Result> call, @NonNull Response<Get_Rtc_Data_Result> response) {
                if (response.isSuccessful()) {
                    Get_Rtc_Data_Result get_rtc_data_result = response.body();
                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {
                        assert get_rtc_data_result != null;
                        backgroundCallBack.onPostResponseSuccessCultivator(get_rtc_data_result.getGettcDataResult());
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
            public void onFailure(@NonNull Call<Get_Rtc_Data_Result> call, @NonNull Throwable error) {
                isTaskExecuting = false;
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

    private void GetDetails_VillageWise_JSON(JsonObject input, String url){
        isTaskExecuting = true;
        if (backgroundCallBack != null)
            backgroundCallBack.onPreExecute5();

        Retrofit retrofit = RtcViewInfoClient.getClient(url);
        RtcViewInformationApi service = retrofit.create(RtcViewInformationApi.class);
        getLandRestrictionResultCall = service.GetDetails_VillageWise_JSON(Constants.CLWS_REST_SERVICE_USER_NAME, Constants.CLWS_REST_SERVICE_PASSWORD, input);
        getLandRestrictionResultCall.enqueue(new Callback<PariharaIndividualDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<PariharaIndividualDetailsResponse> call, @NonNull Response<PariharaIndividualDetailsResponse> response) {
                if (response.isSuccessful()) {
                    PariharaIndividualDetailsResponse get_data_result = response.body();
                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {
                        assert get_data_result != null;
                        backgroundCallBack.onPostResponseSuccess_GetDetails_VilWise(get_data_result.getDetails_VillageWise_JSONResult());
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
            public void onFailure(@NonNull Call<PariharaIndividualDetailsResponse> call, @NonNull Throwable error) {
                isTaskExecuting = false;
                if (backgroundCallBack != null) {
                    String errorResponse = error.getLocalizedMessage();
                    backgroundCallBack.onPostResponseError_GetDetails_VilWise(errorResponse);
                }
            }
        });
    }

    public void terminateExecutionOfGetDetails_VillageWise_JSONResponse(){
        Log.d("Task1", "Entered");
        if (getLandRestrictionResultCall != null && getLandRestrictionResultCall.isExecuted()) {
            getLandRestrictionResultCall.cancel();
        }
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

    }
}
