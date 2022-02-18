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
import app.bmc.com.BHOOMI_MRTC.model.RLand_Data_History;

import app.bmc.com.BHOOMI_MRTC.screens.RestrictionOnLandReport;

public class ShowRLandHistoryAdapter extends ArrayAdapter<RLand_Data_History> {

    private Context mContext;

    private String surveyNo_int;
    private String hissa_int;
    private String surnoc_int;
    int distId_int, talkId_int, hblId_int, villId_int;


    public ShowRLandHistoryAdapter(List<RLand_Data_History> data, Context context) {
        super(context, R.layout.activity_serach_history, data);
        this.mContext = context;
    }


    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        RLand_Data_History dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ShowRLandHistoryAdapter.ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            convertView= LayoutInflater.from(mContext).inflate(R.layout.history_list,parent, false);
            viewHolder= new ShowRLandHistoryAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ShowRLandHistoryAdapter.ViewHolder) convertView.getTag();
        }

        assert dataModel != null;
        viewHolder.tvShowDstName.setText(dataModel.getRLand_DistName());
        viewHolder.tvShowTlkName.setText(dataModel.getRLand_TalukName());
        viewHolder.tvShowHlbName.setText(dataModel.getRLand_HobliName());
        viewHolder.tvShowVllName.setText(dataModel.getRLand_VillageName());
        viewHolder.tvShowSurveyNo.setText(dataModel.getRLR_SNO() + "/"+dataModel.getRLR_HISSA());
        viewHolder.tvShowSurnocNo.setText(dataModel.getRLR_SUROC());
        viewHolder.tvShowHissaNo.setText(dataModel.getRLR_HISSA());
//        viewHolder.tvShowRLand.setText(dataModel.getRLR_RES());

        // Return the completed view to render on screen

        viewHolder.list_layout.setOnClickListener(v -> {

            distId_int = dataModel.getRLR_DST_ID();
            talkId_int = dataModel.getRLR_TLK_ID();
            hblId_int = dataModel.getRLR_HBL_ID();
            villId_int = dataModel.getRLR_VLG_ID();
            surveyNo_int =dataModel.getRLR_SNO();
            surnoc_int = dataModel.getRLR_SUROC();
            hissa_int =dataModel.getRLR_HISSA();


            Intent intent = new Intent(mContext.getApplicationContext(), RestrictionOnLandReport.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            intent.putExtra("d_id", distId_int + "");
            intent.putExtra("t_id", talkId_int + "");
            intent.putExtra("h_id", hblId_int + "");
            intent.putExtra("v_id", villId_int + "");
            intent.putExtra("s_No", surveyNo_int + "");
            intent.putExtra("s_c", surnoc_int + "");
            intent.putExtra("hi_no", hissa_int + "");
            intent.putExtra("AppType", 8);
            getContext().startActivity(intent);

        });

        return convertView;
    }


    static class ViewHolder {
        TextView tvShowDstName,tvShowTlkName,tvShowHlbName,tvShowVllName, tvShowSurveyNo,tvShowSurnocNo,tvShowHissaNo ;
        LinearLayout list_layout;
        ViewHolder(View view) {

            tvShowDstName = view.findViewById(R.id.tvdstNAME);
            tvShowTlkName = view.findViewById(R.id.tvtlkNAME);
            tvShowHlbName = view.findViewById(R.id.tvhlbNAME);
            tvShowVllName = view.findViewById(R.id.tvvllNAME);
            tvShowSurveyNo = view.findViewById(R.id.tvsryNo);
            tvShowSurnocNo = view.findViewById(R.id.tvsernocNo);
            tvShowHissaNo = view.findViewById(R.id.tvhissaNo);
//            tvShowRLand = view.findViewById(R.id.tvRLand);

            tvShowDstName.setVisibility(View.VISIBLE);
            tvShowTlkName.setVisibility(View.VISIBLE);
            tvShowHlbName.setVisibility(View.VISIBLE);
            tvShowVllName.setVisibility(View.VISIBLE);
            tvShowSurveyNo.setVisibility(View.VISIBLE);

            list_layout = view.findViewById(R.id.list_layout);

        }
    }


}
