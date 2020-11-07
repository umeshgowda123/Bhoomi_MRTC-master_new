package app.bmc.com.BHOOMI_MRTC.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

import app.bmc.com.BHOOMI_MRTC.model.RTCByOwnerNameResponse;

public class ViewRTCByOwnerNameNewAdapter extends BaseAdapter implements Filterable {

    private Context mContext;
    private ArrayList<String> distId, talkId, hblId, villId;
    ArrayList<RTCByOwnerNameResponse> dataList;

    ViewRTCByOwnerNameNewAdapter(ArrayList<RTCByOwnerNameResponse> data, Context context, ArrayList<String> distId,
                                 ArrayList<String> talkId, ArrayList<String> hblId, ArrayList<String> villId){
        this.dataList = data;
        this.mContext = context;
        this.distId = distId;
        this.talkId = talkId;
        this.hblId = hblId;
        this.villId = villId;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}

class ViewRTCByOwnerNameViewHolder{

        }
