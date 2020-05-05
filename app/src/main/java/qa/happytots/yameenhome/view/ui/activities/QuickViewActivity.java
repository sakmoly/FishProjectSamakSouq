package qa.happytots.yameenhome.view.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import qa.happytots.yameenhome.DataBase.DatabaseHandler;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.Utility.UtilityClass;
import qa.happytots.yameenhome.components.YameenAutoCompleteTextView;
import qa.happytots.yameenhome.datamodel.PreferenceController;
import qa.happytots.yameenhome.datamodel.network.NetworkManager;
import qa.happytots.yameenhome.datamodel.network.Response;
import qa.happytots.yameenhome.model.Constants;
import qa.happytots.yameenhome.model.cart.add.response.CartAddResponse;
import qa.happytots.yameenhome.model.fish.FishDetails;
import qa.happytots.yameenhome.model.quickview.category.QuickView;
import qa.happytots.yameenhome.model.quickview.category.QuickViewCategoryResponse;
import qa.happytots.yameenhome.model.quickview.product.Datum;
import qa.happytots.yameenhome.model.quickview.product.Option;
import qa.happytots.yameenhome.model.quickview.product.QuickViewProductByCategoryResponse;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.FishListdata;
import qa.happytots.yameenhome.view.adapter.Bridge;
import qa.happytots.yameenhome.view.adapter.QuickViewAdapter;

