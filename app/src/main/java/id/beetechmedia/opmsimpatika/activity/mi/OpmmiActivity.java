package id.beetechmedia.opmsimpatika.activity.mi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import id.beetechmedia.opmsimpatika.R;
import id.beetechmedia.opmsimpatika.adapter.AdapterOpmMI;
import id.beetechmedia.opmsimpatika.api.APIRequestData;
import id.beetechmedia.opmsimpatika.api.Retroserver;
import id.beetechmedia.opmsimpatika.model.ModelOpm;
import id.beetechmedia.opmsimpatika.model.ModelRespon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sabiqul on 23/07/2023.
 * BeeMedia
 * sabiqul.ulum@gmail.com
 */
public class OpmmiActivity extends AppCompatActivity {

    RecyclerView rvOPM;
    android.widget.SearchView cariData;
    private FloatingActionButton fbTambah;
    private ProgressBar pbOPM;
    private RecyclerView.Adapter adOPM;
    private RecyclerView.LayoutManager lmOPM;
    private List<ModelOpm> listOPM = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataopm);

        pbOPM = findViewById(R.id.pb_proses);
        fbTambah = findViewById(R.id.fab_tambah);

        //set transparent statusbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        fbTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OpmmiActivity.this, TambahOpmmi.class));
            }
        });

        rvOPM = findViewById(R.id.rv_data);
        cariData = findViewById(R.id.searchData);

        //transparent background searchview
        int searchPlateId = cariData.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = cariData.findViewById(searchPlateId);
        if (searchPlate != null) {
            searchPlate.setBackgroundColor(Color.TRANSPARENT);
        }

        cariData.setImeOptions(EditorInfo.IME_ACTION_DONE);
        cariData.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                rvOPM.setVisibility(View.GONE);
                pbOPM.setVisibility(View.VISIBLE);
                APIRequestData API = Retroserver.konekRetrofit().create(APIRequestData.class);
                Call<ModelRespon> proses = API.cariOPMMI(newText);

                proses.enqueue(new Callback<ModelRespon>() {
                    @Override
                    public void onResponse(Call<ModelRespon> call, Response<ModelRespon> response) {
                        String kode = response.body().getKode();
                        String pesan = response.body().getPesan();
                        pbOPM.setVisibility(View.GONE);
                        rvOPM.setVisibility(View.VISIBLE);
                        if (kode.equals("1")) {
                            listOPM = response.body().getDataopm();
                            adOPM = new AdapterOpmMI(OpmmiActivity.this, listOPM);
                            rvOPM.setAdapter(adOPM);
                        }

                    }

                    @Override
                    public void onFailure(Call<ModelRespon> call, Throwable t) {
                        Toast.makeText(OpmmiActivity.this, "Gagal Hubungi server", Toast.LENGTH_SHORT).show();
                        pbOPM.setVisibility(View.GONE);
                    }
                });
                return true;
            }

        });

        lmOPM = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvOPM.setLayoutManager(lmOPM);

        retriveOPM();
    }

    public void retriveOPM(){
        pbOPM.setVisibility(View.VISIBLE);
        APIRequestData API = Retroserver.konekRetrofit().create(APIRequestData.class);
        Call<ModelRespon> proses = API.ardRetriveOPMMI();
        proses.enqueue(new Callback<ModelRespon>() {
            @Override
            public void onResponse(Call<ModelRespon> call, Response<ModelRespon> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listOPM = response.body().getDataopm();

                adOPM = new AdapterOpmMI(OpmmiActivity.this, listOPM);
                rvOPM.setAdapter(adOPM);
                adOPM.notifyDataSetChanged();
                pbOPM.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ModelRespon> call, Throwable t) {
                Toast.makeText(OpmmiActivity.this, "Eror : Gagal Terhubung dengan server", Toast.LENGTH_SHORT).show();
                pbOPM.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retriveOPM();
    }
    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (on) {
            layoutParams.flags |= bits;
        } else {
            layoutParams.flags &= ~bits;
        }
        window.setAttributes(layoutParams);
    }
}