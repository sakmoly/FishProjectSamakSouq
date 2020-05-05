package qa.happytots.yameenhome.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.edmodo.rangebar.RangeBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.components.QuantityLayout;
import qa.happytots.yameenhome.components.RoundedPagerIndicator;
import qa.happytots.yameenhome.components.YameenTextView;
import qa.happytots.yameenhome.model.Caption;
import qa.happytots.yameenhome.model.IFishListItem;
import qa.happytots.yameenhome.model.Search;
import qa.happytots.yameenhome.model.SearchTop;
import qa.happytots.yameenhome.model.SubTitle;
import qa.happytots.yameenhome.model.fish.FishDetails;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.FishListOptionvalue;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.FishListdata;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.MainBannerResponse;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.SmallBannerResponse;
import qa.happytots.yameenhome.view.listeners.IFishListListener;


public class FishAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_TYPE_MAIN_BANNER = 0;
    private static final int ITEM_TYPE_CAPTION = 1;
    private static final int ITEM_TYPE_SEARCH = 2;
    private static final int ITEM_TYPE_SUB_TITLE = 3;
    private static final int ITEM_TYPE_FISH = 4;
    private static final int ITEM_TYPE_FISH_DETAIL = 5;
    private static final int ITEM_TYPE_SMALL_BANNER = 6;
    private static final int ITEM_TYPE_FILER = 7;

    private final ArrayList<IFishListItem> mList;
    private final IFishListListener mListener;
    private Handler mTopBnnerHandler;
    private Handler mSmallBannerhandler;


    public FishAdapter(ArrayList<IFishListItem> items, IFishListListener listener) {
        mList = items;
        mListener = listener;
        mTopBnnerHandler = new Handler();
        mSmallBannerhandler = new Handler();
    }


    @Override
    public int getItemViewType(int position) {
        IFishListItem item = mList.get(position);

        if (item instanceof MainBannerResponse) {
            return ITEM_TYPE_MAIN_BANNER;
        } else if (item instanceof FishDetails) {
            return ITEM_TYPE_FISH_DETAIL;
        } else if (item instanceof Caption) {
            return ITEM_TYPE_CAPTION;
        } else if (item instanceof SearchTop) {
            return ITEM_TYPE_SEARCH;
        } else if (item instanceof SubTitle) {
            return ITEM_TYPE_SUB_TITLE;
        } else if (item instanceof FishListdata) {
            return ITEM_TYPE_FISH;
        } else if (item instanceof Search) {
            return ITEM_TYPE_FILER;
        } else {
            return ITEM_TYPE_SMALL_BANNER;
        }
    }


    public int getItemViewSpanValue(int position) {
        IFishListItem item = mList.get(position);
        if (item instanceof FishListdata) {
            return 1;
        } else {
            return 2;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        Context mContext = parent.getContext();
        switch (viewType) {

            case ITEM_TYPE_MAIN_BANNER:
                itemView = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_main_banner, parent, false);
                itemView.setTag(1);
                return new ViewHolderBanner(itemView);

            case ITEM_TYPE_CAPTION:
                itemView = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_caption, parent, false);
                itemView.setTag(0);
                return new ViewHolderCaption(itemView);

            case ITEM_TYPE_SEARCH:
                itemView = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_search, parent, false);
                itemView.setTag(0);
                return new ViewHolderSearch(itemView);

            case ITEM_TYPE_FILER:
                itemView = LayoutInflater.from(mContext)
                        .inflate(R.layout.single_filter, parent, false);
                itemView.setTag(0);
                return new FilterViewHolder(itemView);

            case ITEM_TYPE_SUB_TITLE:
                itemView = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_sub_title, parent, false);
                itemView.setTag(0);
                return new ViewHolderSubTitle(itemView);

            case ITEM_TYPE_FISH:
                itemView = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_fish, parent, false);
                itemView.setTag(0);
                return new ViewHolderFish(itemView);

            case ITEM_TYPE_FISH_DETAIL:
                itemView = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_fish_details, parent, false);
                itemView.setTag(0);
                return new ViewHolderFishDetails(itemView);

            case ITEM_TYPE_SMALL_BANNER:
                itemView = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_sub_banner, parent, false);
                itemView.setTag(2);
                return new ViewHolderSmallBanner(itemView);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {
        int type = getItemViewType(position);
        switch (type) {
            case ITEM_TYPE_MAIN_BANNER:

                final ViewHolderBanner holder = ((ViewHolderBanner) viewHolder);
                final MainBannerResponse bannerItem = ((MainBannerResponse) mList.get(position));

                holder.pager_banner.setAdapter(new TopBannerAdapter(holder.pager_banner.getContext(), bannerItem.getData()));
                holder.indicator.setPager(((ViewHolderBanner) viewHolder).pager_banner);

                break;

            case ITEM_TYPE_FILER:
                FilterViewHolder filterViewHolder = (FilterViewHolder) viewHolder;
                Search search = (Search) mList.get(position);

                if (search.getSelected() == search.getFINISHSMALL()) {
                    filterViewHolder.rbFinish.setChecked(true);
                    filterViewHolder.rbPrice.setChecked(false);
                    search.setSize(true);
                    search.setSelected(search.getFINISHSMALL());
                    filterViewHolder.smallSelected.setBackground(ContextCompat.getDrawable(filterViewHolder.smallSelected.getContext(), R.drawable.filter_selection_shape_red));
                    filterViewHolder.mediumSelected.setBackground(ContextCompat.getDrawable(filterViewHolder.smallSelected.getContext(), R.drawable.filter_selection_shape_grey));
                    filterViewHolder.largeSelected.setBackground(ContextCompat.getDrawable(filterViewHolder.smallSelected.getContext(), R.drawable.filter_selection_shape_grey));
                    filterViewHolder.shelSelected.setBackground(ContextCompat.getDrawable(filterViewHolder.smallSelected.getContext(), R.drawable.filter_selection_shape_grey));
                } else if (search.getSelected() == search.getFINISHMEDIUM()) {
                    filterViewHolder.rbFinish.setChecked(true);
                    filterViewHolder.rbPrice.setChecked(false);
                    search.setSize(true);
                    search.setSelected(search.getFINISHMEDIUM());
                    filterViewHolder.smallSelected.setBackground(ContextCompat.getDrawable(filterViewHolder.smallSelected.getContext(), R.drawable.filter_selection_shape_grey));
                    filterViewHolder.mediumSelected.setBackground(ContextCompat.getDrawable(filterViewHolder.smallSelected.getContext(), R.drawable.filter_selection_shape_red));
                    filterViewHolder.largeSelected.setBackground(ContextCompat.getDrawable(filterViewHolder.smallSelected.getContext(), R.drawable.filter_selection_shape_grey));
                    filterViewHolder.shelSelected.setBackground(ContextCompat.getDrawable(filterViewHolder.smallSelected.getContext(), R.drawable.filter_selection_shape_grey));
                } else if (search.getSelected() == search.getFINISHLARGE()) {
                    filterViewHolder.rbFinish.setChecked(true);
                    filterViewHolder.rbPrice.setChecked(false);
                    search.setSize(true);
                    search.setSelected(search.getFINISHLARGE());
                    filterViewHolder.smallSelected.setBackground(ContextCompat.getDrawable(filterViewHolder.smallSelected.getContext(), R.drawable.filter_selection_shape_grey));
                    filterViewHolder.mediumSelected.setBackground(ContextCompat.getDrawable(filterViewHolder.smallSelected.getContext(), R.drawable.filter_selection_shape_grey));
                    filterViewHolder.largeSelected.setBackground(ContextCompat.getDrawable(filterViewHolder.smallSelected.getContext(), R.drawable.filter_selection_shape_red));
                    filterViewHolder.shelSelected.setBackground(ContextCompat.getDrawable(filterViewHolder.smallSelected.getContext(), R.drawable.filter_selection_shape_grey));
                } else if (search.getSelected() == search.getFINISHSHELL()) {
                    filterViewHolder.rbFinish.setChecked(true);
                    filterViewHolder.rbPrice.setChecked(false);
                    search.setSize(true);
                    search.setSelected(search.getFINISHSHELL());
                    filterViewHolder.smallSelected.setBackground(ContextCompat.getDrawable(filterViewHolder.smallSelected.getContext(), R.drawable.filter_selection_shape_grey));
                    filterViewHolder.mediumSelected.setBackground(ContextCompat.getDrawable(filterViewHolder.smallSelected.getContext(), R.drawable.filter_selection_shape_grey));
                    filterViewHolder.largeSelected.setBackground(ContextCompat.getDrawable(filterViewHolder.smallSelected.getContext(), R.drawable.filter_selection_shape_grey));
                    filterViewHolder.shelSelected.setBackground(ContextCompat.getDrawable(filterViewHolder.smallSelected.getContext(), R.drawable.filter_selection_shape_red));
                } else {
                    filterViewHolder.smallSelected.setBackground(ContextCompat.getDrawable(filterViewHolder.smallSelected.getContext(), R.drawable.filter_selection_shape_grey));
                    filterViewHolder.mediumSelected.setBackground(ContextCompat.getDrawable(filterViewHolder.smallSelected.getContext(), R.drawable.filter_selection_shape_grey));
                    filterViewHolder.largeSelected.setBackground(ContextCompat.getDrawable(filterViewHolder.smallSelected.getContext(), R.drawable.filter_selection_shape_grey));
                    filterViewHolder.shelSelected.setBackground(ContextCompat.getDrawable(filterViewHolder.smallSelected.getContext(), R.drawable.filter_selection_shape_grey));
                }
                break;

            case ITEM_TYPE_SUB_TITLE:
                ((ViewHolderSubTitle) viewHolder).mItem = ((SubTitle) mList.get(position));
                break;

            case ITEM_TYPE_FISH:
                ViewHolderFish viewHolderFish = (ViewHolderFish) viewHolder;
                FishListdata listdata = (FishListdata) mList.get(position);
                Picasso.get().load(listdata.getOriginalImage())
                        .fit()
                        .error(R.drawable.default_placeholder)
                        .placeholder(R.drawable.default_placeholder)
                        .into(viewHolderFish.ivPhoto);

                viewHolderFish.tvAmount.setText(String.format(Locale.getDefault(), "%.2f", (float) listdata.getPrice()));
                viewHolderFish.tvAmount.setVisibility(View.VISIBLE);
                viewHolderFish.tvAmountTitle.setVisibility(View.VISIBLE);
               // viewHolderFish.tvFishName.setText(String.format(Locale.getDefault(),listdata.getName()));
                viewHolderFish.tvFishName.setText(listdata.getName());
                if (listdata.getSpecial() == 0) {
                    viewHolderFish.ivRibbon.setVisibility(View.GONE);
                    viewHolderFish.tvDiscount.setVisibility(View.GONE);
                } else {
                    viewHolderFish.ivRibbon.setVisibility(View.VISIBLE);
                    viewHolderFish.tvDiscount.setVisibility(View.VISIBLE);
                    int discount = (int) ((((float) listdata.getPrice() - (float) listdata.getSpecial()) / (float) listdata.getPrice()) * 100);
                    String template = "%s%% Offer";
                    viewHolderFish.tvDiscount.setText(String.format(template, discount));
                }

                break;

            case ITEM_TYPE_FISH_DETAIL:
                ViewHolderFishDetails holderFishDetails = (ViewHolderFishDetails) viewHolder;

                FishDetails details = (FishDetails) mList.get(position);

                FinishAdapter finish_adapter = new FinishAdapter(details.getTypeOptions(), holderFishDetails);
                holderFishDetails.rvSize.setAdapter(finish_adapter);

                float offerPrice = Float.valueOf(details.getOfferPrice());
                float ogPrice = Float.valueOf(details.getOgPrice());

                if (offerPrice == 0) {
                    holderFishDetails.ivRibbon.setVisibility(View.GONE);
                    holderFishDetails.tvOgPrice.setVisibility(View.GONE);
                    holderFishDetails.tvOgPrice.setText("");
                    offerPrice = ogPrice;
                } else {
                    holderFishDetails.ivRibbon.setVisibility(View.GONE);
                    holderFishDetails.tvOgPrice.setVisibility(View.VISIBLE);
                    holderFishDetails.tvOgPrice.setText(String.format(Locale.getDefault(), "%.2f", ogPrice * (details.getQuantityOption() != 0 ? details.getQuantityOption() : 1)));
                    holderFishDetails.tvOgPrice.setPaintFlags(holderFishDetails.tvOgPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }

                if (details.getQuantityOption() > 0) {
                    offerPrice = offerPrice * details.getQuantityOption();
                }

                holderFishDetails.tvOfferPrice.setText(String.format(Locale.getDefault(), "%.2f", offerPrice));

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
                break;

            case ITEM_TYPE_SMALL_BANNER:
                final ViewHolderSmallBanner holderBanner = ((ViewHolderSmallBanner) viewHolder);
                final SmallBannerResponse smallbannerItem = ((SmallBannerResponse) mList.get(position));

                holderBanner.pager_banner1.setAdapter(new SmallBannerAdapter(holderBanner.pager_banner1.getContext(), smallbannerItem.getData()));
                holderBanner.indicator.setPager(((ViewHolderSmallBanner) viewHolder).pager_banner1);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
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
                    mListener.onExpand(getLayoutPosition(), (FishListdata) mList.get(getLayoutPosition()));
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
        private YameenTextView tvDiscount;
        private AppCompatImageView ivRibbon;


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
            tvDiscount = mView.findViewById(R.id.tv_fish_detail_ribbon_discount);
            ivRibbon = mView.findViewById(R.id.iv_fish_detail_discount_ribbon);

            tvOgPrice.setPaintFlags(tvOgPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(rvSize.getContext(), 3);
            rvSize.setLayoutManager(gridLayoutManager);

            tvAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FishDetails details = (FishDetails) mList.get(getLayoutPosition());
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
                    FishDetails details = (FishDetails) mList.get(getLayoutPosition());
                    details.setQuantityOption(qlOneKG.getQuantity());
                    notifyItemChanged(getLayoutPosition());
                }
            });

            qlTwoKG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FishDetails details = (FishDetails) mList.get(getLayoutPosition());
                    details.setQuantityOption(qlTwoKG.getQuantity());
                    notifyItemChanged(getLayoutPosition());
                }
            });

            qlThreeKG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FishDetails details = (FishDetails) mList.get(getLayoutPosition());
                    details.setQuantityOption(qlThreeKG.getQuantity());
                    notifyItemChanged(getLayoutPosition());
                }
            });

            qlFourKG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FishDetails details = (FishDetails) mList.get(getLayoutPosition());
                    details.setQuantityOption(qlFourKG.getQuantity());
                    notifyItemChanged(getLayoutPosition());
                }
            });

            qlFiveKG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FishDetails details = (FishDetails) mList.get(getLayoutPosition());
                    details.setQuantityOption(qlFiveKG.getQuantity());
                    notifyItemChanged(getLayoutPosition());
                }
            });
        }

        @Override
        public void onTypeSelection(FishListOptionvalue optionvalue) {
            FishDetails details = (FishDetails) mList.get(getLayoutPosition());
            details.setmFinishValue(optionvalue.getProductOptionValueId());

            FishListdata fishListdata = (FishListdata) mList.get(getLayoutPosition() - 1);
            double price = fishListdata.getPrice();
            float offeredPrice = Float.valueOf(optionvalue.getPrice());
            if (offeredPrice != 0) {
                details.setOfferPrice(String.valueOf(price * offeredPrice));
                notifyItemChanged(getLayoutPosition());
            }
        }
    }


    public class ViewHolderBanner extends RecyclerView.ViewHolder {
        private ViewPager pager_banner;
        private RoundedPagerIndicator indicator;

        ViewHolderBanner(View view) {
            super(view);
            pager_banner = view.findViewById(R.id.pager_banner);
            indicator = view.findViewById(R.id.indicator_banner);
        }
    }

    public class ViewHolderSmallBanner extends RecyclerView.ViewHolder {
        private ViewPager pager_banner1;
        private RoundedPagerIndicator indicator;

        ViewHolderSmallBanner(View view) {
            super(view);
            pager_banner1 = view.findViewById(R.id.pager_banner);
            indicator = view.findViewById(R.id.indicator_banner);
        }


    }

    public class ViewHolderSearch extends RecyclerView.ViewHolder {

        private AppCompatTextView tvSearch;
        private AppCompatImageView ivFilter;


        ViewHolderSearch(View mView) {
            super(mView);

            tvSearch = mView.findViewById(R.id.tv_fish_list_search);
            ivFilter = mView.findViewById(R.id.iv_fish_filter);

            tvSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.search(v);
                }
            });

            ivFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.filter(getLayoutPosition());
                }
            });
        }
    }

    class FilterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ConstraintLayout containerShellFish;
        private ConstraintLayout containerLargeFish;
        private ConstraintLayout containerMedium;
        private ConstraintLayout containerSmallFish;
        private TextView tvDone;
        private RangeBar rangeBar;
        private AppCompatRadioButton rbFinish;
        private AppCompatRadioButton rbPrice;
        private AppCompatRadioButton rbOffer;
        private TextView smallSelected;
        private TextView mediumSelected;
        private TextView largeSelected;
        private TextView shelSelected;
        private YameenTextView tvMinRange;
        private YameenTextView tvMaxRange;
        private AppCompatImageView ivClose;

        FilterViewHolder(View itemView) {
            super(itemView);
            tvDone = itemView.findViewById(R.id.filter_done_button);
            rbFinish = itemView.findViewById(R.id.rb_filter_size);
            rbPrice = itemView.findViewById(R.id.rb_filter_price);
            rbOffer = itemView.findViewById(R.id.tv_filter_offer);
            smallSelected = itemView.findViewById(R.id.small_selected);
            mediumSelected = itemView.findViewById(R.id.medium_selected);
            largeSelected = itemView.findViewById(R.id.large_selected);
            shelSelected = itemView.findViewById(R.id.shell_selected);
            tvMinRange = itemView.findViewById(R.id.tv_min_range);
            tvMaxRange = itemView.findViewById(R.id.tv_max_range);

            containerShellFish = itemView.findViewById(R.id.container_shell_fish);
            containerLargeFish = itemView.findViewById(R.id.container_large_fish);
            containerMedium = itemView.findViewById(R.id.container_medium_fish);
            containerSmallFish = itemView.findViewById(R.id.container_small_fish);
            ivClose = itemView.findViewById(R.id.iv_filter_close);

            rangeBar = itemView.findViewById(R.id.rangebar);
            rangeBar.setBarColor(rangeBar.getContext().getResources().getColor(R.color.graylightnew));
            rangeBar.setConnectingLineColor(rangeBar.getContext().getResources().getColor(R.color.red));
            rangeBar.setThumbImageNormal(R.drawable.rtrtr);
            rangeBar.setBarWeight(5);
            rangeBar.setTickCount(100);
            rangeBar.setTickHeight(0);
            rangeBar.setConnectingLineWeight(3);

            rangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
                @Override
                public void onIndexChangeListener(RangeBar rangeBar, int i, int i1) {
                    Search search = (Search) mList.get(getLayoutPosition());
                    search.setMin(i);
                    search.setMax(i1);
                    rbFinish.setChecked(false);
                    rbPrice.setChecked(true);

                    tvMinRange.setText(i + " SAR");
                    tvMaxRange.setText(i1 + " SAR");
                }
            });

            containerSmallFish.setOnClickListener(this);
            containerMedium.setOnClickListener(this);
            containerLargeFish.setOnClickListener(this);
            containerShellFish.setOnClickListener(this);
            tvDone.setOnClickListener(this);
            ivClose.setOnClickListener(this);

            rbOffer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        rbFinish.setChecked(false);
                        rbPrice.setChecked(false);
                    }
                }
            });

            rbFinish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        rbOffer.setChecked(false);
                        rbPrice.setChecked(false);
                    }
                }
            });

            rbPrice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    rbOffer.setChecked(false);
                    rbFinish.setChecked(false);
                }
            });
        }

        @Override
        public void onClick(View v) {
            Search search = (Search) mList.get(getLayoutPosition());
            if (v == containerSmallFish) {
                search.setSize(true);
                search.setSelected(search.getFINISHSMALL());
                notifyItemChanged(getLayoutPosition());
            } else if (v == containerMedium) {
                search.setSize(true);
                search.setSelected(search.getFINISHMEDIUM());
                notifyItemChanged(getLayoutPosition());
            } else if (v == containerLargeFish) {
                search.setSize(true);
                search.setSelected(search.getFINISHLARGE());
                notifyItemChanged(getLayoutPosition());
            } else if (v == containerShellFish) {
                search.setSize(true);
                search.setSelected(search.getFINISHSHELL());
                notifyItemChanged(getLayoutPosition());
            } else if (v == tvDone) {
                if (rbFinish.isChecked() || rbPrice.isChecked()) {
                    if (search.isSize()) {
                        mListener.finishFilter(search.getSelected());
                    } else {
                        mListener.priceFilter(search.getMin(), search.getMax());
                    }
                } else {
                    mListener.offerFilter(getLayoutPosition());
                }
            } else if (v == ivClose) {
                mListener.filterClose(getLayoutPosition());
            }
        }
    }

    public class ViewHolderSubTitle extends RecyclerView.ViewHolder {
        SubTitle mItem;

        ViewHolderSubTitle(View view) {
            super(view);

        }
    }


    public class ViewHolderCaption extends RecyclerView.ViewHolder {
        final View mView;
        Caption mItem;

        ViewHolderCaption(View view) {
            super(view);
            mView = view;

        }
    }
}
