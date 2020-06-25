package app.bmc.com.BHOOMI_MRTC.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.model.BankLoanTableData;
import app.bmc.com.BHOOMI_MRTC.screens.ClwsStatusDetails;
import app.bmc.com.BHOOMI_MRTC.screens.CommercialBankLoanAffidavitDoc;
import app.bmc.com.BHOOMI_MRTC.screens.CommercialBankLoanReportDocActivity;

public class CommercialBankAdapter extends RecyclerView.Adapter<CommercialBankAdapter.MyViewHolder> {


    private List<BankLoanTableData> clist;
    private ClwsStatusDetails activity;

    private String appId;
    private String customerId;
    private String bankId;


    public class MyViewHolder extends RecyclerView.ViewHolder {


    public ImageView ivPdffile;
    public ImageView ivPdfBluefile;
    public TextView tvCLWSID;
    public TextView tvDistrictName;
    public TextView tvTalukName;
    public TextView tvBankName;
    public TextView tvBranch;
    public TextView tvFarmerName;
    public TextView tvRationCardNo;
    public TextView tvLoanType;
    public TextView tvAccountNumber;
    public TextView tvOutStandingAmount;
    public TextView tvStatus;

    public MyViewHolder(View view) {
        super(view);

        ivPdffile = (ImageView)view.findViewById(R.id.ivPdffile);
        ivPdfBluefile = (ImageView)view.findViewById(R.id.ivPdfBluefile);
        tvCLWSID = (TextView) view.findViewById(R.id.tvCLWSID);
        tvDistrictName = (TextView) view.findViewById(R.id.tvDistrictName);
        tvTalukName = (TextView) view.findViewById(R.id.tvTalukName);
        tvBankName = (TextView) view.findViewById(R.id.tvBankName);
        tvBranch = (TextView) view.findViewById(R.id.tvBranch);
        tvFarmerName = (TextView) view.findViewById(R.id.tvFarmerName);
        tvRationCardNo = (TextView) view.findViewById(R.id.tvRationCardNo);
        tvLoanType = (TextView) view.findViewById(R.id.tvLoanType);
        tvAccountNumber = (TextView) view.findViewById(R.id.tvAccountNumber);
        tvOutStandingAmount = (TextView) view.findViewById(R.id.tvOutStandingAmount);
        tvStatus = (TextView) view.findViewById(R.id.tvStatus);
        appId = "1516978b-4b63-4072-9ecf-560dee62baff";

        ivPdffile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION){
                    BankLoanTableData clickedDataItem = clist.get(pos);
                    customerId = clickedDataItem.getTRN_CUSTID();
                    bankId = clickedDataItem.getTRN_BNKID();
                }
                Intent intent = new Intent(activity, CommercialBankLoanReportDocActivity.class);
                intent.putExtra("Cust_ID",customerId);
                intent.putExtra("Bank_ID",bankId);
                intent.putExtra("APP_ID",appId);
                activity.startActivity(intent);
            }
        });

        ivPdfBluefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION){
                    BankLoanTableData clickedDataItem = clist.get(pos);
                  customerId = clickedDataItem.getTRN_CUSTID();
                  bankId = clickedDataItem.getTRN_BNKID();
                }
                Intent intent = new Intent(activity, CommercialBankLoanAffidavitDoc.class);
                intent.putExtra("Cust_ID",customerId);
                intent.putExtra("Bank_ID",bankId);
                intent.putExtra("APP_ID",appId);
                activity.startActivity(intent);
            }
        });
    }
}

    public CommercialBankAdapter(List<BankLoanTableData> cBnakDetailsList, ClwsStatusDetails  activity) {
        this.clist = cBnakDetailsList;
        this.activity = activity;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.commercial_bank_loan_details, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tvCLWSID.setText(clist.get(position).getTRN_CUSTID());
        holder.tvDistrictName.setText(clist.get(position).getFMLY_DISTNME());
        holder.tvTalukName.setText(clist.get(position).getFMLY_TLKNME());
        holder.tvBankName.setText(clist.get(position).getBNK_NME_EN());
        holder.tvBranch.setText(clist.get(position).getBNK_BRNCH_NME());
        holder.tvFarmerName.setText(clist.get(position).getOTRN_LOANEENME());
        holder.tvRationCardNo.setText(clist.get(position).getTRN_RCNO());
        holder.tvLoanType.setText(clist.get(position).getOTRN_LOANCAT());
        holder.tvAccountNumber.setText(clist.get(position).getACNO());
        holder.tvOutStandingAmount.setText(clist.get(position).getLIABAmount());
        holder.tvStatus.setText(clist.get(position).getSTATYS_DESC());
    }

    @Override
    public int getItemCount() {
        return clist.size();
    }
}
