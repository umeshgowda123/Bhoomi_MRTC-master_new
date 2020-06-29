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
import app.bmc.com.BHOOMI_MRTC.model.BenificaryDataLandWise;
import app.bmc.com.BHOOMI_MRTC.screens.ShowPariharaBenificiaryDetailsLandWise;

public class LandWiseBenificaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int VIEW_TYPE_ITEM = 0;

    private List<BenificaryDataLandWise> clist ;
    private ShowPariharaBenificiaryDetailsLandWise activity;



    public LandWiseBenificaryAdapter(List<BenificaryDataLandWise> myBeneficaryList, ShowPariharaBenificiaryDetailsLandWise activity) {
        this.clist = myBeneficaryList;
        this.activity = activity;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

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

            tvLDistrictName = view.findViewById(R.id.tvLDistrictName);
            tvLTalukName = view. findViewById(R.id.tvLTalukName);
            tvLHobliName = view. findViewById(R.id.tvLHobliName);
            tvLVillageCircleName = view.findViewById(R.id.tvLVillageCircleName);
            tvLVillageName = view.findViewById(R.id.tvLVillageName);
            tvLEntryId = view.findViewById(R.id.tvLEntryId);
            tvLSurveyNumber = view.findViewById(R.id.tvLSurveyNumber);
            tvLSurnocNumber = view.findViewById(R.id.tvLSurnocNumber);
            tvLHissaNumber = view.findViewById(R.id.tvLHissaNumber);
            tvLCropName = view.findViewById(R.id.tvLCropName);
            tvLCropCategory = view.findViewById(R.id.tvLCropCategory);
            tvLCropLossExtentAcre = view.findViewById(R.id.tvLCropLossExtentAcre);
            tvLCropLossExtentGunta = view.findViewById(R.id.tvLCropLossExtentGunta);
            tvLCropLossExtentFGunta = view.findViewById(R.id.tvLCropLossExtentFGunta);
        }

    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if(clist.size() == 0)
        {
            Toast.makeText(activity, "No Data Found!", Toast.LENGTH_SHORT).show();
        }

        if (viewHolder instanceof MyViewHolder) {
            populateItemRows((MyViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView();
        }
    }

    @Override
    public int getItemCount() {

        return clist == null ? 0 : clist.size();
    }

    @Override
    public int getItemViewType(int position) {
        int VIEW_TYPE_LOADING = 1;
        return clist.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private void showLoadingView() {
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
