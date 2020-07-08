package app.bmc.com.BHOOMI_MRTC.adapters;


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
        holder.tvOwnerName.setText(list.get(position).getNAME());
        holder.tvExtent.setText(list.get(position).getEXTENT());
        holder.tvMainOwner.setText(list.get(position).getISMAINOWNER());
        holder.tvLandType.setText(list.get(position).getGovt_Private_Land());
        holder.tvlandGovtRestricted.setText(list.get(position).getGovtRestrict());
        holder.tvCourtStay.setText(list.get(position).getCourtStay());
        holder.tvLandAlienated.setText(list.get(position).getAlienation());
        holder.tvTrnscRunning.setText(list.get(position).getTransactionRunning());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
         TextView tvOwnerName;
         TextView tvExtent;
         TextView tvMainOwner;
         TextView tvLandType;
         TextView tvlandGovtRestricted;
         TextView tvCourtStay;
         TextView tvLandAlienated;
         TextView tvTrnscRunning;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOwnerName = itemView.findViewById(R.id.tvOwnerName);
            tvExtent = itemView.findViewById(R.id.tvExtent);
            tvMainOwner = itemView.findViewById(R.id.tvMainOwner);
            tvLandType = itemView.findViewById(R.id.tvLandType);
            tvlandGovtRestricted = itemView.findViewById(R.id.tvlandGovtRestricted);
            tvCourtStay = itemView.findViewById(R.id.tvCourtStay);
            tvLandAlienated = itemView.findViewById(R.id.tvLandAlienated);
            tvTrnscRunning = itemView.findViewById(R.id.tvTrnscRunning);
        }
    }
    public RestrictionOnLandReportAdapter(List<RestrictionOnLandReportTable> list){
        this.list = list;
    }
}
