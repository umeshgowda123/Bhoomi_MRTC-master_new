package app.bmc.com.bhoomi.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import app.bmc.com.bhoomi.R;
import app.bmc.com.bhoomi.model.BankLoanTableData;
import app.bmc.com.bhoomi.model.PariharaEntry;
import app.bmc.com.bhoomi.screens.ClwsStatusDetails;
import app.bmc.com.bhoomi.screens.ShowIndividualPariharaDetailsReport;


public class IndividualPariharaDetailsAdapter extends RecyclerView.Adapter<IndividualPariharaDetailsAdapter.MyViewHolder> {


   private  List<PariharaEntry> clist ;
   private ShowIndividualPariharaDetailsReport activity;



    public IndividualPariharaDetailsAdapter(List<PariharaEntry> myPariharaList, ShowIndividualPariharaDetailsReport activity) {
        this.clist = myPariharaList;
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvSerialNo;
        public TextView tvEntryNumber;
        public TextView tvAdharNumber;
        public TextView tvDistrict;
        public TextView tvTaluk;
        public TextView tvHobli;
        public TextView tvVillage;
        public TextView tvSurveyNo;
        public TextView tvCropCategory;
        public TextView tvCropName;
        public TextView tvCropLostExtent;


        public MyViewHolder(View view) {
            super(view);

            tvSerialNo = (TextView) view.findViewById(R.id.tvSerialNo);
            tvEntryNumber = (TextView) view.findViewById(R.id.tvEntryNumber);
            tvAdharNumber = (TextView) view.findViewById(R.id.tvAdharNumber);
            tvDistrict = (TextView) view.findViewById(R.id.tvDistrict);
            tvTaluk = (TextView) view.findViewById(R.id.tvTaluk);
            tvHobli = (TextView) view.findViewById(R.id.tvHobli);
            tvVillage = (TextView) view.findViewById(R.id.tvVillage);
            tvSurveyNo = (TextView) view.findViewById(R.id.tvSurveyNo);
            tvCropCategory = (TextView) view.findViewById(R.id.tvCropCategory);
            tvCropName = (TextView) view.findViewById(R.id.tvCropName);
            tvCropLostExtent = (TextView) view.findViewById(R.id.tvCropLostExtent);


        }

    }


    @NonNull
    @Override
    public IndividualPariharaDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_individual_parihara_details, parent, false);

        return new IndividualPariharaDetailsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IndividualPariharaDetailsAdapter.MyViewHolder holder, int position) {


        if(clist.size() == 0)
        {
            Toast.makeText(activity, "No Data Found!", Toast.LENGTH_SHORT).show();
        }else
        {
            holder.tvSerialNo.setText(clist.get(position).getSlNo());
            holder.tvEntryNumber.setText(clist.get(position).getEntryID());
            holder.tvAdharNumber.setText(clist.get(position).getAadhaarNo());
            holder.tvDistrict.setText(clist.get(position).getDistrictName());
            holder.tvTaluk.setText(clist.get(position).getTalukName());
            holder.tvHobli.setText(clist.get(position).getHobliName());
            holder.tvVillage.setText(clist.get(position).getVillageName());
            holder.tvSurveyNo.setText(clist.get(position).getSurveyNumber());
            holder.tvCropCategory.setText(clist.get(position).getCropCategory());
            holder.tvCropName.setText(clist.get(position).getCropName());
            holder.tvCropLostExtent.setText(clist.get(position).getCropLossExtent());
        }


    }

    @Override
    public int getItemCount() {
        return clist.size();
    }
}

