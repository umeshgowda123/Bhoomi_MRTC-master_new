package app.bmc.com.BHOOMI_MRTC.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import app.bmc.com.BHOOMI_MRTC.R;

public class ViewConvStatusAdapter extends RecyclerView.Adapter<ViewConvStatusAdapter.ViewHolder> {

    private final ArrayList<String> Values1;
    private final ArrayList<String> Values2;
    private final Context context1;
    private final ArrayList<Integer> Images;
    private final ArrayList<Boolean> isShow;
    Animation animSlideDown, animSlideUp;
    boolean layOpen = false;

    public ViewConvStatusAdapter(Context context2, ArrayList<String> values1, ArrayList<String> values2, ArrayList<Integer> images, ArrayList<Boolean> is_show){
        Values1 = values1;
        Values2 = values2;
        Images = images;
        isShow = is_show;
        context1 = context2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(context1).inflate(R.layout.view_conv_status,parent,false);
        return new ViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        animSlideDown = AnimationUtils.loadAnimation(context1, R.anim.slide_down);
        animSlideUp = AnimationUtils.loadAnimation(context1, R.anim.slide_up);
        holder.linLay.startAnimation(animSlideDown);

        Glide.with(context1).load(R.drawable.gif_bounce_arrow).into(holder.ivBounceArrowDown);
        holder.ivBounceArrowUp.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);

        holder.linLay.setOnClickListener(v -> {
            layOpen = isShow.get(position);
            if (layOpen){
                isShow.set(position, false);
                holder.txtDescription.setVisibility(View.GONE);
                holder.txtDescription.startAnimation(animSlideUp);
                holder.ivBounceArrowDown.setVisibility(View.VISIBLE);
                holder.ivBounceArrowUp.setVisibility(View.GONE);
            } else {
                isShow.set(position, true);
                holder.txtDescription.setVisibility(View.VISIBLE);
                holder.txtDescription.startAnimation(animSlideDown);
                holder.ivBounceArrowDown.setVisibility(View.GONE);
                holder.ivBounceArrowUp.setVisibility(View.VISIBLE);
            }
        });

        holder.expandable_txtHeader.setText(Values1.get(position));
        holder.txtDescription.setText(Values2.get(position));
        holder.img.setImageResource(Images.get(position));
    }

    @Override
    public int getItemCount() {
        return Values2.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView expandable_txtHeader, txtDescription;
        ImageView img;
        ImageView ivBounceArrowDown, ivBounceArrowUp;
        LinearLayout linLay;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            expandable_txtHeader = itemView.findViewById(R.id.exp_txtHeader);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            img =itemView.findViewById(R.id.img);
            linLay = itemView.findViewById(R.id.linLay);
            ivBounceArrowDown = itemView.findViewById(R.id.ivBounceArrowDown);
            ivBounceArrowUp = itemView.findViewById(R.id.ivBounceArrowUp);
        }
    }
}
