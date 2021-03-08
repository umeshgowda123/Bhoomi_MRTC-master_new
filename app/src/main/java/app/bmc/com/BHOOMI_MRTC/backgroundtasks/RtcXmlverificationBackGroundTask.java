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
import app.bmc.com.BHOOMI_MRTC.model.BHOOMI_API_Response;
import app.bmc.com.BHOOMI_MRTC.retrofit.AuthorizationClient;
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
    public void startBackgroundTask(JsonObject jsonObject, String token_type, String token) {
        if (!isTaskExecuting) {
            getRtcVerificationResponse(jsonObject, token_type, token);
        }
    }

    private void getRtcVerificationResponse(JsonObject jsonObject, String token_type, String token) {
        try {
            isTaskExecuting = true;
            if (backgroundCallBack != null)
                backgroundCallBack.onPreExecute1();
            Retrofit retrofit = AuthorizationClient.getClient(getString(R.string.rest_service_url), token_type, token);
            RtcXmlVerificationApi rtcXmlVerificationApi = retrofit.create(RtcXmlVerificationApi.class);
            Call<BHOOMI_API_Response> stringCall = rtcXmlVerificationApi.getStringResponse(jsonObject);
            stringCall.enqueue(new Callback<BHOOMI_API_Response>() {
                @Override
                public void onResponse(@NonNull Call<BHOOMI_API_Response> call, @NonNull Response<BHOOMI_API_Response> response) {
                    Log.d("response : ",response.body()+"");
                    if (response.isSuccessful()) {
                        BHOOMI_API_Response getrtcxmldataResult = response.body();
                        assert getrtcxmldataResult != null;
                        String data = getrtcxmldataResult.getBhoomI_API_Response();

                        isTaskExecuting = false;
                        backgroundCallBack.onPostResponseSuccess1(data);


                    } else {
                        isTaskExecuting = false;

                        String errorResponse = response.message();
                        backgroundCallBack.onPostResponseError(errorResponse);
                    }


                }

                @Override
                public void onFailure(@NonNull Call<BHOOMI_API_Response> call, @NonNull Throwable error) {
                    isTaskExecuting = false;
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
