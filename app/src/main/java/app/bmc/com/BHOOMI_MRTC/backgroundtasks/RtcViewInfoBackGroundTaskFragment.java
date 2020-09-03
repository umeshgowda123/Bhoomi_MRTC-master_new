package app.bmc.com.BHOOMI_MRTC.backgroundtasks;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

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

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This class defined to use web service calling when orientation take place.
 * this is purpose  for Rtc View Information services access .
 */
public class RtcViewInfoBackGroundTaskFragment extends Fragment {
    public static final String TAG_HEADLESS_FRAGMENT = "headless_fragment";
    public boolean isTaskExecuting = false;
    private BackgroundCallBackRtcViewInfo backgroundCallBack;

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


    public void startBackgroundTask1(int district_id, int taluk_id, int hobli_id, int village_id, int surveyNo, String url) {
        if (!isTaskExecuting) {
            getSurnocHissaResponse(String.valueOf(district_id), String.valueOf(taluk_id), String.valueOf(hobli_id), String.valueOf(village_id), String.valueOf(surveyNo), url);

        }
    }

    public void startBackgroundTask2(String district_id, String taluk_id, String hobli_id, String village_id, String land_no, String url) {
        if (!isTaskExecuting) {
            getRtcResponse(district_id, taluk_id, hobli_id, village_id, land_no, url);
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

    public void startBackgroundTaskCultivatorData(String district_id, String taluk_id, String hobli_id, String village_id, String land_no, String url) {
        if (!isTaskExecuting) {
            getCultivatorResponse(district_id, taluk_id, hobli_id, village_id, land_no, url);
        }
    }

    private void getCultivatorResponse(String district_id, String taluk_id, String hobli_id, String village_id, String land_no, String url) {
        isTaskExecuting = true;
        if (backgroundCallBack != null)
            backgroundCallBack.onPreExecute2();

        Retrofit retrofit = RtcViewInfoClient.getClient(url);
        RtcViewInformationApi service = retrofit.create(RtcViewInformationApi.class);
        Call<Get_Rtc_Data_Result> get_rtc_data_resultCall = service.getRtcCultivator(district_id, taluk_id, hobli_id, village_id, land_no);
        get_rtc_data_resultCall.enqueue(new Callback<Get_Rtc_Data_Result>() {
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

                    String errorResponse = response.message();
                    backgroundCallBack.onPostResponseErrorCultivator(errorResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Get_Rtc_Data_Result> call, @NonNull Throwable error) {
                isTaskExecuting = false;

                String errorResponse = error.getLocalizedMessage();

                backgroundCallBack.onPostResponseErrorCultivator(errorResponse);
            }
        });
    }

    private void getRtcResponse(String district_id, String taluk_id, String hobli_id, String village_id, String land_no, String url) {
        isTaskExecuting = true;
        if (backgroundCallBack != null)
            backgroundCallBack.onPreExecute2();

        Retrofit retrofit = RtcViewInfoClient.getClient(url);
        RtcViewInformationApi service = retrofit.create(RtcViewInformationApi.class);
        Call<Get_Rtc_Data_Result> get_rtc_data_resultCall = service.getRtcResponse(district_id, taluk_id, hobli_id, village_id, land_no);
        get_rtc_data_resultCall.enqueue(new Callback<Get_Rtc_Data_Result>() {
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

                    String errorResponse = response.message();
                    backgroundCallBack.onPostResponseError(errorResponse);
                }

            }

            @Override
            public void onFailure(@NonNull Call<Get_Rtc_Data_Result> call, @NonNull Throwable error) {
                isTaskExecuting = false;

                String errorResponse = error.getLocalizedMessage();

                backgroundCallBack.onPostResponseError(errorResponse);
            }
        });

    }

