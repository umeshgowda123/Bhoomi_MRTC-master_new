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
import app.bmc.com.BHOOMI_MRTC.model.MPD_Data_History;
import app.bmc.com.BHOOMI_MRTC.screens.ShowMutationPendencyDetails;

public class ShowMPDHistoryAdapter extends ArrayAdapter<MPD_Data_History> {
    private Context mContext;
    int distId_int, talkId_int, hblId_int, villId_int;
    String MPD_res;


    public ShowMPDHistoryAdapter(List<MPD_Data_History> data, Context context) {
        super(context, R.layout.activity_serach_history, data);
        this.mContext = context;
    }

    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        MPD_Data_History dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ShowMPDHistoryAdapter.ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            convertView= LayoutInflater.from(mContext).inflate(R.layout.history_list,parent, false);
            viewHolder= new ShowMPDHistoryAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ShowMPDHistoryAdapter.ViewHolder) convertView.getTag();
        }

        assert dataModel != null;
        viewHolder.tvShowDstName.setText(dataModel.getMPD_DistName());
        viewHolder.tvShowTlkName.setText(dataModel.getMPD_TalukName());
        viewHolder.tvShowHlbName.setText(dataModel.getMPD_HobliName());
        viewHolder.tvShowVllName.setText(dataModel.getMPD_VillageName());
        viewHolder.tvShowMPDRES.setText(dataModel.getMPD_RES());



        // Return the completed view to render on screen

        viewHolder.list_layout.setOnClickListener(v -> {

            distId_int = dataModel.getMPD_DST_ID();
            talkId_int = dataModel.getMPD_TLK_ID();
            hblId_int = dataModel.getMPD_HBL_ID();
            villId_int = dataModel.getMPD_VLG_ID();
            MPD_res=dataModel.getMPD_RES();

            Intent intent = new Intent(mContext.getApplicationContext(), ShowMutationPendencyDetails.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("distId",""+distId_int);
            intent.putExtra("talkId",""+talkId_int);
            intent.putExtra("hblId",""+hblId_int);
            intent.putExtra("villId",""+villId_int);
            intent.putExtra("ped_response_data",""+MPD_res);
            intent.putExtra("MPD", "MPD");
            intent.putExtra("AppType", 4);
            getContext().startActivity(intent);

        });

        return convertView;
    }


    static class ViewHolder {
        TextView tvShowDstName,tvShowTlkName,tvShowHlbName,tvShowVllName, tvShowSurveyNo,tvShowSurnocNo,tvShowHissaNo,tvShowMPDRES;
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


            tvShowDstName.setVisibility(View.VISIBLE);
            tvShowTlkName.setVisibility(View.VISIBLE);
            tvShowHlbName.setVisibility(View.VISIBLE);
            tvShowVllName.setVisibility(View.VISIBLE);


            list_layout = view.findViewById(R.id.list_layout);

        }
    }


}
