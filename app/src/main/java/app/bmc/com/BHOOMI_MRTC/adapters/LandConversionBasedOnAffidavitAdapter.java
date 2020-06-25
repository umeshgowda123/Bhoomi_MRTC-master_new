package app.bmc.com.BHOOMI_MRTC.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.model.Afdvt_ReqSts_BasedOnAfdvtIdTable;
import app.bmc.com.BHOOMI_MRTC.screens.LandConversionBasedOnAffidavit;

public class LandConversionBasedOnAffidavitAdapter extends RecyclerView.Adapter<LandConversionBasedOnAffidavitAdapter.ViewHolder> {
    List<Afdvt_ReqSts_BasedOnAfdvtIdTable> list ;
    LandConversionBasedOnAffidavit activity;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.land_conversion_based_on_affidavit_userid,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvAffidavitID.setText(list.get(i).getREQ_AID());
        viewHolder.tvReqID.setText(list.get(i).getREQ_ID());
        viewHolder.tvDistID.setText(list.get(i).getDISTRICT_NAME());
        viewHolder.tvTalukName.setText(list.get(i).getTALUKA_NAME());
        viewHolder.tvHobliName.setText(list.get(i).getHOBLI_NAME());
        viewHolder.tvVillName.setText(list.get(i).getVILLAGE_NAME());
        viewHolder.tvSurveydNo.setText(list.get(i).getSurveyno());
        viewHolder.tvCreatedDate.setText(list.get(i).getREQ_CDTE());
        viewHolder.tvTypeOfConverSion.setText(list.get(i).getREQ_TYPE());
        viewHolder.tvStatus.setText(list.get(i).getStatus_Description());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv1;
        public ImageView iv2;
        public TextView tvAffidavitID;
        public TextView tvReqID;
        public TextView tvDistID;
        public TextView tvTalukName;
        public TextView tvHobliName;
        public TextView tvVillName;
        public TextView tvSurveydNo;
        public TextView tvCreatedDate;
        public TextView tvTypeOfConverSion;
        public TextView tvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv1 = itemView.findViewById(R.id.iv1);
            iv2 = itemView.findViewById(R.id.iv2);
            tvAffidavitID = itemView.findViewById(R.id.tvAffidavitID);
            tvReqID = itemView.findViewById(R.id.tvReqID);
            tvDistID = itemView.findViewById(R.id.tvDistID);
            tvTalukName = itemView.findViewById(R.id.tvTalukName);
            tvHobliName = itemView.findViewById(R.id.tvHobliName);
            tvVillName = itemView.findViewById(R.id.tvVillName);
            tvSurveydNo = itemView.findViewById(R.id.tvSurveydNo);
            tvCreatedDate = itemView.findViewById(R.id.tvCreatedDate);
            tvTypeOfConverSion = itemView.findViewById(R.id.tvTypeOfConverSion);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }

    public LandConversionBasedOnAffidavitAdapter(List<Afdvt_ReqSts_BasedOnAfdvtIdTable> paymentList, LandConversionBasedOnAffidavit activity) {
        this.list = paymentList;
        this.activity = activity;
    }


}
