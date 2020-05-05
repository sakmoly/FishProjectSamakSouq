package qa.happytots.yameenhome.model;

import java.util.ArrayList;
import java.util.List;

import qa.happytots.yameenhome.retrofit.Retrofit_Models.MainBannerData;

public class MainBanner implements IFishListItem {

    private int image;
    private ArrayList<Integer> image_list = new ArrayList<>();
   // private List<MainBannerData> image_list;

    public MainBanner() {
    }


    public MainBanner(int image) {
        this.image = image;
    }


    public int getImage() {
        return image;
    }



    public void setImage(int image) {
        this.image = image;
    }



    public ArrayList<Integer> getImage_list() {
        return image_list;
    }


    public void setImage_list(ArrayList<Integer> image_list) {
        this.image_list = image_list;
    }
}
