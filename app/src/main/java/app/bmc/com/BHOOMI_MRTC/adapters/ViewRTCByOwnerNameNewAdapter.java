package app.bmc.com.BHOOMI_MRTC.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.model.RTCByOwnerNameResponse;
import app.bmc.com.BHOOMI_MRTC.screens.RtcDetails;

public class ViewRTCByOwnerNameNewAdapter extends BaseAdapter implements Filterable {

    Context mContext;
    ArrayList<String> distId, talkId, hblId, villId;
    ArrayList<RTCByOwnerNameResponse> dataList;
    ArrayList<RTCByOwnerNameResponse> filterList;
    CustomFilter customFilter;
    private String distId_int, talkId_int, hblId_int, villId_int;
    private String land_code_int;
    public String owner;
    public String survey_no;
    public String surnoc;
    public String hissa_no;

    public ViewRTCByOwnerNameNewAdapter(ArrayList<RTCByOwnerNameResponse> data, Context context, ArrayList<String> distId,
                                        ArrayList<String> talkId, ArrayList<String> hblId, ArrayList<String> villId) {
        this.dataList = data;
        this.filterList = data;
        this.mContext = context;
        this.distId = distId;
        this.talkId = talkId;
        this.hblId = hblId;
        this.villId = villId;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewRTCByOwnerNameViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_owner_report, parent, false);
            viewHolder = new ViewRTCByOwnerNameViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewRTCByOwnerNameViewHolder) convertView.getTag();
        }

        String strShowSurNo = dataList.get(position).getSurvey_no() + "/" + dataList.get(position).getSurnoc() + "/" + dataList.get(position).getHissa_no();
        viewHolder.tvShowOwnerName.setText(dataList.get(position).getOwner());
        viewHolder.tvShowSurveyNo.setText(strShowSurNo);
        // Return the completed view to render on screen

        viewHolder.btnViewReport.setOnClickListener(v -> {

            distId_int = distId.get(position);
            talkId_int = talkId.get(position);
            hblId_int = hblId.get(position);
            villId_int = villId.get(position);
            land_code_int = dataList.get(position).land_code;
            owner = dataList.get(position).owner;
            survey_no = (dataList.get(position).survey_no)+"";
            surnoc = dataList.get(position).surnoc;
            hissa_no = dataList.get(position).hissa_no;

            Intent intent = new Intent(mContext, RtcDetails.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("distId", "" + distId_int);
            intent.putExtra("talkId", "" + talkId_int);
            intent.putExtra("hblId", "" + hblId_int);
            intent.putExtra("villId", "" + villId_int);
            intent.putExtra("land_code", "" + land_code_int);
            intent.putExtra("survey", survey_no + "/" + surnoc + "/" + hissa_no);
            intent.putExtra("surveyNo", survey_no + "");
            intent.putExtra("hissa_str", hissa_no);
            intent.putExtra("RTC", "RTC");
            mContext.startActivity(intent);

        });

        return convertView;
    }

    @Override
    public Filter getFilter() {

        if (customFilter == null){
            customFilter = new CustomFilter();
        }
        return customFilter;
    }


    public class ViewRTCByOwnerNameViewHolder {
        TextView tvShowOwnerName, tvShowSurveyNo;
        ImageView btnViewReport;

        ViewRTCByOwnerNameViewHolder(View view) {
            tvShowOwnerName = view.findViewById(R.id.tvShowOwnerName);
            tvShowSurveyNo = view.findViewById(R.id.tvShowSurveyNo);
            btnViewReport = view.findViewById(R.id.btnViewReport);
        }
    }

    class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString();
                ArrayList<RTCByOwnerNameResponse> filter = new ArrayList<>();
                for (int i = 0; i < filterList.size(); i++)
                {
                    if (filterList.get(i).owner.contains(constraint) || filterList.get(i).survey_no.contains(constraint)){
                        RTCByOwnerNameResponse rtcByOwnerNameResponse = new RTCByOwnerNameResponse(filterList.get(i).getLand_code(),
                                filterList.get(i).getMain_owner_no(),filterList.get(i).getOwner_no(),filterList.get(i).getOwner(),filterList.get(i).getSurvey_no(),
                                filterList.get(i).getSurnoc(),filterList.get(i).getHissa_no(),filterList.get(i).getDistId(),filterList.get(i).getTalkId(),filterList.get(i).getHblId());
                        filter.add(rtcByOwnerNameResponse);
                    }
                }
                results.count = filter.size();
                results.values = filter;
            }else {

                results.count = filterList.size();
                results.values = filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            dataList = (ArrayList<RTCByOwnerNameResponse>) results.values;
            notifyDataSetChanged();

        }
    }
}

