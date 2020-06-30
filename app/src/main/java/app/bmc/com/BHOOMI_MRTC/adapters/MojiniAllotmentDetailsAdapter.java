package app.bmc.com.BHOOMI_MRTC.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.model.MojiniAllotementDetailsTable;
import app.bmc.com.BHOOMI_MRTC.screens.Mojini_Allotment_Details_BasedOnAppNo;

public class MojiniAllotmentDetailsAdapter extends RecyclerView.Adapter<MojiniAllotmentDetailsAdapter.ViewHolder>{
    private List<MojiniAllotementDetailsTable> list;
    private Mojini_Allotment_Details_BasedOnAppNo activity;

    public MojiniAllotmentDetailsAdapter(List<MojiniAllotementDetailsTable> allotementDetailsTableList, Mojini_Allotment_Details_BasedOnAppNo mojini_allotment_details_basedOnAppNo) {
        this.list = allotementDetailsTableList;
        this.activity = mojini_allotment_details_basedOnAppNo;
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDistName;
        public TextView tvTalukaName;
        public TextView tvHobliiName;
        public TextView tvVillageName;
        public TextView tvAppNo;
        public TextView tvTypeOfApp;
        public TextView tvApplAllottedTo;
        public TextView tvApplCreatedDate;
        public TextView tvAllottedDate;
        public TextView tvCompletionDate;
        public TextView tvtypofAppNm;
        public TextView tvSurveyNum;

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
