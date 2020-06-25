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
import app.bmc.com.BHOOMI_MRTC.model.PaymentDetail;
import app.bmc.com.BHOOMI_MRTC.screens.ShowIndividualPariharaDetailsReport;


public class IndividualPariharaPayementAdapter extends RecyclerView.Adapter<IndividualPariharaPayementAdapter.MyViewHolder> {


   private  List<PaymentDetail> clist ;
   private ShowIndividualPariharaDetailsReport activity;



    public IndividualPariharaPayementAdapter(List<PaymentDetail> myPariharaList, ShowIndividualPariharaDetailsReport activity) {
        this.clist = myPariharaList;
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

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


        public MyViewHolder(View view) {
            super(view);

            tvPSerialNo = (TextView) view.findViewById(R.id.tvPSerialNo);
            tvPDistCode = (TextView) view.findViewById(R.id.tvPDistCode);
            tvPBankName = (TextView) view.findViewById(R.id.tvPBankName);
            tvPAmountInRs = (TextView) view.findViewById(R.id.tvPAmountInRs);
            tvPAcHolder = (TextView) view.findViewById(R.id.tvPAcHolder);
            tvPBankAccountNo = (TextView) view.findViewById(R.id.tvPBankAccountNo);
            tvPStatus = (TextView) view.findViewById(R.id.tvPStatus);
            tvPPaymentDate = (TextView) view.findViewById(R.id.tvPPaymentDate);
            tvPCalamityType = (TextView) view.findViewById(R.id.tvPCalamityType);
            tvPSeason = (TextView) view.findViewById(R.id.tvPSeason);
            tvPYear = (TextView) view.findViewById(R.id.tvPYear);


        }

    }


    @NonNull
    @Override
    public IndividualPariharaPayementAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_individual_payment_details, parent, false);

        return new IndividualPariharaPayementAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IndividualPariharaPayementAdapter.MyViewHolder holder, int position) {

        if(clist.size() == 0)
        {
            Toast.makeText(activity, "No Data Found!", Toast.LENGTH_SHORT).show();
        }else {

            holder.tvPSerialNo.setText(clist.get(position).getSlNo());
            holder.tvPDistCode.setText(clist.get(position).getDistrictCode());
            holder.tvPBankName.setText(clist.get(position).getBankName());
            holder.tvPAmountInRs.setText(clist.get(position).getAmount());
            holder.tvPAcHolder.setText(clist.get(position).getaCHolderName());
            holder.tvPBankAccountNo.setText(clist.get(position).getBankAccountNumber());
            holder.tvPStatus.setText(clist.get(position).getPaymentStatus());
            holder.tvPPaymentDate.setText(clist.get(position).getPaymentDate());
            holder.tvPCalamityType.setText(clist.get(position).getCalamityType());
            holder.tvPSeason.setText(clist.get(position).getSeason());
            holder.tvPYear.setText(clist.get(position).getYear());
        }

    }

    @Override
    public int getItemCount() {
        return clist.size();
    }
}

