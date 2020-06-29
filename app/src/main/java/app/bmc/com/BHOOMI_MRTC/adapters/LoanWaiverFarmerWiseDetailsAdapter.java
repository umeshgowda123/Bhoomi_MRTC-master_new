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
import app.bmc.com.BHOOMI_MRTC.model.LoanWaiverFramerWiseResponseData;
import app.bmc.com.BHOOMI_MRTC.screens.ShowLoanWaiverReportFarmerWise;


public class LoanWaiverFarmerWiseDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;

    private  List<LoanWaiverFramerWiseResponseData> clist ;
   private ShowLoanWaiverReportFarmerWise activity;


    public LoanWaiverFarmerWiseDetailsAdapter(List<LoanWaiverFramerWiseResponseData> myBankDataList, ShowLoanWaiverReportFarmerWise activity) {
        this.clist = myBankDataList;
        this.activity = activity;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvFVillage;
        public TextView tvFCustomerName;
        public TextView tvFCustomerRName;
        public TextView tvFBankName;
        public TextView tvFBankBranchName;

        public TextView TvFLoanAccNo;
        public TextView tvFLoanType;

        public TextView tvFLiabilityAmount;
        public TextView tvFInGreenList;
        public TextView tvFRasonIfNo;
        public TextView tvFLoanWaiverDisbured;

        public TextView tvFPaidAmount;
        public TextView tvFLoanWaiverDisburedCom;
        public TextView tvFRationCardNo;


        public MyViewHolder(View view) {
            super(view);

            tvFVillage = view.findViewById(R.id.tvFVillage);
            tvFCustomerName = view.findViewById(R.id.tvFCustomerName);
            tvFCustomerRName = view.findViewById(R.id.tvFCustomerRName);
            tvFBankName = view.findViewById(R.id.tvFBankName);
            tvFBankBranchName = view.findViewById(R.id.tvFBankBranchName);

            TvFLoanAccNo = view.findViewById(R.id.TvFLoanAccNo);
            tvFLoanType = view.findViewById(R.id.tvFLoanType);

            tvFLiabilityAmount = view.findViewById(R.id.tvFLiabilityAmount);
            tvFInGreenList = view.findViewById(R.id.tvFInGreenList);

            tvFRasonIfNo = view.findViewById(R.id.tvFRasonIfNo);

            tvFLoanWaiverDisbured = view.findViewById(R.id.tvFLoanWaiverDisbured);
            tvFPaidAmount = view.findViewById(R.id.tvFPaidAmount);
            tvFLoanWaiverDisburedCom = view.findViewById(R.id.tvFLoanWaiverDisburedCom);
            tvFRationCardNo = view.findViewById(R.id.tvFRationCardNo);

        }

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_farmer_bank_data_list, parent, false);
            return new MyViewHolder(view);
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
        return  clist == null ? 0 : clist.size();
    }

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

        holder.tvFVillage.setText(clist.get(position).getLnd_villgnme());
        holder.tvFCustomerName.setText(clist.get(position).getCustomerName());
        holder.tvFCustomerRName.setText(clist.get(position).getCustomerRelativeName());
        holder. tvFBankName.setText(clist.get(position).getBnk_nme_en());
        holder. tvFBankBranchName.setText(clist.get(position).getBNK_BRNCH_NME());

        holder. TvFLoanAccNo.setText(clist.get(position).getLoanAccount());
        holder. tvFLoanType.setText(clist.get(position).getLoantype());

        holder. tvFLiabilityAmount.setText(clist.get(position).getAmountLIAB20171231());
        holder. tvFInGreenList.setText(clist.get(position).getIngreenList());
        holder. tvFRasonIfNo.setText(clist.get(position).getREason());
        holder. tvFLoanWaiverDisbured.setText(clist.get(position).getLoanWaiverDisbursed());

        holder. tvFPaidAmount.setText(clist.get(position).getPaidamt());
        holder. tvFLoanWaiverDisburedCom.setText(clist.get(position).getLoanWaiverdisbursedCompleted());
        holder.tvFRationCardNo.setText(clist.get(position).getRationCardNum());
    }
}

