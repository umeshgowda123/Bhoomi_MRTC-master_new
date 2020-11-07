package app.bmc.com.BHOOMI_MRTC.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.model.RTCByOwnerNameResponse;
import app.bmc.com.BHOOMI_MRTC.screens.RtcDetails;

public class ShowRtcOwnerReportAdapter extends ArrayAdapter<RTCByOwnerNameResponse>{


    private Context mContext;
    private ArrayList<String> distId, talkId, hblId, villId;

    private String distId_int, talkId_int, hblId_int, villId_int;
    private String land_code_int;
    public String owner;
    public String survey_no;
    public String surnoc;
    public String hissa_no;

    public ShowRtcOwnerReportAdapter(ArrayList<RTCByOwnerNameResponse> data, Context context, ArrayList<String> distId, ArrayList<String> talkId, ArrayList<String> hblId, ArrayList<String> villId ) {
        super(context, R.layout.list_owner_report, data);
        this.mContext = context;
        this.distId = distId;
        this.talkId = talkId;
        this.hblId = hblId;
        this.villId = villId;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        RTCByOwnerNameResponse dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            convertView= LayoutInflater.from(mContext).inflate(R.layout.list_owner_report,parent, false);
            viewHolder= new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        assert dataModel != null;
        viewHolder.tvShowOwnerName.setText(dataModel.getOwner());
        viewHolder.tvShowSurveyNo.setText(dataModel.getSurvey_no() + "/" + dataModel.getSurnoc() + "/" + dataModel.getHissa_no());
        // Return the completed view to render on screen

        viewHolder.btnViewReport.setOnClickListener(v -> {

            distId_int = distId.get(position);
            talkId_int = talkId.get(position);
            hblId_int = hblId.get(position);
            villId_int = villId.get(position);
            land_code_int = dataModel.land_code;
            owner = dataModel.owner;
            survey_no = dataModel.survey_no;
            surnoc = dataModel.surnoc;
            hissa_no = dataModel.hissa_no;

            Intent intent = new Intent(getContext(), RtcDetails.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("distId",""+distId_int);
            intent.putExtra("talkId",""+talkId_int);
            intent.putExtra("hblId",""+hblId_int);
            intent.putExtra("villId",""+villId_int);
            intent.putExtra("land_code",""+land_code_int);
            intent.putExtra("survey", survey_no + "/" + surnoc + "/" + hissa_no);
            intent.putExtra("surveyNo",survey_no+"");
            intent.putExtra("hissa_str",hissa_no);
            intent.putExtra("RTC", "RTC");
            getContext().startActivity(intent);

        });

        return convertView;
    }

    static class ViewHolder {
        TextView tvShowOwnerName, tvShowSurveyNo;
        ImageView btnViewReport;
        ViewHolder(View view) {
            tvShowOwnerName = view.findViewById(R.id.tvShowOwnerName);
            tvShowSurveyNo = view.findViewById(R.id.tvShowSurveyNo);
            btnViewReport = view.findViewById(R.id.btnViewReport);
        }
    }

}