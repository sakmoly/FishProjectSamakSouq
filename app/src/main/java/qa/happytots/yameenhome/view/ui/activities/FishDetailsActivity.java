package qa.happytots.yameenhome.view.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import qa.happytots.yameenhome.DataBase.DatabaseHandler;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.Utility.UtilityClass;
import qa.happytots.yameenhome.view.adapter.Bridge;
import qa.happytots.yameenhome.view.adapter.FishDetailsAdapter;
import qa.happytots.yameenhome.datamodel.network.NetworkManager;
import qa.happytots.yameenhome.datamodel.network.Response;
import qa.happytots.yameenhome.model.Constants;
import qa.happytots.yameenhome.model.cart.add.response.CartAddResponse;
import qa.happytots.yameenhome.model.fish.FishDetails;
import qa.happytots.yameenhome.model.quickview.product.Datum;
import qa.happytots.yameenhome.model.quickview.product.Option;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.FishListOptionvalue;
import qa.happytots.yameenhome.view.adapter.OnProductDetailInteractionListener;

public class FishDetailsActivity extends AppCompatActivity implements NetworkManager.OnNetworkResponseListener,
        OnProductDetailInteractionListener {

    public static final String BUNDLE_FISH_DETAILS = "fish_details";

    private FishDetailsAdapter mAdapter;
    private List<Bridge> mBirdge;

    private Datum mFishData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_details);

        Toolbar toolbar = findViewById(R.id.toolbar_fish_details);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        AppCompatTextView addCart = findViewById(R.id.tv_fish_details_add_to_cart);
        RecyclerView rvFishDetails = findViewById(R.id.rv_cart_details);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvFishDetails.setLayoutManager(manager);

        mFishData = getIntent().getParcelableExtra(BUNDLE_FISH_DETAILS);

        toolbar.setTitle(mFishData.getName());

        mBirdge = new ArrayList<>();

        mAdapter = new FishDetailsAdapter(mBirdge, this);
        rvFishDetails.setAdapter(mAdapter);

        detailsForList(mFishData);

        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpRequest();
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

    private void detailsForList(Datum fish) {
        FishDetails details = new FishDetails();
        details.setFishId(fish.getProductId());
        details.setOgPrice(fish.getPriceFormated());
        details.setOfferPrice(fish.getSpecialFormated());

        List<Option> options = fish.getOptions();
        if (options != null && options.size() > 0) {
            details.setmFinishKey(options.get(0).getProductOptionId());
            details.setTypeOptions(options.get(0).getOptionValue());
        }
        details.setLandscapeUrl(fish.getImage());
        mBirdge.add(0, details);
        mAdapter.notifyItemInserted(0);
    }

    private void setUpRequest() {
        FishDetails details = (FishDetails) mBirdge.get(0);

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
        UtilityClass.showProgressDialog(FishDetailsActivity.this);
        details.setAlreadyAddedToCart(true);

        callApiForAddToCart(request);
    }

    private void callApiForAddToCart(JsonObject request) {
        DatabaseHandler handler = new DatabaseHandler(FishDetailsActivity.this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForAddToCart(handler.getSessionValue(), request);
    }


    @Override
    public void successResponse(Response response) {
        UtilityClass.dismissProgressDialog();
        CartAddResponse toCartResponse = (CartAddResponse) response.body;
        if (toCartResponse.getSuccess() == Constants.SUCCESS_RESPONSE) {
            qa.happytots.yameenhome.components.Toast.makeToast("Added to Cart");
            FishDetailsActivity.this.finish();
        }
    }

    @Override
    public void failureResponse(Response response) {
        UtilityClass.dismissProgressDialog();
//        Toast.makeText(FishDetailsActivity.this, response.errorBody, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void noInternet() {
        qa.happytots.yameenhome.components.Toast.makeToast(R.string.no_internet);
    }

    @Override
    public void onTypeSelection(FishListOptionvalue optionvalue) {
        FishDetails details = (FishDetails) mBirdge.get(0);
        details.setmFinishValue(optionvalue.getProductOptionValueId());
    }
}
