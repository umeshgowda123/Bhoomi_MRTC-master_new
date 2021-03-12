package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;

import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import app.bmc.com.BHOOMI_MRTC.R;

import app.bmc.com.BHOOMI_MRTC.adapters.ViewConvStatusAdapter;
import app.bmc.com.BHOOMI_MRTC.model.MutationStatusData;

public class ShowMutationStatusDetails extends AppCompatActivity {

    private List<MutationStatusData> myPendingDataList = new ArrayList<>();

    RecyclerView recyclerView;
    ViewConvStatusAdapter recyclerView_Adapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    LinearLayout layoutStageWiseStatus;
    String showText_notEnt = "Still Not Entered to this Stage";
    TextView txtStageWiseStatus;

    TextView tvYear, tvMutationType, tvMutationStatusKan, tvMutationStatusEng, tvApplicationNo, tvTransactionNo, tvMRNo, tvSurveyNo;
    ArrayList<String> models;
    ArrayList<String> txtHeader;
    ArrayList<Integer> Images;
    ArrayList<Boolean> isShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mutation_status_report);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        recyclerView = findViewById(R.id.rv);
        txtStageWiseStatus = findViewById(R.id.txtStageWiseStatus);
        layoutStageWiseStatus = findViewById(R.id.layoutStageWiseStatus);

        tvYear = findViewById(R.id.tvYear);
        tvMutationType = findViewById(R.id.tvMutationType);
        tvMutationStatusKan = findViewById(R.id.tvMutationStatusKan);
        tvMutationStatusEng = findViewById(R.id.tvMutationStatusEng);
        tvApplicationNo = findViewById(R.id.tvApplicationNo);
        tvTransactionNo = findViewById(R.id.tvTransactionNo);
        tvMRNo = findViewById(R.id.tvMRNo);
        tvSurveyNo = findViewById(R.id.tvSurveyNo);

        models = new ArrayList<>();
        txtHeader = new ArrayList<>();
        Images = new ArrayList<>();
        isShow = new ArrayList<>();

        models.add(0, "" + showText_notEnt);
        models.add(1, "" + showText_notEnt);
        models.add(2, "" + showText_notEnt);
        models.add(3, "" + showText_notEnt);
        models.add(4, "" + showText_notEnt);
        models.add(5, "" + showText_notEnt);
        models.add(6, "" + showText_notEnt);

        txtHeader.add(0, "");
        txtHeader.add(1, "");
        txtHeader.add(2, "");
        txtHeader.add(3, "");
        txtHeader.add(4, "");
        txtHeader.add(5, "");
        txtHeader.add(6, "");

        Images.add(0, R.drawable.ic_baseline_adjust_24);
        Images.add(1, R.drawable.ic_baseline_adjust_24);
        Images.add(2, R.drawable.ic_baseline_adjust_24);
        Images.add(3, R.drawable.ic_baseline_adjust_24);
        Images.add(4, R.drawable.ic_baseline_adjust_24);
        Images.add(5, R.drawable.ic_baseline_adjust_24);
        Images.add(6, R.drawable.ic_baseline_adjust_24);


        isShow.add(0, false);
        isShow.add(1, false);
        isShow.add(2, false);
        isShow.add(3, false);
        isShow.add(4, false);
        isShow.add(5, false);
        isShow.add(6, false);

        String status_data_response = getIntent().getStringExtra("status_response_data");

        showData(status_data_response);
    }

    private void showData(String status_data_response) {

        try {

//            XmlToJson xmlToJson = new XmlToJson.Builder(status_data_response.replace("\\r\\n", "").trim()).build();
//            Log.d("xmlToJson_Formatted", ""+xmlToJson.toFormattedString());
//            String formatted = xmlToJson.toFormattedString().replace("\n", "");
//            Log.d("formatted",""+formatted);
//
//            JSONObject responseObject = new JSONObject(formatted);
//            Log.d("responseObject",""+responseObject);
//            JSONObject mutationDetails =  responseObject.getJSONObject("NewDataSet");
//            Log.d("mutation_NewDataSet",""+mutationDetails);
//            JSONArray mutationEntries = null;

            JSONObject obj1 = new JSONObject(status_data_response);
            String form = String.valueOf(obj1);
            form = form.replace("{\"MutationStatus\":{", "{\"MutationStatus\":[{");
            form = form.replace("}}", "}]}");

            JSONObject obj = new JSONObject(form);

            JSONArray tableEntries;

            if(status_data_response.contains("MutationStatus"))
            {
                //tableEntries = new JSONArray(form);
                tableEntries = obj.getJSONArray("MutationStatus");
                Type listType = new TypeToken<List<MutationStatusData>>() {
                }.getType();
                myPendingDataList = new Gson().fromJson(tableEntries.toString(), listType);
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
        }


        if (myPendingDataList.size() > 0) {

            Log.d("SIZEEEEE",myPendingDataList.size()+"");
            tvApplicationNo.setText(myPendingDataList.get(0).getAPPL_NO());
            tvTransactionNo.setText(myPendingDataList.get(0).getTRAN_NO());
            tvMRNo.setText(myPendingDataList.get(0).getMR_NO());
            tvYear.setText(myPendingDataList.get(0).getYEAR());
            tvSurveyNo.setText(myPendingDataList.get(0).getSURVEY_NO());
            tvMutationType.setText(myPendingDataList.get(0).getMUTATION_TYPE());
            tvMutationStatusEng.setText(myPendingDataList.get(0).getMUTAION_STATUS_ENG());
            tvMutationStatusKan.setText(myPendingDataList.get(0).getMUTAION_STATUS_KN());

            displayAppStatus();

        }else
        {
            final AlertDialog.Builder builder = new AlertDialog.Builder(ShowMutationStatusDetails.this, R.style.MyDialogTheme);
            builder.setTitle(getString(R.string.status))
                    .setMessage(getString(R.string.no_data_found_for_this_record))
                    .setIcon(R.drawable.ic_notifications_black_24dp)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
            final AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        }


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void displayAppStatus( ){
            try {
                    for (int j = 0; j < myPendingDataList.size(); j++) {

                        String TranEnteredBy = myPendingDataList.get(j).getTranEnteredBy() + "";
                        String TranEnteredDate = myPendingDataList.get(j).getTranEnteredDate() + "";
                        String CheckListApprBy = myPendingDataList.get(j).getCheckListApprBy() + "";
                        String CheckListApprDate = myPendingDataList.get(j).getCheckListApprDate() + "";
                        String NoticeGenOn = myPendingDataList.get(j).getNoticeGenOn() + "";
                        String RI_ProvAcc = myPendingDataList.get(j).getRI_ProvAcc() + "";
                        String RI_ProvAccDate = myPendingDataList.get(j).getRI_ProvAccDate() + "";
                        String SurveyorApprDate = myPendingDataList.get(j).getSurveyorApprDate() + "";
                        String TypeOfMutCode = myPendingDataList.get(j).getTypeOfMutCode()+"";

                        String NoticePeriod = "30 Days";

                        if (TypeOfMutCode.equals("21") || TypeOfMutCode.equals("22")){

                            Log.d("TypeOfMutCode",TypeOfMutCode);

                            txtHeader.set(0, "Data Entry");
                            models.set(0, "Transaction Entered By : "+ TranEnteredBy +"\n" +" Transaction Entered on : "+TranEnteredDate);
                            Images.set(0, R.drawable.ic_baseline_check_circle_24);

                            txtHeader.set(1, "Checklist Approval");
                            models.set(1, "\n" + "Checklist Approved By : "+ CheckListApprBy+"\n" +" Approved On : "+CheckListApprDate);
                            Images.set(1, R.drawable.ic_baseline_check_circle_24);

                            txtHeader.set(2, "Notice Generation (Form 21)");
                            models.set(2, "\n" + "Notice Generated on : "+NoticeGenOn +"\n" +"Notice Period : "+NoticePeriod);
                            Images.set(2, R.drawable.ic_baseline_check_circle_24);

                            txtHeader.set(3, "RI Provisional Acceptance");
                            models.set(3, "\n" + "Provisional Accepted : "+RI_ProvAcc +"\n" +" Approved On : "+RI_ProvAccDate);
                            Images.set(3, R.drawable.ic_baseline_access_time_24);

                            txtHeader.set(4, "File Acknowledged By RI");
                            models.set(4, "\n" + "Yes"); // Yes is the Default value
                            Images.set(4, R.drawable.ic_baseline_check_circle_24);

                            txtHeader.set(5, "Hissa Assignment By Survey Supervisor");// Yes is the Default value
                            models.set(5, "\n"+ "New Hissa Assigned as per 11 E : "+ "Yes" +"\n"+ "Assigned On: "+SurveyorApprDate );
                            Images.set(5, R.drawable.ic_baseline_access_time_24);

                            txtHeader.set(6, "Final Approval");
                            models.set(6, "\n" + "Pending");
                            Images.set(6, R.drawable.ic_baseline_access_time_24);
                        }
                        else {

                            txtHeader.clear();
                            models.clear();
                            Images.clear();
                            isShow.clear();

                            models.add(0, "" + showText_notEnt);
                            models.add(1, "" + showText_notEnt);
                            models.add(2, "" + showText_notEnt);

                            txtHeader.add(0, "");
                            txtHeader.add(1, "");
                            txtHeader.add(2, "");

                            Images.add(0, R.drawable.ic_baseline_adjust_24);
                            Images.add(1, R.drawable.ic_baseline_adjust_24);
                            Images.add(2, R.drawable.ic_baseline_adjust_24);

                            isShow.add(0, false);
                            isShow.add(1, false);
                            isShow.add(2, false);

                            txtHeader.set(0, "Data Entry");
                            models.set(0, "Transaction Entered By : "+ TranEnteredBy +"\n" +" Transaction Entered on : "+TranEnteredDate);
                            Images.set(0, R.drawable.ic_baseline_check_circle_24);

                            txtHeader.set(1, "Checklist Approval");
                            models.set(1, "\n" + "Checklist Approved By : "+ CheckListApprBy+"\n" +" Approved On : "+CheckListApprDate);
                            Images.set(1, R.drawable.ic_baseline_check_circle_24);

                            txtHeader.set(2, "Final Approval");
                            models.set(2, "\n" + "Pending");
                            Images.set(2, R.drawable.ic_baseline_access_time_24);
                        }
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