    private void getSurnocHissaResponse(String district_id, String taluk_id, String hobli_id, String village_id, String surveyNo, String url) {
        isTaskExecuting = true;
        if (backgroundCallBack != null)
            backgroundCallBack.onPreExecute1();

        Retrofit retrofit = RtcViewInfoClient.getClient(url);
        RtcViewInformationApi service = retrofit.create(RtcViewInformationApi.class);
        Call<Get_Surnoc_HissaResult> get_surnoc_hissaResultCall = service.getSurnocHissaResponse(district_id, taluk_id, hobli_id, village_id, surveyNo);
        get_surnoc_hissaResultCall.enqueue(new Callback<Get_Surnoc_HissaResult>() {
            @Override
            public void onResponse(@NonNull Call<Get_Surnoc_HissaResult> call, @NonNull Response<Get_Surnoc_HissaResult> response) {
                if (response.isSuccessful()) {
                    Get_Surnoc_HissaResult get_surnoc_hissaResult = response.body();
                    isTaskExecuting = false;
                    assert get_surnoc_hissaResult != null;
                    backgroundCallBack.onPostResponseSuccess1(get_surnoc_hissaResult.getGetSurnocHissaResult());
                } else {
                    isTaskExecuting = false;

                    String errorResponse = response.message();
                    backgroundCallBack.onPostResponseError(errorResponse);
                }

            }

            @Override
            public void onFailure(@NonNull Call<Get_Surnoc_HissaResult> call, @NonNull Throwable error) {
                isTaskExecuting = false;

                String errorResponse = error.getLocalizedMessage();

                backgroundCallBack.onPostResponseError(errorResponse);
            }


        });

    }

    private void getMutationStatusResponse(String district_id, String taluk_id, String hobli_id, String village_id, String land_no, String url) {
        isTaskExecuting = true;
        if (backgroundCallBack != null)
            backgroundCallBack.onPreExecute3();

        Retrofit retrofit = RtcViewInfoClient.getClient(url);
        RtcViewInformationApi service = retrofit.create(RtcViewInformationApi.class);
        Call<Get_ViewMutationStatusResult> get_rtc_data_resultCall = service.getMutationStatusResponse(Constants.REPORT_SERVICE_USER_NAME,Constants.REPORT_SERVICE_PASSWORD,Integer.parseInt(district_id),Integer.parseInt(taluk_id),Integer.parseInt(hobli_id),Integer.parseInt(village_id),Integer.parseInt(land_no));
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

                    String errorResponse = response.message();
                    backgroundCallBack.onPostResponseError(errorResponse);
                }

            }


            @Override
            public void onFailure(@NonNull Call<Get_ViewMutationStatusResult> call, @NonNull Throwable error) {
                isTaskExecuting = false;
                String errorResponse = error.getLocalizedMessage();
                backgroundCallBack.onPostResponseError(errorResponse);
            }
        });

    }

    private void getLandRestrictionResult(JsonObject input, String url){
        isTaskExecuting = true;
        if (backgroundCallBack != null)
            backgroundCallBack.onPreExecute4();

        Retrofit retrofit = RtcViewInfoClient.getClient(url);
        PariharaIndividualReportInteface service = retrofit.create(PariharaIndividualReportInteface.class);
        Call<PariharaIndividualDetailsResponse> get_rtc_data_resultCall = service.fnGetLandRestrictionResult(Constants.REPORT_SERVICE_USER_NAME,Constants.REPORT_SERVICE_PASSWORD, input);
        get_rtc_data_resultCall.enqueue(new Callback<PariharaIndividualDetailsResponse>() {
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

                    String errorResponse = response.message();
                    backgroundCallBack.onPostResponseError(errorResponse);
                }

            }


            @Override
            public void onFailure(@NonNull Call<PariharaIndividualDetailsResponse> call, @NonNull Throwable error) {
                isTaskExecuting = false;

                String errorResponse = error.getLocalizedMessage();
                backgroundCallBack.onPostResponseError(errorResponse);
            }
        });
    }

    public interface BackgroundCallBackRtcViewInfo {
        void onPreExecute1();

        void onPostResponseSuccess1(String data);

        void onPostResponseError(String data);

        void onPreExecute2();

        void onPostResponseSuccess2(String data);

        void onPreExecute3();

        void onPostResponseSuccess3(String data);

        void onPostResponseSuccess4(String data);

        void onPreExecute4();

        void onPostResponseErrorCultivator(String errorResponse);

        void onPostResponseSuccessCultivator(String gettcDataResult);
    }
}
