package app.bmc.com.BHOOMI_MRTC.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.model.LandCon_Data_History;
import app.bmc.com.BHOOMI_MRTC.screens.LandConversionBasedOnAffidavit;
import app.bmc.com.BHOOMI_MRTC.screens.LandConversionBasedOnUserId;


public class ShowLandConHistoryAdapter extends ArrayAdapter<LandCon_Data_History> {

    private final Context mContext;

    String AFFIDAVIT_ID,AFFIDAVIT_RES,USER_ID,USER_RES;


    public ShowLandConHistoryAdapter(List<LandCon_Data_History> data, Context context) {
        super(context, R.layout.activity_serach_history, data);
        this.mContext = context;
    }

    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        LandCon_Data_History dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            convertView= LayoutInflater.from(mContext).inflate(R.layout.history_list,parent, false);
            viewHolder= new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        assert dataModel != null;
        viewHolder.tvShowlandConAffid.setText(dataModel.getAFFIDAVIT_ID());
        viewHolder.tvShowlandConAffRES.setText(dataModel.getAFFIDAVIT_RES());
        viewHolder.tvShowlandConUserId.setText(dataModel.getUSER_ID());
        viewHolder.tvShowlandConUseRES.setText(dataModel.getUSER_RES());

        // Return the completed view to render on screen

        viewHolder.list_layout.setOnClickListener(v -> {

            AFFIDAVIT_ID = dataModel.getAFFIDAVIT_ID();
            AFFIDAVIT_RES = dataModel.getAFFIDAVIT_RES();
            USER_ID = dataModel.getUSER_ID();
            USER_RES = dataModel.getUSER_RES();

            if(AFFIDAVIT_ID != null){
            Intent intent = new Intent(mContext.getApplicationContext(), LandConversionBasedOnAffidavit.class);//2 methods
//            intent.putExtra("refnum",""+refNumber);
//            intent.putExtra("refData",""+refData);
//            intent.putExtra("RTCXML", "RTCXML");USER_ID
            intent.putExtra("AFFIDAVIT_ResponseData", AFFIDAVIT_RES);
            intent.putExtra("AFFIDAVIT_ID", AFFIDAVIT_ID);

            intent.putExtra("AppType", 8);
            getContext().startActivity(intent);
            } else if(USER_ID != null){
                Intent intent = new Intent(mContext.getApplicationContext(), LandConversionBasedOnUserId.class);
                intent.putExtra("USER_ID", USER_ID);
//                intent.putExtra("tokenType", tokenType);
//                intent.putExtra("accessToken", accessToken);
                intent.putExtra("AppType", 8);
                getContext().startActivity(intent);

            }

        });

        return convertView;
    }


    static class ViewHolder {
        TextView tvShowRtcXmlRefnum,tvShowRtcXmlRefnumData,tvShowDstName,tvShowTlkName,tvShowHlbName,tvShowVllName, tvShowSurveyNo,tvShowSurnocNo,tvShowHissaNo;
        TextView tvShowlandConAffid,tvShowlandConAffRES,tvShowlandConUserId,tvShowlandConUseRES,tvlandConFinalReqestId,tvLandConFinalSurveyNo;
        LinearLayout list_layout;

        ViewHolder(View view) {

            tvShowRtcXmlRefnum = view.findViewById(R.id.tvrtcxml_ref);
            tvShowRtcXmlRefnumData = view.findViewById(R.id.tvRTC_refData);
            tvShowDstName = view.findViewById(R.id.tvdstNAME);
            tvShowTlkName = view.findViewById(R.id.tvtlkNAME);
            tvShowHlbName = view.findViewById(R.id.tvhlbNAME);
            tvShowVllName = view.findViewById(R.id.tvvllNAME);
            tvShowSurveyNo = view.findViewById(R.id.tvsryNo);
            tvShowSurnocNo = view.findViewById(R.id.tvsernocNo);
            tvShowHissaNo = view.findViewById(R.id.tvhissaNo);

            tvShowlandConAffid = view.findViewById(R.id.tvLandConAffID);
            tvShowlandConAffRES = view.findViewById(R.id.tvLandConAffRES);
            tvShowlandConUserId = view.findViewById(R.id.tvLandConUserID_1);
            tvShowlandConUseRES = view.findViewById(R.id.tvLandConUserRES);


            tvlandConFinalReqestId=view.findViewById(R.id.tvLandConFinalRequesID);
            tvLandConFinalSurveyNo=view.findViewById(R.id.tvLandConFinalSurveyNo);

            tvShowDstName.setVisibility(View.GONE);
            tvShowTlkName.setVisibility(View.GONE);
            tvShowHlbName.setVisibility(View.GONE);
            tvShowVllName.setVisibility(View.GONE);
            tvShowSurveyNo.setVisibility(View.GONE);
            tvShowRtcXmlRefnum.setVisibility(View.GONE);
            tvlandConFinalReqestId.setVisibility(View.GONE);
            tvShowlandConAffid.setVisibility(View.GONE);
            tvShowlandConUserId.setVisibility(View.GONE);
            tvLandConFinalSurveyNo.setVisibility(View.GONE);

            list_layout = view.findViewById(R.id.list_layout);

        }
    }
}
