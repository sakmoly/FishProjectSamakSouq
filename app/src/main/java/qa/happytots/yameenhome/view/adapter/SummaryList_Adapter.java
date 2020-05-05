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
import qa.happytots.yameenhome.model.SummaryList;

public class SummaryList_Adapter extends RecyclerView.Adapter<SummaryList_Adapter.Holder> {
    private List<SummaryList> summaryList;
    private Context context;

    public SummaryList_Adapter(List<SummaryList> summaryList, Context context) {
        this.summaryList = summaryList;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.summary_list,viewGroup,false);
        Holder holder =new Holder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.tv1.setText(summaryList.get(position).getTv1());
        holder.tv2.setText(summaryList.get(position).getTv2());
        holder.tv3.setText(summaryList.get(position).getTv3());
        holder.tv4.setText(summaryList.get(position).getTv4());
        holder.tv1.setTypeface( Typeface.createFromAsset(context.getAssets(),"fonts/opensans_regular.ttf"));
        holder.tv2.setTypeface( Typeface.createFromAsset(context.getAssets(),"fonts/opensans_regular.ttf"));
        holder.tv3.setTypeface( Typeface.createFromAsset(context.getAssets(),"fonts/opensans_regular.ttf"));
        holder.tv4.setTypeface( Typeface.createFromAsset(context.getAssets(),"fonts/opensans_regular.ttf"));

        }

    @Override
    public int getItemCount() {
        return summaryList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView tv1,tv2,tv3,tv4;


        public Holder(View itemView) {
            super(itemView);

            tv1 =(TextView)itemView.findViewById(R.id.tv1);
            tv2 =(TextView)itemView.findViewById(R.id.tv2);
            tv3 =(TextView)itemView.findViewById(R.id.tv3);
            tv4 =(TextView)itemView.findViewById(R.id.tv4);


        }
    }
}
