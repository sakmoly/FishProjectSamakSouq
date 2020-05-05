package qa.happytots.yameenhome.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.components.QuantityLayout;
import qa.happytots.yameenhome.components.YameenTextView;
import qa.happytots.yameenhome.model.fish.FishDetails;
import qa.happytots.yameenhome.model.quickview.category.QuickView;
import qa.happytots.yameenhome.model.quickview.product.Datum;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.FishListOptionvalue;

public class QuickViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_MAIN = 0;
    private static final int TYPE_SUB = 1;
    private static final int ITEM_TYPE_FISH_DETAIL = 2;

    private List<Bridge> mQuickViewList;
    private OnQuickViewInteractionListener mListener;

    public QuickViewAdapter(List<Bridge> mQuickViewList, OnQuickViewInteractionListener mListener) {
        this.mQuickViewList = mQuickViewList;
        this.mListener = mListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_MAIN) {
            View view = inflater.inflate(R.layout.single_quick_view, parent, false);
            return new QuickViewMainViewHolder(view);
        } else if (viewType == ITEM_TYPE_FISH_DETAIL) {
            View itemView = LayoutInflater.from(context)
                    .inflate(R.layout.item_fish_details, parent, false);
            return new ViewHolderFishDetails(itemView);
        } else {
            View view = inflater.inflate(R.layout.item_fish, parent, false);
            return new ViewHolderFish(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof QuickViewMainViewHolder) {
            QuickViewMainViewHolder viewMainViewHolder = (QuickViewMainViewHolder) holder;
            QuickView quickView = (QuickView) mQuickViewList.get(position);
            viewMainViewHolder.tvMain.setText(quickView.getName());
            if (quickView.isSelected()) {
                viewMainViewHolder.tvMain.setTextColor(ContextCompat.getColor(viewMainViewHolder.tvMain.getContext(), R.color.lightYellow));
            } else {
                viewMainViewHolder.tvMain.setTextColor(ContextCompat.getColor(viewMainViewHolder.tvMain.getContext(), R.color.black));
            }
        } else if (holder instanceof ViewHolderFishDetails) {
            ViewHolderFishDetails holderFishDetails = (ViewHolderFishDetails) holder;

            FishDetails details = (FishDetails) mQuickViewList.get(position);

            FinishAdapter finish_adapter = new FinishAdapter(details.getTypeOptions(), holderFishDetails);
            holderFishDetails.rvSize.setAdapter(finish_adapter);

            float price = Float.valueOf(details.getOfferPrice());
            float ogPrice = Float.valueOf(details.getOgPrice());
            if (details.getQuantityOption() > 0) {
                price = price * details.getQuantityOption();
            }

            holderFishDetails.tvOfferPrice.setText(String.format(Locale.getDefault(), "%.2f", price));

            if (details.getOgPrice().equals("0")) {
                holderFishDetails.tvOgPrice.setVisibility(View.GONE);
            } else {
                holderFishDetails.tvOgPrice.setVisibility(View.VISIBLE);
                holderFishDetails.tvOgPrice.setText(String.format(Locale.getDefault(), "%.2f", ogPrice * (details.getQuantityOption() != 0 ? details.getQuantityOption() : 1)));
                holderFishDetails.tvOgPrice.setPaintFlags(holderFishDetails.tvOgPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }

            holderFishDetails.qlOneKG.setType(QuantityLayout.TYPE_UNDERLINE);
            holderFishDetails.qlTwoKG.setType(QuantityLayout.TYPE_UNDERLINE);
            holderFishDetails.qlThreeKG.setType(QuantityLayout.TYPE_UNDERLINE);
            holderFishDetails.qlFourKG.setType(QuantityLayout.TYPE_UNDERLINE);
            holderFishDetails.qlFiveKG.setType(QuantityLayout.TYPE_UNDERLINE);

            if (details.getQuantityOption() == 1) {
                holderFishDetails.qlOneKG.setType(QuantityLayout.TYPE_UNDERLINE_RED);
            } else if (details.getQuantityOption() == 2) {
                holderFishDetails.qlTwoKG.setType(QuantityLayout.TYPE_UNDERLINE_RED);
            } else if (details.getQuantityOption() == 3) {
                holderFishDetails.qlThreeKG.setType(QuantityLayout.TYPE_UNDERLINE_RED);
            } else if (details.getQuantityOption() == 4) {
                holderFishDetails.qlFourKG.setType(QuantityLayout.TYPE_UNDERLINE_RED);
            } else if (details.getQuantityOption() == 5) {
                holderFishDetails.qlFiveKG.setType(QuantityLayout.TYPE_UNDERLINE_RED);
            }

            holderFishDetails.tvName.setText(details.getName());

            Picasso.get().load(details.getLandscapeUrl())
                    .fit()
                    .error(R.drawable.default_placeholder)
                    .placeholder(R.drawable.default_placeholder)
                    .into(holderFishDetails.ivLandscape);
        } else {
            ViewHolderFish viewHolderFish = (ViewHolderFish) holder;
            Datum listdata = (Datum) mQuickViewList.get(position);
            Picasso.get().load(listdata.getOriginalImage())
                    .fit()
                    .error(R.drawable.default_placeholder)
                    .placeholder(R.drawable.default_placeholder)
                    .into(viewHolderFish.ivPhoto);

            viewHolderFish.tvAmount.setText(String.format(Locale.getDefault(), "%.2f", Float.valueOf(listdata.getPrice())));
            viewHolderFish.tvAmount.setVisibility(View.VISIBLE);
            viewHolderFish.tvAmountTitle.setVisibility(View.VISIBLE);
            viewHolderFish.tvFishName.setText(listdata.getName());

            float special = Float.valueOf(listdata.getSpecial());
            float price = Float.valueOf(listdata.getPrice());
            if (special == 0) {
                viewHolderFish.ivRibbon.setVisibility(View.GONE);
                viewHolderFish.tvDiscount.setVisibility(View.GONE);
            } else {
                viewHolderFish.ivRibbon.setVisibility(View.VISIBLE);
                viewHolderFish.tvDiscount.setVisibility(View.VISIBLE);
                int discount = (int) (((price - special) / price) * 100);
                String template = "%s%% Offer";
                viewHolderFish.tvDiscount.setText(String.format(template, discount));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mQuickViewList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mQuickViewList.get(position) instanceof QuickView) {
            return TYPE_MAIN;
        }  else if (mQuickViewList.get(position) instanceof FishDetails) {
            return ITEM_TYPE_FISH_DETAIL;
        }
        return TYPE_SUB;
    }

    class QuickViewMainViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMain;
        QuickViewMainViewHolder(View itemView) {
            super(itemView);
            tvMain = itemView.findViewById(R.id.tv_quick_view_main);

            tvMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onQuickViewMainClick(getLayoutPosition(), (QuickView) mQuickViewList.get(getLayoutPosition()));
                }
            });
        }
    }

    class QuickViewSubViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSub;
        private TextView tvSubPrice;
        QuickViewSubViewHolder(View itemView) {
            super(itemView);
            tvSub = itemView.findViewById(R.id.tv_quick_view_sub);
            tvSubPrice = itemView.findViewById(R.id.tv_quick_view_sub_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onQuickViewSubClick(getLayoutPosition(), (Datum) mQuickViewList.get(getLayoutPosition()));
                }
            });
        }
    }

    public class ViewHolderFish extends RecyclerView.ViewHolder {


        private TextView tvFishName;
        private TextView tvAmountTitle;
        private TextView tvAmount;
        private AppCompatImageView ivPhoto;
        private YameenTextView tvDiscount;
        private AppCompatImageView ivRibbon;

        ViewHolderFish(View mView) {
            super(mView);
            tvFishName = mView.findViewById(R.id.tv_fish_name);
            tvAmountTitle = mView.findViewById(R.id.tv_fish_offer_price_text);
            ivPhoto = mView.findViewById(R.id.iv_fish_photo);
            tvAmount = mView.findViewById(R.id.tv_fish_offer_price);
            tvDiscount = mView.findViewById(R.id.tv_fish_ribbon_discount);
            ivRibbon = mView.findViewById(R.id.iv_fish_discount_ribbon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onQuickViewSubClick(getLayoutPosition(), (Datum) mQuickViewList.get(getLayoutPosition()));
//                    mListener.onExpand(getLayoutPosition(), (FishListdata) mList.get(getLayoutPosition()));
                }
            });
        }
    }

    public class ViewHolderFishDetails extends RecyclerView.ViewHolder implements OnProductDetailInteractionListener {

        private AppCompatImageView ivLandscape;
        private final TextView tvOgPrice;
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


        ViewHolderFishDetails(View mView) {
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

            tvOgPrice.setPaintFlags(tvOgPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(rvSize.getContext(), 3);
            rvSize.setLayoutManager(gridLayoutManager);

            tvAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FishDetails details = (FishDetails) mQuickViewList.get(getLayoutPosition());
                    if (details.getmFinishValue() == -1) {
                        mListener.showMessage("Please select a finish");
                    } else if (details.getQuantityOption() == -1) {
                        mListener.showMessage("Please select a quantity");
                    } else {
                        mListener.addToCart(getLayoutPosition(), details);
                    }
                }
            });

            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.closeDetailedFish(getLayoutPosition());
                }
            });


            qlOneKG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FishDetails details = (FishDetails) mQuickViewList.get(getLayoutPosition());
                    details.setQuantityOption(qlOneKG.getQuantity());
                    notifyItemChanged(getLayoutPosition());
                }
            });

            qlTwoKG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FishDetails details = (FishDetails) mQuickViewList.get(getLayoutPosition());
                    details.setQuantityOption(qlTwoKG.getQuantity());
                    notifyItemChanged(getLayoutPosition());
                }
            });

            qlThreeKG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FishDetails details = (FishDetails) mQuickViewList.get(getLayoutPosition());
                    details.setQuantityOption(qlThreeKG.getQuantity());
                    notifyItemChanged(getLayoutPosition());
                }
            });

            qlFourKG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FishDetails details = (FishDetails) mQuickViewList.get(getLayoutPosition());
                    details.setQuantityOption(qlFourKG.getQuantity());
                    notifyItemChanged(getLayoutPosition());
                }
            });

            qlFiveKG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FishDetails details = (FishDetails) mQuickViewList.get(getLayoutPosition());
                    details.setQuantityOption(qlFiveKG.getQuantity());
                    notifyItemChanged(getLayoutPosition());
                }
            });
        }

        @Override
        public void onTypeSelection(FishListOptionvalue optionvalue) {
            FishDetails details = (FishDetails) mQuickViewList.get(getLayoutPosition());
            details.setmFinishValue(optionvalue.getProductOptionValueId());

            Datum fishListdata = (Datum) mQuickViewList.get(getLayoutPosition() - 1);
            float price = Float.valueOf(fishListdata.getPrice());
            float offeredPrice = Float.valueOf(optionvalue.getPrice());
            if (offeredPrice != 0) {
                details.setOfferPrice(String.valueOf(price * offeredPrice));
                notifyItemChanged(getLayoutPosition());
            }
        }
    }

    public interface OnQuickViewInteractionListener {
        void onQuickViewMainClick(int position, QuickView quickView);
        void onQuickViewSubClick(int position, Datum datum);
        void showMessage(String message);
        void closeDetailedFish(int position);
        void addToCart(int position, FishDetails details);
    }
}
