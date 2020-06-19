package app.bmc.com.bhoomi.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import app.bmc.com.bhoomi.R;
import app.bmc.com.bhoomi.model.MutationStatusData;
import app.bmc.com.bhoomi.screens.ShowMutationStatusDetails;

public class MutationStatusAdapter extends RecyclerView.Adapter<MutationStatusAdapter.MyViewHolder> {

    private  List<MutationStatusData> clist ;
    private ShowMutationStatusDetails activity;

    public MutationStatusAdapter(List<MutationStatusData> mutationStatusDataList, ShowMutationStatusDetails activity){
        this.clist = mutationStatusDataList;
        this.activity = activity;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvYear, tvMutationType, tvMutationStatusKan, tvMutationStatusEng;

        public MyViewHolder(View view) {
            super(view);

            tvYear = view.findViewById(R.id.tvYear);
            tvMutationType = view.findViewById(R.id.tvMutationType);
            tvMutationStatusKan = view.findViewById(R.id.tvMutationStatusKan);
            tvMutationStatusEng = view.findViewById(R.id.tvMutationStatusEng);

        }
    }

    @NonNull
    @Override
    public MutationStatusAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mutation_status_details_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MutationStatusAdapter.MyViewHolder myViewHolder, int position) {
        if(clist.size() == 0)
        {
            Toast.makeText(activity, "No Data Found!", Toast.LENGTH_SHORT).show();
        }else
        {
            myViewHolder.tvYear.setText(clist.get(position).getYEAR());
            myViewHolder.tvMutationType.setText(clist.get(position).getMUTATION_TYPE());
            myViewHolder.tvMutationStatusEng.setText(clist.get(position).getMUTAION_STATUS_ENG());
            myViewHolder.tvMutationStatusKan.setText(clist.get(position).getMUTAION_STATUS_KN());
        }
    }

    @Override
    public int getItemCount() {
        return clist.size();
    }
}
