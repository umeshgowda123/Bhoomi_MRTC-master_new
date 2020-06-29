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
import app.bmc.com.BHOOMI_MRTC.model.LoanWaiverPacsBranchResponseData;
import app.bmc.com.BHOOMI_MRTC.screens.ShowLoanWaiverPacsReportBranchWise;


public class LoanWaiverPacsBranchWiseDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;

    private List<LoanWaiverPacsBranchResponseData> clist;
    private ShowLoanWaiverPacsReportBranchWise activity;


    public LoanWaiverPacsBranchWiseDetailsAdapter(List<LoanWaiverPacsBranchResponseData> myBankDataList, ShowLoanWaiverPacsReportBranchWise activity) {
        this.clist = myBankDataList;
        this.activity = activity;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvBranchDist;
        public TextView tvBranchBankName;
        public TextView tvBankBranchName;
        public TextView tvBranchLoanType;
        public TextView tvBranchTotalLonee;
        public TextView tvBranchLoanAmount;
        public TextView TvBranchPendingDue;
        public TextView tvBranchRationMisMatch;
        public TextView tvBranchEligibleLoans;
        public TextView tvBranchEligibleLoanAmount;
        public TextView tvBranchGreenListLoan;
        public TextView tvBranchGreenListAmount;
        public TextView tvBranchPaidLoan;
        public TextView tvBranchPaidAmount;
        public TextView tvBranchPercentage;
        TextView txt;


        public MyViewHolder(View view) {
            super(view);

            tvBranchDist = view.findViewById(R.id.tvBranchDist);
            tvBranchBankName = view.findViewById(R.id.tvBranchBankName);
            tvBankBranchName = view.findViewById(R.id.tvBankBranchName);
            tvBranchLoanType = view.findViewById(R.id.tvBranchLoanType);
            tvBranchTotalLonee = view.findViewById(R.id.tvBranchTotalLonee);

            tvBranchLoanAmount = view.findViewById(R.id.tvBranchLoanAmount);
            TvBranchPendingDue = view.findViewById(R.id.TvBranchPendingDue);

            tvBranchRationMisMatch = view.findViewById(R.id.tvBranchRationMisMatch);
            tvBranchEligibleLoans = view.findViewById(R.id.tvBranchEligibleLoans);

            tvBranchEligibleLoanAmount = view.findViewById(R.id.tvBranchEligibleLoanAmount);

            tvBranchGreenListLoan = view.findViewById(R.id.tvBranchGreenListLoan);
            tvBranchGreenListAmount = view.findViewById(R.id.tvBranchGreenListAmount);
            tvBranchPaidLoan = view.findViewById(R.id.tvBranchPaidLoan);
            tvBranchPaidAmount = view.findViewById(R.id.tvBranchPaidAmount);
            tvBranchPercentage = view.findViewById(R.id.tvBranchPercentage);
            txt = view.findViewById(R.id.txt);

        }

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.loan_branch_bank_data_list, parent, false);

            return new MyViewHolder(itemView);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if (clist.size() == 0) {
            Toast.makeText(activity, "No Data Found!", Toast.LENGTH_SHORT).show();
        } else {

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
        holder.tvBranchDist.setText(clist.get(position).getDISTRICT_NAME());
        holder.tvBranchBankName.setText(clist.get(position).getBnk_nme_en());
        holder.tvBankBranchName.setText(clist.get(position).getBNK_BRNCH_NME());
        holder.tvBranchLoanType.setText(clist.get(position).getLoantype());
        holder.tvBranchTotalLonee.setText(clist.get(position).getTotal());
        holder.tvBranchLoanAmount.setText(clist.get(position).getLoanAmount());
        holder.TvBranchPendingDue.setText(clist.get(position).getCoopCommon());
        holder.tvBranchRationMisMatch.setText(clist.get(position).getAuthenticationFailed());
        holder.tvBranchEligibleLoans.setText(clist.get(position).getEligibleLoan());
        holder.tvBranchEligibleLoanAmount.setText(clist.get(position).getEligibleLoanAmount());
        holder.tvBranchGreenListLoan.setText(clist.get(position).getGreenListLoans());

        holder.tvBranchGreenListAmount.setText(clist.get(position).getGreenListAmount());
        holder.tvBranchPaidLoan.setText(clist.get(position).getPaidLoans());
        holder.tvBranchPaidAmount.setText(clist.get(position).getPaidLoanAmount());
        holder.tvBranchPercentage.setText(clist.get(position).getPerc());
        holder.txt.setText(R.string.loan_waiver_report_pacs_branch);
    }
}

