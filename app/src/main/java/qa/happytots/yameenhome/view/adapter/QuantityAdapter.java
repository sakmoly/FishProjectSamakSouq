package qa.happytots.yameenhome.view.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.FishListOptionvalue;

public class QuantityAdapter extends RecyclerView.Adapter<QuantityAdapter.Holder> {

    private List<FishListOptionvalue> list_rc2;
    private OnProductDetailInteractionListener mListener;

    public QuantityAdapter(List<FishListOptionvalue> list_rc2, OnProductDetailInteractionListener listener) {
        this.list_rc2 = list_rc2;
        FishListOptionvalue value = new FishListOptionvalue();
        value.setName("MORE");
        value.setProductOptionValueId(-1);
        list_rc2.add(3, value);
        if (list_rc2.size() > 8) {
            FishListOptionvalue valueLess = new FishListOptionvalue();
            valueLess.setName("LESS");
            valueLess.setProductOptionValueId(-2);
            list_rc2.add(valueLess);
        }
        mListener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_quantity, viewGroup, false);
        return new Holder(view);
    }


    @Override
    public void onBindViewHolder(final Holder holder, int position) {

        if (list_rc2.get(position).isFishquantityType_status()) {
            holder.bar_view.setBackground(ContextCompat.getDrawable(holder.bar_view.getContext(), R.drawable.bg_rounded_red));
        } else {
            holder.bar_view.setBackground(ContextCompat.getDrawable(holder.bar_view.getContext(), R.drawable.bg_rounded_gray));
        }

        holder.tv1.setText(list_rc2.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return list_rc2 != null? list_rc2.size() : 0;
    }


    class Holder extends RecyclerView.ViewHolder {

        private TextView tv1,bar_view;

        Holder(View itemView) {
            super(itemView);

            tv1 =  itemView.findViewById(R.id.tv1);
            bar_view =  itemView.findViewById(R.id.bar_view);


        }
    }
}
