package qa.happytots.yameenhome.view.ui.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.datamodel.PreferenceController;

public class Me extends AppCompatActivity {
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv6 = (TextView) findViewById(R.id.tv6);
        tv7 = (TextView) findViewById(R.id.tv7);
        tv8 = (TextView) findViewById(R.id.tv_version);



        //showVersion();
        Typeface font= Typeface.createFromAsset(getAssets(),"fonts/opensans_regular.ttf");
        tv2.setTypeface(font);

        Typeface font1= Typeface.createFromAsset(getAssets(),"fonts/opensans_extrabold.ttf");
        tv1.setTypeface(font1);
        tv3.setTypeface(font);
        tv4.setTypeface(font);
        tv5.setTypeface(font);
        tv6.setTypeface(font);
        tv7.setTypeface(font);

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ServiceAvailableArea.class);
                startActivity(intent);
            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PreferenceController.isUsrLoggedIn()){
                    Intent intent=new Intent(getApplicationContext(),MyOrderActivity.class);
                    startActivity(intent);
                }

            }
        });
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MyAccount.class);
                startActivity(intent);
            }
        });
        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Support.class);
                startActivity(intent);
            }
        });

        tv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Me.this, ContentActivity.class);
                intent.putExtra(ContentActivity.BUNDLE_URL, "http://yameen.ae/dev/about_us");
                intent.putExtra(ContentActivity.BUNDLE_TITLE, "About Us");
                startActivity(intent);
            }
        });

    }
    public void showVersion()
    {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            tv8.setText("Version "+pInfo.versionName);
            Log.w("Version", pInfo.versionName +"");
        } catch(PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.w("VersionError", e.toString()+"");
            //qa.happytots.yameenhome.components.Toast.makeToast(e.toString());
        }
    }
}
