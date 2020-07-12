package com.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class DistrictDetailsActivity extends AppCompatActivity {

    private int positionCountry;
    TextView tvDistrict, tvCases, tvDeaths, tvRecovered, tvActive;
    Button share;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_details);

        Intent intent = getIntent();
        positionCountry = intent.getIntExtra("position", 0);

        getSupportActionBar().setTitle("Details of "+AffectedDistrict.countryModelList.get(positionCountry).getDistrict());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7794711814086493/1655248594");

        tvDistrict = findViewById(R.id.tvDistrict);
        tvCases = findViewById(R.id.tvCases);
        tvDeaths = findViewById(R.id.tvDeaths);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvActive = findViewById(R.id.tvActive);
        share = findViewById(R.id.btnShare);

        tvDistrict.setText(AffectedDistrict.countryModelList.get(positionCountry).getDistrict());
        tvCases.setText(AffectedDistrict.countryModelList.get(positionCountry).getCases());
        tvDeaths.setText(AffectedDistrict.countryModelList.get(positionCountry).getDeath());
        tvRecovered.setText(AffectedDistrict.countryModelList.get(positionCountry).getRecovered());
        tvActive.setText(AffectedDistrict.countryModelList.get(positionCountry).getActive());

        final String msg_txt = "Covid 19 Cases" +"\n"+
                "\n District: "+ AffectedDistrict.countryModelList.get(positionCountry).getDistrict()+
                "\n Cases: "+ AffectedDistrict.countryModelList.get(positionCountry).getCases()+
                "\n Deaths: "+ AffectedDistrict.countryModelList.get(positionCountry).getDeath()+
                "\n Recovered: "+ AffectedDistrict.countryModelList.get(positionCountry).getRecovered()+
                "\n Active: "+ AffectedDistrict.countryModelList.get(positionCountry).getActive();

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                mInterstitialAd.show();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, msg_txt);
                startActivity(Intent.createChooser(intent, "Share"));
            }
        });
    }
}