public class QuickViewActivity extends AppCompatActivity implements QuickViewAdapter.OnQuickViewInteractionListener,
        NetworkManager.OnNetworkResponseListener {

    public static final String BUNDLE_QUICK_VIEW_PURPOSE = "quick_view_purpose";
    public static final String BUNDLE_QUICK_VIEW_FISHS = "quick_view_fishes";

    public static final int PURPOSE_SEARCH = 0;
    public static final int PURPOSE_QUICK_VIEW = 1;

    private RecyclerView rvQuickList;
    private YameenAutoCompleteTextView edtSearch;

    private QuickViewAdapter mAdapter;
    private List<Bridge> mQuickList;
    private ArrayList<String> mFishes;

    private GridLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_list);

        Toolbar toolbar = findViewById(R.id.toolbar_quick);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        edtSearch = findViewById(R.id.edt_quick_search);
        rvQuickList = findViewById(R.id.rv_quick_list);

        manager = new GridLayoutManager(this, 2);
        rvQuickList.setLayoutManager(manager);

        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mQuickList.get(position) instanceof QuickView || mQuickList.get(position) instanceof FishDetails) {
                    return 2;
                }
                return 1;
            }
        });

        mQuickList = new ArrayList<>();
        mAdapter = new QuickViewAdapter(mQuickList, this);
        rvQuickList.setAdapter(mAdapter);

        int purpose = getIntent().getIntExtra(BUNDLE_QUICK_VIEW_PURPOSE, 1);
        if (purpose == PURPOSE_QUICK_VIEW) {
            UtilityClass.showProgressDialog(this);
            getQuickCategory();
            edtSearch.clearFocus();
        } else {
            mFishes = getIntent().getStringArrayListExtra(BUNDLE_QUICK_VIEW_FISHS);
            edtSearch.requestFocus();
            InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSearch, InputMethodManager.SHOW_IMPLICIT);
        }

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    validateSearchKey();
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
                    edtSearch.clearFocus();

                    return true;
                }
                return false;
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, mFishes);
        edtSearch.setAdapter(adapter);

        edtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                validateSearchKey();
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
                edtSearch.clearFocus();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void validateSearchKey() {
        String key = edtSearch.getText().toString();
        if (!TextUtils.isEmpty(key)) {
            searchKey(key);
        }
    }

    private void getQuickCategory() {
        DatabaseHandler handler = new DatabaseHandler(this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForgetQuickView(handler.getSessionValue());
    }

    private void searchKey(String key) {
        refresh();
        DatabaseHandler handler = new DatabaseHandler(this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        UtilityClass.showProgressDialog(QuickViewActivity.this);
        manager.callAPIForSearching(handler.getSessionValue(), key);
    }

    @Override
    public void successResponse(Response response) {
        UtilityClass.dismissProgressDialog();
        if (response.body instanceof QuickViewCategoryResponse) {
            QuickViewCategoryResponse cRes = (QuickViewCategoryResponse) response.body;
            if (cRes.getData().size() > 0) {
                for (QuickView quickView : cRes.getData()) {
                    quickView.setSelected(false);
                    mQuickList.add(quickView);
                    mAdapter.notifyItemInserted(mQuickList.size() - 1);
                }
            }
        }  else if (response.body instanceof CartAddResponse) {
            CartAddResponse toCartResponse = (CartAddResponse) response.body;
            if (toCartResponse.getSuccess() == Constants.SUCCESS_RESPONSE) {
                int count = PreferenceController.getIntPreference(PreferenceController.PreferenceKeys.PREFERENCE_CART_COUNT);
                PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_CART_COUNT, count + 1);
                qa.happytots.yameenhome.components.Toast.makeToast("Added To Cart");
            }
        } else {
            QuickViewProductByCategoryResponse res = (QuickViewProductByCategoryResponse) response.body;
            for (int i = 0; i < res.getData().size(); i++) {
                Datum datum = res.getData().get(i);
                datum.setSide(i % 2 == 0? FishListdata.LEFT_SIDE : FishListdata.RIGHT_SIDE);
                mQuickList.add(datum);
                mAdapter.notifyItemInserted(mQuickList.size() - 1);
            }
        }
    }

    @Override
    public void failureResponse(Response response) {
        UtilityClass.dismissProgressDialog();
        Toast.makeText(QuickViewActivity.this, response.errorBody, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void noInternet() {
        UtilityClass.dismissProgressDialog();
        Toast.makeText(QuickViewActivity.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
    }

    private void refresh() {
        Iterator<Bridge> items = mQuickList.iterator();
        while (items.hasNext()) {
            Bridge item = items.next();
            if (item instanceof Datum || item instanceof FishDetails) {
                items.remove();
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onQuickViewMainClick(int position, QuickView quickView) {
        updateQuickView();
        quickView.setSelected(true);
        mAdapter.notifyItemChanged(position);

        refresh();
        callQuickViewSearch(quickView);
    }

    @Override
    public void onQuickViewSubClick(int position, Datum datum) {

        if (datum.isNeedDetailedView()) {
            return;
        }

        position = collapseFishDetail(position);

        datum.setNeedDetailedView(true);

        FishDetails details = new FishDetails();
        details.setFishId(datum.getProductId());
        details.setOgPrice(String.valueOf(datum.getPrice().equals(datum.getSpecial()) ? 0 : datum.getPrice()));
        details.setOfferPrice(String.valueOf(datum.getSpecial()));
        details.setName(datum.getModel());
        details.setParentPosition(position);

        List<String> ogUrls = datum.getOriginalImages();
        if (ogUrls != null && ogUrls.size() > 0) {
            details.setLandscapeUrl(ogUrls.get(0));
        }

        List<Option> options = datum.getOptions();
        if (options != null && options.size() > 0) {
            details.setmFinishKey(options.get(0).getProductOptionId());
            details.setTypeOptions(options.get(0).getOptionValue());
        }

        int addingPosition;
        if (datum.getSide() == FishListdata.LEFT_SIDE) {
            addingPosition = position + 2;
            if (addingPosition > mQuickList.size()) {
                addingPosition = addingPosition - 1;
            }
        } else {
            addingPosition = position + 1;
        }

        mQuickList.add(addingPosition, details);
        mAdapter.notifyItemInserted(addingPosition);
        manager.scrollToPosition(addingPosition);
    }

    @Override
    public void showMessage(String message) {
        qa.happytots.yameenhome.components.Toast.makeToast(message);
    }

    @Override
    public void closeDetailedFish(int position) {
        FishDetails detail = (FishDetails) mQuickList.get(position);
        Datum data = (Datum) mQuickList.get(detail.getParentPosition());
        data.setNeedDetailedView(false);
        mQuickList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    @Override
    public void addToCart(int position, FishDetails details) {
        if (details.isAlreadyAddedToCart()) {
            qa.happytots.yameenhome.components.Toast.makeToast("Already Added to cart");
            return;
        }

        JsonObject request = new JsonObject();
        request.addProperty("product_id", String.valueOf(details.getFishId()));
        request.addProperty("quantity", details.getQuantityOption());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(String.valueOf(details.getmFinishKey()), details.getmFinishValue());
        request.add("option", jsonObject);
        UtilityClass.showProgressDialog(QuickViewActivity.this);
        details.setAlreadyAddedToCart(true);

        Datum fishListdata = (Datum) mQuickList.get(position - 1);
        fishListdata.setNeedDetailedView(false);
        mAdapter.notifyItemChanged(position - 1);

        mQuickList.remove(details);
        mAdapter.notifyItemRemoved(position);
        callApiForAddToCart(request);
    }

    private int collapseFishDetail(int position) {
        Iterator<Bridge> items = mQuickList.iterator();
        int i = 0;
        while (items.hasNext()) {
            Bridge item = items.next();
            if (item instanceof FishDetails) {
                FishDetails details = (FishDetails) item;
                Datum datum = (Datum) mQuickList.get(details.getParentPosition());
                datum.setNeedDetailedView(false);
                items.remove();
                if (datum.getSide() == FishListdata.LEFT_SIDE) {
                    mAdapter.notifyItemRemoved(details.getParentPosition() + 2);
                } else {
                    mAdapter.notifyItemRemoved(details.getParentPosition() + 1);
                }

                if (position > i) {
                    position = position - 1;
                }
            }
            i++;
        }

        return position;
    }

    private void updateQuickView() {
        for (Bridge b : mQuickList) {
            if (b instanceof QuickView) {
                ((QuickView) b).setSelected(false);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    private void callQuickViewSearch(QuickView quickView) {
        UtilityClass.showProgressDialog(QuickViewActivity.this);
        DatabaseHandler handler = new DatabaseHandler(this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForgetQuickViewByCategory(handler.getSessionValue(), String.valueOf(quickView.getCategoryId()));
    }

    private void callApiForAddToCart(JsonObject request) {
        DatabaseHandler handler = new DatabaseHandler(QuickViewActivity.this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForAddToCart(handler.getSessionValue(), request);
    }
}
