package qa.happytots.yameenhome.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.components.YameenTextView;
import qa.happytots.yameenhome.model.country.Country;

public class CountryAdapter extends BaseAdapter {

    private List<Country> countries;

    public CountryAdapter(List<Country> countries) {
        this.countries = countries;
    }

    @Override
    public int getCount() {
        return countries.size();
    }

    @Override
    public Object getItem(int position) {
        return countries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_country, parent, false);
        YameenTextView tvCountryName = view.findViewById(R.id.tv_country_name);
        Country country = countries.get(position);
        tvCountryName.setText(country.getName());
        return view;
    }
}
