package qa.happytots.yameenhome.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.components.YameenTextView;
import qa.happytots.yameenhome.model.country.zone.Zone;

public class ZoneAdapter extends BaseAdapter {

    private List<Zone> zones;

    public ZoneAdapter(List<Zone> zones) {
        this.zones = zones;
    }

    @Override
    public int getCount() {
        return zones.size();
    }

    @Override
    public Object getItem(int position) {
        return zones.get(position);
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
        Zone zone = zones.get(position);
        tvCountryName.setText(zone.getName());
        return view;
    }
}
