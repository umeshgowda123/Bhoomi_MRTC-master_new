package app.bmc.com.BHOOMI_MRTC.fragments;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.adapters.OwnerCustomAdapter;
import app.bmc.com.BHOOMI_MRTC.model.Owner;

public class OwnerDetailsFragment extends Fragment {

    public OwnerDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_owner_details, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //  WebView owner_details_html= view.findViewById(R.id.owner_details_html);
        try {
            // linearLayout=view.findViewById(R.id.owner_table_layout);
            Bundle bundle = this.getArguments();
            assert bundle != null;
            String data = bundle.getString("OwnerDetailsFragment", "");
            TextView taluk_textview = view.findViewById(R.id.textview_taluk);
            TextView hobli_textview = view.findViewById(R.id.textview_hobli);
            TextView village_textview = view.findViewById(R.id.textview_village);
            TextView survey_no_textview = view.findViewById(R.id.textview_survey_no);
            TextView valid_from_textview = view.findViewById(R.id.textview_validfrom);
            taluk_textview.setText(bundle.getString("Taluk", ""));
            hobli_textview.setText(bundle.getString("Hobli", ""));
            village_textview.setText(bundle.getString("Village", ""));
            survey_no_textview.setText(bundle.getString("Survey", ""));
            valid_from_textview.setText(bundle.getString("Valid", ""));


            displayData(data, view);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayData(String data, View view) {
        try {
            ArrayList<Owner> allOwnerList = new ArrayList<>();
            JSONArray jointOwersArray = new JSONArray(data);
            for (int i = 0; i < jointOwersArray.length(); i++) {
                JSONObject jointOwnerObject = jointOwersArray.getJSONObject(i);
                JSONArray owersArray = new JSONArray();
                Object object = jointOwnerObject.get("owner");

                if (object instanceof JSONObject) {
                    owersArray.put(object);
                } else if (object instanceof JSONArray) {
                    owersArray = (JSONArray) object;
                } else if (object instanceof String) {
                    owersArray.put(new JSONObject());
                }
                Type listType = new TypeToken<ArrayList<Owner>>() {
                }.getType();
                List<Owner> ownerList = new Gson().fromJson(owersArray.toString(), listType);
                allOwnerList.addAll(ownerList);
            }

            RecyclerView recyclerView = view.findViewById(R.id.owner_recycler_view);
            recyclerView.setHasFixedSize(true);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            OwnerCustomAdapter adapter = new OwnerCustomAdapter(allOwnerList);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
