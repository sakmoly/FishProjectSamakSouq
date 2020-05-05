package qa.happytots.yameenhome.view.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.components.YameenTextView;
import qa.happytots.yameenhome.model.location.Zone;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private String mSelectedZone;
    private List<Zone> mZones;
    private OnLocationInteractorListener mListener;

    public LocationAdapter(String selectedZone, List<Zone> mZones, OnLocationInteractorListener mListener) {
        this.mZones = mZones;
        this.mListener = mListener;
        mSelectedZone = selectedZone;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        Zone zone = mZones.get(position);
        holder.tvLocation.setText(zone.getName());

        if (zone.getZoneId().equals(mSelectedZone)) {
            holder.ivTick.setVisibility(View.VISIBLE);
        } else {
            holder.ivTick.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mZones.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {

        private YameenTextView tvLocation;
        private AppCompatImageView ivTick;
        LocationViewHolder(View itemView) {
            super(itemView);
            tvLocation = itemView.findViewById(R.id.tv_location);
            ivTick = itemView.findViewById(R.id.iv_tick);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onZoneClick(getLayoutPosition(), mZones.get(getLayoutPosition()));
                }
            });


        }
    }

    public interface OnLocationInteractorListener {
        void onZoneClick(int position, Zone zone);
    }
}
