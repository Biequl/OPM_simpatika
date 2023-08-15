package id.beetechmedia.opmsimpatika.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URLEncoder;
import java.util.List;

import id.beetechmedia.opmsimpatika.R;
import id.beetechmedia.opmsimpatika.activity.mts.EditOpmmts;
import id.beetechmedia.opmsimpatika.activity.mts.MTsBaruActivity;
import id.beetechmedia.opmsimpatika.api.APIRequestData;
import id.beetechmedia.opmsimpatika.api.Retroserver;
import id.beetechmedia.opmsimpatika.model.ModelOpm;
import id.beetechmedia.opmsimpatika.model.ModelRespon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sabiqul on 20/07/2023.
 * BeeMedia
 * sabiqul.ulum@gmail.com
 */
public class AdapterOpmMTs extends RecyclerView.Adapter<AdapterOpmMTs.VHOpm>{

    private Context ctx;
    private List<ModelOpm> listOpm;

    ImageView psanWA, telp, edit;

    public AdapterOpmMTs(Context ctx, List<ModelOpm> listOpm) {
        this.ctx = ctx;
        this.listOpm = listOpm;
    }

    @NonNull
    @Override
    public VHOpm onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(ctx).inflate(R.layout.list_dataopm, parent, false);
        return new VHOpm(varView);

    }

    @Override
    public void onBindViewHolder(@NonNull VHOpm holder, int position) {
        ModelOpm MO = listOpm.get(position);

        holder.tvNO.setText(MO.getNO());
        holder.tvNSM.setText(MO.getNSM());
        holder.tvJENJANG.setText(MO.getJENJANG());
        holder.tvMADRASAH.setText(MO.getMADRASAH());
        holder.tvKEC.setText(MO.getKEC());
        holder.tvDESA.setText(MO.getDESA());
        holder.tvNAMAOPM.setText(MO.getNAMAOPM());
        holder.tvWAOPM.setText(MO.getWAOPM());

    }

    @Override
    public int getItemCount() {
        return listOpm.size();
    }

    public class VHOpm extends RecyclerView.ViewHolder{
        TextView tvNO, tvNSM, tvJENJANG, tvMADRASAH, tvKEC,tvDESA, tvNAMAOPM, tvWAOPM;

        public VHOpm(@NonNull View itemView) {
            super(itemView);

            tvNO = itemView.findViewById(R.id.tv_no);
            tvNSM = itemView.findViewById(R.id.tv_nsm);
            tvJENJANG = itemView.findViewById(R.id.tv_jenjang);
            tvMADRASAH = itemView.findViewById(R.id.tv_namamadrasah);
            tvKEC = itemView.findViewById(R.id.tv_kecamatan);
            tvDESA = itemView.findViewById(R.id.tv_desa);
            tvNAMAOPM = itemView.findViewById(R.id.tv_opmnama);
            tvWAOPM = itemView.findViewById(R.id.tv_waopm);

            psanWA = (ImageView) itemView.findViewById(R.id.iv_pesan);
            psanWA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startSupportChat ();
                }
            });

            telp = (ImageView)itemView.findViewById(R.id.iv_telpon);
            telp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startTelp();
                }
            });

            edit = (ImageView) itemView.findViewById(R.id.iv_edit);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent kirim = new Intent(ctx, EditOpmmts.class);
                    kirim.putExtra("xNO", tvNO.getText().toString());
                    kirim.putExtra("xNSM", tvNSM.getText().toString());
                    kirim.putExtra("xMADRASAH", tvMADRASAH.getText().toString());
                    kirim.putExtra("xKEC", tvKEC.getText().toString());
                    kirim.putExtra("xDESA", tvDESA.getText().toString());
                    kirim.putExtra("xNAMAOPM", tvNAMAOPM.getText().toString());
                    kirim.putExtra("xWAOPM", tvWAOPM.getText().toString());
                    ctx.startActivity(kirim);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder pesan = new AlertDialog.Builder(ctx);
                    pesan.setTitle("Perhatian");
                    pesan.setMessage("Anda Akan Menghapus data "+tvMADRASAH.getText().toString()+" ?");

                    pesan.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            prosesHapus(tvNO.getText().toString());
                        }
                    });

                    pesan.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    pesan.show();
                    return false;
                }
            });
        }
        private void startSupportChat() {
            try {
                String headerReceiver = "*Kami Tim teknis SIMPATIKA*";// Replace with your message.
                String bodyMessageFormal = "\nMemberi informasi bahwa "+tvMADRASAH.getText().toString()+" Ada kendala tentang :\n....";// Replace with your message.
                String message = headerReceiver + bodyMessageFormal;
                Intent intent = new Intent ( Intent.ACTION_VIEW );
                intent.setData ( Uri.parse ( "https://api.whatsapp.com/send?phone=" + tvWAOPM.getText().toString() + "&text=" + URLEncoder.encode(message, "UTF-8")) );
                ctx.startActivity ( intent );
            } catch (Exception e) {
                e.printStackTrace ();
            }

        }

        private void startTelp() {
            try {
                Intent callIntent = new Intent( Intent.ACTION_DIAL );
                callIntent.setData(Uri.parse("tel:" + tvWAOPM.getText().toString()));
                ctx.startActivity ( callIntent );
            } catch (Exception e) {
                e.printStackTrace ();
            }

        }
        void prosesHapus(String NO){
            APIRequestData API = Retroserver.konekRetrofit().create(APIRequestData.class);
            Call<ModelRespon> proses = API.ardDeleteMTs(NO);
            proses.enqueue(new Callback<ModelRespon>() {
                @Override
                public void onResponse(Call<ModelRespon> call, Response<ModelRespon> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode : "+ kode + " Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                    ((MTsBaruActivity)ctx).getNamaOpm();

                }

                @Override
                public void onFailure(Call<ModelRespon> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Hubungi server" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}