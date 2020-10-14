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
import app.bmc.com.BHOOMI_MRTC.adapters.LandCustomAdapter;
import app.bmc.com.BHOOMI_MRTC.model.Irrigation;
import app.bmc.com.BHOOMI_MRTC.model.Irrigationdetails;
import app.bmc.com.BHOOMI_MRTC.model.Landdetails;
import app.bmc.com.BHOOMI_MRTC.model.Pahanidetails;
import app.bmc.com.BHOOMI_MRTC.model.Staticinfopahani;
import app.bmc.com.BHOOMI_MRTC.model.Tree;
import app.bmc.com.BHOOMI_MRTC.model.Treedetails;
import app.bmc.com.BHOOMI_MRTC.model.Villagedetails;

public class LandDetailsFragment extends Fragment {


    public LandDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_land_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            //WebView land_details_html=view.findViewById(R.id.land_details_html);
            Bundle bundle = this.getArguments();
            assert bundle != null;
            String data = bundle.getString("LandDetailsFragment", "");
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

    private void displayData(String data, View view) throws Exception {
        Staticinfopahani staticinfopahani = new Staticinfopahani();
        Pahanidetails pahanidetails = new Pahanidetails();
        Villagedetails villagedetails = new Villagedetails();
        Gson gson = new Gson();
        ArrayList<Landdetails> landdetailsArrayList = new ArrayList<>();
        JSONObject rtc = new JSONObject(data);


        if (rtc.has("staticinfopahani")) {
            Object staticinfopahaniObject = rtc.get("staticinfopahani");

            if (staticinfopahaniObject instanceof JSONObject) {
                staticinfopahani = gson.fromJson(staticinfopahaniObject.toString(), Staticinfopahani.class);

            } else if (staticinfopahaniObject instanceof String) {
                staticinfopahani = new Staticinfopahani();
            }

        }
        if (rtc.has("pahanidetails")) {
            Object pahanidetailsObject = rtc.get("pahanidetails");

            if (pahanidetailsObject instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) pahanidetailsObject;
                pahanidetails = gson.fromJson(jsonObject.toString(), Pahanidetails.class);
            } else if (pahanidetailsObject instanceof String) {
                pahanidetails = new Pahanidetails();
            }
        }

        Treedetails treedetails = new Treedetails();
        JSONArray jsonArray = new JSONArray();
        if (rtc.has("treedetails")) {
            Object treedetailsObject = rtc.get("treedetails");
            if (treedetailsObject instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) treedetailsObject;
                if (jsonObject.has("tree")) {
                    Object treeObj = jsonObject.get("tree");
                    if (treeObj instanceof JSONArray) {
                        jsonArray = (JSONArray) treeObj;

                    } else if (treeObj instanceof JSONObject) {
                        jsonArray.put(treeObj);
                    } else if (treeObj instanceof String) {
                        jsonArray.put(new JSONObject());
                    }
                }


            }


        }
        Type treeListType = new TypeToken<ArrayList<Tree>>() {
        }.getType();
        List<Tree> treedetailsList = new Gson().fromJson(jsonArray.toString(), treeListType);
        treedetails.setTree(treedetailsList);


        if (rtc.has("villagedetails")) {

            Object villagedetailsObject = rtc.get("villagedetails");
            if (villagedetailsObject instanceof JSONObject) {
                villagedetails = gson.fromJson(villagedetailsObject.toString(), Villagedetails.class);
            } else if (villagedetailsObject instanceof String) {
                villagedetails = new Villagedetails();
            }
        }
        List<Irrigation> irrigationList = new ArrayList<>();

        if (rtc.has("irrigationdetails")) {
            Object irrigationdetailsObject = rtc.get("irrigationdetails");
            if (irrigationdetailsObject instanceof JSONObject) {

                JSONObject irrigationdetails = (JSONObject) irrigationdetailsObject;
                Object irrigationObject = irrigationdetails.get("irrigation");
                JSONArray irrigationArray = new JSONArray();

                if (irrigationObject instanceof JSONArray) {
                    irrigationArray = (JSONArray) irrigationObject;
                } else if (irrigationObject instanceof JSONObject) {
                    irrigationArray.put(irrigationObject);
                } else if (irrigationObject instanceof String) {
                    irrigationArray.put(new JSONObject());
                }
                Type listType = new TypeToken<ArrayList<Irrigation>>() {
                }.getType();
                irrigationList = new Gson().fromJson(irrigationArray.toString(), listType);
            }
            Landdetails landdetails = new Landdetails();
            Irrigationdetails irrigationdetails1 = new Irrigationdetails();
            irrigationdetails1.setIrrigation(irrigationList);
            landdetails.setIrrigationdetails(irrigationdetails1);
            landdetails.setPahanidetails(pahanidetails);
            landdetails.setStaticinfopahani(staticinfopahani);
            landdetails.setVillagedetails(villagedetails);
            landdetails.setTreedetails(treedetails);
            landdetailsArrayList.add(landdetails);
        }


        RecyclerView recyclerView = view.findViewById(R.id.land_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LandCustomAdapter adapter = new LandCustomAdapter(landdetailsArrayList, getContext());
        recyclerView.setAdapter(adapter);
    }
}
