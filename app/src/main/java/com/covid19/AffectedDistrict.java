package com.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.covid19.Adapters.CustomAdapterDistrict;
import com.covid19.Adapters.CustomAdapterIndia;
import com.covid19.models.DistrictModel;
import com.covid19.models.IndiaModel;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AffectedDistrict extends AppCompatActivity {

    private int positionState;
    EditText edtSearch;
    ListView listView;
    SimpleArcLoader simpleArcLoader;

    public static List<DistrictModel> countryModelList = new ArrayList<>();
    DistrictModel countryModel;
    CustomAdapterDistrict customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_district);

        edtSearch = findViewById(R.id.edtSearch);
        listView = findViewById(R.id.listview);
        simpleArcLoader = findViewById(R.id.loader);

        Intent intent = getIntent();
        positionState = intent.getIntExtra("position", 0);

        getSupportActionBar().setTitle("Affected "+AffectedIndia.countryModelList.get(positionState).getState()+" Districts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fetchData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(), DistrictDetailsActivity.class).putExtra("position", position));
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                customAdapter.getFilter().filter(s);
                customAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void fetchData() {
        String url = "https://api.covid19india.org/v2/state_district_wise.json";
        simpleArcLoader.start();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i=0; i<jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        JSONArray array = jsonObject.getJSONArray("districtData");

                        String state = jsonObject.getString("state");
                        Log.d("data is", AffectedIndia.countryModelList.get(positionState).getState());

                        if(state.equals(AffectedIndia.countryModelList.get(positionState).getState())) {

                            for(int j=0; j<array.length(); j++) {

                                JSONObject object = array.getJSONObject(j);

                                String district = object.getString("district");
                                String cases = object.getString("confirmed");
                                String active = object.getString("active");
                                String death = object.getString("deceased");
                                String recovered = object.getString("recovered");

                                countryModel = new DistrictModel(district, cases, active, death, recovered);
                                countryModelList.add(countryModel);
                            }
                        }
                    }

                    customAdapter = new CustomAdapterDistrict(AffectedDistrict.this, countryModelList);
                    listView.setAdapter(customAdapter);
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                Toast.makeText(AffectedDistrict.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countryModelList.clear();
    }
}
