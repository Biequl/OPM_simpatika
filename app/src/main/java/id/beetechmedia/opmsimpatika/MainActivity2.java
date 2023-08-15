package id.beetechmedia.opmsimpatika;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import id.beetechmedia.opmsimpatika.activity.EditprofilActivity;
import id.beetechmedia.opmsimpatika.activity.LoginActivity;
import id.beetechmedia.opmsimpatika.activity.SessionManager;
import id.beetechmedia.opmsimpatika.activity.ma.OpmmaActivity;
import id.beetechmedia.opmsimpatika.activity.mi.OpmmiActivity;
import id.beetechmedia.opmsimpatika.activity.mts.MTsBaruActivity;
import id.beetechmedia.opmsimpatika.activity.ra.OpmraActivity;

/**
 * Created by Sabiqul on 27/07/2023.
 * BeeMedia
 * sabiqul.ulum@gmail.com
 */
public class MainActivity2 extends AppCompatActivity {

    private FloatingActionButton fab_main, fab1_mail, fab2_share;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    TextView textview_mail, textview_share, etUsername, etName;
    SessionManager sessionManager;
    String username, name;
    ImageView profil;
    Boolean isOpen = false;

    CardView OpmRa, OpmMi, OpmMts, OpmMa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInitLayout();
        sessionManager = new SessionManager(MainActivity2.this);
        if(!sessionManager.isLoggedIn()){
            moveToLogin();
        }

        etUsername = findViewById(R.id.etMainUsername);
        etName = findViewById(R.id.etMainName);
        profil = findViewById(R.id.imageProfile);

        username = sessionManager.getUserDetail().get(SessionManager.USERNAME);
        name = sessionManager.getUserDetail().get(SessionManager.NAME);

        etUsername.setText(username);
        etName.setText(name);

        fab_main = findViewById(R.id.fab);
        fab1_mail = findViewById(R.id.fab1);
        fab2_share = findViewById(R.id.fab2);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_fab_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_fab_anticlock);

        textview_mail = (TextView) findViewById(R.id.textview_mail);
        textview_share = (TextView) findViewById(R.id.textview_share);

        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpen) {

                    textview_mail.setVisibility(View.INVISIBLE);
                    textview_share.setVisibility(View.INVISIBLE);
                    fab2_share.startAnimation(fab_close);
                    fab1_mail.startAnimation(fab_close);
                    fab_main.startAnimation(fab_anticlock);
                    fab2_share.setClickable(false);
                    fab1_mail.setClickable(false);
                    isOpen = false;
                } else {
                    textview_mail.setVisibility(View.VISIBLE);
                    textview_share.setVisibility(View.VISIBLE);
                    fab2_share.startAnimation(fab_open);
                    fab1_mail.startAnimation(fab_open);
                    fab_main.startAnimation(fab_clock);
                    fab2_share.setClickable(true);
                    fab1_mail.setClickable(true);
                    isOpen = true;
                }

            }
        });

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), EditprofilActivity.class);
                startActivity(intent);
            }
        });


        fab2_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Share", Toast.LENGTH_SHORT).show();

            }
        });

        fab1_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "LogOut", Toast.LENGTH_SHORT).show();
                sessionManager.logoutSession();
                moveToLogin();
            }
        });
    }

    private void setInitLayout(){
        CardView OpmRa = findViewById(R.id.cv_RaOpm);
        OpmRa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(), OpmraActivity.class);
                startActivity(intent);
            }
        });
        CardView OpmMi = findViewById(R.id.cv_MiOpm);
        OpmMi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(), OpmmiActivity.class);
                startActivity(intent);
            }
        });
        CardView OpmMts = findViewById(R.id.cv_MtsOpm);
        OpmMts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(), MTsBaruActivity.class);
                startActivity(intent);
            }
        });
        CardView OpmMa = findViewById(R.id.cv_MaOpm);
        OpmMa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(), OpmmaActivity.class);
                startActivity(intent);
            }
        });
    }
    private void moveToLogin() {
        Intent intent = new Intent(MainActivity2.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Peringatan !!!");
        builder.setMessage("Apakah  Ingin Keluar ?");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }

}