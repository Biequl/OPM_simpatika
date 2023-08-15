package id.beetechmedia.opmsimpatika.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URLEncoder;
import java.util.List;

import id.beetechmedia.opmsimpatika.R;
import id.beetechmedia.opmsimpatika.activity.ra.EditOpmra;
import id.beetechmedia.opmsimpatika.model.ModelOpm;

/**
 * Created by Sabiqul on 23/07/2023.
 * BeeMedia
 * sabiqul.ulum@gmail.com
 */
public class AdapterOpmRA extends RecyclerView.Adapter<AdapterOpmRA.VHOpm>{

    private Context ctx;
    private List<ModelOpm> listOpm;

    ImageView psanWA, telp, edit;

    public AdapterOpmRA(Context ctx, List<ModelOpm> listOpm) {
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
                    Intent kirim = new Intent(ctx, EditOpmra.class);
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

    }
}