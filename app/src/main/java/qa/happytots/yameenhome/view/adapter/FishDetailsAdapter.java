package qa.happytots.yameenhome.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.constraint.Group;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.components.QuantityLayout;
import qa.happytots.yameenhome.components.YameenTextView;
import qa.happytots.yameenhome.model.fish.FishDetails;
import qa.happytots.yameenhome.model.quickview.product.Datum;

public class FishDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<Bridge> mFishItem;
    private OnProductDetailInteractionListener mListener;

    public FishDetailsAdapter(List<Bridge> mFishItem, OnProductDetailInteractionListener listener) {
        this.mFishItem = mFishItem;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_HEADER) {
            View view = inflater.inflate(R.layout.single_fish, parent, false);
            return new FishViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_fish_details, parent, false);
            return new FishDetailViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FishViewHolder) {
            FishViewHolder viewHolder = (FishViewHolder) holder;
            Datum listdata = (Datum) mFishItem.get(position);
            String price_template = "SAR %s";
            viewHolder.tvName.setText(listdata.getName());
            viewHolder.tvPrice.setText(String.format(price_template, listdata.getPrice()));
            Picasso.get().load(listdata.getOriginalImage())
                    .fit()
                    .error(R.drawable.default_placeholder)
                    .placeholder(R.drawable.default_placeholder)
                    .into(viewHolder.ivPhoto);
        } else {
            FishDetails detail = (FishDetails) mFishItem.get(position);
            FishDetailViewHolder viewHolder = (FishDetailViewHolder) holder;

            FinishAdapter finishAdapter = new FinishAdapter(detail.getTypeOptions(), mListener);
            viewHolder.rvSize.setAdapter(finishAdapter);

            viewHolder.qlOneKG.setType(QuantityLayout.TYPE_UNDERLINE);
            viewHolder.qlTwoKG.setType(QuantityLayout.TYPE_UNDERLINE);
            viewHolder.qlThreeKG.setType(QuantityLayout.TYPE_UNDERLINE);
            viewHolder.qlFourKG.setType(QuantityLayout.TYPE_UNDERLINE);
            viewHolder.qlFiveKG.setType(QuantityLayout.TYPE_UNDERLINE);

            if (detail.getQuantityOption() == 1) {
                viewHolder.qlOneKG.setType(QuantityLayout.TYPE_UNDERLINE_RED);
            } else if (detail.getQuantityOption() == 2) {
                viewHolder.qlTwoKG.setType(QuantityLayout.TYPE_UNDERLINE_RED);
            } else if (detail.getQuantityOption() == 3) {
                viewHolder.qlThreeKG.setType(QuantityLayout.TYPE_UNDERLINE_RED);
            } else if (detail.getQuantityOption() == 4) {
                viewHolder.qlFourKG.setType(QuantityLayout.TYPE_UNDERLINE_RED);
            } else if (detail.getQuantityOption() == 5) {
                viewHolder.qlFiveKG.setType(QuantityLayout.TYPE_UNDERLINE_RED);
            }

            viewHolder.tvName.setText(detail.getName());

            Picasso.get().load(detail.getLandscapeUrl())
                    .fit()
                    .error(R.drawable.default_placeholder)
                    .placeholder(R.drawable.default_placeholder)
                    .into(viewHolder.ivLandscape);
        }
    }

    @Override
    public int getItemCount() {
        return mFishItem.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mFishItem.get(position) instanceof FishDetails) {
            return TYPE_ITEM;
        }
        return TYPE_HEADER;
    }

    class FishViewHolder extends RecyclerView.ViewHolder {

        private AppCompatImageView ivPhoto;
        private AppCompatTextView tvName;
        private AppCompatTextView tvPrice;

        FishViewHolder(View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_fish_details_photo);
            tvName = itemView.findViewById(R.id.tv_fish_detail_name);
            tvPrice = itemView.findViewById(R.id.tv_fish_detail_price);
        }
    }

    class FishDetailViewHolder extends RecyclerView.ViewHolder {

        private AppCompatImageView ivLandscape;
        private TextView tvOgPrice;
        private TextView tvOfferPrice;
        private AppCompatImageView ivClose;
        private RecyclerView rvSize;
        private QuantityLayout qlOneKG;
        private QuantityLayout qlTwoKG;
        private QuantityLayout qlThreeKG;
        private QuantityLayout qlFourKG;
        private QuantityLayout qlFiveKG;
        private TextView tvAddToCart;
        private YameenTextView tvName;
        private Group group;

        FishDetailViewHolder(View mView) {
            super(mView);

            ivClose = mView.findViewById(R.id.iv_fish_details_close);
            tvAddToCart = mView.findViewById(R.id.tv_fish_details_add_to_cart);
            tvOgPrice = mView.findViewById(R.id.tv_fish_details_original_value);
            tvOfferPrice = mView.findViewById(R.id.tv_fish_details_display_amount);
            rvSize = mView.findViewById(R.id.rv_fish_details_size_filter);
            qlOneKG = mView.findViewById(R.id.ql_one_kg);
            qlTwoKG = mView.findViewById(R.id.ql_two_kg);
            qlThreeKG = mView.findViewById(R.id.ql_three_kg);
            qlFourKG = mView.findViewById(R.id.ql_four_kg);
            qlFiveKG = mView.findViewById(R.id.ql_five_kg);
            ivLandscape = mView.findViewById(R.id.iv_fish_landscape_image);
            tvName = mView.findViewById(R.id.tv_fish_details_name);
            group = mView.findViewById(R.id.group_bottom);
            group.setVisibility(View.GONE);
            ivClose.setVisibility(View.GONE);

            tvOgPrice.setPaintFlags(tvOgPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(rvSize.getContext(), 3);
            rvSize.setLayoutManager(gridLayoutManager);

            qlOneKG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FishDetails details = (FishDetails) mFishItem.get(getLayoutPosition());
                    details.setQuantityOption(qlOneKG.getQuantity());
                    notifyItemChanged(getLayoutPosition());
                }
            });

            qlTwoKG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FishDetails details = (FishDetails) mFishItem.get(getLayoutPosition());
                    details.setQuantityOption(qlTwoKG.getQuantity());
                    notifyItemChanged(getLayoutPosition());
                }
            });

            qlThreeKG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FishDetails details = (FishDetails) mFishItem.get(getLayoutPosition());
                    details.setQuantityOption(qlThreeKG.getQuantity());
                    notifyItemChanged(getLayoutPosition());
                }
            });

            qlFourKG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FishDetails details = (FishDetails) mFishItem.get(getLayoutPosition());
                    details.setQuantityOption(qlFourKG.getQuantity());
                    notifyItemChanged(getLayoutPosition());
                }
            });

            qlFiveKG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FishDetails details = (FishDetails) mFishItem.get(getLayoutPosition());
                    details.setQuantityOption(qlFiveKG.getQuantity());
                    notifyItemChanged(getLayoutPosition());
                }
            });
        }
    }
}
