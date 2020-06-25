package app.bmc.com.BHOOMI_MRTC.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.model.Afdvt_ReqSts_BasedOnAfdvtIdTable;
import app.bmc.com.BHOOMI_MRTC.model.BankLoanTableData;
import app.bmc.com.BHOOMI_MRTC.screens.CommercialBankLoanReportDocActivity;
import app.bmc.com.BHOOMI_MRTC.screens.LandConversionBasedOnAffidavit;
import app.bmc.com.BHOOMI_MRTC.screens.LandConversionBasedOnUserId;

public class LandConversionBasedOnUserIDAdapter extends RecyclerView.Adapter<LandConversionBasedOnUserIDAdapter.ViewHolder> {
    List<Afdvt_ReqSts_BasedOnAfdvtIdTable> list ;
    LandConversionBasedOnUserId activity;
    private String req_id;
    private String req_Aid;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.land_conversion_based_on_affidavit_userid,viewGroup,false);
        LandConversionBasedOnUserIDAdapter.ViewHolder viewHolder = new LandConversionBasedOnUserIDAdapter.ViewHolder(v);
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
        public ImageView ivEndorsement_Report;
        public ImageView ivTransaction_Report;
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
            ivEndorsement_Report = itemView.findViewById(R.id.iv1);
            ivTransaction_Report = itemView.findViewById(R.id.iv2);
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

            ivEndorsement_Report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Afdvt_ReqSts_BasedOnAfdvtIdTable clickedDataItem = list.get(pos);
                        req_id = clickedDataItem.getREQ_ID();
                        Log.d("IDDDDD",req_id);
                    }
//                    Intent intent = new Intent(activity, .class);
//                    intent.putExtra("REQ_ID",req_id);
//                    activity.startActivity(intent);
                }
            });
            ivTransaction_Report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Afdvt_ReqSts_BasedOnAfdvtIdTable clickedDataItem = list.get(pos);
                        req_Aid = clickedDataItem.getREQ_AID();
                        Log.d("IDDDDD",req_Aid);
                    }
//                    Intent intent = new Intent(activity, .class);
//                    intent.putExtra("REQ_AID",req_Aid);
//                    activity.startActivity(intent);
                }
            });

        }
    }
    public LandConversionBasedOnUserIDAdapter(List<Afdvt_ReqSts_BasedOnAfdvtIdTable> paymentList, LandConversionBasedOnUserId activity) {
        this.list = paymentList;
        this.activity = activity;
    }
}
