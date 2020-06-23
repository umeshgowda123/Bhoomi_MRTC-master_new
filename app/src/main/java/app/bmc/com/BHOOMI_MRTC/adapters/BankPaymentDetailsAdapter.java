package app.bmc.com.BHOOMI_MRTC.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.model.BankPaymentTableData;
import app.bmc.com.BHOOMI_MRTC.screens.ClwsStatusDetails;

public class BankPaymentDetailsAdapter extends RecyclerView.Adapter<BankPaymentDetailsAdapter.MyViewHolder> {


    private List<BankPaymentTableData> paylist;
    private ClwsStatusDetails activity;


    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView tvBPDClwsId;
        public TextView tvBPDCLoneeName;
        public TextView tvBPDCAccountNumber;
        public TextView tvBPDCLoanType;
        public TextView tvBPDCLoanAmountWaiver;
        public TextView tvBPDCLoanTotalWaiverAmount;
        public TextView tvBPDCBalanceWaiverAmount;
        public TextView tvBPDCPaymentStatus;
        public TextView tvBPDCPaidDate;


        public MyViewHolder(View view) {
            super(view);
            tvBPDClwsId = (TextView) view.findViewById(R.id.tvBPDClwsId);
            tvBPDCLoneeName = (TextView) view.findViewById(R.id.tvBPDCLoneeName);
            tvBPDCAccountNumber = (TextView) view.findViewById(R.id.tvBPDCAccountNumber);
            tvBPDCLoanType = (TextView) view.findViewById(R.id.tvBPDCLoanType);
            tvBPDCLoanAmountWaiver = (TextView) view.findViewById(R.id.tvBPDCLoanAmountWaiver);
            tvBPDCLoanTotalWaiverAmount = (TextView) view.findViewById(R.id.tvBPDCLoanTotalWaiverAmount);
            tvBPDCBalanceWaiverAmount = (TextView) view.findViewById(R.id.tvBPDCBalanceWaiverAmount);
            tvBPDCPaymentStatus = (TextView) view.findViewById(R.id.tvBPDCPaymentStatus);
            tvBPDCPaidDate = (TextView) view.findViewById(R.id.tvBPDCPaidDate);

        }
    }

    public BankPaymentDetailsAdapter(List<BankPaymentTableData> paymentList, ClwsStatusDetails  activity) {
        this.paylist = paymentList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bank_payment_details_list, parent, false);

        return new BankPaymentDetailsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BankPaymentDetailsAdapter.MyViewHolder holder, int position) {
        holder.tvBPDClwsId.setText(paylist.get(position).getPCUST_ID());
        holder.tvBPDCLoneeName.setText(paylist.get(position).getCustomerName());
        holder.tvBPDCAccountNumber.setText(paylist.get(position).getLoanAccount());
        holder.tvBPDCLoanType.setText(paylist.get(position).getLoanType());
        holder.tvBPDCLoanAmountWaiver.setText(paylist.get(position).getMaxAmount());
        holder.tvBPDCLoanTotalWaiverAmount.setText(paylist.get(position).getPayingAmount());
        holder.tvBPDCBalanceWaiverAmount.setText(paylist.get(position).getProrataAmount());
        String bankpaymentstatus = paylist.get(position).getPPMT_Status();

        if(bankpaymentstatus.equals("1"))
        {
            holder.tvBPDCPaymentStatus.setText("Payment Done");
        }
        if(bankpaymentstatus.equals("2"))
        {
            holder.tvBPDCPaymentStatus.setText("Payment Failed");
        }
        if(bankpaymentstatus.equals("3"))
        {
            holder.tvBPDCPaymentStatus.setText("Payment Initiated");
        }
        if(bankpaymentstatus.equals("4"))
        {
            holder.tvBPDCPaymentStatus.setText("File Aadhaar, Ration Card & Land Sy No along with FSD for approval");
        }

        holder.tvBPDCPaidDate.setText(paylist.get(position).getPAYMENT_DT());

    }

    @Override
    public int getItemCount() {
        return paylist.size();
    }
}
