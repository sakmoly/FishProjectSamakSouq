package qa.happytots.yameenhome.view.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import qa.happytots.yameenhome.DataBase.DatabaseHandler;
import qa.happytots.yameenhome.HomeActivity;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.Utility.UtilityClass;
import qa.happytots.yameenhome.components.Toast;
import qa.happytots.yameenhome.datamodel.PreferenceController;
import qa.happytots.yameenhome.datamodel.network.NetworkManager;
import qa.happytots.yameenhome.datamodel.network.NetworkManager.OnNetworkResponseListener;
import qa.happytots.yameenhome.model.Caption;
import qa.happytots.yameenhome.model.Constants;
import qa.happytots.yameenhome.model.IFishListItem;
import qa.happytots.yameenhome.model.Search;
import qa.happytots.yameenhome.model.SearchTop;
import qa.happytots.yameenhome.model.SubTitle;
import qa.happytots.yameenhome.model.cart.add.response.CartAddResponse;
import qa.happytots.yameenhome.model.fish.FishDetails;
import qa.happytots.yameenhome.model.quickview.category.QuickView;
import qa.happytots.yameenhome.model.quickview.category.QuickViewCategoryResponse;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.FishListOptions;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.FishList_Response;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.FishListdata;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.MainBannerResponse;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.SmallBannerResponse;
import qa.happytots.yameenhome.view.adapter.FishAdapter;
import qa.happytots.yameenhome.view.listeners.IFishListListener;
import qa.happytots.yameenhome.view.ui.activities.FilterActivity;
import qa.happytots.yameenhome.view.ui.activities.LocationActivity;
import qa.happytots.yameenhome.view.ui.activities.QuickViewActivity;


public class FishFragment extends Fragment implements IFishListListener, OnNetworkResponseListener {

    private static final int REQUEST_LOCATION_CODE = 1010;

    private RecyclerView recyclerView;

    private ArrayList<IFishListItem> listdata;
    private FishAdapter mAdapter;

    private GridLayoutManager manager;

    private int mSmallBannerPosition = 0;

    private Search search = new Search();

    private boolean mFishExpanded = false;
    private int mExpandedPosition = -1;

    public FishFragment() {
    }

    public static FishFragment newInstance() {
        FishFragment fragment = new FishFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listdata = new ArrayList<>();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fish_list, container, false);

        if (listdata.size() == 0) {
            UtilityClass.showProgressDialog(getContext());
            getQuickCategory();
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_product_home);
        mAdapter = new FishAdapter(listdata, this);
        manager = new GridLayoutManager(getContext(), 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.getItemViewSpanValue(position);
            }
        });

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mFishExpanded) {
                        closeDetailedFish(mExpandedPosition);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_location) {
            Intent intent = new Intent(getContext(), LocationActivity.class);
            startActivityForResult(intent, REQUEST_LOCATION_CODE);
            return true;
        }
