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
import app.bmc.com.BHOOMI_MRTC.model.VR_INFO_HISTORY;
import app.bmc.com.BHOOMI_MRTC.screens.RtcDetails;

public class ShowHistoryDataAdapter extends ArrayAdapter<VR_INFO_HISTORY> {

    private Context mContext;


    private String surveyNo_int,hissa_int,surnoc_int;
    int landId_int,distId_int, talkId_int, hblId_int, villId_int;


    public ShowHistoryDataAdapter(List<VR_INFO_HISTORY> data, Context context) {
        super(context, R.layout.activity_serach_history, data);
        this.mContext = context;
    }


    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        VR_INFO_HISTORY dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ShowHistoryDataAdapter.ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            convertView= LayoutInflater.from(mContext).inflate(R.layout.history_list,parent, false);
            viewHolder= new ShowHistoryDataAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ShowHistoryDataAdapter.ViewHolder) convertView.getTag();
        }

        assert dataModel != null;
        viewHolder.tvShowDstName.setText(dataModel.getHistory_DistName());
        viewHolder.tvShowTlkName.setText(dataModel.getHistory_TalukName());
        viewHolder.tvShowHlbName.setText(dataModel.getHistory_HobliName());
        viewHolder.tvShowVllName.setText(dataModel.getHistory_VillageName());
        viewHolder.tvShowSurveyNo.setText(dataModel.getVR_SNO() + "/"+dataModel.getVR_HISSA_NM());
        viewHolder.tvShowSurnocNo.setText(dataModel.getVR_sarnoc());
        viewHolder.tvShowHissaNo.setText(dataModel.getVR_HISSA_NM());


        // Return the completed view to render on screen

        viewHolder.list_layout.setOnClickListener(v -> {

            distId_int = dataModel.getVR_DST_ID();
            talkId_int = dataModel.getVR_TLK_ID();
            hblId_int = dataModel.getVR_HBL_ID();
            villId_int = dataModel.getVR_VLG_ID();
            landId_int=dataModel.getVR_LAND_NO();
            surveyNo_int =dataModel.getVR_SNO();
            surnoc_int = dataModel.getVR_sarnoc();
            hissa_int =dataModel.getVR_HISSA_NM();


            Intent intent = new Intent(mContext.getApplicationContext(), RtcDetails.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("distId",""+distId_int);
            intent.putExtra("talkId",""+talkId_int);
            intent.putExtra("hblId",""+hblId_int);
            intent.putExtra("villId",""+villId_int);
            intent.putExtra("land_code",""+landId_int);
            intent.putExtra("surveyNo",surveyNo_int+"");
            intent.putExtra("surnoc",""+surnoc_int);
            intent.putExtra("hissa_str",""+hissa_int);
            intent.putExtra("RTC", "RTC");
            intent.putExtra("AppType", 1);
            getContext().startActivity(intent);

        });

        return convertView;
    }


    static class ViewHolder {
        TextView tvShowDstName,tvShowTlkName,tvShowHlbName,tvShowVllName, tvShowSurveyNo,tvShowSurnocNo,tvShowHissaNo,tvShowRtcXmlRefnum;
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


            tvShowDstName.setVisibility(View.VISIBLE);
            tvShowTlkName.setVisibility(View.VISIBLE);
            tvShowHlbName.setVisibility(View.VISIBLE);
            tvShowVllName.setVisibility(View.VISIBLE);
            tvShowSurveyNo.setVisibility(View.VISIBLE);


            list_layout = view.findViewById(R.id.list_layout);

        }
    }

}
