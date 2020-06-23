package app.bmc.com.BHOOMI_MRTC.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.model.Irrigation;
import app.bmc.com.BHOOMI_MRTC.model.Landdetails;
import app.bmc.com.BHOOMI_MRTC.model.Tree;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This class defined to create custom list to display data comming server which is
 * Land xml response
 */
public class LandCustomAdapter extends RecyclerView.Adapter<LandCustomAdapter.MyViewHolder> {

    private ArrayList<Landdetails> dataSet;
    private Context context;

    public LandCustomAdapter(ArrayList<Landdetails> data, Context context) {
        this.dataSet = data;
        this.context = context;
    }

    @Override
    public LandCustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.land_details_list, parent, false);
        LandCustomAdapter.MyViewHolder myViewHolder = new LandCustomAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final LandCustomAdapter.MyViewHolder holder, final int listPosition) {
        Landdetails landdetails = dataSet.get(listPosition);
        TextView land_details_survey = holder.land_details_survey;
        TextView land_details_hissa_no = holder.land_details_hissa_no;
        TextView land_details_total_extent = holder.land_details_total_extent;
        TextView land_details_karab_a = holder.land_details_karab_a;
        TextView land_details_karab_b = holder.land_details_karab_b;
        TextView land_details_remaining = holder.land_details_remaining;
        TextView land_details_landrevenue = holder.land_details_landrevenue;
        TextView land_details_jodi = holder.land_details_jodi;
        TextView land_details_cesses = holder.land_details_cesses;
        TextView land_details_water_rate = holder.land_details_water_rate;
        TextView land_details_total_revenue = holder.land_details_total_revenue;
        TextView land_details_soil_type = holder.land_details_soil_type;
        TextView land_details_patta = holder.land_details_patta;
        LinearLayout tree_layout = holder.treeLayout;
        LinearLayout irrigation_layout = holder.irrigationLayout;
        if (landdetails.getPahanidetails() != null) {
            land_details_survey.setText(landdetails.getPahanidetails().getSurveyno() + "/" + landdetails.getPahanidetails().getSurnoc());
            land_details_hissa_no.setText(landdetails.getPahanidetails().getHissano());
            land_details_total_extent.setText(landdetails.getPahanidetails().getTotalextents());
            land_details_karab_a.setText(landdetails.getPahanidetails().getPhodkharaba());
            land_details_karab_b.setText(landdetails.getPahanidetails().getPhodkharabb());
            land_details_remaining.setText(landdetails.getPahanidetails().getBalanceextents());
            land_details_landrevenue.setText(landdetails.getPahanidetails().getLandrevenue());
            land_details_jodi.setText(landdetails.getPahanidetails().getJodi());
            land_details_cesses.setText(landdetails.getPahanidetails().getCesses());
            land_details_water_rate.setText(landdetails.getPahanidetails().getWaterrate());
            land_details_total_revenue.setText(landdetails.getPahanidetails().getTotalrevenue());
            land_details_soil_type.setText(landdetails.getPahanidetails().getSoiltype());
            land_details_patta.setText(landdetails.getPahanidetails().getPatta());
        }
        for (Tree tree : landdetails.getTreedetails().getTree()) {

            LayoutInflater li = LayoutInflater.from(context);
            View v = li.inflate(R.layout.tree_layout, null, false);
            TextView land_details_tree_name = v.findViewById(R.id.land_details_tree_name);
            TextView land_details_tree_no = v.findViewById(R.id.land_details_tree_no);
            land_details_tree_name.setText(tree.getTreename());
            land_details_tree_no.setText(tree.getNumberoftrees());

            tree_layout.addView(v);
        }
        for (Irrigation irrigation : landdetails.getIrrigationdetails().getIrrigation()) {
            LayoutInflater li = LayoutInflater.from(context);
            View v = li.inflate(R.layout.irrigation_layout, null, false);
            TextView land_details_irigation_s_no = v.findViewById(R.id.land_details_irigation_s_no);
            TextView land_details_irigation_water_source = v.findViewById(R.id.land_details_irigation_water_source);
            TextView land_details_irigation_kharif_ac_gun = v.findViewById(R.id.land_details_irigation_kharif_ac_gun);
            TextView land_details_irigation_rabi_ac_gun = v.findViewById(R.id.land_details_irigation_rabi_ac_gun);
            TextView land_details_irigation_garden_ac_gun = v.findViewById(R.id.land_details_irigation_garden_ac_gun);
            TextView land_details_irigation_total_ac_gun = v.findViewById(R.id.land_details_irigation_total_ac_gun);

            land_details_irigation_s_no.setText(irrigation.getSerialnumber());
            land_details_irigation_water_source.setText(irrigation.getWatersource());
            land_details_irigation_kharif_ac_gun.setText(irrigation.getKharifextents());
            land_details_irigation_rabi_ac_gun.setText(irrigation.getRabiextents());
            land_details_irigation_garden_ac_gun.setText(irrigation.getGardenextents());
            land_details_irigation_total_ac_gun.setText(irrigation.getTotalextents());
            irrigation_layout.addView(v);
        }


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView land_details_survey;
        TextView land_details_hissa_no;
        TextView land_details_total_extent;
        TextView land_details_karab_a;
        TextView land_details_karab_b;
        TextView land_details_remaining;
        TextView land_details_landrevenue;
        TextView land_details_jodi;
        TextView land_details_cesses;
        TextView land_details_water_rate;
        TextView land_details_total_revenue;
        TextView land_details_soil_type;
        TextView land_details_patta;
        LinearLayout irrigationLayout;
        LinearLayout treeLayout;

        public MyViewHolder(View view) {
            super(view);

            land_details_survey = view.findViewById(R.id.land_details_survey);
            land_details_hissa_no = view.findViewById(R.id.land_details_hissa);
            land_details_total_extent = view.findViewById(R.id.land_details_total_extent);
            land_details_karab_a = view.findViewById(R.id.land_details_karab_a);
            land_details_karab_b = view.findViewById(R.id.land_details_karab_b);
            land_details_remaining = view.findViewById(R.id.land_details_remaining);
            land_details_landrevenue = view.findViewById(R.id.land_details_land_revenue);
            land_details_jodi = view.findViewById(R.id.land_details_jodi);
            land_details_cesses = view.findViewById(R.id.land_details_cesses);
            land_details_water_rate = view.findViewById(R.id.land_details_water_rate);
            land_details_total_revenue = view.findViewById(R.id.land_details_total_revenue);
            land_details_soil_type = view.findViewById(R.id.land_details_soil_type);
            land_details_patta = view.findViewById(R.id.land_details_patta);
            irrigationLayout = view.findViewById(R.id.irrigation_layout);
            treeLayout = view.findViewById(R.id.tree_layout);
        }
    }
}
