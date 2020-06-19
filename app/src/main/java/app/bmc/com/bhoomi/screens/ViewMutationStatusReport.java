package app.bmc.com.bhoomi.screens;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import app.bmc.com.bhoomi.R;
import app.bmc.com.bhoomi.api.RtcViewInformationApi;
import app.bmc.com.bhoomi.database.DataBaseHelper;
import app.bmc.com.bhoomi.interfaces.DistrictModelInterface;
import app.bmc.com.bhoomi.interfaces.HobliModelInterface;
import app.bmc.com.bhoomi.interfaces.TalukModelInterface;
import app.bmc.com.bhoomi.interfaces.VillageModelInterface;
import app.bmc.com.bhoomi.model.Get_Surnoc_HissaResult;
import app.bmc.com.bhoomi.model.Hissa_Response;
import app.bmc.com.bhoomi.retrofit.RtcViewInfoClient;
import app.bmc.com.bhoomi.util.Constants;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ViewMutationStatusReport extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mutation_status_report);
    }



}
