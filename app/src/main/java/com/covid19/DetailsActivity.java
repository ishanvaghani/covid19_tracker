package com.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.covid19.AffectedCountries;
import com.covid19.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class DetailsActivity extends AppCompatActivity {

    private int positionCountry;
    TextView tvCountry, tvCases, tvTodayCases, tvTodayDeaths, tvDeaths, tvRecovered, tvActive, tvCritical;
    ImageView imageView;
    Button share;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        positionCountry = intent.getIntExtra("position", 0);

        getSupportActionBar().setTitle("Details of "+AffectedCountries.countryModelList.get(positionCountry).getCountry());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7794711814086493/5786065290");

        tvCountry = findViewById(R.id.tvCountry);
        tvCases = findViewById(R.id.tvCases);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths);
        tvDeaths = findViewById(R.id.tvDeaths);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvActive = findViewById(R.id.tvActive);
        tvCritical = findViewById(R.id.tvCritical);
        imageView = findViewById(R.id.imageFlag);
        share = findViewById(R.id.btnShare);

        tvCountry.setText(AffectedCountries.countryModelList.get(positionCountry).getCountry());
        tvCases.setText(AffectedCountries.countryModelList.get(positionCountry).getCases());
        tvTodayCases.setText(AffectedCountries.countryModelList.get(positionCountry).getTodayCases());
        tvTodayDeaths.setText(AffectedCountries.countryModelList.get(positionCountry).getTodayDeaths());
        tvDeaths.setText(AffectedCountries.countryModelList.get(positionCountry).getDeaths());
        tvRecovered.setText(AffectedCountries.countryModelList.get(positionCountry).getRecovered());
        tvActive.setText(AffectedCountries.countryModelList.get(positionCountry).getActive());
        tvCritical.setText(AffectedCountries.countryModelList.get(positionCountry).getCritical());
        Glide.with(this).load(AffectedCountries.countryModelList.get(positionCountry).getFlag()).into(imageView);

        final String msg_txt = "Covid 19 Cases" +"\n"+
                "\n Country: "+ AffectedCountries.countryModelList.get(positionCountry).getCountry()+
                "\n Cases: "+ AffectedCountries.countryModelList.get(positionCountry).getCases()+
                "\n Today Cases: "+ AffectedCountries.countryModelList.get(positionCountry).getTodayCases()+
                "\n Today Deaths: "+ AffectedCountries.countryModelList.get(positionCountry).getTodayDeaths()+
                "\n Deaths: "+ AffectedCountries.countryModelList.get(positionCountry).getDeaths()+
                "\n Recovered: "+ AffectedCountries.countryModelList.get(positionCountry).getRecovered()+
                "\n Active: "+ AffectedCountries.countryModelList.get(positionCountry).getActive()+
                "\n Critical: "+ AffectedCountries.countryModelList.get(positionCountry).getCritical();

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
