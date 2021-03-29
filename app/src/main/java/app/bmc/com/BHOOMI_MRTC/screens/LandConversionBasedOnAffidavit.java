package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.adapters.LandConversionBasedOnAffidavitAdapter;
import app.bmc.com.BHOOMI_MRTC.adapters.ViewConvStatusAdapter;
import app.bmc.com.BHOOMI_MRTC.model.Afdvt_ReqSts_BasedOnAfdvtIdTable;

public class LandConversionBasedOnAffidavit extends AppCompatActivity {

    List<Afdvt_ReqSts_BasedOnAfdvtIdTable> AfdvtBasedOnAfdvtIdData;
    RecyclerView rvAffidavit;
    TextView tvAffidavit, txtStageWiseStatus;
    ArrayList<String> models;
    ArrayList<String> txtHeader;
    ArrayList<Integer> Images;
    ArrayList<Boolean> isShow;
    RecyclerView recyclerView;
    ViewConvStatusAdapter recyclerView_Adapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    LinearLayout layoutStageWiseStatus;
    String showText_notEnt = "Still Not Entered to this Stage";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_conversion_based_on_affidavit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        rvAffidavit = findViewById(R.id.rvAffidavit);
        tvAffidavit = findViewById(R.id.tvAffidavit);
        recyclerView = findViewById(R.id.recycler_view1);
        txtStageWiseStatus = findViewById(R.id.txtStageWiseStatus);
        layoutStageWiseStatus = findViewById(R.id.layoutStageWiseStatus);

        models = new ArrayList<>();
        txtHeader = new ArrayList<>();
        Images = new ArrayList<>();
        isShow = new ArrayList<>();

        models.add(0, "" + showText_notEnt);
        models.add(1, "" + showText_notEnt);
        models.add(2, "" + showText_notEnt);
        models.add(3, "" + showText_notEnt);
        models.add(4, "" + showText_notEnt);

        txtHeader.add(0, "");
        txtHeader.add(1, "");
        txtHeader.add(2, "Bhoomi Data Entry");
        txtHeader.add(3, "Office RI Login");
        txtHeader.add(4, "Mutation Approved/Rejected");

        Images.add(0, R.drawable.ic_baseline_adjust_24);
        Images.add(1, R.drawable.ic_baseline_adjust_24);
        Images.add(2, R.drawable.ic_baseline_adjust_24);
        Images.add(3, R.drawable.ic_baseline_adjust_24);
        Images.add(4, R.drawable.ic_baseline_adjust_24);

        isShow.add(0, false);
        isShow.add(1, false);
        isShow.add(2, false);
        isShow.add(3, false);
        isShow.add(4, false);

        String AFFIDAVIT_ResponseData = (String) getIntent().getExtras().getSerializable("AFFIDAVIT_ResponseData");

        if(AFFIDAVIT_ResponseData != null) {
            try {
//                JSONObject jsonObject = new JSONObject(AFFIDAVIT_ResponseData);
//
//                GetStatusBasedOnIDClass getStatusBasedOnIDClass = new GetStatusBasedOnIDClass();
//                getStatusBasedOnIDClass.setMessage(jsonObject.getString("Message"));
//                getStatusBasedOnIDClass.setStatusCode(jsonObject.getString("StatusCode"));
//                getStatusBasedOnIDClass.setRequestID(jsonObject.getString("RequestID"));
//                getStatusBasedOnIDClass.setAffidavitID(jsonObject.getString("AffidavitID"));
//                getStatusBasedOnIDClass.setStatus(jsonObject.getString("Status"));
//                getStatusBasedOnIDClass.setDistrict(jsonObject.getString("District"));
//                getStatusBasedOnIDClass.setTaluk(jsonObject.getString("Taluk"));
//                getStatusBasedOnIDClass.setHobli(jsonObject.getString("Hobli"));
//                getStatusBasedOnIDClass.setVillage(jsonObject.getString("Village"));
//                getStatusBasedOnIDClass.setSurveyNos(jsonObject.getString("SurveyNos"));
//                getStatusBasedOnIDClass.setApplicationDate(jsonObject.getString("ApplicationDate"));
//                getStatusBasedOnIDClass.setREQ_WFID(jsonObject.getString("REQ_WFID"));
//                getStatusBasedOnIDClass.setApprovedDate(jsonObject.getString("ApprovedDate"));
//
//                String Status = getStatusBasedOnIDClass.getStatus();
//
//                if (Status.contains("Generate Endorsement")){
//                    Log.d("Status", ""+Status);
//                } else {
//                    Log.d("Status", ""+getStatusBasedOnIDClass.getStatus());
//                }

                JSONArray jsonArray = new JSONArray(AFFIDAVIT_ResponseData);

                Gson gson = new Gson();
                AfdvtBasedOnAfdvtIdData = gson.fromJson(String.valueOf(jsonArray), new TypeToken<List<Afdvt_ReqSts_BasedOnAfdvtIdTable>>() {}.getType());

                if (AfdvtBasedOnAfdvtIdData.size() == 0) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(LandConversionBasedOnAffidavit.this, R.style.MyDialogTheme);
                    builder.setTitle(getString(R.string.status))
                            .setMessage(getString(R.string.no_data_found_for_this_record))
                            .setIcon(R.drawable.ic_notifications_black_24dp)
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                    final AlertDialog alert = builder.create();
                    alert.show();
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                } else {
                    AfdvtBasedOnAfdvtIdData.size();
                    tvAffidavit.setVisibility(View.VISIBLE);
                    rvAffidavit.setVisibility(View.VISIBLE);
                    LandConversionBasedOnAffidavitAdapter adapter = new LandConversionBasedOnAffidavitAdapter(AfdvtBasedOnAfdvtIdData,this);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    rvAffidavit.setLayoutManager(mLayoutManager);
                    rvAffidavit.setItemAnimator(new DefaultItemAnimator());
                    rvAffidavit.setAdapter(adapter);

                    displayAppStatus(AFFIDAVIT_ResponseData);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Null", Toast.LENGTH_SHORT).show();
        }
    }

