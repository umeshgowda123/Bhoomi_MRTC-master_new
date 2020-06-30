package app.bmc.com.BHOOMI_MRTC.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.model.PhodySketch;

public class SkatchAdapter extends RecyclerView.Adapter<SkatchAdapter.ViewHolder> {
    private List<PhodySketch> list ;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sketch_row,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imgres = list.get(position).getImage();
        Log.d("img_11",""+imgres);
        if (imgres!=null) {
            imgres = imgres.replace("data:image/png;base64,", "");
            Log.d("img_11", "" + imgres);

            byte[] im_bytes = Base64.decode(imgres, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(im_bytes, 0, im_bytes.length);
            Log.d("bitmap",bitmap+"");

            holder.sketch.setImageBitmap(bitmap);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView sketch;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sketch = itemView.findViewById(R.id.sketch);
        }
    }
    public SkatchAdapter(List<PhodySketch> list) {
        this.list = list;
    }
}
