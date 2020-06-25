package app.bmc.com.BHOOMI_MRTC.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.model.Afdvt_ReqSts_BasedOnAfdvtIdTable;
import app.bmc.com.BHOOMI_MRTC.model.GetLandConversionFinalOrders_Table;
import app.bmc.com.BHOOMI_MRTC.screens.ConversionFinalOrders_BasedOnReq_ID;
import app.bmc.com.BHOOMI_MRTC.screens.Endorsement_ReportWebView;
import app.bmc.com.BHOOMI_MRTC.screens.LandConversionBasedOnAffidavit;

public class LandConversionFinalOrdersAdapter extends RecyclerView.Adapter<LandConversionFinalOrdersAdapter.ViewHolder> {

    List<GetLandConversionFinalOrders_Table> list ;
    ConversionFinalOrders_BasedOnReq_ID activity;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.landconversionfinalorders_list,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvReqID.setText(list.get(i).getREQ_ID());
        viewHolder.tvDistID.setText(list.get(i).getDISTRICT_NAME());
        viewHolder.tvTalukName.setText(list.get(i).getTALUKA_NAME());
        viewHolder.tvHobliName.setText(list.get(i).getHOBLI_NAME());
        viewHolder.tvVillName.setText(list.get(i).getVILLAGE_NAME());
        viewHolder.tvSurveydNo.setText(list.get(i).getSURVEYNO());
        viewHolder.tvTypeOfConverSion.setText(list.get(i).getTypeOfConv());
        viewHolder.tvStatus.setText(list.get(i).getWFL_STG());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivFinalOrder;
        TextView tvReqID;
        TextView tvDistID;
        TextView tvTalukName;
        TextView tvHobliName;
        TextView tvVillName;
        TextView tvSurveydNo;
        TextView tvTypeOfConverSion;
        TextView tvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFinalOrder = itemView.findViewById(R.id.iv1);
            tvReqID = itemView.findViewById(R.id.tvReqID);
            tvDistID = itemView.findViewById(R.id.tvDistID);
            tvTalukName = itemView.findViewById(R.id.tvTalukName);
            tvHobliName = itemView.findViewById(R.id.tvHobliName);
            tvVillName = itemView.findViewById(R.id.tvVillName);
            tvSurveydNo = itemView.findViewById(R.id.tvSurveydNo);
            tvTypeOfConverSion = itemView.findViewById(R.id.tvTypeOfConverSion);
            tvStatus = itemView.findViewById(R.id.tvStatus);

        }
    }

    public LandConversionFinalOrdersAdapter(List<GetLandConversionFinalOrders_Table> paymentList, ConversionFinalOrders_BasedOnReq_ID activity) {
        this.list = paymentList;
        this.activity = activity;
    }
}
