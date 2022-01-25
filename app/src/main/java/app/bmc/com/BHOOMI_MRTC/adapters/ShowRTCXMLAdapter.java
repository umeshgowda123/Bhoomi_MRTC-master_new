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
import app.bmc.com.BHOOMI_MRTC.model.RTCV_Data_history;
import app.bmc.com.BHOOMI_MRTC.screens.RtcVerificationData;


public class ShowRTCXMLAdapter extends ArrayAdapter<RTCV_Data_history> {

    private Context mContext;
    private String refNumber,refData;

    public ShowRTCXMLAdapter(List<RTCV_Data_history> data, Context context) {
        super(context, R.layout.activity_serach_history, data);
        this.mContext = context;
    }


    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        RTCV_Data_history dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ShowRTCXMLAdapter.ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            convertView= LayoutInflater.from(mContext).inflate(R.layout.history_list,parent, false);
            viewHolder= new ShowRTCXMLAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ShowRTCXMLAdapter.ViewHolder) convertView.getTag();
        }

        assert dataModel != null;
        viewHolder.tvShowRtcXmlRefnum.setText(dataModel.getREFF_NO());
        viewHolder.tvShowRtcXmlRefnumData.setText(dataModel.getREFF_RES());

        // Return the completed view to render on screen

        viewHolder.list_layout.setOnClickListener(v -> {

            refNumber = dataModel.getREFF_NO();
            refData = dataModel.getREFF_RES();


            Intent intent = new Intent(mContext.getApplicationContext(), RtcVerificationData.class);
//            intent.putExtra("refnum",""+refNumber);
//            intent.putExtra("refData",""+refData);
            intent.putExtra("RTCXML", "RTCXML");
            intent.putExtra("xml_data", refData);
            intent.putExtra("AppType", 2);
            getContext().startActivity(intent);

        });

        return convertView;
    }



    static class ViewHolder {
        TextView tvShowRtcXmlRefnum,tvShowRtcXmlRefnumData,tvShowDstName,tvShowTlkName,tvShowHlbName,tvShowVllName, tvShowSurveyNo,tvShowSurnocNo,tvShowHissaNo;
        TextView tvlandConAffID,tvlandCOnUserID,tvLandConFinalRequesID,tvLandConFinalSurveyNo;

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

            tvlandConAffID = view.findViewById(R.id.tvLandConAffID);
            tvlandCOnUserID = view.findViewById(R.id.tvLandConUserID_1);
            tvLandConFinalRequesID = view.findViewById(R.id.tvLandConFinalRequesID);
            tvLandConFinalSurveyNo = view.findViewById(R.id.tvLandConFinalSurveyNo);

            tvShowDstName.setVisibility(View.GONE);
            tvShowTlkName.setVisibility(View.GONE);
            tvShowHlbName.setVisibility(View.GONE);
            tvShowVllName.setVisibility(View.GONE);
            tvShowSurveyNo.setVisibility(View.GONE);
            tvlandConAffID.setVisibility(View.GONE);
            tvlandCOnUserID.setVisibility(View.GONE);
            tvLandConFinalRequesID.setVisibility(View.GONE);
            tvLandConFinalSurveyNo.setVisibility(View.GONE);

            list_layout = view.findViewById(R.id.list_layout);

        }
    }
}
