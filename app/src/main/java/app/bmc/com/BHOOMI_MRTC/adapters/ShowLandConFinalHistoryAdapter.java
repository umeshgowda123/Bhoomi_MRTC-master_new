package app.bmc.com.BHOOMI_MRTC.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
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


    private String LandConFinalRequesID_report;
    private String LandConFinalSNo_report;
    private String LandConFinalRequesID;
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
        if(TextUtils.isEmpty(dataModel.getREQUEST_ID())){
            viewHolder.tvShowDstName.setVisibility(View.VISIBLE);
            viewHolder.tvShowTlkName.setVisibility(View.VISIBLE);
            viewHolder.tvShowHlbName.setVisibility(View.VISIBLE);
            viewHolder.tvShowVllName.setVisibility(View.VISIBLE);
            viewHolder.tvShowSurveyNo.setVisibility(View.VISIBLE);
            viewHolder.tvShowlandConFinalReqestId.setVisibility(View.GONE);

        } else {
            viewHolder.tvShowDstName.setVisibility(View.GONE);
            viewHolder.tvShowTlkName.setVisibility(View.GONE);
            viewHolder.tvShowHlbName.setVisibility(View.GONE);
            viewHolder.tvShowVllName.setVisibility(View.GONE);
            viewHolder.tvShowSurveyNo.setVisibility(View.GONE);
            viewHolder.tvShowlandConFinalReqestId.setVisibility(View.VISIBLE);

        }
        viewHolder.tvShowDstName.setText(dataModel.getLandFinal_DistName());
        viewHolder.tvShowTlkName.setText(dataModel.getLandFinal_TalukName());
        viewHolder.tvShowHlbName.setText(dataModel.getLandFinal_TalukName());
        viewHolder.tvShowVllName.setText(dataModel.getLandFinal_VillageName());
        viewHolder.tvShowSurveyNo.setText(dataModel.getS_NO());

        viewHolder.tvShowlandConFinalReqestId.setText(dataModel.getREQUEST_ID());

//        viewHolder.tvShowlandConFinalReqestId_RES.setText(dataModel.getREQUEST_ID_RES());
//        viewHolder.tvShowlandConFinalServayNo_RES.setText(dataModel.getSNO_RES());

        // Return the completed view to render on screen

        viewHolder.list_layout.setOnClickListener(v -> {

            distId_final = dataModel.getDST_ID();
            talkId_final = dataModel.getTLK_ID();
            hblId_final = dataModel.getHBL_ID();
            villId_final = dataModel.getVLG_ID();

            LandConFinalSNo_report=dataModel.getSNO_RES();
            LandConFinalRequesID_report = dataModel.getREQUEST_ID_RES();
            LandConFinalRequesID = dataModel.getREQUEST_ID();


            if(LandConFinalRequesID_report != null){
            Intent intent = new Intent(mContext.getApplicationContext(), ConversionFinalOrders_BasedOnReq_ID.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("LandConversionFinalOrders", LandConFinalRequesID_report);
            intent.putExtra("AppType", 9);
            getContext().startActivity(intent);
            }
            else if (LandConFinalSNo_report != null) {
                Intent intent = new Intent(mContext.getApplicationContext(), ConversionFinalOrders_BasedOnReq_ID.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("LandConversionFinalOrders", LandConFinalSNo_report);
                intent.putExtra("AppType", 9);
                getContext().startActivity(intent);
            }

        });

        return convertView;
    }


    static class ViewHolder {
        TextView tvShowDstName,tvShowTlkName,tvShowHlbName,tvShowVllName, tvShowSurveyNo;
        TextView tvShowlandConFinalReqestId ;

        LinearLayout list_layout;

        ViewHolder(View view) {

            tvShowDstName = view.findViewById(R.id.tvdstNAME);
            tvShowTlkName = view.findViewById(R.id.tvtlkNAME);
            tvShowHlbName = view.findViewById(R.id.tvhlbNAME);
            tvShowVllName = view.findViewById(R.id.tvvllNAME);
            tvShowSurveyNo = view.findViewById(R.id.tvsryNo);


            tvShowlandConFinalReqestId = view.findViewById(R.id.tvLandConFinalRequesID_RES);

            list_layout = view.findViewById(R.id.list_layout);

        }
    }
}