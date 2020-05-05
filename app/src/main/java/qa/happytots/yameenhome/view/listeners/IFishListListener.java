package qa.happytots.yameenhome.view.listeners;

import android.view.View;

import qa.happytots.yameenhome.model.fish.FishDetails;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.FishListdata;

public interface IFishListListener {
    void search(View view);
    void onExpand(int position, FishListdata id);
    void closeDetailedFish(int position);
    void addToCart(int position, FishDetails details);
    void priceFilter(int min, int max);
    void finishFilter(int categoryId);
    void showMessage(String message);
    void filter(int position);
    void offerFilter(int position);
    void filterClose(int position);
    void onQuantityClick(int quantity);
}
