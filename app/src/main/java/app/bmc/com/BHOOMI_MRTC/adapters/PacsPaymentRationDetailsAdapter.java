package app.bmc.com.BHOOMI_MRTC.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.model.PacsPaymentRationTableData;

public class PacsPaymentRationDetailsAdapter extends RecyclerView.Adapter<PacsPaymentRationDetailsAdapter.MyViewHolder> {

    private List<PacsPaymentRationTableData> pacPayRationdetailist;


    public static class MyViewHolder extends RecyclerView.ViewHolder {

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
            tvPacPaymentClwsId = view.findViewById(R.id.tvPacPaymentClwsId);
            tvPacPaymentLoneeId = view.findViewById(R.id.tvPacPaymentLoneeId);
            tvPacPaymentAccountNumber = view.findViewById(R.id.tvPacPaymentAccountNumber);
            tvPacPaymentLoanType = view.findViewById(R.id.tvPacPaymentLoanType);
            tvPacPaymentLoanAmountWaiver = view.findViewById(R.id.tvPacPaymentLoanAmountWaiver);
            tvPacPaymentTotalWaiverAmountReportDate = view.findViewById(R.id.tvPacPaymentTotalWaiverAmountReportDate);
            tvPacPaymentStatus = view.findViewById(R.id.tvPacPaymentStatus);
            tvPacPaymentPaidDate = view.findViewById(R.id.tvPacPaymentPaidDate);

        }
    }

    public PacsPaymentRationDetailsAdapter(List<PacsPaymentRationTableData> pacspaymentList) {
        this.pacPayRationdetailist = pacspaymentList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
         View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pacs_payment_details_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
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
        return pacPayRationdetailist.size();

    }
}
