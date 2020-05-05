package qa.happytots.yameenhome.view.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.model.Fishlist;

public class Fishlist_Adapter extends RecyclerView.Adapter<Fishlist_Adapter.Holder> {
    private List<Fishlist> fishlist;
    private Context context;

    public Fishlist_Adapter(List<Fishlist> fishlist, Context context) {
        this.fishlist = fishlist;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fish_list,viewGroup,false);
        Holder holder =new Holder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final Holder holder, final int position) {

        holder.tv1.setText(fishlist.get(position).getTv1());
        holder.tv2.setText(fishlist.get(position).getTv2());
        holder.tv1.setTypeface( Typeface.createFromAsset(context.getAssets(),"fonts/opensans_extrabold.ttf"));
        holder.tv2.setTypeface( Typeface.createFromAsset(context.getAssets(),"fonts/opensans_extrabold.ttf"));
        }



    @Override
    public int getItemCount() {
        return fishlist.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView tv1,tv2;
        public Holder(View itemView) {
            super(itemView);

            tv1 =(TextView)itemView.findViewById(R.id.tv1);
            tv2 =(TextView)itemView.findViewById(R.id.tv2);

        }
    }
}