    public void displayAppStatus(String ResponseData){
        if (ResponseData!=null){
            try {
                JSONArray jsonArray = new JSONArray(ResponseData);

                if (jsonArray.length()>0) {
                    for (int j=0; j<jsonArray.length(); j++){
                        JSONObject jsonObject = jsonArray.getJSONObject(j);

                        String Status_Description = jsonObject.getString("Status_Description");

                        String showText_0 = "Land Conversion Request No : "+jsonObject.getString("REQ_ID")
                                +", Affidavit ID : "+jsonObject.getString("REQ_AID")
                                +"  is Initiated On : "+jsonObject.getString("REQ_CDTE");

                        String shwText_00 = "\n"
                                +"Application Entry Date : "+jsonObject.getString("REQ_CDTE")
                                +"\n"
                                + "Description :\n"
                                + "\n"
                                + "Application Status is : "+Status_Description+", Affidavit ID : " +jsonObject.getString("REQ_AID")
                                +", District : "+jsonObject.getString("DISTRICT_NAME")
                                +", Taluk : "+jsonObject.getString("TALUKA_NAME")
                                +", Hobli : "+jsonObject.getString("HOBLI_NAME")
                                +", Village : "+jsonObject.getString("VILLAGE_NAME")
                                +", SurveyNo : "+jsonObject.getString("surveyno");

                        String showText_1 = "Application Status is : "+Status_Description;

                        String showText_11 = "Affidavit ID : "+jsonObject.getString("REQ_AID")+
                                " is Initiated On : "+jsonObject.getString("REQ_CDTE")+" Application Status is : "
                                +Status_Description;

                        txtHeader.set(0, ""+showText_0);
                        models.set(0,""+shwText_00);
                        Images.set(0, R.drawable.ic_baseline_check_circle_24);

                        txtStageWiseStatus.setVisibility(View.VISIBLE);
                        layoutStageWiseStatus.setVisibility(View.VISIBLE);

                        txtHeader.set(1, ""+showText_1);
                        if (Status_Description.contains("Generate Endorsement")) {
                            txtStageWiseStatus.setVisibility(View.GONE);
                            layoutStageWiseStatus.setVisibility(View.GONE);
                            models.set(1, "\n" + showText_11);
                            Images.set(1, R.drawable.ic_baseline_cancel_24);
                        } else if(Status_Description.contains("Pending For DC") ||
                                Status_Description.equalsIgnoreCase("Pending For DC") ||
                                Status_Description.contains("Pending For Other departments")||
                                Status_Description.equalsIgnoreCase("Pending For Other departments")||
                                Status_Description.contains("Pending for other departments to take action")||
                                Status_Description.equalsIgnoreCase("Pending with DC Caseworker or Other Departments")||
                                Status_Description.equalsIgnoreCase("Pending for other departments to take action")) {
                            models.set(1, "\n" + showText_11);
                            Images.set(1, R.drawable.ic_baseline_access_time_24);
                        } else if(Status_Description.contains("Generate Final Order") ||
                                Status_Description.equalsIgnoreCase("Generate Final Order")){
                            models.set(1, "\n" + showText_11);
                            Images.set(1, R.drawable.ic_baseline_check_circle_24);
                        } else {
                            Images.set(1, R.drawable.ic_baseline_adjust_24);
                        }
                    }
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(LandConversionBasedOnAffidavit.this, R.style.MyDialogTheme);
                    builder.setTitle(getString(R.string.status))
                            .setMessage(getString(R.string.no_data_found_for_this_record))
                            .setIcon(R.drawable.ic_notifications_black_24dp)
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                    final AlertDialog alert = builder.create();
                    alert.show();
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                }

                recyclerViewLayoutManager = new GridLayoutManager(this,1);
                recyclerView.setLayoutManager(recyclerViewLayoutManager);
                recyclerView_Adapter = new ViewConvStatusAdapter(this, txtHeader,models, Images, isShow);
                recyclerView.setAdapter(recyclerView_Adapter);

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
