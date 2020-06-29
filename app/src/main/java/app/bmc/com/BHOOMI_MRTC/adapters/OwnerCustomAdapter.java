package app.bmc.com.BHOOMI_MRTC.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.model.Owner;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This class defined to create custom list to display data comming server which is
 * Owner xml response.
 */
public class OwnerCustomAdapter extends RecyclerView.Adapter<OwnerCustomAdapter.MyViewHolder> {

    private ArrayList<Owner> dataSet;

    public OwnerCustomAdapter(ArrayList<Owner> data) {
        this.dataSet = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.owner_list, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView owner_name = holder.owner_name;
        TextView extent_ac_gun = holder.extent_ac_gun;
        TextView khata_no = holder.khata_no;
        TextView liablities = holder.liablities;
        TextView rights = holder.rights;
        TextView Acquisitiondetails = holder.Acquisitiondetails;

        owner_name.setText(dataSet.get(listPosition).getOwnername().getName() + " " + dataSet.get(listPosition).getOwnername().getRelationship() + " " + dataSet.get(listPosition).getOwnername().getRelativename());
        extent_ac_gun.setText(dataSet.get(listPosition).getOwnerextents());
        khata_no.setText(dataSet.get(listPosition).getKhathanumber());
        liablities.setText(dataSet.get(listPosition).getLiabilities());
        rights.setText(dataSet.get(listPosition).getRights());
        Acquisitiondetails.setText(dataSet.get(listPosition).getAcquisitiondetails());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView owner_name;
        TextView extent_ac_gun;
        TextView khata_no;
        TextView liablities;
        TextView rights;
        TextView Acquisitiondetails;

        public MyViewHolder(View itemView) {
            super(itemView);

            owner_name = itemView.findViewById(R.id.owner_table_owner_name);
            extent_ac_gun = itemView.findViewById(R.id.owner_table_extent_ac_gun);
            khata_no = itemView.findViewById(R.id.owner_table_khata_no);
            liablities = itemView.findViewById(R.id.owner_table_liablities);
            rights = itemView.findViewById(R.id.owner_table_rights);
            Acquisitiondetails = itemView.findViewById(R.id.owner_table_Acquisitiondetails);
        }
    }
}
