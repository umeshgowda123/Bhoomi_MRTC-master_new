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
import app.bmc.com.BHOOMI_MRTC.model.LandConFinal_Data_History;
import app.bmc.com.BHOOMI_MRTC.screens.ConversionFinalOrders_BasedOnReq_ID;

public class ShowLandConFinalHistoryAdapter extends ArrayAdapter<LandConFinal_Data_History> {

    private Context mContext;


    private String surveyNo_final,LandConFinalRequesID,LandConFinalRequesID_report,LandConFinalServayNo_report;
    int distId_final, talkId_final, hblId_final, villId_final;


    public ShowLandConFinalHistoryAdapter(List<LandConFinal_Data_History> data, Context context) {
        super(context, R.layout.activity_serach_history, data);
        this.mContext = context;
    }

    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        LandConFinal_Data_History dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ShowLandConFinalHistoryAdapter.ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            convertView= LayoutInflater.from(mContext).inflate(R.layout.history_list,parent, false);
            viewHolder= new ShowLandConFinalHistoryAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ShowLandConFinalHistoryAdapter.ViewHolder) convertView.getTag();
        }

        assert dataModel != null;
        viewHolder.tvShowDstName.setText(dataModel.getLandFinal_DistName());
        viewHolder.tvShowTlkName.setText(dataModel.getLandFinal_TalukName());
        viewHolder.tvShowHlbName.setText(dataModel.getLandFinal_TalukName());
        viewHolder.tvShowVllName.setText(dataModel.getLandFinal_VillageName());

        viewHolder.tvShowlandConFinalReqestId.setText(dataModel.getREQUEST_ID());
        viewHolder.tvShowlandConFinalServayNo.setText(dataModel.getS_NO());
        viewHolder.tvShowlandConFinalReqestId_RES.setText(dataModel.getREQUEST_ID_RES());
        viewHolder.tvShowlandConFinalServayNo_RES.setText(dataModel.getSNO_RES());

        // Return the completed view to render on screen

        viewHolder.list_layout.setOnClickListener(v -> {

            distId_final = dataModel.getDST_ID();
            talkId_final = dataModel.getTLK_ID();
            hblId_final = dataModel.getHBL_ID();
            villId_final = dataModel.getVLG_ID();

            surveyNo_final = dataModel.getS_NO();
            LandConFinalRequesID = dataModel.getREQUEST_ID();
            LandConFinalRequesID_report = dataModel.getREQUEST_ID_RES();
            LandConFinalServayNo_report = dataModel.getSNO_RES();


            Intent intent = new Intent(mContext.getApplicationContext(), ConversionFinalOrders_BasedOnReq_ID.class);//2 methods
//            intent.putExtra("refnum",""+refNumber);
//            intent.putExtra("refData",""+refData);
//            intent.putExtra("RTCXML", "RTCXML");USER_ID
            intent.putExtra("LandConversionFinalOrders", LandConFinalRequesID_report);
//            intent.putExtra("USER_ID", USER_ID);

            intent.putExtra("AppType", 9);
            getContext().startActivity(intent);

        });

        return convertView;
    }


    static class ViewHolder {
        TextView tvShowRtcXmlRefnum,tvShowRtcXmlRefnumData,tvShowDstName,tvShowTlkName,tvShowHlbName,tvShowVllName, tvShowSurveyNo,tvShowSurnocNo,tvShowHissaNo;
        TextView tvShowlandConAffid,tvShowlandConAffRES,tvShowlandConUserId,tvShowlandConUseRES;
        TextView tvShowlandConFinalReqestId,tvShowlandConFinalServayNo,tvShowlandConFinalReqestId_RES,tvShowlandConFinalServayNo_RES;

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

            tvShowlandConFinalReqestId = view.findViewById(R.id.tvLandConFinalRequesID);
            tvShowlandConFinalServayNo = view.findViewById(R.id.tvLandConFinalSurveyNo);
            tvShowlandConFinalReqestId_RES = view.findViewById(R.id.tvLandConFinalRequesID_RES);
            tvShowlandConFinalServayNo_RES = view.findViewById(R.id.tvLandConFinalSurveyNo_RES);



            tvShowDstName.setVisibility(View.GONE);
            tvShowTlkName.setVisibility(View.GONE);
            tvShowHlbName.setVisibility(View.GONE);
            tvShowVllName.setVisibility(View.GONE);
            tvShowSurveyNo.setVisibility(View.GONE);
            tvShowRtcXmlRefnum.setVisibility(View.GONE);
            tvShowlandConAffid.setVisibility(View.GONE);
            tvShowlandConUserId.setVisibility(View.GONE);


            list_layout = view.findViewById(R.id.list_layout);

        }
    }
}
