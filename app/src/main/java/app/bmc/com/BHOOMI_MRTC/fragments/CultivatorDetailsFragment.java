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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.adapters.CultivatorCustomAdapter;
import app.bmc.com.BHOOMI_MRTC.model.Cropdetails;
import app.bmc.com.BHOOMI_MRTC.model.Cultivator;
import app.bmc.com.BHOOMI_MRTC.model.CultivatorDisplay;
import app.bmc.com.BHOOMI_MRTC.model.Cultivatorname;
import app.bmc.com.BHOOMI_MRTC.model.Landutilisation;
import app.bmc.com.BHOOMI_MRTC.model.Mixedcropdetails;
import app.bmc.com.BHOOMI_MRTC.model.Season;


/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This fragment defined to display cultivator details.
 */
public class CultivatorDetailsFragment extends Fragment {


    private LinearLayout linearLayout;

    public CultivatorDetailsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_cultivator_details, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        String data = bundle.getString("CultivatorDetailsFragment", "");
        // WebView cultivator_details_html = view.findViewById(R.id.cultivator_details_html);
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
    }

    private void displayData(String data, View view) {
        try {
            List<Season> seasonListTotal = new ArrayList<>();
            JSONObject cultivatorObject = new JSONObject(data);
            if (cultivatorObject.has("year")) {
                Object object = cultivatorObject.get("year");
                JSONArray jsonArray = new JSONArray();
                if (object instanceof JSONArray) {
                    jsonArray = (JSONArray) object;
                } else if (object instanceof JSONObject) {
                    jsonArray.put((JSONObject) object);
                } else if (object instanceof String) {
                    jsonArray.put(new JSONObject());
                }

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject yearObject = jsonArray.getJSONObject(i);
                    String year = "";
                    if (yearObject.has("yearname")) {
                        year = yearObject.getString("yearname");
                    }
                    if (yearObject.has("season")) {
                        Object season = yearObject.get("season");
                        JSONArray seasonArray = new JSONArray();
                        if (season instanceof JSONArray) {
                            seasonArray = (JSONArray) season;
                        } else if (season instanceof JSONObject) {
                            seasonArray.put((JSONObject) season);

                        } else if (season instanceof String) {
                            seasonArray.put(new JSONObject());
                        }
                        List<Season> seasonList = new ArrayList<>();
                        for (int j = 0; j < seasonArray.length(); j++) {

                            List<Cultivator> allCultivatorList = new ArrayList<>();
                            JSONObject seasonObject = seasonArray.getJSONObject(j);
                            String seasonName = seasonObject.getString("seasonname");
                            Object cultivatorobject = seasonObject.get("cultivator");
                            JSONArray cultivatorArray = new JSONArray();
                            if (cultivatorobject instanceof JSONObject) {
                                cultivatorArray.put((JSONObject) cultivatorobject);
                            } else if (cultivatorobject instanceof JSONArray) {
                                cultivatorArray = (JSONArray) cultivatorobject;
                            } else if (cultivatorobject instanceof String) {
                                cultivatorArray.put(new JSONObject());
                            }
                            for (int k = 0; k < cultivatorArray.length(); k++) {
                                Gson gson = new Gson();
                                JSONObject cultivator = cultivatorArray.getJSONObject(k);
                                Mixedcropdetails mixedcropdetails = new Mixedcropdetails();
                                if (cultivator.has("mixedcropdetails")) {
                                    Object mixedcropdetailsObject = cultivator.get("mixedcropdetails");
                                    if (mixedcropdetailsObject instanceof JSONObject) {
                                        mixedcropdetails = gson.fromJson(((JSONObject) mixedcropdetailsObject).toString(), Mixedcropdetails.class);

                                    } else if (mixedcropdetailsObject instanceof String) {
                                        mixedcropdetails = gson.fromJson((new JSONObject()).toString(), Mixedcropdetails.class);

                                    }
                                }
                                Cultivatorname cultivatorname = new Cultivatorname();
                                if (cultivator.has("cultivatorname")) {
                                    cultivatorname = gson.fromJson(cultivator.getJSONObject("cultivatorname").toString(), Cultivatorname.class);

                                }
                                String tenantamount = "";
                                if (cultivator.has("tenantamount")) {
                                    tenantamount = cultivator.getString("tenantamount");
                                }
                                Type listType = new TypeToken<ArrayList<Cropdetails>>() {
                                }.getType();
                                ArrayList<Cropdetails> cropdetailsArrayList = new ArrayList<>();
                                if (cultivator.has("cropdetails")) {
                                    Object cropdetailsObject = cultivator.get("cropdetails");
                                    JSONArray cropdetailsjsonArray = new JSONArray();
                                    if (cropdetailsObject instanceof JSONObject) {
                                        cropdetailsjsonArray.put((JSONObject) cropdetailsObject);
                                    } else if (cropdetailsObject instanceof JSONArray) {
                                        cropdetailsjsonArray = (JSONArray) cropdetailsObject;
                                    } else if (cropdetailsObject instanceof String) {
                                        cropdetailsjsonArray.put(new JSONObject());
                                    }
                                    cropdetailsArrayList = new Gson().fromJson(cropdetailsjsonArray.toString(), listType);
                                }
                                String cultivationextent = "";
                                String cultivationtype = "";
                                if (cultivator.has("cultivationextent")) {
                                    cultivationextent = cultivator.getString("cultivationextent");
                                }
                                if (cultivator.has("cultivationtype")) {
                                    cultivationtype = cultivator.getString("cultivationtype");
                                }
                                Landutilisation landutilisation = new Landutilisation();
                                if (cultivator.has("landutilisation")) {
                                    Object landutilisationObject = cultivator.get("landutilisation");
                                    if (landutilisationObject instanceof JSONObject) {
                                        landutilisation = gson.fromJson(((JSONObject) landutilisationObject).toString(), Landutilisation.class);
                                    } else if (landutilisationObject instanceof String) {
                                        landutilisation = gson.fromJson((new JSONObject()).toString(), Landutilisation.class);

                                    }
                                }
                                Cultivator cultivatorDetails = new Cultivator();
                                cultivatorDetails.setCropdetails(cropdetailsArrayList);
                                cultivatorDetails.setCultivationextent(cultivationextent);
                                cultivatorDetails.setCultivationtype(cultivationtype);
                                cultivatorDetails.setLandutilisation(landutilisation);
                                cultivatorDetails.setTenantamount(tenantamount);
                                cultivatorDetails.setCultivatorname(cultivatorname);
                                cultivatorDetails.setMixedcropdetails(mixedcropdetails);
                                allCultivatorList.add(cultivatorDetails);
                            }
                            Season season1 = new Season();
                            season1.setCultivators(allCultivatorList);
                            season1.setSeasonname(seasonName);
                            season1.setYear(year);
                            seasonList.add(season1);
                        }
                        seasonListTotal.addAll(seasonList);
                    }


                }
            }

            ArrayList<CultivatorDisplay> cultivatorDisplayArrayList = new ArrayList<>();
            for (Season season1 : seasonListTotal) {
                for (Cultivator cultivator : season1.getCultivators()) {
                    for (Cropdetails cropdetails : cultivator.getCropdetails()) {
                        CultivatorDisplay cultivatorDisplay = new CultivatorDisplay();
                        cultivatorDisplay.setYear_season(season1.getYear() + "&" + season1.getSeasonname());
                        cultivatorDisplay.setCultivator_name(cultivator.getCultivatorname().getName());
                        cultivatorDisplay.setCult_type(cultivator.getCultivationtype());
                        cultivatorDisplay.setTenancy_extent(cultivator.getCultivationextent());
                        cultivatorDisplay.setTenancy_rent(cultivator.getTenantamount());
                        cultivatorDisplay.setLand_utilisation_cls(cultivator.getLandutilisation().getClassification());
                        cultivatorDisplay.setLand_utilisation_rent(cultivator.getLandutilisation().getUtilisationextents());
                        cultivatorDisplay.setDry_wet_garden(cropdetails.getLandclassification());
                        cultivatorDisplay.setCrop_name(cropdetails.getCropname());
                        cultivatorDisplay.setSingle_crop_extents(cropdetails.getSinglecropextents());
                        cultivatorDisplay.setMixed_crop_extents(cropdetails.getMixedcropextent());
                        cultivatorDisplay.setTotal_crop_extents(cropdetails.getTotalcropextents());
                        cultivatorDisplay.setWater_source(cropdetails.getWatersource());
                        cultivatorDisplay.setYield(cropdetails.getYieldperacre());
                        cultivatorDisplay.setMixed_crop_name(cultivator.getMixedcropdetails().getMixedcropname());
                        cultivatorDisplay.setMixed_crop_name_extents(cultivator.getMixedcropdetails().getMixedcropextents());
                        cultivatorDisplayArrayList.add(cultivatorDisplay);

                    }
                }
            }

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.cultivator_recycler_view);
            recyclerView.setHasFixedSize(true);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            CultivatorCustomAdapter adapter = new CultivatorCustomAdapter(cultivatorDisplayArrayList);
            recyclerView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
