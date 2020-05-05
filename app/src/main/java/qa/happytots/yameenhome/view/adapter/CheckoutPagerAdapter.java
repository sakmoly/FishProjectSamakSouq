package qa.happytots.yameenhome.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import qa.happytots.yameenhome.view.ui.fragment.AddressFragment;
import qa.happytots.yameenhome.view.ui.fragment.OrderFragment;
import qa.happytots.yameenhome.view.ui.fragment.SlotFragment;

public class CheckoutPagerAdapter extends FragmentPagerAdapter {

    public CheckoutPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return AddressFragment.getInstance();
        } else if (position == 1) {
            return SlotFragment.getInstance();
        } else if (position == 2) {
            return OrderFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
