package qa.happytots.yameenhome.view.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.viewpagerindicator.CirclePageIndicator;

import qa.happytots.yameenhome.HomeActivity;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.components.YameenTextView;
import qa.happytots.yameenhome.datamodel.PreferenceController;
import qa.happytots.yameenhome.view.ui.fragment.WelcomeFragment;


public class WelcomeActivity extends AppCompatActivity {

    private ViewPager vpWelcome;
    private CirclePageIndicator cplWelcome;
    private AppCompatImageView ivPrevious;
    private AppCompatImageView ivNext;
    private YameenTextView tvDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.TRANSPARENT);

        vpWelcome = findViewById(R.id.vp_welcome);
        cplWelcome = findViewById(R.id.cpi_welcome_indicator);
        ivPrevious = findViewById(R.id.iv_welcome_previous);
        ivNext = findViewById(R.id.iv_welcome_next);
        tvDone = findViewById(R.id.tv_welcome_done);


        WelcomePagerAdapter adapter = new WelcomePagerAdapter(getSupportFragmentManager());
        vpWelcome.setAdapter(adapter);
        cplWelcome.setViewPager(vpWelcome);

        vpWelcome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                updateBothViews(i);
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = vpWelcome.getCurrentItem();
                if (currentPosition < 2) {
                    vpWelcome.setCurrentItem(currentPosition + 1);
                }
            }
        });

        ivPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = vpWelcome.getCurrentItem();
                if (currentPosition > 0) {
                    vpWelcome.setCurrentItem(currentPosition - 1);
                }
            }
        });

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_IS_WELCOME_VIEWED, true);
                goToHome();
            }
        });
    }

    private void updateBothViews(int position) {
        if (position == 0) {
            ivPrevious.setVisibility(View.INVISIBLE);
            ivNext.setVisibility(View.VISIBLE);
            tvDone.setVisibility(View.GONE);
        } else  if (position == 1) {
            ivPrevious.setVisibility(View.VISIBLE);
            ivNext.setVisibility(View.VISIBLE);
            tvDone.setVisibility(View.GONE);
        } else {
            ivPrevious.setVisibility(View.VISIBLE);
            tvDone.setVisibility(View.VISIBLE);
            ivNext.setVisibility(View.GONE);
        }
    }

    private void goToHome() {
        Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    class WelcomePagerAdapter extends FragmentPagerAdapter {

        public WelcomePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if (i == 0) {
                return WelcomeFragment.getInstance(R.drawable.yameen_welcome_1);
            } else if (i == 1) {
                return WelcomeFragment.getInstance(R.drawable.yameen_welcome_2);
            } else {
                return WelcomeFragment.getInstance(R.drawable.yameen_welcome_3);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

    }
}
