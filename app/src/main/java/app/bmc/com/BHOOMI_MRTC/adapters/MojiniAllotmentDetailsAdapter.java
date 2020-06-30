package app.bmc.com.BHOOMI_MRTC.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.model.MojiniAllotementDetailsTable;

public class MojiniAllotmentDetailsAdapter extends RecyclerView.Adapter<MojiniAllotmentDetailsAdapter.ViewHolder>{
    private List<MojiniAllotementDetailsTable> list;

    public MojiniAllotmentDetailsAdapter(List<MojiniAllotementDetailsTable> allotementDetailsTableList) {
        this.list = allotementDetailsTableList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.allotment_details_mojini_row,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvDistName.setText(list.get(position).getDistrictName());
        holder.tvTalukaName.setText(list.get(position).getTalukName());
        holder.tvHobliiName.setText(list.get(position).getHobliName());
        holder.tvVillageName.setText(list.get(position).getVillageName());
        holder.tvAppNo.setText(list.get(position).getApplicationNo());
        holder.tvTypeOfApp.setText(list.get(position).getTypeOfApplication());
        holder.tvApplAllottedTo.setText(list.get(position).getApplAllottedTo());
        holder.tvApplCreatedDate.setText(list.get(position).getApplCreatedDate());
        holder.tvAllottedDate.setText(list.get(position).getAllottedDate());
        holder.tvCompletionDate.setText(list.get(position).getCompletionDate());
        holder.tvtypofAppNm.setText(list.get(position).getTypofAppNm());
        holder.tvSurveyNum.setText(list.get(position).getSurveyNum());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDistName;
        TextView tvTalukaName;
        TextView tvHobliiName;
        TextView tvVillageName;
        TextView tvAppNo;
        TextView tvTypeOfApp;
        TextView tvApplAllottedTo;
        TextView tvApplCreatedDate;
        TextView tvAllottedDate;
        TextView tvCompletionDate;
        TextView tvtypofAppNm;
        TextView tvSurveyNum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDistName = itemView.findViewById(R.id.tvDistName);
            tvTalukaName = itemView.findViewById(R.id.tvTalukName);
            tvHobliiName = itemView.findViewById(R.id.tvHobliName);
            tvVillageName = itemView.findViewById(R.id.tvVillName);
            tvAppNo = itemView.findViewById(R.id.tvAppNo);
            tvTypeOfApp = itemView.findViewById(R.id.tvTypeOfApp);
            tvApplAllottedTo = itemView.findViewById(R.id.tvApplAllottedTo);
            tvApplCreatedDate = itemView.findViewById(R.id.tvApplCreatedDate);
            tvAllottedDate = itemView.findViewById(R.id.tvAllottedDate);
            tvCompletionDate = itemView.findViewById(R.id.tvCompletionDate);
            tvtypofAppNm = itemView.findViewById(R.id.tvtypofAppNm);
            tvSurveyNum = itemView.findViewById(R.id.tvSurveyNum);
        }
    }
}
