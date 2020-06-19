package app.bmc.com.bhoomi.screens;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.bmc.com.bhoomi.R;
import app.bmc.com.bhoomi.adapters.CultivatorCustomAdapter;
import app.bmc.com.bhoomi.adapters.LandCustomAdapter;
import app.bmc.com.bhoomi.adapters.OwnerCustomAdapter;
import app.bmc.com.bhoomi.model.Cropdetails;
import app.bmc.com.bhoomi.model.Cultivator;
import app.bmc.com.bhoomi.model.CultivatorDisplay;
import app.bmc.com.bhoomi.model.Cultivatorname;
import app.bmc.com.bhoomi.model.Irrigation;
import app.bmc.com.bhoomi.model.Irrigationdetails;
import app.bmc.com.bhoomi.model.Landdetails;
import app.bmc.com.bhoomi.model.Landutilisation;
import app.bmc.com.bhoomi.model.Mixedcropdetails;
import app.bmc.com.bhoomi.model.Owner;
import app.bmc.com.bhoomi.model.Pahanidetails;
import app.bmc.com.bhoomi.model.Season;
import app.bmc.com.bhoomi.model.Staticinfopahani;
import app.bmc.com.bhoomi.model.Tree;
import app.bmc.com.bhoomi.model.Treedetails;
import app.bmc.com.bhoomi.model.Villagedetails;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This class  for reprajents UI and display response from server i.e rtc xml verification
 */
public class RtcVerificationData extends AppCompatActivity {
    private TextView taluk_textview;
    private TextView hobli_textview;
    private TextView village_textview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtc_verification_data);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        taluk_textview = findViewById(R.id.verification_textview_taluk);
        hobli_textview = findViewById(R.id.verification_textview_hobli);
        village_textview = findViewById(R.id.verification_textview_village);
        String displayData = getIntent().getStringExtra("xml_data");
        displayData(displayData);
    }

    private void displayData(String displayData) {

        XmlToJson xmlToJson = new XmlToJson.Builder(displayData.replace("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>", "").trim()).build();
        // convert to a JSONObject
        JSONObject output = xmlToJson.toJson();


        try {
            String formatted = xmlToJson.toFormattedString().replace("\n", "");
            JSONObject obj = new JSONObject(formatted);
            JSONObject rtc = obj.getJSONObject("rtc");
            JSONObject StatusTag = rtc.getJSONObject("StatusTag");
            int Status = StatusTag.getInt("Status");
            if (Status == 0) {

                JSONObject ownerdetails = rtc.getJSONObject("ownerdetails");
                Object object = ownerdetails.get("jointowners");
                JSONArray jointOwners = new JSONArray();
                if (object instanceof JSONObject) {
                    jointOwners.put((JSONObject) object);
                } else {
                    jointOwners = (JSONArray) object;
                }
                JSONObject cultivatordetails = rtc.getJSONObject("cultivatordetails");
                displayCultivatorData(cultivatordetails.toString());
                displayOwnerData(jointOwners.toString());
                displayLandData(rtc.toString());
            } else if (Status == 1) {
                String StatusMsg = StatusTag.getString("StatusMsg");
                Toast.makeText(getApplicationContext(), StatusMsg, Toast.LENGTH_LONG).show();
                return;
            } else {
                String StatusMsg = "Something Went Wrong try later";
                Toast.makeText(getApplicationContext(), StatusMsg, Toast.LENGTH_LONG).show();
                return;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void displayLandData(String data) {
        try {
            Staticinfopahani staticinfopahani = new Staticinfopahani();
            Pahanidetails pahanidetails = new Pahanidetails();
            Villagedetails villagedetails = new Villagedetails();
            Gson gson = new Gson();
            ArrayList<Landdetails> landdetailsArrayList = new ArrayList<Landdetails>();
            JSONObject rtc = new JSONObject(data);


            if (rtc.has("staticinfopahani")) {
                Object staticinfopahaniObject = rtc.get("staticinfopahani");
                if (staticinfopahaniObject instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) staticinfopahaniObject;
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
                            jsonArray.put((JSONObject) treeObj);
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
                    JSONObject jsonObject = (JSONObject) villagedetailsObject;
                    villagedetails = gson.fromJson(villagedetailsObject.toString(), Villagedetails.class);
                    taluk_textview.setText(villagedetails.getTalukname());
                    hobli_textview.setText(villagedetails.getHobliname());
                    village_textview.setText(villagedetails.getVillagename());
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
                        irrigationArray.put((JSONObject) irrigationObject);
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


            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rtc_verification_land_recycler_view);
            recyclerView.setHasFixedSize(true);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            LandCustomAdapter adapter = new LandCustomAdapter(landdetailsArrayList, getApplicationContext());
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void displayOwnerData(String data) {
        try {
            ArrayList<Owner> allOwnerList = new ArrayList<>();
            JSONArray jointOwersArray = new JSONArray(data);
            for (int i = 0; i < jointOwersArray.length(); i++) {
                JSONObject jointOwnerObject = jointOwersArray.getJSONObject(i);
                JSONArray owersArray = new JSONArray();
                Object object = jointOwnerObject.get("owner");

                if (object instanceof JSONObject) {
                    owersArray.put((JSONObject) object);
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

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rtc_verification_owner_recycler_view);
            recyclerView.setHasFixedSize(true);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            OwnerCustomAdapter adapter = new OwnerCustomAdapter(allOwnerList);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayCultivatorData(String data) {
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
                        cultivatorDisplay.setYear_season(season1.getYear() + "& " + season1.getSeasonname());
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

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rtc_verification_cultivator_recycler_view);
            recyclerView.setHasFixedSize(true);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            CultivatorCustomAdapter adapter = new CultivatorCustomAdapter(cultivatorDisplayArrayList);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