//        else if (id == R.id.action_quick_view) {
//            Intent intent = new Intent(getContext(), QuickViewActivity.class);
//            startActivity(intent);
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) getActivity()).updateCartCount();
    }

    @Override
    public void search(View view) {
        Intent intent = new Intent(getContext(), QuickViewActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(), view, "search");
        intent.putExtra(QuickViewActivity.BUNDLE_QUICK_VIEW_PURPOSE, QuickViewActivity.PURPOSE_SEARCH);
        intent.putStringArrayListExtra(QuickViewActivity.BUNDLE_QUICK_VIEW_FISHS, getProductsFromList());
        startActivity(intent, options.toBundle());
    }


    @Override
    public void onExpand(int position, FishListdata fish) {
        if (fish.isNeedDetailedView()) {
            return;
        }

        mFishExpanded = true;
        position = collapseFishDetail(position);

        fish.setNeedDetailedView(true);

        FishDetails details = new FishDetails();
        details.setFishId(fish.getProductId());
        details.setOgPrice(String.valueOf(fish.getPrice()));
        details.setOfferPrice(String.valueOf(fish.getSpecial()));
        details.setName(fish.getModel());
        details.setParentPosition(position);

        List<String> ogUrls = fish.getOriginalImages();
        if (ogUrls != null && ogUrls.size() > 0) {
            details.setLandscapeUrl(ogUrls.get(0));
        }

        List<FishListOptions> options = fish.getOptions();
        if (options != null && options.size() > 0) {
            details.setmFinishKey(options.get(0).getProductOptionId());
            details.setTypeOptions(options.get(0).getOptionValue());
        }
        int addingPosition;
        if (fish.getSide() == FishListdata.LEFT_SIDE) {
            addingPosition = position + 2;
            if (addingPosition > listdata.size()) {
                addingPosition = addingPosition - 1;
            }
        } else {
            addingPosition = position + 1;
        }

        mExpandedPosition = addingPosition;
        listdata.add(addingPosition, details);
        mAdapter.notifyItemInserted(addingPosition);
        manager.scrollToPosition(addingPosition);
    }

    @Override
    public void closeDetailedFish(int position) {
        FishDetails detail = (FishDetails) listdata.get(position);
        FishListdata data = (FishListdata) listdata.get(detail.getParentPosition());
        data.setNeedDetailedView(false);
        listdata.remove(position);
        mFishExpanded = false;
        mExpandedPosition = -1;
        mAdapter.notifyItemRemoved(position);
    }

    @Override
    public void addToCart(int position, FishDetails details) {
        if (details.isAlreadyAddedToCart()) {
            Toast.makeToast("Already Added to cart");
            return;
        }

        JsonObject request = new JsonObject();
        request.addProperty("product_id", String.valueOf(details.getFishId()));
        request.addProperty("quantity", details.getQuantityOption());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(String.valueOf(details.getmFinishKey()), details.getmFinishValue());
        request.add("option", jsonObject);
        UtilityClass.showProgressDialog(getContext());
        details.setAlreadyAddedToCart(true);

        FishListdata fishListdata = (FishListdata) listdata.get(position - 1);
        fishListdata.setNeedDetailedView(false);
        mAdapter.notifyItemChanged(position - 1);

        listdata.remove(details);
        mAdapter.notifyItemRemoved(position);
        callApiForAddToCart(request);
    }

    @Override
    public void priceFilter(int min, int max) {
        callApiPriceRangeFilter(min, max);
    }

    @Override
    public void finishFilter(int categoryId) {
        callAPIForSizeFilter(categoryId);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeToast(message);
    }

    @Override
    public void filter(int position) {
        IFishListItem item = listdata.get(position + 1);
        for (IFishListItem item2:
             listdata) {
            if (item2 instanceof FishListdata) {
                FishListdata fishListdata = (FishListdata) item2;
                List<FishListOptions> options = fishListdata.getOptions();
            }
        }
        if (!(item instanceof Search)) {
            listdata.add(position + 1, search);
            mAdapter.notifyItemInserted(position + 1);
            manager.scrollToPosition(position + 1);
        }
    }

    @Override
    public void offerFilter(int position) {
        callQuickViewSearch(String.valueOf(search.getTodaysOfferId()));
    }

    @Override
    public void filterClose(int position) {
        listdata.remove(position);
        mAdapter.notifyItemRemoved(position);
        callApiForFishList();
    }

    @Override
    public void onQuantityClick(int quantity) {

    }

    private void getQuickCategory() {
        DatabaseHandler handler = new DatabaseHandler(getContext());
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForgetQuickView(handler.getSessionValue());
    }

    private void callQuickViewSearch(String categoryId) {
        UtilityClass.showProgressDialog(getContext());
        DatabaseHandler handler = new DatabaseHandler(getContext());
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.getProductByCategoryAtHome(handler.getSessionValue(), categoryId);
    }

    private void callApiForAddToCart(JsonObject request) {
        DatabaseHandler handler = new DatabaseHandler(getContext());
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForAddToCart(handler.getSessionValue(), request);
    }

    private void callApiForFishList() {
        String locationId = PreferenceController.getStringPreference(PreferenceController.PreferenceKeys.PREFERENCE_LOCATION_ID);
        if (!locationId.equals("")) {
            NetworkManager manager = NetworkManager.getInstance();
            manager.setNetworkListener(this);
            manager.callAPIForFistList(locationId);
        } else {
            Toast.makeToast("Location not available");
        }
    }

    private void callApiForMainBanner() {
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForMainBanner();
    }

    private void callApiForSmallBanner() {
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForSmallBanner();
    }

    private void callApiPriceRangeFilter(int min, int max) {
        UtilityClass.showProgressDialog(getContext());
        DatabaseHandler handler = new DatabaseHandler(getContext());
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForPriceRangeFilter(handler.getSessionValue(), min + "," + max);
    }


    private void callAPIForSizeFilter(int id) {
        UtilityClass.showProgressDialog(getContext());
        DatabaseHandler handler = new DatabaseHandler(getContext());
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForSizeFilter(handler.getSessionValue(), String.valueOf(id));
    }

    @Override
    public void successResponse(qa.happytots.yameenhome.datamodel.network.Response response) {
        UtilityClass.dismissProgressDialog();
        if (response.body instanceof FishList_Response) {
            removeFish();
            FishList_Response list_response = (FishList_Response) response.body;
            populateFistList(list_response.getData());

            UtilityClass.showProgressDialog(getContext());
            callApiForSmallBanner();
        } else if (response.body instanceof MainBannerResponse) {
            MainBannerResponse mainRes = (MainBannerResponse) response.body;

            listdata.add(0, mainRes);
            mAdapter.notifyItemInserted(0);

            listdata.add(1, new Caption());
            mAdapter.notifyItemInserted(1);

            listdata.add(2, new SearchTop());
            mAdapter.notifyItemInserted(2);

            listdata.add(3, new SubTitle());
            mAdapter.notifyItemInserted(3);

            UtilityClass.showProgressDialog(getContext());
            callApiForFishList();

        } else if (response.body instanceof SmallBannerResponse) {
            SmallBannerResponse smallRes = (SmallBannerResponse) response.body;
            int size;
            if (isFilterExpanded()) {
                size = 8;
            } else {
                size = 7;
            }
            if (listdata.size() > size) {
                listdata.add(size + 1, smallRes);
                mAdapter.notifyItemInserted(size + 1);
                mSmallBannerPosition = size + 1;
            } else {
                listdata.add(smallRes);
                mAdapter.notifyDataSetChanged();
                mSmallBannerPosition = listdata.size() - 1;
            }
        } else if (response.body instanceof CartAddResponse) {
            CartAddResponse toCartResponse = (CartAddResponse) response.body;
            if (toCartResponse.getSuccess() == Constants.SUCCESS_RESPONSE) {
                int count = PreferenceController.getIntPreference(PreferenceController.PreferenceKeys.PREFERENCE_CART_COUNT);
                PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_CART_COUNT, count + 1);
                ((HomeActivity) getActivity()).updateCartCount();
                Toast.makeToast("Added To Cart");
            }
        } else if (response.body instanceof QuickViewCategoryResponse) {
            QuickViewCategoryResponse cRes = (QuickViewCategoryResponse) response.body;
            List<QuickView> quickViews = cRes.getData();
            if (quickViews != null && quickViews.size() > 0) {
                for (QuickView q :
                        quickViews) {
                    String name = q.getName();
                    if (name.contains("Small")) {
                        search.setFINISHSMALL(q.getCategoryId());
                    } else if (name.contains("Medium")) {
                        search.setFINISHMEDIUM(q.getCategoryId());
                    } else if (name.contains("Big")) {
                        search.setFINISHLARGE(q.getCategoryId());
                    } else if (name.contains("Shell")) {
                        search.setFINISHSHELL(q.getCategoryId());
                    } else if (name.contains("Today")) {
                        search.setTodaysOfferId(q.getCategoryId());
                    }
                }
            }

            callApiForMainBanner();
        }
    }

    @Override
    public void failureResponse(qa.happytots.yameenhome.datamodel.network.Response response) {
        UtilityClass.dismissProgressDialog();
    }

    @Override
    public void noInternet() {
        Toast.makeToast(R.string.no_internet);
        UtilityClass.dismissProgressDialog();
    }

    private void populateFistList(List<FishListdata> list) {
        for (int i = 0; i < list.size(); i++) {
            FishListdata listdata = list.get(i);
            listdata.setSide(i % 2 == 0 ? FishListdata.LEFT_SIDE : FishListdata.RIGHT_SIDE);
        }
        listdata.addAll(list);
        mAdapter.notifyDataSetChanged();
    }


    private void removeFish() {
        Iterator<IFishListItem> iterator = listdata.iterator();
        while (iterator.hasNext()) {
            IFishListItem item = iterator.next();
            if (item instanceof FishDetails || item instanceof FishListdata || item instanceof SmallBannerResponse) {
                iterator.remove();
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    private int collapseFishDetail(int position) {
        Iterator<IFishListItem> items = listdata.iterator();
        int i = 0;
        while (items.hasNext()) {
            IFishListItem item = items.next();
            if (item instanceof FishDetails) {
                FishDetails details = (FishDetails) item;
                FishListdata fishListdata = (FishListdata) listdata.get(details.getParentPosition());
                fishListdata.setNeedDetailedView(false);
                items.remove();
                if (fishListdata.getSide() == FishListdata.LEFT_SIDE) {
                    mAdapter.notifyItemRemoved(details.getParentPosition() + 2);
                } else {
                    mAdapter.notifyItemRemoved(details.getParentPosition() + 1);
                }
                mFishExpanded = false;
                mExpandedPosition = -1;
                if (position > i) {
                    position = position - 1;
                }
            }
            i++;
        }

        return position;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Search search = data.getParcelableExtra(FilterActivity.FILTER);
                if (search != null) {
                    if (search.isSize()) {
                        callAPIForSizeFilter(search.getSelected());
                    } else {
                        callApiPriceRangeFilter(search.getMin(), search.getMax());
                    }
                }
            }
        } else if (requestCode == 1002) {
            int count = data.getIntExtra(HomeActivity.CART_COUNT, -1);
            if (count != -1) {
                PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_CART_COUNT, count);
            }
        } else if (requestCode == REQUEST_LOCATION_CODE) {
            callApiForFishList();
        }
    }

    private ArrayList<String> getProductsFromList() {
        ArrayList<String> fishes = new ArrayList<>();
        for (IFishListItem item : listdata) {
            if (item instanceof FishListdata) {
                String fishName = ((FishListdata) item).getName();
                fishes.add(fishName);
            }
        }

        return fishes;
    }

    private boolean isFilterExpanded() {
        for (IFishListItem item : listdata) {
            if (item instanceof Search) {
                return true;
            }
        }

        return false;
    }
}
