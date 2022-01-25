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

import app.bmc.com.BHOOMI_MRTC.model.MS_Data_History;

import app.bmc.com.BHOOMI_MRTC.screens.ShowMutationSummeryReport;

public class ShowMSHistoryAdapter extends ArrayAdapter<MS_Data_History> {
    private Context mContext;
    int distId_int, talkId_int, hblId_int, villId_int;
    String survayNo,ms_report;


    public ShowMSHistoryAdapter(List<MS_Data_History> data, Context context) {
        super(context, R.layout.activity_serach_history, data);
        this.mContext = context;
    }

    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        MS_Data_History dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ShowMSHistoryAdapter.ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            convertView= LayoutInflater.from(mContext).inflate(R.layout.history_list,parent, false);
            viewHolder= new ShowMSHistoryAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ShowMSHistoryAdapter.ViewHolder) convertView.getTag();
        }

        assert dataModel != null;
        viewHolder.tvShowDstName.setText(dataModel.getMS_DistName());
        viewHolder.tvShowTlkName.setText(dataModel.getMS_TalukName());
        viewHolder.tvShowHlbName.setText(dataModel.getMS_HobliName());
        viewHolder.tvShowVllName.setText(dataModel.getMS_VillageName());
        viewHolder.tvShowSurveyNo.setText(dataModel.getMSR_SNO());
        viewHolder.tvShowMS_RES.setText(dataModel.getMSR_RES());


        // Return the completed view to render on screen

        viewHolder.list_layout.setOnClickListener(v -> {

            distId_int = dataModel.getMSR_DST_ID();
            talkId_int = dataModel.getMSR_TLK_ID();
            hblId_int = dataModel.getMSR_HBL_ID();
            villId_int = dataModel.getMSR_VLG_ID();
            survayNo=dataModel.getMSR_SNO();
            ms_report = dataModel.getMSR_RES();

            Intent intent = new Intent(mContext.getApplicationContext(), ShowMutationSummeryReport.class);
            intent.putExtra("distId",""+distId_int);
            intent.putExtra("talkId",""+talkId_int);
            intent.putExtra("hblId",""+hblId_int);
            intent.putExtra("villId",""+villId_int);
            intent.putExtra("survayno",""+survayNo);
            intent.putExtra("html_response_data",""+ms_report);
            intent.putExtra("MPD", "MPD");
            intent.putExtra("AppType", 4);
            getContext().startActivity(intent);

        });

        return convertView;
    }



    static class ViewHolder {
        TextView tvShowDstName,tvShowTlkName,tvShowHlbName,tvShowVllName, tvShowSurveyNo,tvShowSurnocNo,tvShowHissaNo,tvShowRtcXmlRefnum,tvShowMPDRES;
        TextView tvShowMS_RES;
        TextView tvlandConAffID,tvlandCOnUserID,tvLandConFinalRequesID,tvLandConFinalSurveyNo;
        LinearLayout list_layout;

        ViewHolder(View view) {

            tvShowRtcXmlRefnum = view.findViewById(R.id.tvrtcxml_ref);
            tvShowDstName = view.findViewById(R.id.tvdstNAME);
            tvShowTlkName = view.findViewById(R.id.tvtlkNAME);
            tvShowHlbName = view.findViewById(R.id.tvhlbNAME);
            tvShowVllName = view.findViewById(R.id.tvvllNAME);
            tvShowSurveyNo = view.findViewById(R.id.tvsryNo);
            tvShowSurnocNo = view.findViewById(R.id.tvsernocNo);
            tvShowHissaNo = view.findViewById(R.id.tvhissaNo);
            tvShowMPDRES = view.findViewById(R.id.tvMPDRES);
            tvShowMS_RES = view.findViewById(R.id.tvMSRES);

            tvlandConAffID = view.findViewById(R.id.tvLandConAffID);
            tvlandCOnUserID = view.findViewById(R.id.tvLandConUserID_1);
            tvLandConFinalRequesID = view.findViewById(R.id.tvLandConFinalRequesID);
            tvLandConFinalSurveyNo = view.findViewById(R.id.tvLandConFinalSurveyNo);



            tvlandConAffID.setVisibility(View.GONE);
            tvlandCOnUserID.setVisibility(View.GONE);
            tvLandConFinalRequesID.setVisibility(View.GONE);
            tvLandConFinalSurveyNo.setVisibility(View.GONE);

            tvShowRtcXmlRefnum.setVisibility(View.GONE);
            tvShowMPDRES.setVisibility(View.GONE);
            tvShowHissaNo.setVisibility(View.GONE);


            list_layout = view.findViewById(R.id.list_layout);

        }
    }

}
