package app.bmc.com.BHOOMI_MRTC.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.model.LoanWaiverBankResponseData;
import app.bmc.com.BHOOMI_MRTC.screens.ShowLoanWaiverReportBankWise;


public class LoanWaiverBankWiseDetailsAdapter extends RecyclerView.Adapter<LoanWaiverBankWiseDetailsAdapter.MyViewHolder> {

   private  List<LoanWaiverBankResponseData> clist ;
   private ShowLoanWaiverReportBankWise activity;


    public LoanWaiverBankWiseDetailsAdapter(List<LoanWaiverBankResponseData> myBankDataList, ShowLoanWaiverReportBankWise activity) {
        this.clist = myBankDataList;
        this.activity = activity;
    }

    public LoanWaiverBankWiseDetailsAdapter(List<LoanWaiverBankResponseData> myBankDataList, ShowLoanWaiverReportBankWise activity,int i) {
        this.clist = myBankDataList;
        this.activity = activity;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvBDistName;
        public TextView tvLWBankName;
        public TextView tvLWLoanType;
        public TextView tvLWTotalNoOfLonee;
        public TextView tvLWLoanAmount;

        public TextView tvLWEligibleLoans;
        public TextView tvLWEligibleLoanAmount;

        public TextView tvLWGreenListLoan;
        public TextView tvLWGreenListAmount;
        public TextView tvLWPaidLoan;
        public TextView tvLWPaidLoanAmount;

        public TextView tvLWTranPendingDue;
        public TextView tvLWRationCardMismatch;

        public TextView tvLWPercentage;


        public MyViewHolder(View view) {
            super(view);

            tvBDistName = view.findViewById(R.id.tvBDistName);
            tvLWBankName = view.findViewById(R.id.tvLWBankName);
            tvLWLoanType = view.findViewById(R.id.tvLWLoanType);
            tvLWTotalNoOfLonee = view.findViewById(R.id.tvLWTotalNoOfLonee);
            tvLWLoanAmount = view.findViewById(R.id.tvLWLoanAmount);

            tvLWEligibleLoans = view.findViewById(R.id.tvLWEligibleLoans);
            tvLWEligibleLoanAmount = view.findViewById(R.id.tvLWEligibleLoanAmount);

            tvLWGreenListLoan = view.findViewById(R.id.tvLWGreenListLoan);
            tvLWGreenListAmount = view.findViewById(R.id.tvLWGreenListAmount);

            tvLWPaidLoan = view.findViewById(R.id.tvLWPaidLoan);

            tvLWPaidLoanAmount = view.findViewById(R.id.tvLWPaidLoanAmount);
            tvLWTranPendingDue = view.findViewById(R.id.tvLWTranPendingDue);
            tvLWRationCardMismatch = view.findViewById(R.id.tvLWRationCardMismatch);
            tvLWPercentage = view.findViewById(R.id.tvLWPercentage);
        }

    }


    @NonNull
    @Override
    public LoanWaiverBankWiseDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.loan_bank_data_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LoanWaiverBankWiseDetailsAdapter.MyViewHolder holder, int position) {


        if(clist.size() == 0)
        {
            Toast.makeText(activity, "No Data Found!", Toast.LENGTH_SHORT).show();
        }else
        {
            holder.tvBDistName.setText(clist.get(position).getDISTRICT_NAME());
            holder.tvLWBankName.setText(clist.get(position).getBnk_nme_en());
            holder.tvLWLoanType.setText(clist.get(position).getLoantype());
            holder. tvLWTotalNoOfLonee.setText(clist.get(position).getTotal());
            holder. tvLWLoanAmount.setText(clist.get(position).getLoanAmount());

            holder. tvLWEligibleLoans.setText(clist.get(position).getEligibleLoan());
            holder. tvLWEligibleLoanAmount.setText(clist.get(position).getEligibleLoanAmount());

            holder. tvLWGreenListLoan.setText(clist.get(position).getGreenListLoans());
            holder. tvLWGreenListAmount.setText(clist.get(position).getGreenListAmount());
            holder. tvLWPaidLoan.setText(clist.get(position).getPaidLoans());
            holder. tvLWPaidLoanAmount.setText(clist.get(position).getPaidLoanAmount());

            holder. tvLWTranPendingDue.setText(clist.get(position).getCoopCommon());
            holder. tvLWRationCardMismatch.setText(clist.get(position).getAuthenticationFailed());
            holder.tvLWPercentage.setText(clist.get(position).getPerc());
        }

    }

    @Override
    public int getItemCount() {
        return clist.size();
    }
}

