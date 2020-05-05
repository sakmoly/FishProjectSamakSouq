package qa.happytots.yameenhome.model;

import java.util.ArrayList;

public class SmallBanner implements IFishListItem {

    private ArrayList<Integer> image_list;

    public SmallBanner(ArrayList<Integer> image_list) {
        this.image_list = image_list;
    }

    public ArrayList<Integer> getImage_list() {
        return image_list;
    }

    public void setImage_list(ArrayList<Integer> image_list) {
        this.image_list = image_list;
    }
}
