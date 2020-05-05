package qa.happytots.yameenhome.view.base;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.Utility.UtilityClass;
import qa.happytots.yameenhome.datamodel.network.NetworkManager;
import qa.happytots.yameenhome.datamodel.network.Response;

public class BaseActivity extends AppCompatActivity implements NetworkManager.OnNetworkResponseListener {


    private static final String TAG = "BaseActivity";


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void successResponse(Response response) {

    }

    @Override
    public void failureResponse(Response response) {
        UtilityClass.dismissProgressDialog();
        Log.d(TAG, response.errorBody);
    }

    @Override
    public void noInternet() {
        UtilityClass.dismissProgressDialog();
        Toast.makeText(BaseActivity.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
    }

    public void showMessage(String message) {
        Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
