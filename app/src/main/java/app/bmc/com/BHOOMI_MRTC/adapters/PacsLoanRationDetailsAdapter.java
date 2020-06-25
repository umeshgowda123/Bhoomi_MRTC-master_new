package app.bmc.com.BHOOMI_MRTC.adapters;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.model.PacsLoanRationTableData;
import app.bmc.com.BHOOMI_MRTC.screens.ClwsStatusRationCardDetails;
import app.bmc.com.BHOOMI_MRTC.screens.PacsLoanAffidavitView;
import app.bmc.com.BHOOMI_MRTC.screens.PacsLoanReportView;

public class PacsLoanRationDetailsAdapter extends RecyclerView.Adapter<PacsLoanRationDetailsAdapter.MyViewHolder> {


    private List<PacsLoanRationTableData> pacsRcLoanList;
    private ClwsStatusRationCardDetails activity;


    private String appId;
    private String customerId;
    private String bankId;


    public PacsLoanRationDetailsAdapter(List<PacsLoanRationTableData> pacsList, ClwsStatusRationCardDetails activity) {
        this.pacsRcLoanList = pacsList;
        this.activity= activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivReortfile;
        private ImageView ivAffidavitfile;
        public TextView tvPacsCLWSID;
        public TextView tvPacsDistrictID;
        public TextView tvPacsTalukName;
        public TextView tvPacsBankName;
        public TextView tvPacsName;
        public TextView tvPacsFarmerName;
        public TextView tvPacsRationCardNo;
        public TextView tvPacsLoanType;
        public TextView tvPacsShareNumber;
        public TextView tvPacsOutStandingAmount;
        public TextView tvPacsStatus;

        public MyViewHolder(View view) {
            super(view);

            ivReortfile = view.findViewById(R.id.ivReortfile);
            ivAffidavitfile = view.findViewById(R.id.ivAffidavitfile);
            tvPacsCLWSID = (TextView) view.findViewById(R.id.tvPacsCLWSID);
            tvPacsDistrictID = (TextView) view.findViewById(R.id.tvPacsDistrictID);
            tvPacsTalukName = (TextView) view.findViewById(R.id.tvPacsTalukName);
            tvPacsBankName = (TextView) view.findViewById(R.id.tvPacsBankName);
            tvPacsName = (TextView) view.findViewById(R.id.tvPacsName);
            tvPacsFarmerName = (TextView) view.findViewById(R.id.tvPacsFarmerName);
            tvPacsRationCardNo = (TextView) view.findViewById(R.id.tvPacsRationCardNo);
            tvPacsLoanType = (TextView) view.findViewById(R.id.tvPacsLoanType);
            tvPacsShareNumber = (TextView) view.findViewById(R.id.tvPacsShareNumber);
            tvPacsOutStandingAmount = (TextView) view.findViewById(R.id.tvPacsOutStandingAmount);
            tvPacsStatus = (TextView) view.findViewById(R.id.tvPacsStatus);

            appId = "1516978b-4b63-4072-9ecf-560dee62baff";

            ivReortfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        PacsLoanRationTableData clickedDataItem = pacsRcLoanList.get(pos);
                        customerId = clickedDataItem.getTRN_CUSTID();
                        bankId = clickedDataItem.getTRN_BNKID();
                    }

                    Intent intent = new Intent(activity, PacsLoanReportView.class);
                    intent.putExtra("Cust_ID",customerId);
                    intent.putExtra("Bank_ID",bankId);
                    intent.putExtra("APP_ID",appId);
                    activity.startActivity(intent);

                }
            });

          ivAffidavitfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        PacsLoanRationTableData clickedDataItem = pacsRcLoanList.get(pos);
                        customerId = clickedDataItem.getTRN_CUSTID();
                        bankId = clickedDataItem.getTRN_BNKID();
                    }

                    Intent intent = new Intent(activity, PacsLoanAffidavitView.class);
                    intent.putExtra("Cust_ID",customerId);
                    intent.putExtra("Bank_ID",bankId);
                    intent.putExtra("APP_ID",appId);
                    activity.startActivity(intent);
                }
            });
        }
    }



    @NonNull
    @Override
    public PacsLoanRationDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pacs_loan_details, parent, false);

        return new PacsLoanRationDetailsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PacsLoanRationDetailsAdapter.MyViewHolder holder, int position) {

        holder.tvPacsCLWSID.setText(pacsRcLoanList.get(position).getTRN_CUSTID());
        holder.tvPacsDistrictID.setText(pacsRcLoanList.get(position).getFMLY_DISTNME());
        holder.tvPacsTalukName.setText(pacsRcLoanList.get(position).getFMLY_TLKNME());
        holder.tvPacsBankName.setText(pacsRcLoanList.get(position).getBNK_NME_EN());
        holder.tvPacsName.setText(pacsRcLoanList.get(position).getBNK_BRNCH_NME());
        holder.tvPacsFarmerName.setText(pacsRcLoanList.get(position).getTRN_LOONENME());
        holder.tvPacsRationCardNo.setText(pacsRcLoanList.get(position).getTRN_RCNO());
        holder.tvPacsLoanType.setText(pacsRcLoanList.get(position).getPCS_PUR());
        holder.tvPacsShareNumber.setText(pacsRcLoanList.get(position).getOTRN_SHARENO());
        holder.tvPacsOutStandingAmount.setText(pacsRcLoanList.get(position).getPCS_LIAB_10072018());
        holder.tvPacsStatus.setText(pacsRcLoanList.get(position).getSTS_DESC());
    }

    @Override
    public int getItemCount() {
        return pacsRcLoanList.size();
    }
}
