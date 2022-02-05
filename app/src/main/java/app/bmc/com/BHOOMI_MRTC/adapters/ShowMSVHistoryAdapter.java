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
import app.bmc.com.BHOOMI_MRTC.model.MSV_Data_History;
import app.bmc.com.BHOOMI_MRTC.screens.ShowMutationStatusDetails;

public class ShowMSVHistoryAdapter extends ArrayAdapter<MSV_Data_History> {

    private Context mContext;
    int distId_int, talkId_int, hblId_int, villId_int;
    String survayNo,msv_report,hissa,sarnoc;



    public ShowMSVHistoryAdapter(List<MSV_Data_History> data, Context context) {
        super(context, R.layout.activity_serach_history, data);
        this.mContext = context;
    }


    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        MSV_Data_History dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ShowMSVHistoryAdapter.ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            convertView= LayoutInflater.from(mContext).inflate(R.layout.history_list,parent, false);
            viewHolder= new ShowMSVHistoryAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ShowMSVHistoryAdapter.ViewHolder) convertView.getTag();
        }

        assert dataModel != null;
        viewHolder.tvShowDstName.setText(dataModel.getMSV_DistName());
        viewHolder.tvShowTlkName.setText(dataModel.getMSV_TalukName());
        viewHolder.tvShowHlbName.setText(dataModel.getMSV_HobliName());
        viewHolder.tvShowVllName.setText(dataModel.getMSV_VillageName());
        viewHolder.tvShowSurveyNo.setText(dataModel.getVMS_SURVEY_NO() + "/"+dataModel.getVMS_HISSA());
        viewHolder.tvShowSurnocNo.setText(dataModel.getVMS_SERNOC());
        viewHolder.tvShowHissaNo.setText(dataModel.getVMS_HISSA());
        viewHolder.tvShowMSV_RES.setText(dataModel.getVMS_RES());

        // Return the completed view to render on screen

        viewHolder.list_layout.setOnClickListener(v -> {

            distId_int = dataModel.getVMS_DST_ID();
            talkId_int = dataModel.getVMS_TLK_ID();
            hblId_int = dataModel.getVMS_HBL_ID();
            villId_int = dataModel.getVMS_VLG_ID();
            survayNo=dataModel.getVMS_SURVEY_NO();
            sarnoc = dataModel.getVMS_SERNOC();
            hissa = dataModel.getVMS_HISSA();

            msv_report = dataModel.getVMS_RES();

            Intent intent = new Intent(mContext.getApplicationContext(), ShowMutationStatusDetails.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("distId",""+distId_int);
            intent.putExtra("talkId",""+talkId_int);
            intent.putExtra("hblId",""+hblId_int);
            intent.putExtra("villId",""+villId_int);
            intent.putExtra("survayno",""+survayNo);
            intent.putExtra("sernoc",""+sarnoc);
            intent.putExtra("hissa",""+hissa);
            intent.putExtra("status_response_data",""+msv_report);
            intent.putExtra("VMS", "VMS");
            intent.putExtra("AppType", 6);
            getContext().startActivity(intent);

        });

        return convertView;
    }


    static class ViewHolder {
        TextView tvShowDstName,tvShowTlkName,tvShowHlbName,tvShowVllName, tvShowSurveyNo,tvShowSurnocNo,tvShowHissaNo,tvShowMPDRES;
        TextView tvShowMS_RES,tvShowMSV_RES;
        LinearLayout list_layout;

        ViewHolder(View view) {

            tvShowDstName = view.findViewById(R.id.tvdstNAME);
            tvShowTlkName = view.findViewById(R.id.tvtlkNAME);
            tvShowHlbName = view.findViewById(R.id.tvhlbNAME);
            tvShowVllName = view.findViewById(R.id.tvvllNAME);
            tvShowSurveyNo = view.findViewById(R.id.tvsryNo);
            tvShowSurnocNo = view.findViewById(R.id.tvsernocNo);
            tvShowHissaNo = view.findViewById(R.id.tvhissaNo);

            tvShowMPDRES = view.findViewById(R.id.tvMPDRES);
            tvShowMS_RES = view.findViewById(R.id.tvMSRES);
            tvShowMSV_RES = view.findViewById(R.id.tvMSVRES);

            tvShowDstName.setVisibility(View.VISIBLE);
            tvShowTlkName.setVisibility(View.VISIBLE);
            tvShowHlbName.setVisibility(View.VISIBLE);
            tvShowVllName.setVisibility(View.VISIBLE);
            tvShowSurveyNo.setVisibility(View.VISIBLE);


            list_layout = view.findViewById(R.id.list_layout);

        }
    }
}
