package app.bmc.com.BHOOMI_MRTC.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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


    public class MyViewHolder extends RecyclerView.ViewHolder {

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


        public MyViewHolder(View view) {
            super(view);

            tvBDistName = (TextView) view.findViewById(R.id.tvBDistName);
            tvLWBankName = (TextView) view.findViewById(R.id.tvLWBankName);
            tvLWLoanType = (TextView) view.findViewById(R.id.tvLWLoanType);
            tvLWTotalNoOfLonee = (TextView) view.findViewById(R.id.tvLWTotalNoOfLonee);
            tvLWLoanAmount = (TextView) view.findViewById(R.id.tvLWLoanAmount);

            tvLWEligibleLoans = (TextView) view.findViewById(R.id.tvLWEligibleLoans);
            tvLWEligibleLoanAmount = (TextView) view.findViewById(R.id.tvLWEligibleLoanAmount);

            tvLWGreenListLoan = (TextView) view.findViewById(R.id.tvLWGreenListLoan);
            tvLWGreenListAmount = (TextView) view.findViewById(R.id.tvLWGreenListAmount);

            tvLWPaidLoan = (TextView) view.findViewById(R.id.tvLWPaidLoan);

            tvLWPaidLoanAmount = (TextView) view.findViewById(R.id.tvLWPaidLoanAmount);
            tvLWTranPendingDue = (TextView) view.findViewById(R.id.tvLWTranPendingDue);
            tvLWRationCardMismatch = (TextView) view.findViewById(R.id.tvLWRationCardMismatch);




        }

    }


    @NonNull
    @Override
    public LoanWaiverBankWiseDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.loan_bank_data_list, parent, false);

        return new LoanWaiverBankWiseDetailsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LoanWaiverBankWiseDetailsAdapter.MyViewHolder holder, int position) {


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
        }

    }

    @Override
    public int getItemCount() {
        return clist.size();
    }
}

