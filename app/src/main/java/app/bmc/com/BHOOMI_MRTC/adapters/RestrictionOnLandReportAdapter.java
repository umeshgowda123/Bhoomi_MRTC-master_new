package app.bmc.com.BHOOMI_MRTC.adapters;


import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.model.RestrictionOnLandReportTable;


public class RestrictionOnLandReportAdapter extends RecyclerView.Adapter<RestrictionOnLandReportAdapter.ViewHolder> {

    private List<RestrictionOnLandReportTable> list;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restriction_on_land_report_row,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvSurveyNum.setText(list.get(position).getSurveyNumber());
        holder.tvOwnerName.setText(list.get(position).getOwner());
        holder.tvExtent.setText(list.get(position).getExtent());
        String owner_no = list.get(position).getOwner_no();
        String main_owner_no = list.get(position).getMain_owner_no();
        if (owner_no.equals(main_owner_no)){
            holder.tvMainOwner.setText("YES");
        }else {
            holder.tvMainOwner.setText("NO");
        }
        holder.tvLandType.setText(list.get(position).getGovt_Private_Land());
        holder.tvlandGovtRestricted.setText(list.get(position).getGovtRestrict());
        holder.tvCourtStay.setText(list.get(position).getCourtStay());
        holder.tvLandAlienated.setText(list.get(position).getAlienation());
        holder.tvTrnscRunning.setText(list.get(position).getTransactionRunning());
        String str = list.get(position).getPyki_surveyor_desc();
        if (TextUtils.isEmpty(str)){
            str = "Pyki update not happened on the selected survey number";
        }
        holder.tvPyki.setText(str);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
         TextView tvSurveyNum;
         TextView tvOwnerName;
         TextView tvExtent;
         TextView tvMainOwner;
         TextView tvLandType;
         TextView tvlandGovtRestricted;
         TextView tvCourtStay;
         TextView tvLandAlienated;
         TextView tvTrnscRunning;
         TextView tvPyki;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSurveyNum = itemView.findViewById(R.id.tvSurveyNumberrrr);
            tvOwnerName = itemView.findViewById(R.id.tvOwnerName);
            tvExtent = itemView.findViewById(R.id.tvExtent);
            tvMainOwner = itemView.findViewById(R.id.tvMainOwner);
            tvLandType = itemView.findViewById(R.id.tvLandType);
            tvlandGovtRestricted = itemView.findViewById(R.id.tvlandGovtRestricted);
            tvCourtStay = itemView.findViewById(R.id.tvCourtStay);
            tvLandAlienated = itemView.findViewById(R.id.tvLandAlienated);
            tvTrnscRunning = itemView.findViewById(R.id.tvTrnscRunning);
            tvPyki = itemView.findViewById(R.id.tvPyki);

        }
    }
    public RestrictionOnLandReportAdapter(List<RestrictionOnLandReportTable> list){
        this.list = list;
    }
}
