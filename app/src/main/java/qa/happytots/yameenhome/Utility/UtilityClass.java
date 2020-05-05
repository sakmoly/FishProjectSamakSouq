package qa.happytots.yameenhome.Utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.widget.Toast;

import qa.happytots.yameenhome.R;

public class UtilityClass {

    private static ProgressDialog progressDialog;

    public static Typeface setExtraBoldFont(Context context) {

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/opensans_extrabold.ttf");
        return typeface;
    }

    public static Typeface setRegularFont(Context context) {

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/opensans_regular.ttf");
        return typeface;
    }

    public static Typeface setBoldFont(Context context) {

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/opensans_bold.ttf");
        return typeface;
    }


    public static String setPrefs(String key, String value, Context context) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
        return key;
    }


    public static String getPrefs(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public static Toast ToastCenter(Context context, String msg) {

        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);

        return toast;
    }

    public static void ClearPrefs(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();

        editor.clear();
    }


    public static void showProgressDialog(Context context) {
        progressDialog = new ProgressDialog(context, R.style.MyTheme);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();
    }

    public static void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


}
