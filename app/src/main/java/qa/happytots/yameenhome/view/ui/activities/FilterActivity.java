package qa.happytots.yameenhome.view.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edmodo.rangebar.RangeBar;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.model.Search;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String FILTER = "filter";

    private LinearLayout containerShellFish;
    private LinearLayout containerLargeFish;
    private LinearLayout containerMedium;
    private LinearLayout containerSmallFish;
    private TextView tvDone;
    private RangeBar rangeBar;
    private AppCompatRadioButton rbFinish;
    private AppCompatRadioButton rbPrice;
    private TextView smallSelected;
    private TextView mediumSelected;
    private TextView largeSelected;
    private TextView shelSelected;
    private AppCompatImageView ivClose;

    private Search search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_filter);

        tvDone = findViewById(R.id.filter_done_button);
        rbFinish = findViewById(R.id.rb_filter_size);
        rbPrice = findViewById(R.id.rb_filter_price);
        smallSelected = findViewById(R.id.small_selected);
        mediumSelected = findViewById(R.id.medium_selected);
        largeSelected = findViewById(R.id.large_selected);
        shelSelected = findViewById(R.id.shell_selected);

        containerShellFish = findViewById(R.id.container_shell_fish);
        containerLargeFish = findViewById(R.id.container_large_fish);
        containerMedium = findViewById(R.id.container_medium_fish);
        containerSmallFish = findViewById(R.id.container_small_fish);

        rangeBar = findViewById(R.id.rangebar);
        rangeBar.setBarColor(rangeBar.getContext().getResources().getColor(R.color.graylightnew));
        rangeBar.setConnectingLineColor(rangeBar.getContext().getResources().getColor(R.color.red));
        rangeBar.setThumbImageNormal(R.drawable.rtrtr);
        rangeBar.setBarWeight(5);
        rangeBar.setTickCount(100);
        rangeBar.setTickHeight(0);
        rangeBar.setConnectingLineWeight(3);

        search = new Search();

        defaultSelected();

        tvDone.setOnClickListener(this);
        containerSmallFish.setOnClickListener(this);
        containerMedium.setOnClickListener(this);
        containerLargeFish.setOnClickListener(this);
        containerShellFish.setOnClickListener(this);

        rbFinish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rbPrice.setChecked(false);
                    search.setSize(true);
                }
            }
        });

        rbPrice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    search.setSize(false);
                    rbFinish.setChecked(false);
                }
            }
        });

        onRangeChange();
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

    private void defaultSelected() {
        smallSelected.setBackground(ContextCompat.getDrawable(this, R.drawable.filter_selection_shape_grey));
        mediumSelected.setBackground(ContextCompat.getDrawable(this, R.drawable.filter_selection_shape_grey));
        largeSelected.setBackground(ContextCompat.getDrawable(this, R.drawable.filter_selection_shape_grey));
        shelSelected.setBackground(ContextCompat.getDrawable(this, R.drawable.filter_selection_shape_grey));
        containerSmallFish.setClickable(false);
        containerMedium.setClickable(false);
        containerLargeFish.setClickable(false);
        containerShellFish.setClickable(false);
    }

    private void onSmallSelected() {
        rbFinish.setChecked(true);
        rbPrice.setChecked(false);
        search.setSize(true);
        smallSelected.setBackground(ContextCompat.getDrawable(this, R.drawable.filter_selection_shape_red));
        mediumSelected.setBackground(ContextCompat.getDrawable(this, R.drawable.filter_selection_shape_grey));
        largeSelected.setBackground(ContextCompat.getDrawable(this, R.drawable.filter_selection_shape_grey));
        shelSelected.setBackground(ContextCompat.getDrawable(this, R.drawable.filter_selection_shape_grey));
    }

    private void onMediumSelected() {
        rbFinish.setChecked(true);
        rbPrice.setChecked(false);
        search.setSize(true);
        smallSelected.setBackground(ContextCompat.getDrawable(this, R.drawable.filter_selection_shape_grey));
        mediumSelected.setBackground(ContextCompat.getDrawable(this, R.drawable.filter_selection_shape_red));
        largeSelected.setBackground(ContextCompat.getDrawable(this, R.drawable.filter_selection_shape_grey));
        shelSelected.setBackground(ContextCompat.getDrawable(this, R.drawable.filter_selection_shape_grey));
    }

    private void onLargeSelected() {
        rbFinish.setChecked(true);
        rbPrice.setChecked(false);
        search.setSize(true);
        smallSelected.setBackground(ContextCompat.getDrawable(this, R.drawable.filter_selection_shape_grey));
        mediumSelected.setBackground(ContextCompat.getDrawable(this, R.drawable.filter_selection_shape_grey));
        largeSelected.setBackground(ContextCompat.getDrawable(this, R.drawable.filter_selection_shape_red));
        shelSelected.setBackground(ContextCompat.getDrawable(this, R.drawable.filter_selection_shape_grey));
    }

    private void onShellSelected() {
        rbFinish.setChecked(true);
        rbPrice.setChecked(false);
        search.setSize(true);
        smallSelected.setBackground(ContextCompat.getDrawable(this, R.drawable.filter_selection_shape_grey));
        mediumSelected.setBackground(ContextCompat.getDrawable(this, R.drawable.filter_selection_shape_grey));
        largeSelected.setBackground(ContextCompat.getDrawable(this, R.drawable.filter_selection_shape_grey));
        shelSelected.setBackground(ContextCompat.getDrawable(this, R.drawable.filter_selection_shape_red));
    }

    private void onRangeChange() {
        rangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int i, int i1) {
                search.setMin(i);
                search.setMax(i1);
                rbFinish.setChecked(false);
                rbPrice.setChecked(true);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == containerSmallFish) {
            onSmallSelected();
        } else if (v == containerMedium) {
            onMediumSelected();
        } else if (v == containerLargeFish) {
            onLargeSelected();
        } else if (v == containerShellFish) {
            onShellSelected();
        } else if (v == tvDone) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putParcelable(FILTER, search);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
