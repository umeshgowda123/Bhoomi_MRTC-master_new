package app.bmc.com.BHOOMI_MRTC.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.model.PacsWaiverBankResponseData;
import app.bmc.com.BHOOMI_MRTC.screens.ShowLoanWaiverPacsReportBankWise;


public class LoanWaiverPacsWiseDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   private  List<PacsWaiverBankResponseData> clist ;
   private ShowLoanWaiverPacsReportBankWise activity;

    private final int VIEW_TYPE_ITEM = 0;

    public LoanWaiverPacsWiseDetailsAdapter(List<PacsWaiverBankResponseData> myBankDataList, ShowLoanWaiverPacsReportBankWise activity) {
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
        TextView txt;

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
            txt = view.findViewById(R.id.txt);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.loan_bank_data_list, parent, false);

            return new MyViewHolder(itemView);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if(clist.size() == 0)
        {
            Toast.makeText(activity, "No Data Found!", Toast.LENGTH_SHORT).show();
        }else
        {

            if (holder instanceof MyViewHolder) {
                populateItemRows((MyViewHolder) holder, position);
            } else if (holder instanceof LoadingViewHolder) {
                showLoadingView();
            }
        }

    }

    @Override
    public int getItemCount() {

        return clist == null ? 0 : clist.size();
    }

    @Override
    public int getItemViewType(int position) {
        int VIEW_TYPE_LOADING = 1;
        return clist.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView() {
        //ProgressBar would be displayed

    }

    private void populateItemRows(MyViewHolder holder, int position) {
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
        holder.txt.setText(R.string.loan_waiver_report_pacs);
    }
}

