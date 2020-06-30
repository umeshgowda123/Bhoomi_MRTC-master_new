package app.bmc.com.BHOOMI_MRTC.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.model.PariharaEntry;
import app.bmc.com.BHOOMI_MRTC.model.PaymentDetail;
import app.bmc.com.BHOOMI_MRTC.screens.ShowIndividualPariharaDetailsReport;


public class IndividualPariharaPayementAdapter extends RecyclerView.Adapter<IndividualPariharaPayementAdapter.MyViewHolder> {


    private  List<PariharaEntry> clistEntry ;
   private  List<PaymentDetail> clist ;
   private ShowIndividualPariharaDetailsReport activity;



    public IndividualPariharaPayementAdapter(List<PariharaEntry> myPariharaEntryList, List<PaymentDetail> myPariharaList, ShowIndividualPariharaDetailsReport activity) {
        this.clistEntry = myPariharaEntryList;
        this.clist = myPariharaList;
        this.activity = activity;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvPSerialNo;
        public TextView tvPDistCode;
        public TextView tvPBankName;
        public TextView tvPAmountInRs;
        public TextView tvPAcHolder;
        public TextView tvPBankAccountNo;
        public TextView tvPStatus;
        public TextView tvPPaymentDate;
        public TextView tvPCalamityType;
        public TextView tvPSeason;
        public TextView tvPYear;
        public TextView tvAdharNumber;


        public MyViewHolder(View view) {
            super(view);

            tvPSerialNo = view.findViewById(R.id.tvPSerialNo);
            tvPDistCode = view.findViewById(R.id.tvPDistCode);
            tvPBankName = view.findViewById(R.id.tvPBankName);
            tvPAmountInRs = view.findViewById(R.id.tvPAmountInRs);
            tvPAcHolder = view.findViewById(R.id.tvPAcHolder);
            tvPBankAccountNo = view.findViewById(R.id.tvPBankAccountNo);
            tvPStatus = view.findViewById(R.id.tvPStatus);
            tvPPaymentDate = view.findViewById(R.id.tvPPaymentDate);
            tvPCalamityType = view.findViewById(R.id.tvPCalamityType);
            tvPSeason = view.findViewById(R.id.tvPSeason);
            tvPYear = view.findViewById(R.id.tvPYear);
            tvAdharNumber = view.findViewById(R.id.tvAdharNumber);


        }

    }


    @NonNull
    @Override
    public IndividualPariharaPayementAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_individual_payment_details, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IndividualPariharaPayementAdapter.MyViewHolder holder, int position) {

        if(clist.size() == 0)
        {
            Toast.makeText(activity, "No Data Found!", Toast.LENGTH_SHORT).show();
        }else {

            String payDate = clist.get(position).getPaymentDate();
            Log.d("payDate", ""+payDate);
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            Date date;
            String finalDate = null;
            try {
                date = format.parse(payDate);
                Log.d("FormattedDate", ""+date);

                assert date != null;
                finalDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(date);
                Log.d("finalDate", ""+finalDate);
            } catch (ParseException | NullPointerException e) {
                e.printStackTrace();
            }

            String AccNo = clist.get(position).getBankAccountNumber();
            AccNo = MaskAcc(AccNo);
            Log.d("MaskedAccNo", ""+AccNo);

            holder.tvPSerialNo.setText(clist.get(position).getSlNo());
            holder.tvPDistCode.setText(clistEntry.get(1).getDistrictName());
            holder.tvPBankName.setText(clist.get(position).getBankName());
            holder.tvPAmountInRs.setText(clist.get(position).getAmount());
            holder.tvPAcHolder.setText(clist.get(position).getaCHolderName());
            holder.tvPBankAccountNo.setText(AccNo);
            holder.tvPStatus.setText(clist.get(position).getPaymentStatus());
            holder.tvPPaymentDate.setText(finalDate);
            holder.tvPCalamityType.setText(clist.get(position).getCalamityType());
            holder.tvPSeason.setText(clist.get(position).getSeason());
            holder.tvPYear.setText(clist.get(position).getYear());
            holder.tvAdharNumber.setText(clistEntry.get(1).getAadhaarNo());
        }

    }

    @Override
    public int getItemCount() {
        return clist.size();
    }

    private String MaskAcc(String AccNo){
        StringBuilder sb = new StringBuilder();
        String str;
        int numLeading = AccNo.length() - 3;
        if(AccNo.length() > 3) {
            str = AccNo.substring(AccNo.length() - 3);

            for (int i = 0; i < numLeading; i++) {
                sb.append("*");
            }
            sb.append(str);
        }

        return sb.toString();
    }
}

