package qa.happytots.yameenhome.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.model.country.Country;

public class Service_Adapter extends RecyclerView.Adapter<Service_Adapter.Holder> {
    private List<Country> mCountryList;

    public Service_Adapter(List<Country> fishlist) {
        this.mCountryList = fishlist;
    }

    @Override
    public Service_Adapter.Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fish_list, viewGroup, false);
        return new Holder(view);
    }


    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        Country country = mCountryList.get(position);
        holder.tv1.setText(country.getName());
        holder.tv2.setText(String.valueOf(country.getCountryId()));
    }


    @Override
    public int getItemCount() {
        return mCountryList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView tv1, tv2;

        Holder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
        }
    }
}
