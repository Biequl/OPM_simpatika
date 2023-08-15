package id.beetechmedia.opmsimpatika.activity.ra;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import id.beetechmedia.opmsimpatika.R;
import id.beetechmedia.opmsimpatika.api.APIRequestData;
import id.beetechmedia.opmsimpatika.api.Retroserver;
import id.beetechmedia.opmsimpatika.model.ModelRespon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sabiqul on 23/07/2023.
 * BeeMedia
 * sabiqul.ulum@gmail.com
 */
public class TambahOpmra extends AppCompatActivity {

    private Button btnSimpan;

    private EditText etDesa;
    private TextInputEditText etNsm, etMadrasah, etJenjang, etKec, etNamaopm, etWaopm;
    private String NSM, MADRASAH, JENJANG, KEC, DESA, NAMAOPM, WAOPM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahopm);

        etNsm = findViewById(R.id.et_nsm);
        etMadrasah = findViewById(R.id.et_madrasah);
        etJenjang = findViewById(R.id.et_jenjang);
        etKec = findViewById(R.id.et_kec);
        etDesa = findViewById(R.id.et_desa);
        etNamaopm = findViewById(R.id.et_namaopm);
        etWaopm = findViewById(R.id.et_waopm);

        btnSimpan = findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NSM = etNsm.getText().toString();
                MADRASAH = etMadrasah.getText().toString();
                JENJANG = etJenjang.getText().toString();
                KEC = etKec.getText().toString();
                DESA = etDesa.getText().toString();
                NAMAOPM = etNamaopm.getText().toString();
                WAOPM = etWaopm.getText().toString();

                if (NSM.trim().isEmpty()){
                    etNsm.setError("NSM Harus Diisi");
                } else if (MADRASAH.trim().isEmpty()){
                    etMadrasah.setError("Nama Madrasah Harus Diisi");
                } else if (JENJANG.trim().isEmpty()){
                    etJenjang.setError("Jenjang Madrasah Harus Diisi");
                } else if (KEC.trim().isEmpty()) {
                    etKec.setError("Nama Kec Harus diisi");
                } else if (DESA.trim().isEmpty()){
                    etDesa.setError("Nama Desa Harus Diisi");
                } else if (NAMAOPM.trim().isEmpty()) {
                    etNamaopm.setError("Nama OPM Harus diisi");
                } else if (WAOPM.trim().isEmpty()){
                    etWaopm.setError("No OPM Harus Diisi");
                } else {
                    prosesSimpan();
                }
            }
        });
    }

    private void prosesSimpan(){
        APIRequestData API = Retroserver.konekRetrofit().create(APIRequestData.class);
        Call<ModelRespon> proses = API.ardCreateRA(NSM, MADRASAH,JENJANG, KEC, DESA, NAMAOPM, WAOPM);

        proses.enqueue(new Callback<ModelRespon>() {
            @Override
            public void onResponse(Call<ModelRespon> call, Response<ModelRespon> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahOpmra.this, "Kode : "+ kode + " Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void onFailure(Call<ModelRespon> call, Throwable t) {
                Toast.makeText(TambahOpmra.this, "Gagal Hubungi server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}