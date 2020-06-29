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
import app.bmc.com.BHOOMI_MRTC.model.CommonBankPaymentCertiTableData;
import app.bmc.com.BHOOMI_MRTC.screens.PaymentBankCertificateDetails;
import app.bmc.com.BHOOMI_MRTC.screens.ShowEnglishCertificateActivity;
import app.bmc.com.BHOOMI_MRTC.util.Constants;

public class CommonBankCertificatePaymentAdapter extends RecyclerView.Adapter<CommonBankCertificatePaymentAdapter.MyViewHolder> {


    private List<CommonBankPaymentCertiTableData> bankcertificatePaymentlist;
    private String  custId;
    private String languageEnglish ="2";
    private String languageKanadaa ="1";
    private PaymentBankCertificateDetails activity;

    private String paymentStatus;


    public CommonBankCertificatePaymentAdapter(List<CommonBankPaymentCertiTableData> bankcertiPaymentlist, PaymentBankCertificateDetails activity) {
        this.bankcertificatePaymentlist = bankcertiPaymentlist;
        this.activity = activity;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivEnglishAction;
        public ImageView ivKanadaAction;
        public TextView tvBankCertLoneeName;
        public TextView tvBankCertDistrict;
        public TextView tvBankCertTaluka;
        public TextView tvBankCertBankName;
        public TextView tvBankCertBranchName;
        public TextView tvBankCertLonneAccNo;
        public TextView tvBankCertOutStandingAmount;
        public TextView tvBankCertLoanType;
        public TextView tvBankCertPaidAmount;
        public TextView tvBankCertPaymentStatus;
        public TextView tvHeadingBankPaymentRemarks;
        public TextView tvBankCertPaymentRemarks;


        public MyViewHolder(View view) {
            super(view);

            ivEnglishAction = view.findViewById(R.id.ivEnglishAction);
            ivKanadaAction = view.findViewById(R.id.ivKanadaAction);
            tvBankCertLoneeName = view.findViewById(R.id.tvBankCertLoneeName);
            tvBankCertDistrict = view.findViewById(R.id.tvBankCertDistrict);
            tvBankCertTaluka = view.findViewById(R.id.tvBankCertTaluka);
            tvBankCertBankName = view.findViewById(R.id.tvBankCertBankName);
            tvBankCertBranchName = view.findViewById(R.id.tvBankCertBranchName);
            tvBankCertLonneAccNo = view.findViewById(R.id.tvBankCertLonneAccNo);
            tvBankCertOutStandingAmount = view.findViewById(R.id.tvBankCertOutStandingAmount);
            tvBankCertLoanType = view.findViewById(R.id.tvBankCertLoanType);
            tvBankCertPaidAmount = view.findViewById(R.id.tvBankCertPaidAmount);
            tvBankCertPaymentStatus = view.findViewById(R.id.tvBankCertPaymentStatus);
            tvHeadingBankPaymentRemarks = view.findViewById(R.id.tvHeadingBankPaymentRemarks);
            tvBankCertPaymentRemarks = view.findViewById(R.id.tvBankCertPaymentRemarks);



            ivEnglishAction.setOnClickListener(v -> {
                // get position
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION){
                    CommonBankPaymentCertiTableData clickedDataItem = bankcertificatePaymentlist.get(pos);
                    custId = clickedDataItem.getTRN_CUSTID();
                    paymentStatus = clickedDataItem.getPaymentStatus();
                    if(Constants.convertInt(paymentStatus)==1)
                    {
                        Intent intent = new Intent(activity, ShowEnglishCertificateActivity.class);
                        intent.putExtra("Request_Parameter",custId);
                        intent.putExtra("Language_Parameter",languageEnglish);
                        activity.startActivity(intent);
                    }
                    else {
                        Toast.makeText(activity, "Certificate is not Avilable!", Toast.LENGTH_SHORT).show();
                    }
                }


            });

           ivKanadaAction.setOnClickListener(v -> {

               int pos = getAdapterPosition();
               if(pos != RecyclerView.NO_POSITION){
                   CommonBankPaymentCertiTableData clickedDataItem = bankcertificatePaymentlist.get(pos);
                   custId = clickedDataItem.getTRN_CUSTID();
                   paymentStatus = clickedDataItem.getPaymentStatus();
                   if(Constants.convertInt(paymentStatus)==1) {
                       Intent intent = new Intent(activity, ShowEnglishCertificateActivity.class);
                       intent.putExtra("Request_Parameter", custId);
                       intent.putExtra("Language_Kanada", languageKanadaa);
                       activity.startActivity(intent);
                   }
                   else {
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
                .inflate(R.layout.common_bank_payment_certificate_list, parent, false);

        return new CommonBankCertificatePaymentAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
/*if(Constants.convertInt(bankcertificatePaymentlist.get(position).getPaymentStatus())!=1){
    holder.ivEnglishAction.setVisibility(View.GONE);
    holder.ivKanadaAction.setVisibility(View.GONE);
}*/

        holder.tvBankCertLoneeName.setText(bankcertificatePaymentlist.get(position).getTRN_LOONENME());
        holder.tvBankCertDistrict.setText(bankcertificatePaymentlist.get(position).getFMLY_DISTNME());
        holder.tvBankCertTaluka.setText(bankcertificatePaymentlist.get(position).getFMLY_TLKNME());
        holder.tvBankCertBankName.setText(bankcertificatePaymentlist.get(position).getDCC_BNK_NME_EN());
        holder.tvBankCertBranchName.setText(bankcertificatePaymentlist.get(position).getPACS_BNK_BRNCH_NME());
        holder.tvBankCertLonneAccNo.setText(bankcertificatePaymentlist.get(position).getOTRN_SHARENO());
        holder.tvBankCertOutStandingAmount.setText(bankcertificatePaymentlist.get(position).getPCS_LIAB_10072018());
        holder.tvBankCertLoanType.setText(bankcertificatePaymentlist.get(position).getPCS_PUR());
        holder.tvBankCertPaidAmount.setText(bankcertificatePaymentlist.get(position).getReleasedAmount());

        String bankPaymentstatus = bankcertificatePaymentlist.get(position).getPaymentStatus();
        if(bankPaymentstatus.equalsIgnoreCase("99"))
        {
            holder.tvBankCertPaymentStatus.setText("File Aadhaar, Ration Card & Land Sy No along with FSD for approval");
            holder.tvHeadingBankPaymentRemarks.setVisibility(View.GONE);
            holder.tvBankCertPaymentRemarks.setVisibility(View.GONE);
        }
        else if(bankPaymentstatus.equalsIgnoreCase("1"))
        {
            holder.tvBankCertPaymentStatus.setText("Payment Successfull");
            holder.tvHeadingBankPaymentRemarks.setVisibility(View.GONE);
            holder.tvBankCertPaymentRemarks.setVisibility(View.GONE);
        }
        else if(bankPaymentstatus.equalsIgnoreCase("2"))
        {
            holder.tvBankCertPaymentStatus.setText("Payment Failed");
            holder.tvHeadingBankPaymentRemarks.setVisibility(View.VISIBLE);
            holder.tvBankCertPaymentRemarks.setVisibility(View.VISIBLE);
            holder.tvBankCertPaymentRemarks.setText(bankcertificatePaymentlist.get(position).getPaymentRemarks());
        }
        else
        {
            holder.tvBankCertPaymentStatus.setText("Payment Initiated");
            holder.tvHeadingBankPaymentRemarks.setVisibility(View.GONE);
            holder.tvBankCertPaymentRemarks.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return bankcertificatePaymentlist.size();
    }
}
