package app.bmc.com.BHOOMI_MRTC.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.model.CultivatorDisplay;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This class defined to create custom list to display data comming server which is
 * cultivator xml response
 */
public class CultivatorCustomAdapter extends RecyclerView.Adapter<CultivatorCustomAdapter.MyViewHolder> {

    private ArrayList<CultivatorDisplay> dataSet;

    public CultivatorCustomAdapter(ArrayList<CultivatorDisplay> data) {
        this.dataSet = data;
    }

    @Override
    public CultivatorCustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cultivator_list, parent, false);
        CultivatorCustomAdapter.MyViewHolder myViewHolder = new CultivatorCustomAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final CultivatorCustomAdapter.MyViewHolder holder, final int listPosition) {

        TextView year_season = holder.year_season;
        TextView cultivator_name = holder.cultivator_name;
        TextView cult_type = holder.cult_type;
        TextView tenancy_extent = holder.tenancy_extent;
        TextView tenancy_rent = holder.tenancy_rent;
        TextView land_utilisation_cls = holder.land_utilisation_cls;
        TextView land_utilisation_rent = holder.land_utilisation_rent;
        TextView dry_wet_garden = holder.dry_wet_garden;
        TextView crop_name = holder.crop_name;
        TextView single_crop_extents = holder.single_crop_extents;
        TextView mixed_crop_extents = holder.mixed_crop_extents;
        TextView total_crop_extents = holder.total_crop_extents;
        TextView water_source = holder.water_source;
        TextView yield = holder.yield;
        TextView mixed_crop_name = holder.mixed_crop_name;
        TextView mixed_crop_name_extents = holder.mixed_crop_name_extents;
        year_season.setText(dataSet.get(listPosition).getYear_season());
        ;
        cultivator_name.setText(dataSet.get(listPosition).getCultivator_name());
        cult_type.setText(dataSet.get(listPosition).getCult_type());
        tenancy_extent.setText(dataSet.get(listPosition).getTenancy_extent());
        tenancy_rent.setText(dataSet.get(listPosition).getTenancy_rent());
        land_utilisation_cls.setText(dataSet.get(listPosition).getLand_utilisation_cls());
        land_utilisation_rent.setText(dataSet.get(listPosition).getLand_utilisation_rent());
        dry_wet_garden.setText(dataSet.get(listPosition).getDry_wet_garden());
        crop_name.setText(dataSet.get(listPosition).getCrop_name());
        single_crop_extents.setText(dataSet.get(listPosition).getSingle_crop_extents());
        mixed_crop_extents.setText(dataSet.get(listPosition).getMixed_crop_extents());
        total_crop_extents.setText(dataSet.get(listPosition).getTotal_crop_extents());
        water_source.setText(dataSet.get(listPosition).getWater_source());
        yield.setText(dataSet.get(listPosition).getYield());
        mixed_crop_name.setText(dataSet.get(listPosition).getMixed_crop_name());
        mixed_crop_name_extents.setText(dataSet.get(listPosition).getMixed_crop_name_extents());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView year_season;
        TextView cultivator_name;
        TextView cult_type;
        TextView tenancy_extent;
        TextView tenancy_rent;
        TextView land_utilisation_cls;
        TextView land_utilisation_rent;
        TextView dry_wet_garden;
        TextView crop_name;
        TextView single_crop_extents;
        TextView mixed_crop_extents;
        TextView total_crop_extents;
        TextView water_source;
        TextView yield;
        TextView mixed_crop_name;
        TextView mixed_crop_name_extents;

        public MyViewHolder(View customView) {
            super(customView);

            year_season = (TextView) customView.findViewById(R.id.cultivator_table_year_season);
            cultivator_name = (TextView) customView.findViewById(R.id.cultivator_table_cultivator_name);
            cult_type = (TextView) customView.findViewById(R.id.cultivator_table_cult_type);
            tenancy_extent = (TextView) customView.findViewById(R.id.cultivator_table_tenancy_extent);
            tenancy_rent = (TextView) customView.findViewById(R.id.cultivator_table_tenancy_rent);
            land_utilisation_cls = (TextView) customView.findViewById(R.id.cultivator_table_land_utilisation_cls);
            land_utilisation_rent = (TextView) customView.findViewById(R.id.cultivator_table_land_utilisation_rent);
            dry_wet_garden = (TextView) customView.findViewById(R.id.cultivator_table_dry_wet_garden);
            crop_name = (TextView) customView.findViewById(R.id.cultivator_table_crop_name);
            single_crop_extents = (TextView) customView.findViewById(R.id.cultivator_table_single_crop_extents);
            mixed_crop_extents = (TextView) customView.findViewById(R.id.cultivator_table_mixed_crop_extents);
            total_crop_extents = (TextView) customView.findViewById(R.id.cultivator_table_total_crop_extents);
            water_source = (TextView) customView.findViewById(R.id.cultivator_table_water_source);
            yield = (TextView) customView.findViewById(R.id.cultivator_table_yield);
            mixed_crop_name = (TextView) customView.findViewById(R.id.cultivator_table_mixed_crop_name);
            mixed_crop_name_extents = (TextView) customView.findViewById(R.id.cultivator_table_mixed_crop_name_extents);
        }
    }
}

