package com.covid19.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.covid19.AffectedDistrict;
import com.covid19.AffectedIndia;
import com.covid19.R;
import com.covid19.models.DistrictModel;
import com.covid19.models.IndiaModel;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterDistrict extends ArrayAdapter<DistrictModel> {

    private Context context;
    private List<DistrictModel> countryModelList;
    private List<DistrictModel> countryModelListFiltered;

    public CustomAdapterDistrict(@NonNull Context context, List<DistrictModel> countryModelList) {
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

        tvCountryname.setText(countryModelListFiltered.get(position).getDistrict());
        return view;
    }

    @Override
    public int getCount() {
        return countryModelListFiltered.size();
    }

    @Nullable
    @Override
    public DistrictModel getItem(int position) {
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
                    List<DistrictModel> resultModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(DistrictModel itemsModel: countryModelList) {
                        if(itemsModel.getDistrict().toLowerCase().contains(searchStr)) {
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
                countryModelListFiltered = (List<DistrictModel>) results.values;
                AffectedDistrict.countryModelList = (List<DistrictModel>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
