package app.bmc.com.BHOOMI_MRTC.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.model.BankPaymentRationTableData;
import app.bmc.com.BHOOMI_MRTC.screens.ClwsStatusRationCardDetails;

public class BankPaymentRationDetailsAdapter extends RecyclerView.Adapter<BankPaymentRationDetailsAdapter.MyViewHolder> {


    private List<BankPaymentRationTableData> crationlist;
    private ClwsStatusRationCardDetails activity;


    public BankPaymentRationDetailsAdapter(List<BankPaymentRationTableData> cBankdataList, ClwsStatusRationCardDetails activity) {
        this.crationlist = cBankdataList;
        this.activity = activity;
    }


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

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bank_payment_details_list, parent, false);

        return new BankPaymentRationDetailsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvBPDClwsId.setText(crationlist.get(position).getPCUST_ID());
        holder.tvBPDCLoneeName.setText(crationlist.get(position).getCustomerName());
        holder.tvBPDCAccountNumber.setText(crationlist.get(position).getLoanAccount());
        holder.tvBPDCLoanType.setText(crationlist.get(position).getLoanType());
        holder.tvBPDCLoanAmountWaiver.setText(crationlist.get(position).getMaxAmount());
        holder.tvBPDCLoanTotalWaiverAmount.setText(crationlist.get(position).getPayingAmount());
        holder.tvBPDCBalanceWaiverAmount.setText(crationlist.get(position).getProrataAmount());
        holder.tvBPDCPaymentStatus.setText(crationlist.get(position).getPPMT_Status());
        holder.tvBPDCPaidDate.setText(crationlist.get(position).getPAYMENT_DT());
    }

    @Override
    public int getItemCount() {
        return crationlist.size();
    }

}
