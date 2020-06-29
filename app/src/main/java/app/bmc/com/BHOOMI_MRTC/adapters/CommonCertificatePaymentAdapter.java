package app.bmc.com.BHOOMI_MRTC.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.model.PacsCertificateAadharTableData;
import app.bmc.com.BHOOMI_MRTC.screens.PacsWebViewActivity;
import app.bmc.com.BHOOMI_MRTC.screens.PaymentCertifiacteDetails;
import app.bmc.com.BHOOMI_MRTC.util.Constants;

public class CommonCertificatePaymentAdapter extends RecyclerView.Adapter<CommonCertificatePaymentAdapter.MyViewHolder> {


    private List<PacsCertificateAadharTableData> certificatePaymentlist;
    private PaymentCertifiacteDetails activity;

    private String appId;
    private String customerId;

    private String paymentStatus;


    public CommonCertificatePaymentAdapter(List<PacsCertificateAadharTableData> certiPaymentlist, PaymentCertifiacteDetails activity) {
        this.certificatePaymentlist = certiPaymentlist;
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivAction;
        public TextView tvPayCertLoneeName;
        public TextView tvPayCertFatherName;
        public TextView tvPayCertDistrict;
        public TextView tvPayCertTaluka;
        public TextView tvPayCertDCCBankName;
        public TextView tvPayCertPACSName;
        public TextView tvPayCertRationCardNo;
        public TextView tvPayCertShareNo;
        public TextView tvPayCertOutstandingAmount;
        public TextView tvPayCertLoanType;
        public TextView tvPayCertPaidAmount;
        public TextView tvPayCertPaymentStatus;
        public TextView tvPayCertPaymentRemarks;
        public TextView tvHeadingPaymentRemarks;


        public MyViewHolder(View view) {
            super(view);

            ivAction = view.findViewById(R.id.ivAction);
            tvPayCertLoneeName = view.findViewById(R.id.tvPayCertLoneeName);
            tvPayCertFatherName = view.findViewById(R.id.tvPayCertFatherName);
            tvPayCertDistrict = view.findViewById(R.id.tvPayCertDistrict);
            tvPayCertTaluka = view.findViewById(R.id.tvPayCertTaluka);
            tvPayCertDCCBankName = view.findViewById(R.id.tvPayCertDCCBankName);
            tvPayCertPACSName = view.findViewById(R.id.tvPayCertPACSName);
            tvPayCertRationCardNo = view.findViewById(R.id.tvPayCertRationCardNo);
            tvPayCertShareNo = view.findViewById(R.id.tvPayCertShareNo);
            tvPayCertOutstandingAmount = view.findViewById(R.id.tvPayCertOutstandingAmount);
            tvPayCertLoanType = view.findViewById(R.id.tvPayCertLoanType);
            tvPayCertPaidAmount = view.findViewById(R.id.tvPayCertPaidAmount);
            tvPayCertPaymentStatus = view.findViewById(R.id.tvPayCertPaymentStatus);
            tvPayCertPaymentRemarks = view.findViewById(R.id.tvPayCertPaymentRemarks);
            tvHeadingPaymentRemarks = view.findViewById(R.id.tvHeadingPaymentRemarks);


            appId = "1516978b-4b63-4072-9ecf-560dee62baff";
            ivAction.setOnClickListener(v -> {

                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION){
                    PacsCertificateAadharTableData clickedDataItem = certificatePaymentlist.get(pos);
                    customerId = clickedDataItem.getTrn_custid();
                    paymentStatus = clickedDataItem.getPaymentStatus();
                    if(Constants.convertInt(paymentStatus) ==1)
                    {
                        Intent intent = new Intent(activity, PacsWebViewActivity.class);
                        intent.putExtra("Cust_ID",customerId);
                        intent.putExtra("APP_ID",appId);
                        activity.startActivity(intent);
                    }else {
                        Toast.makeText(activity, "Certificate is not Avilable!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.common_payment_certificate_list, parent, false);

        return new CommonCertificatePaymentAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if(certificatePaymentlist.size() != 0)
        {
            holder.tvPayCertLoneeName.setText(certificatePaymentlist.get(position).getTrn_loonenme());
            holder.tvPayCertFatherName.setText(certificatePaymentlist.get(position).getCustFather_guardian());
            holder.tvPayCertDistrict.setText(certificatePaymentlist.get(position).getFmly_distnme());
            holder.tvPayCertTaluka.setText(certificatePaymentlist.get(position).getFmly_tlknme());
            holder.tvPayCertDCCBankName.setText(certificatePaymentlist.get(position).getDcc_bnk_nme_en());
            holder.tvPayCertPACSName.setText(certificatePaymentlist.get(position).getPacs_bnk_brnch_nme());
            holder.tvPayCertRationCardNo.setText(certificatePaymentlist.get(position).getTrn_rcno());
            holder.tvPayCertShareNo.setText(certificatePaymentlist.get(position).getOtrn_shareno());
            holder.tvPayCertOutstandingAmount.setText(certificatePaymentlist.get(position).getPcs_liab_10072018());
            holder.tvPayCertLoanType.setText(certificatePaymentlist.get(position).getPcs_pur());
            holder.tvPayCertPaidAmount.setText(certificatePaymentlist.get(position).getReleasedAmount());

            String status = certificatePaymentlist.get(position).getPaymentStatus();
            if(status.equalsIgnoreCase("99"))
            {
                holder.tvPayCertPaymentStatus.setText("Not Considered For Payment");
                holder.tvPayCertPaymentRemarks.setVisibility(View.GONE);
                holder.tvHeadingPaymentRemarks.setVisibility(View.GONE);
            }else if(status.equalsIgnoreCase("1"))
            {
                holder.tvPayCertPaymentStatus.setText("Payment Successfull");
                holder.tvPayCertPaymentRemarks.setVisibility(View.GONE);
                holder.tvHeadingPaymentRemarks.setVisibility(View.GONE);
            }else if(status.equalsIgnoreCase("2"))
            {
                holder.tvPayCertPaymentStatus.setText("Payment Failed");
                holder.tvHeadingPaymentRemarks.setVisibility(View.VISIBLE);
                holder.tvPayCertPaymentRemarks.setVisibility(View.VISIBLE);
                holder.tvPayCertPaymentRemarks.setText(certificatePaymentlist.get(position).getPaymentRemarks());
            }else
            {
                holder.tvPayCertPaymentStatus.setText("Payment Initiated");
                holder.tvPayCertPaymentRemarks.setVisibility(View.GONE);
                holder.tvHeadingPaymentRemarks.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
            return certificatePaymentlist.size();
    }
}
