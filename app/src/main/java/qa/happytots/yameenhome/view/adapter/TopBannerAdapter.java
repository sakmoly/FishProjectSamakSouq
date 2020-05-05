package qa.happytots.yameenhome.view.adapter;


import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.MainBannerData;

public class TopBannerAdapter extends PagerAdapter {

    private List<MainBannerData> IMAGES;
    private LayoutInflater inflater;


    TopBannerAdapter(Context context, List<MainBannerData> IMAGES) {
        this.IMAGES=IMAGES;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);
        final ImageView imageView = imageLayout.findViewById(R.id.image);

        Picasso.get().load(IMAGES.get(position).getImage())
                .placeholder(R.drawable.default_placeholder)
                .into(imageView);
        view.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}
