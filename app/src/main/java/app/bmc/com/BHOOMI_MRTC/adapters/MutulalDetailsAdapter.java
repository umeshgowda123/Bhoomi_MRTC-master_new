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
import app.bmc.com.BHOOMI_MRTC.model.MutualPendacyData;
import app.bmc.com.BHOOMI_MRTC.screens.ShowMutationPendencyDetails;


public class MutulalDetailsAdapter extends RecyclerView.Adapter<MutulalDetailsAdapter.MyViewHolder> {


   private  List<MutualPendacyData> clist ;
   private ShowMutationPendencyDetails activity;



    public MutulalDetailsAdapter(List<MutualPendacyData> myPariharaList, ShowMutationPendencyDetails activity) {
        this.clist = myPariharaList;
        this.activity = activity;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvMPHobliName;
        public TextView tvMPVillageName;
        public TextView tvMPApplicationNo;
        public TextView tvMPTransactionNo;
        public TextView tvMPTStartDate;
        public TextView tvMMrNumber;
        public TextView tvMPMRegisterNo;
        public TextView tvMPRegisteredDate;
        public TextView tvMPMutationType;
        public TextView tvMPMutationStatus;
        public TextView tvAcquatationType;
        public TextView tvCourtType;
//        public TextView tvReportGenratedDate;
        public TextView tvMPSurveyNoIncluTransaction;


        public MyViewHolder(View view) {
            super(view);

            tvMPHobliName = view.findViewById(R.id.tvMPHobliName);
            tvMPVillageName = view.findViewById(R.id.tvMPVillageName);
            tvMPApplicationNo = view.findViewById(R.id.tvMPApplicationNo);
            tvMPTransactionNo = view.findViewById(R.id.tvMPTransactionNo);
            tvMPTStartDate = view.findViewById(R.id.tvMPTStartDate);
            tvMMrNumber = view.findViewById(R.id.tvMMrNumber);
            tvMPMRegisterNo = view.findViewById(R.id.tvMPMRegisterNo);
            tvMPRegisteredDate = view.findViewById(R.id.tvMPRegisteredDate);
            tvMPMutationType = view.findViewById(R.id.tvMPMutationType);
            tvMPMutationStatus = view.findViewById(R.id.tvMPMutationStatus);
            tvAcquatationType = view.findViewById(R.id.tvAcquatationType);
            tvCourtType = view.findViewById(R.id.tvCourtType);
//            tvReportGenratedDate = view.findViewById(R.id.tvReportGenratedDate);
            tvMPSurveyNoIncluTransaction = view.findViewById(R.id.tvMPSurveyNoIncluTransaction);


        }

    }


    @NonNull
    @Override
    public MutulalDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mutual_pending_details_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MutulalDetailsAdapter.MyViewHolder holder, int position) {


        if(clist.size() == 0)
        {
            Toast.makeText(activity, "No Data Found!", Toast.LENGTH_SHORT).show();
        }else
        {
            holder.tvMPHobliName.setText(clist.get(position).getHobliName());
            holder.tvMPVillageName.setText(clist.get(position).getVillageName());
            holder.tvMPApplicationNo.setText(clist.get(position).get???????????????_x0020_???????????????());
            holder.tvMPTransactionNo.setText(clist.get(position).get????????????????????????_x0020_??????????????????());
            holder.tvMPTStartDate.setText(clist.get(position).get?????????????????????_x0020_????????????????????????_x0020_??????????????????());
            holder.tvMMrNumber.setText(clist.get(position).getMR_x0020_???????????????());
            holder.tvMPMRegisterNo.setText(clist.get(position).get????????????_x0020__x002F__x0020_??????????????????_x0020_??????????????????());
            holder.tvMPRegisteredDate.setText(clist.get(position).get????????????_x0020__x002F__x0020_??????????????????_x0020_??????????????????());
            holder.tvMPMutationType.setText(clist.get(position).get???????????????????????????_x0020_????????????());
            holder.tvMPMutationStatus.setText(clist.get(position).get???????????????????????????_x0020_????????????());
            holder.tvAcquatationType.setText(clist.get(position).get???????????????????????????_x0020_??????());
            holder.tvCourtType.setText(clist.get(position).getCourtType());
//            holder.tvReportGenratedDate.setText(clist.get(position).getReportGeneratedDate());
            holder.tvMPSurveyNoIncluTransaction.setText(clist.get(position).get????????????????????????????????????_x0020_??????????????????????????????_x0020_???????????????_x0020_???????????????());
        }


    }

    @Override
    public int getItemCount() {
        return clist.size();
    }
}

