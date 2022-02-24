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

        if(TextUtils.isEmpty(dataModel.getAFFIDAVIT_ID())){

            viewHolder.tvShowlandConUserId.setVisibility(View.VISIBLE);
            viewHolder.tvShowlandConAffid.setVisibility(View.GONE);

        } else {
            viewHolder.tvShowlandConUserId.setVisibility(View.GONE);
            viewHolder.tvShowlandConAffid.setVisibility(View.VISIBLE);
        }

        viewHolder.tvShowlandConAffid.setText(dataModel.getAFFIDAVIT_ID());
//        viewHolder.tvShowlandConAffRES.setText(dataModel.getAFFIDAVIT_RES());
        viewHolder.tvShowlandConUserId.setText(dataModel.getUSER_ID());
//        viewHolder.tvShowlandConUseRES.setText(dataModel.getUSER_RES());

        // Return the completed view to render on screen

        viewHolder.list_layout.setOnClickListener(v -> {

            AFFIDAVIT_ID = dataModel.getAFFIDAVIT_ID();
            AFFIDAVIT_RES = dataModel.getAFFIDAVIT_RES();
            USER_ID = dataModel.getUSER_ID();
            USER_RES = dataModel.getUSER_RES();

            if(AFFIDAVIT_ID != null){
                Intent intent = new Intent(mContext.getApplicationContext(), LandConversionBasedOnAffidavit.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("AFFIDAVIT_ResponseData", AFFIDAVIT_RES);
//                intent.putExtra("AFFIDAVIT_ID", AFFIDAVIT_ID);

                intent.putExtra("AppType", 8);
                getContext().startActivity(intent);
            } else if(USER_ID != null){

                Intent intent = new Intent(mContext.getApplicationContext(), LandConversionBasedOnUserId.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("USER_ID", USER_ID);
                intent.putExtra("AppType", 8);
                getContext().startActivity(intent);

            }

        });

        return convertView;
    }


    static class ViewHolder {
        TextView tvShowlandConAffid,tvShowlandConAffRES,tvShowlandConUserId,tvShowlandConUseRES;
        LinearLayout list_layout;

        ViewHolder(View view) {

            tvShowlandConAffid = view.findViewById(R.id.tvLandConAffRES);
//            tvShowlandConAffRES = view.findViewById(R.id.tvLandConAffRES);
            tvShowlandConUserId = view.findViewById(R.id.tvLandConUserRES);
//            tvShowlandConUseRES = view.findViewById(R.id.tvLandConUserRES);



            list_layout = view.findViewById(R.id.list_layout);



        }
    }
}