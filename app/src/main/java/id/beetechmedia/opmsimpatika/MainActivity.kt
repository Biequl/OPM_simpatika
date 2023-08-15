package id.beetechmedia.opmsimpatika

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import id.beetechmedia.opmsimpatika.activity.LoginActivity
import id.beetechmedia.opmsimpatika.activity.SessionManager
import id.beetechmedia.opmsimpatika.activity.ma.OpmmaActivity
import id.beetechmedia.opmsimpatika.activity.mi.OpmmiActivity
import id.beetechmedia.opmsimpatika.activity.mts.MTsBaruActivity
import id.beetechmedia.opmsimpatika.activity.ra.OpmraActivity

class MainActivity : AppCompatActivity() {
    private var mInterstitialAd: InterstitialAd? = null
    lateinit var mAdView : AdView

    private var etUsername: TextView? = null
    private var etName: TextView? = null
    private lateinit var sessionManager: SessionManager
    lateinit var username: String
    lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setStatusBar()
        setInitLayout()

        sessionManager = SessionManager(this@MainActivity)
        if (!sessionManager!!.isLoggedIn) {
            moveToLogin()
        }
        etUsername = findViewById(R.id.etMainUsername)
        etName = findViewById(R.id.etMainName)
        username = sessionManager!!.userDetail[SessionManager.USERNAME]!!
        name = sessionManager!!.userDetail[SessionManager.NAME]!!
        //etUsername.setText(username)
        //etName.setText(name)

        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        InterstitialAd.load(this,getString((R.string.ADSinterstitial)), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                adError?.toString()?.let { Log.d(ContentValues.TAG, it) }
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(ContentValues.TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })
        var requestConfiguration = MobileAds.getRequestConfiguration()
            .toBuilder()
            .setTagForChildDirectedTreatment(RequestConfiguration.TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE)
            .setMaxAdContentRating(RequestConfiguration.MAX_AD_CONTENT_RATING_G)
            .build()
        MobileAds.setRequestConfiguration(requestConfiguration)

    }
    private fun moveToLogin() {
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(intent)
        finish()
    }
    private fun setInitLayout() {

        val OpmRa = findViewById<CardView>(R.id.cv_RaOpm)
        OpmRa.setOnClickListener { v: View? ->
            val intent = Intent(this@MainActivity, OpmraActivity::class.java)
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this)
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.")
            }
            startActivity(intent)
        }
        val OpmMi = findViewById<CardView>(R.id.cv_MiOpm)
        OpmMi.setOnClickListener { v: View? ->
            val intent = Intent(this@MainActivity, OpmmiActivity::class.java)
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this)
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.")
            }
            startActivity(intent)
        }
        val OpmMts = findViewById<CardView>(R.id.cv_MtsOpm)
        OpmMts.setOnClickListener { v: View? ->
            val intent = Intent(this@MainActivity, MTsBaruActivity::class.java)
            startActivity(intent)
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this)
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.")
            }
        }
        val OpmMa = findViewById<CardView>(R.id.cv_MaOpm)
        OpmMa.setOnClickListener { v: View? ->
            val intent = Intent(this@MainActivity, OpmmaActivity::class.java)
            startActivity(intent)
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this)
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.")
            }
        }
    }
    private fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            @Suppress("DEPRECATION")
            setWindowFlag(this, FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    companion object {
        fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
            val window = activity.window
            val layoutParams = window.attributes
            if (on) {
                layoutParams.flags = layoutParams.flags or bits
            } else {
                layoutParams.flags = layoutParams.flags and bits.inv()
            }
            window.attributes = layoutParams
        }
    }
    override fun onBackPressed() {
        exit()//Pergi ke method exit
    }

    private fun exit() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Peringatan !!!")
        builder.setMessage("Apakah  Ingin Keluar ?")
        builder.setIcon(R.mipmap.ic_launcher)
        builder.setPositiveButton("Ya") { dialogInterface, i ->
            System.exit(0)
            moveTaskToBack(true)
        }

        builder.setNegativeButton("Tidak") { dialogInterface, i -> }

        builder.show()
    }
}