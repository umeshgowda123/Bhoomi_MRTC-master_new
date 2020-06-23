package app.bmc.com.BHOOMI_MRTC.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.model.BenificaryDataVlgWise;

import app.bmc.com.BHOOMI_MRTC.screens.ShowPariharaBenificiaryDetailsVlgWise;

public class VillageWiseBenificaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private List<BenificaryDataVlgWise> clist ;
    private ShowPariharaBenificiaryDetailsVlgWise activity;



    public VillageWiseBenificaryAdapter(List<BenificaryDataVlgWise> myBeneficaryList, ShowPariharaBenificiaryDetailsVlgWise activity) {
        this.clist = myBeneficaryList;
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvBAadharNumber;
        public TextView tvBBankName;
        public TextView tvBPStatus;
        public TextView tvBPaymentDate;
        public TextView tvBeneficiaryName;
        public TextView tvBankAccountNumber;
        public TextView tvBAmount;


        public MyViewHolder(View view) {
            super(view);

            tvBAadharNumber = (TextView) view.findViewById(R.id.tvBAadharNumber);
            tvBBankName = (TextView) view.findViewById(R.id.tvBBankName);
            tvBPStatus = (TextView) view.findViewById(R.id.tvBPStatus);
            tvBPaymentDate = (TextView) view.findViewById(R.id.tvBPaymentDate);
            tvBeneficiaryName = (TextView) view.findViewById(R.id.tvBeneficiaryName);
            tvBankAccountNumber = (TextView) view.findViewById(R.id.tvBankAccountNumber);
            tvBAmount = (TextView) view.findViewById(R.id.tvBAmount);

        }

    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_beneficary_details_vlg_wise, parent, false);
            return new MyViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if(clist.size() == 0)
        {
            Toast.makeText(activity, "No Data Found!", Toast.LENGTH_SHORT).show();
        }

        if (viewHolder instanceof MyViewHolder) {

            populateItemRows((MyViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }
    }

    @Override
    public int getItemCount() {

        return clist == null ? 0 : clist.size();
    }

    @Override
    public int getItemViewType(int position) {
        return clist.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed


    }

    private void populateItemRows(MyViewHolder viewHolder, int position) {
        viewHolder.tvBAadharNumber.setText(clist.get(position).getAadhaarNumber());
        viewHolder.tvBBankName.setText(clist.get(position).getBankName());
        viewHolder.tvBPStatus.setText(clist.get(position).getPaymentSTATUS());
        viewHolder.tvBPaymentDate.setText(clist.get(position).getPaymentDate());
        viewHolder.tvBeneficiaryName.setText(clist.get(position).getBeneficiaryName());
        viewHolder.tvBankAccountNumber.setText(clist.get(position).getBankAccountNumber());
        viewHolder.tvBAmount.setText(clist.get(position).getAmount());
    }
}
