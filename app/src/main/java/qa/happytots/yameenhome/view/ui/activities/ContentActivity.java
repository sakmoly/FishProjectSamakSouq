package qa.happytots.yameenhome.view.ui.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import qa.happytots.yameenhome.R;

public class ContentActivity extends AppCompatActivity {

    public static final String BUNDLE_TITLE = "title";
    public static final String BUNDLE_URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);


        Toolbar toolbar = findViewById(R.id.toolbar_web);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        final ProgressBar progressBar = findViewById(R.id.pb_loader);


        final WebView web = findViewById(R.id.web_view);

        String title = getIntent().getStringExtra(BUNDLE_TITLE);
        String url = getIntent().getStringExtra(BUNDLE_URL);

        if (!TextUtils.isEmpty(title)) {
            getSupportActionBar().setTitle(title);
        }

        web.getSettings().setLoadsImagesAutomatically(true);
        web.getSettings().setJavaScriptEnabled(true);
        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        // To enable responsive layout
        web.getSettings().setUseWideViewPort(true);

        web.getSettings().setLoadWithOverviewMode(true);

        web.getSettings().setSupportZoom(true);
        web.getSettings().setBuiltInZoomControls(true); // allow pinch to zooom
        web.getSettings().setDisplayZoomControls(false);


        web.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
                web.setVisibility(View.GONE);
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                progressBar.setVisibility(View.GONE);
                web.setVisibility(View.VISIBLE);
            }
        });

        if (!TextUtils.isEmpty(url)) {
            web.loadUrl(url);
        } else {
            super.onBackPressed();
        }

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
}
