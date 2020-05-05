package qa.happytots.yameenhome.view.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.FishListOptionvalue;

public class FinishAdapter extends RecyclerView.Adapter<FinishAdapter.Holder> {

    private List<FishListOptionvalue> list_rc1;
    private OnProductDetailInteractionListener mListener;

    public FinishAdapter(List<FishListOptionvalue> list_rc1, OnProductDetailInteractionListener listener) {
        this.list_rc1 = list_rc1;
        mListener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_rc1, viewGroup, false);
        return new Holder(view);
    }


    @Override
    public void onBindViewHolder(final Holder holder, int position) {

        if (list_rc1.get(position).isFishSellType_status()) {
            holder.tv2.setBackground(ContextCompat.getDrawable(holder.tv2.getContext(), R.drawable.bg_rounded_red));
        } else {
            holder.tv2.setBackground(ContextCompat.getDrawable(holder.tv2.getContext(), R.drawable.bg_rounded_gray));
        }

        holder.tv1.setText(list_rc1.get(position).getName());
        switch (list_rc1.get(position).getName()) {
            case "Cleaned":
                holder.iv1.setImageResource(R.drawable.ic_fish_1_new);
                break;

            case "Slices":
                holder.iv1.setImageResource(R.drawable.ic_fish_2_new);
                break;
            default:
                holder.iv1.setImageResource(R.drawable.ic_fillets_new);
                break;
        }

    }


    @Override
    public int getItemCount() {
        return list_rc1 != null ? list_rc1.size() : 0;
    }


    class Holder extends RecyclerView.ViewHolder {

        private TextView tv1, tv2;
        private ImageView iv1;


        Holder(View itemView) {
            super(itemView);

            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            iv1 = itemView.findViewById(R.id.iv1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mListener.onTypeSelection(list_rc1.get(getLayoutPosition()));
                    int position = getLayoutPosition();
                    for (int i = 0; i < list_rc1.size(); i++) {

                        if (position == i) {
                            list_rc1.get(i).setFishSellType_status(!list_rc1.get(i).isFishSellType_status());

                        } else {
                            list_rc1.get(i).setFishSellType_status(false);
                        }
                    }

                    notifyDataSetChanged();
                }

            });
        }
    }


}
