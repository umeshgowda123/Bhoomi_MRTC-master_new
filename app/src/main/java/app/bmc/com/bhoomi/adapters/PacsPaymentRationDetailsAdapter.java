package app.bmc.com.bhoomi.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import app.bmc.com.bhoomi.R;
import app.bmc.com.bhoomi.model.PacsPaymentRationTableData;
import app.bmc.com.bhoomi.screens.ClwsStatusRationCardDetails;

public class PacsPaymentRationDetailsAdapter extends RecyclerView.Adapter<PacsPaymentRationDetailsAdapter.MyViewHolder> {

    private List<PacsPaymentRationTableData> pacPayRationdetailist;
    private ClwsStatusRationCardDetails activity;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvPacPaymentClwsId;
        public TextView tvPacPaymentLoneeId;
        public TextView tvPacPaymentAccountNumber;
        public TextView tvPacPaymentLoanType;
        public TextView tvPacPaymentLoanAmountWaiver;
        public TextView tvPacPaymentTotalWaiverAmountReportDate;
        public TextView tvPacPaymentStatus;
        public TextView tvPacPaymentPaidDate;


        public MyViewHolder(View view) {
            super(view);
            tvPacPaymentClwsId = (TextView) view.findViewById(R.id.tvPacPaymentClwsId);
            tvPacPaymentLoneeId = (TextView) view.findViewById(R.id.tvPacPaymentLoneeId);
            tvPacPaymentAccountNumber = (TextView) view.findViewById(R.id.tvPacPaymentAccountNumber);
            tvPacPaymentLoanType = (TextView) view.findViewById(R.id.tvPacPaymentLoanType);
            tvPacPaymentLoanAmountWaiver = (TextView) view.findViewById(R.id.tvPacPaymentLoanAmountWaiver);
            tvPacPaymentTotalWaiverAmountReportDate = (TextView) view.findViewById(R.id.tvPacPaymentTotalWaiverAmountReportDate);
            tvPacPaymentStatus = (TextView) view.findViewById(R.id.tvPacPaymentStatus);
            tvPacPaymentPaidDate = (TextView) view.findViewById(R.id.tvPacPaymentPaidDate);

        }
    }

    public PacsPaymentRationDetailsAdapter(List<PacsPaymentRationTableData> pacspaymentList, ClwsStatusRationCardDetails activity) {
        this.pacPayRationdetailist = pacspaymentList;
        this.activity= activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
         View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pacs_payment_details_list, parent, false);

        return new PacsPaymentRationDetailsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvPacPaymentClwsId.setText(pacPayRationdetailist.get(position).getPCUST_ID());
        holder.tvPacPaymentLoneeId.setText(pacPayRationdetailist.get(position).getPCUST_NME());
        holder.tvPacPaymentAccountNumber.setText(pacPayRationdetailist.get(position).getPCUST_LINKED_SAVED_ACNT_NO());
        holder.tvPacPaymentLoanType.setText("CROP Loan");
        holder.tvPacPaymentLoanAmountWaiver.setText(pacPayRationdetailist.get(position).getPCS_LIAB_10072018());
        holder.tvPacPaymentTotalWaiverAmountReportDate.setText(pacPayRationdetailist.get(position).getPayingAmount());
        holder.tvPacPaymentStatus.setText(pacPayRationdetailist.get(position).getPPMT_Status());
        holder.tvPacPaymentPaidDate.setText(pacPayRationdetailist.get(position).getPAYMENT_DT());
    }

    @Override
    public int getItemCount() {
        Log.d("pacPayRationdetailist",pacPayRationdetailist.size()+"");
        return pacPayRationdetailist.size();

    }
}
