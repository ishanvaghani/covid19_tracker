package com.covid19.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.covid19.AffectedCountries;
import com.covid19.AffectedIndia;
import com.covid19.R;
import com.covid19.models.CountryModel;
import com.covid19.models.IndiaModel;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterIndia extends ArrayAdapter<IndiaModel> {

    private Context context;
    private List<IndiaModel> countryModelList;
    private List<IndiaModel> countryModelListFiltered;

    public CustomAdapterIndia(Context context, List<IndiaModel> countryModelList) {
        super(context, R.layout.india_list_item, countryModelList);

        this.context = context;
        this.countryModelList = countryModelList;
        this.countryModelListFiltered = countryModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.india_list_item, null, true);

        TextView tvCountryname = view.findViewById(R.id.states);

        tvCountryname.setText(countryModelListFiltered.get(position).getState());
        return view;
    }

    @Override
    public int getCount() {
        return countryModelListFiltered.size();
    }

    @Nullable
    @Override
    public IndiaModel getItem(int position) {
        return countryModelListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0) {
                    filterResults.count = countryModelList.size();
                    filterResults.values = countryModelList;
                }
                else {
                    List<IndiaModel> resultModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(IndiaModel itemsModel: countryModelList) {
                        if(itemsModel.getState().toLowerCase().contains(searchStr)) {
                            resultModel.add(itemsModel);
                        }
                        filterResults.count = resultModel.size();
                        filterResults.values = resultModel;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                countryModelListFiltered = (List<IndiaModel>) results.values;
                AffectedIndia.countryModelList = (List<IndiaModel>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
