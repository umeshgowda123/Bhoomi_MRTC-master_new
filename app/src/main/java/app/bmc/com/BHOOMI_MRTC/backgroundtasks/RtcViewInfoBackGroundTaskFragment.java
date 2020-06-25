package app.bmc.com.BHOOMI_MRTC.backgroundtasks;


import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.api.RtcViewInformationApi;
import app.bmc.com.BHOOMI_MRTC.model.Get_Rtc_Data_Result;
import app.bmc.com.BHOOMI_MRTC.model.Get_Surnoc_HissaResult;
import app.bmc.com.BHOOMI_MRTC.model.Get_ViewMutationStatusResult;
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
    BackgroundCallBackRtcViewInfo backgroundCallBack;

    /**
     * Called when a fragment is first attached to its activity.
     * onCreate(Bundle) will be called after this.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        backgroundCallBack = (BackgroundCallBackRtcViewInfo) activity;
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


    public void startBackgroundTask1(int district_id, int taluk_id, int hobli_id, int village_id, int surveyNo) {
        if (!isTaskExecuting) {
            getSurnocHissaResponse(String.valueOf(district_id), String.valueOf(taluk_id), String.valueOf(hobli_id), String.valueOf(village_id), String.valueOf(surveyNo));

        }
    }


    public void startBackgroundTask2(String district_id, String taluk_id, String hobli_id, String village_id, String land_no) {
        if (!isTaskExecuting) {
            getRtcResponse(district_id, taluk_id, hobli_id, village_id, land_no);


        }
    }

    public void startBackgroundTask3(int district_id, int taluk_id, int hobli_id, int village_id, int land_no) {
        if (!isTaskExecuting) {
            getMutationStatusResponse(String.valueOf(district_id), String.valueOf(taluk_id), String.valueOf(hobli_id), String.valueOf(village_id), String.valueOf(land_no));

        }
    }

    private void getRtcResponse(String district_id, String taluk_id, String hobli_id, String village_id, String land_no) {
        isTaskExecuting = true;
        if (backgroundCallBack != null)
            backgroundCallBack.onPreExecute2();

        Retrofit retrofit = RtcViewInfoClient.getClient("https://www.landrecords.karnataka.gov.in/RWSROR/GETBhoomiCode/");
        RtcViewInformationApi service = retrofit.create(RtcViewInformationApi.class);
        Call<Get_Rtc_Data_Result> get_rtc_data_resultCall = service.getRtcResponse(district_id, taluk_id, hobli_id, village_id, land_no);
        get_rtc_data_resultCall.enqueue(new Callback<Get_Rtc_Data_Result>() {
            @Override
            public void onResponse(Call<Get_Rtc_Data_Result> call, Response<Get_Rtc_Data_Result> response) {
                if (response.isSuccessful()) {
                    Get_Rtc_Data_Result get_rtc_data_result = response.body();
                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {
                        backgroundCallBack.onPostResponseSuccess2(get_rtc_data_result.getGetRtcDataResult());

                    }
                } else {
                    isTaskExecuting = false;

                    String errorResponse = response.message();
                    backgroundCallBack.onPostResponseError(errorResponse);
                }

            }


            @Override
            public void onFailure(Call<Get_Rtc_Data_Result> call, Throwable error) {
                isTaskExecuting = false;

                String errorResponse = error.getLocalizedMessage();

                backgroundCallBack.onPostResponseError(errorResponse);
            }
        });

    }

    private void getSurnocHissaResponse(String district_id, String taluk_id, String hobli_id, String village_id, String surveyNo) {
        isTaskExecuting = true;
        if (backgroundCallBack != null)
            backgroundCallBack.onPreExecute1();

        Retrofit retrofit = RtcViewInfoClient.getClient(getString(R.string.rtc_view_info_url));
        RtcViewInformationApi service = retrofit.create(RtcViewInformationApi.class);
        Call<Get_Surnoc_HissaResult> get_surnoc_hissaResultCall = service.getSurnocHissaResponse(district_id, taluk_id, hobli_id, village_id, surveyNo);
        get_surnoc_hissaResultCall.enqueue(new Callback<Get_Surnoc_HissaResult>() {
            @Override
            public void onResponse(Call<Get_Surnoc_HissaResult> call, Response<Get_Surnoc_HissaResult> response) {
                if (response.isSuccessful()) {
                    Get_Surnoc_HissaResult get_surnoc_hissaResult = response.body();
                    isTaskExecuting = false;
                    backgroundCallBack.onPostResponseSuccess1(get_surnoc_hissaResult.getGetSurnocHissaResult());
                } else {
                    isTaskExecuting = false;

                    String errorResponse = response.message();
                    backgroundCallBack.onPostResponseError(errorResponse);
                }

            }

            @Override
            public void onFailure(Call<Get_Surnoc_HissaResult> call, Throwable error) {
                isTaskExecuting = false;

                String errorResponse = error.getLocalizedMessage();

                backgroundCallBack.onPostResponseError(errorResponse);
            }


        });

    }

    private void getMutationStatusResponse(String district_id, String taluk_id, String hobli_id, String village_id, String land_no) {
        isTaskExecuting = true;
        if (backgroundCallBack != null)
            backgroundCallBack.onPreExecute3();

        Retrofit retrofit = RtcViewInfoClient.getClient("https://clws.karnataka.gov.in/Service4/BHOOMI/");
        RtcViewInformationApi service = retrofit.create(RtcViewInformationApi.class);
        Call<Get_ViewMutationStatusResult> get_rtc_data_resultCall = service.getMutationStatusResponse(Constants.REPORT_SERVICE_USER_NAME,Constants.REPORT_SERVICE_PASSWORD,Integer.valueOf(district_id),Integer.valueOf(taluk_id),Integer.valueOf(hobli_id),Integer.valueOf(village_id),Integer.valueOf(land_no));
        get_rtc_data_resultCall.enqueue(new Callback<Get_ViewMutationStatusResult>() {
            @Override
            public void onResponse(Call<Get_ViewMutationStatusResult> call, Response<Get_ViewMutationStatusResult> response) {
                if (response.isSuccessful()) {
                    Get_ViewMutationStatusResult viewMutationStatusResult = response.body();
                    //String get_rtc_data_result = response.body();
                    isTaskExecuting = false;
                    if (backgroundCallBack != null) {
                        backgroundCallBack.onPostResponseSuccess3(viewMutationStatusResult.getViewMutationStatusResult());
                    }
                } else {
                    isTaskExecuting = false;

                    String errorResponse = response.message();
                    backgroundCallBack.onPostResponseError(errorResponse);
                }

            }


            @Override
            public void onFailure(Call<Get_ViewMutationStatusResult> call, Throwable error) {
                isTaskExecuting = false;

                String errorResponse = error.getLocalizedMessage();
                Log.d("errorResponse",""+errorResponse);
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

    }
}
