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
import app.bmc.com.BHOOMI_MRTC.api.RtcXmlVerificationApi;
import app.bmc.com.BHOOMI_MRTC.model.GETRTCXMLDATAResult;
import app.bmc.com.BHOOMI_MRTC.retrofit.RtcVerificationClient;
import app.bmc.com.BHOOMI_MRTC.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RtcXmlverificationBackGroundTask extends Fragment {
    public static final String TAG_HEADLESS_FRAGMENT = "headless_fragment";
    public boolean isTaskExecuting = false;
    private BackgroundCallBackRtcXmlVerification backgroundCallBack;

    /**
     * Called when a fragment is first attached to its activity.
     * onCreate(Bundle) will be called after this.
     */
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        backgroundCallBack = (BackgroundCallBackRtcXmlVerification) activity;
//    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);

        Activity activity;

        if (context instanceof Activity){
            activity=(Activity) context;
            backgroundCallBack = (BackgroundCallBackRtcXmlVerification) activity;
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


//    public void startBackgroundTask(String referenceNo) {
//        if (!isTaskExecuting) {
//            getRtcVerificationResponse(referenceNo);
//        }
//    }
    public void startBackgroundTask(JsonObject jsonObject) {
        if (!isTaskExecuting) {
            getRtcVerificationResponse(jsonObject);
        }
    }

    private void getRtcVerificationResponse(JsonObject jsonObject) {
        try {
            isTaskExecuting = true;
            if (backgroundCallBack != null)
                backgroundCallBack.onPreExecute1();
            Retrofit retrofit = RtcVerificationClient.getClient(getString(R.string.rest_service_url));
            RtcXmlVerificationApi rtcXmlVerificationApi = retrofit.create(RtcXmlVerificationApi.class);
            Call<GETRTCXMLDATAResult> stringCall = rtcXmlVerificationApi.getStringResponse(Constants.CLWS_REST_SERVICE_USER_NAME,Constants.CLWS_REST_SERVICE_PASSWORD,jsonObject);
            stringCall.enqueue(new Callback<GETRTCXMLDATAResult>() {
                @Override
                public void onResponse(@NonNull Call<GETRTCXMLDATAResult> call, @NonNull Response<GETRTCXMLDATAResult> response) {
                    if (response.isSuccessful()) {
                        GETRTCXMLDATAResult getrtcxmldataResult = response.body();
                        isTaskExecuting = false;
                        assert getrtcxmldataResult != null;
                        backgroundCallBack.onPostResponseSuccess1(getrtcxmldataResult.getGETRTCXMLDATAResult());


                    } else {
                        isTaskExecuting = false;

                        String errorResponse = response.message();
                        backgroundCallBack.onPostResponseError(errorResponse);
                    }


                }

                @Override
                public void onFailure(@NonNull Call<GETRTCXMLDATAResult> call, @NonNull Throwable error) {
                    isTaskExecuting = false;
                    assert error != null;
                    String errorResponse = error.getLocalizedMessage();
                    Log.d("errorResponse : ", errorResponse+"");
                    backgroundCallBack.onPostResponseError(errorResponse);


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface BackgroundCallBackRtcXmlVerification {
        void onPreExecute1();

        void onPostResponseSuccess1(String data);

        void onPostResponseError(String data);


    }
}
