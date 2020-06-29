package app.bmc.com.BHOOMI_MRTC.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.model.PacsPaymentTableData;

public class PacsPaymentDetailsAdapter extends RecyclerView.Adapter<PacsPaymentDetailsAdapter.MyViewHolder> {

    private List<PacsPaymentTableData> pacPayDetaillist;

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

    public PacsPaymentDetailsAdapter(List<PacsPaymentTableData> pacspaymentList) {
        this.pacPayDetaillist = pacspaymentList;
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
        holder.tvPacPaymentClwsId.setText(pacPayDetaillist.get(position).getPCUST_ID());
        holder.tvPacPaymentLoneeId.setText(pacPayDetaillist.get(position).getPCUST_NME());
        holder.tvPacPaymentAccountNumber.setText(pacPayDetaillist.get(position).getPCUST_LINKED_SAVED_ACNT_NO());
        holder.tvPacPaymentLoanType.setText("CROP Loan");
        holder.tvPacPaymentLoanAmountWaiver.setText(pacPayDetaillist.get(position).getPCS_LIAB_10072018());
        holder.tvPacPaymentTotalWaiverAmountReportDate.setText(pacPayDetaillist.get(position).getPayingAmount());
        String ppmtStstus = pacPayDetaillist.get(position).getPPMT_Status();
        if(ppmtStstus.equals("1"))
        {
            holder.tvPacPaymentStatus.setText("Payment Done");
        }
        if(ppmtStstus.equals("2"))
        {
            holder.tvPacPaymentStatus.setText("Payment Failed");
        }
        if(ppmtStstus.equals("3"))
        {
            holder.tvPacPaymentStatus.setText("Payment Initiated");
        }
        if(ppmtStstus.equals("4"))
        {
            holder.tvPacPaymentStatus.setText("File Aadhaar, Ration Card & Land Sy No along with FSD for approval");
        }

        holder.tvPacPaymentPaidDate.setText(pacPayDetaillist.get(position).getPAYMENT_DT());

    }

    @Override
    public int getItemCount() {
        Log.d("pacPayDetaillist",pacPayDetaillist.size()+"");
        return pacPayDetaillist.size();
    }





}
