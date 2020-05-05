package qa.happytots.yameenhome.model;

public class Order {
    String fish_names,date,tv1,tv2,tv3;
    int iv1;

    public Order(String fish_names, String date, String tv1, String tv2, String tv3, int iv1) {
        this.fish_names = fish_names;
        this.date = date;
        this.tv1 = tv1;
        this.tv2 = tv2;
        this.tv3 = tv3;
        this.iv1 = iv1;
    }

    public String getFish_names() {
        return fish_names;
    }

    public void setFish_names(String fish_names) {
        this.fish_names = fish_names;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTv1() {
        return tv1;
    }

    public void setTv1(String tv1) {
        this.tv1 = tv1;
    }

    public String getTv2() {
        return tv2;
    }

    public void setTv2(String tv2) {
        this.tv2 = tv2;
    }

    public String getTv3() {
        return tv3;
    }

    public void setTv3(String tv3) {
        this.tv3 = tv3;
    }

    public int getIv1() {
        return iv1;
    }

    public void setIv1(int iv1) {
        this.iv1 = iv1;
    }
}
