package qa.happytots.yameenhome.model;

public class Fish implements IFishListItem {
    private  String fishName;
    private int fish_image,id;
    private boolean needDetailedView=false;

    public Fish() {
    }

    public Fish(int id,String fishName, int fish_image) {
        this.fishName = fishName;
        this.fish_image = fish_image;
        this.id = id;
    }


    /* public Fish(int i) {
        fishName="fish"+i;
    }*/


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isNeedDetailedView() {
        return needDetailedView;
    }

    public void setNeedDetailedView(boolean needDetailedView) {
        this.needDetailedView = needDetailedView;
    }

   /* public String getFishName() {
        return fishName;
    }*/

    public String getFishName() {
        return fishName;
    }

    public void setFishName(String fishName) {
        this.fishName = fishName;
    }

    public int getFish_image() {
        return fish_image;
    }

    public void setFish_image(int fish_image) {
        this.fish_image = fish_image;
    }
}
