package app.bmc.com.BHOOMI_MRTC.backgroundtasks;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.api.RtcXmlVerificationApi;
import app.bmc.com.BHOOMI_MRTC.model.GETRTCXMLDATAResult;
import app.bmc.com.BHOOMI_MRTC.retrofit.RtcVerificationClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This class defined to use web service calling when orientation take place.
 * this is purpose  for Rtc Xml Verification services access .
 */
public class RtcXmlverificationBackGroundTask extends Fragment {
    public static final String TAG_HEADLESS_FRAGMENT = "headless_fragment";
    public boolean isTaskExecuting = false;
    BackgroundCallBackRtcXmlVerification backgroundCallBack;

    /**
     * Called when a fragment is first attached to its activity.
     * onCreate(Bundle) will be called after this.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        backgroundCallBack = (BackgroundCallBackRtcXmlVerification) activity;
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


    public void startBackgroundTask(String referenceNo) {
        if (!isTaskExecuting) {
            getRtcVerificationResponse(referenceNo);
        }
    }

    private void getRtcVerificationResponse(String referenceNo) {
        try {
            isTaskExecuting = true;
            if (backgroundCallBack != null)
                backgroundCallBack.onPreExecute1();
            String passcode = getString(R.string.passcode);
            String saltkey = getString(R.string.saltkey);
            Retrofit retrofit = RtcVerificationClient.getClient(getString(R.string.rtc_xml_verification_url));

            RtcXmlVerificationApi rtcXmlVerificationApi = retrofit.create(RtcXmlVerificationApi.class);
            Call<GETRTCXMLDATAResult> stringCall = rtcXmlVerificationApi.getStringResponse(referenceNo, passcode, saltkey);
            stringCall.enqueue(new Callback<GETRTCXMLDATAResult>() {
                @Override
                public void onResponse(Call<GETRTCXMLDATAResult> call, Response<GETRTCXMLDATAResult> response) {
                    if (response.isSuccessful()) {
                        GETRTCXMLDATAResult getrtcxmldataResult = response.body();
                        isTaskExecuting = false;
                        backgroundCallBack.onPostResponseSuccess1(getrtcxmldataResult.getGETRTCXMLDATAResult());


                    } else {
                        isTaskExecuting = false;

                        String errorResponse = response.message();
                        backgroundCallBack.onPostResponseError(errorResponse);
                    }


                }

                @Override
                public void onFailure(Call<GETRTCXMLDATAResult> call, Throwable error) {
                    isTaskExecuting = false;
                    String errorResponse = error.getLocalizedMessage();
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
