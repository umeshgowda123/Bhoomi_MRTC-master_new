package app.bmc.com.bhoomi.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import app.bmc.com.bhoomi.R;
import app.bmc.com.bhoomi.model.BenificaryDataLandWise;
import app.bmc.com.bhoomi.model.BenificaryDataVlgWise;
import app.bmc.com.bhoomi.screens.ShowPariharaBenificiaryDetailsLandWise;
import app.bmc.com.bhoomi.screens.ShowPariharaBenificiaryDetailsVlgWise;

public class LandWiseBenificaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private List<BenificaryDataLandWise> clist ;
    private ShowPariharaBenificiaryDetailsLandWise activity;



    public LandWiseBenificaryAdapter(List<BenificaryDataLandWise> myBeneficaryList, ShowPariharaBenificiaryDetailsLandWise activity) {
        this.clist = myBeneficaryList;
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvLDistrictName;
        public TextView tvLTalukName;
        public TextView tvLHobliName;
        public TextView tvLVillageCircleName;
        public TextView tvLVillageName;
        public TextView tvLEntryId;
        public TextView tvLSurveyNumber;
        public TextView tvLSurnocNumber;
        public TextView tvLHissaNumber;
        public TextView tvLCropName;
        public TextView tvLCropCategory;
        public TextView tvLCropLossExtentAcre;
        public TextView tvLCropLossExtentGunta;
        public TextView tvLCropLossExtentFGunta;


        public MyViewHolder(View view) {
            super(view);

            tvLDistrictName = (TextView) view.findViewById(R.id.tvLDistrictName);
            tvLTalukName = (TextView) view. findViewById(R.id.tvLTalukName);
            tvLHobliName = (TextView) view. findViewById(R.id.tvLHobliName);
            tvLVillageCircleName = (TextView) view.findViewById(R.id.tvLVillageCircleName);
            tvLVillageName = (TextView) view.findViewById(R.id.tvLVillageName);
            tvLEntryId = (TextView) view.findViewById(R.id.tvLEntryId);
            tvLSurveyNumber = (TextView) view.findViewById(R.id.tvLSurveyNumber);
            tvLSurnocNumber = (TextView) view.findViewById(R.id.tvLSurnocNumber);
            tvLHissaNumber = (TextView) view.findViewById(R.id.tvLHissaNumber);
            tvLCropName = (TextView) view.findViewById(R.id.tvLCropName);
            tvLCropCategory = (TextView) view.findViewById(R.id.tvLCropCategory);
            tvLCropLossExtentAcre = (TextView) view.findViewById(R.id.tvLCropLossExtentAcre);
            tvLCropLossExtentGunta = (TextView) view.findViewById(R.id.tvLCropLossExtentGunta);
            tvLCropLossExtentFGunta = (TextView) view.findViewById(R.id.tvLCropLossExtentFGunta);
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_beneficary_details_land_wise, parent, false);
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

        viewHolder.tvLDistrictName.setText(clist.get(position).getDistrictName());
        viewHolder.tvLTalukName.setText(clist.get(position).getTalukName());
        viewHolder.tvLHobliName.setText(clist.get(position).getHobliname());
        viewHolder.tvLVillageCircleName.setText(clist.get(position).getVillageCircleName());
        viewHolder.tvLVillageName.setText(clist.get(position).getVillageName());
        viewHolder.tvLEntryId.setText(clist.get(position).getENTRYID());
        viewHolder.tvLSurveyNumber.setText(clist.get(position).getSurveyNumber());
        viewHolder.tvLSurnocNumber.setText(clist.get(position).getSurnoc());
        viewHolder.tvLHissaNumber.setText(clist.get(position).getHissaNumber());
        viewHolder.tvLCropName.setText(clist.get(position).getCropName());
        viewHolder.tvLCropCategory.setText(clist.get(position).getCropCatagory());
        viewHolder.tvLCropLossExtentAcre.setText(clist.get(position).getCropLossExtentAcre());
        viewHolder.tvLCropLossExtentGunta.setText(clist.get(position).getCropLossExtentGunta());
        viewHolder.tvLCropLossExtentFGunta.setText(clist.get(position).getCropLossExtentFgunta());
    }
}
