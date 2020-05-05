package qa.happytots.yameenhome.view.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.model.user.Data;

public class Support extends AppCompatActivity {

    public static final String BUNDLE_USER_LOGIN_STATUS = "user_login_status";
    public static final String BUNDLE_USER_DETAILS = "user_details";

    private TextView tvComplaint;
    private TextView tvFAQ;
    private TextView tvPolicy;
    private TextView tvContact;

    private boolean isUserLoggedin;
    private Data mUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        Toolbar toolbar = findViewById(R.id.toolbar_support);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }


        tvComplaint = findViewById(R.id.tv1);
        tvFAQ =  findViewById(R.id.tv2);
        tvPolicy = findViewById(R.id.tv3);
        tvContact = findViewById(R.id.tv4);
        TextView tvTerms = findViewById(R.id.tv5);

        isUserLoggedin = getIntent().getBooleanExtra(BUNDLE_USER_LOGIN_STATUS, false);
        mUserData = getIntent().getParcelableExtra(BUNDLE_USER_DETAILS);


        tvComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Support.this, ComplaintActivity.class);
                intent.putExtra(BUNDLE_USER_DETAILS, mUserData);
                startActivity(intent);
            }
        });

        tvFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Support.this, ContentActivity.class);
//                intent.putExtra(ContentActivity.BUNDLE_URL, "https://yameen.ae/FAQ.html");
//                intent.putExtra(ContentActivity.BUNDLE_TITLE, "FAQ");
//                startActivity(intent);
            }
        });

        tvPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Support.this, ContentActivity.class);
//                intent.putExtra(ContentActivity.BUNDLE_URL, "https://yameen.ae/Privacy-Policy.html");
//                intent.putExtra(ContentActivity.BUNDLE_TITLE, "Policy");
//                startActivity(intent);
            }
        });

        tvTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Support.this, ContentActivity.class);
//                intent.putExtra(ContentActivity.BUNDLE_URL, "https://yameen.ae/Terms-conditions.html");
//                intent.putExtra(ContentActivity.BUNDLE_TITLE, "Terms and Conditions");
//                startActivity(intent);
            }
        });

        tvContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Support.this, ContentActivity.class);
//                intent.putExtra(ContentActivity.BUNDLE_URL, "https://yameen.ae/index.php?route=information/contact");
//                intent.putExtra(ContentActivity.BUNDLE_TITLE, "Contact Us");
//                startActivity(intent);
            }
        });

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

    private void showUserLoginAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Support.this);
        builder.setMessage("User is not logged in. Do you want to login");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Support.this, Login.class);
                startActivity(intent);
            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
