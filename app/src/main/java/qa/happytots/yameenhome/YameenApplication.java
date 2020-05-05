package qa.happytots.yameenhome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.telr.mobile.sdk.TelrApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import qa.happytots.yameenhome.datamodel.PreferenceController;

public class YameenApplication extends TelrApplication {

    private static Context sContext;

    public static String USER_LANGUAGE;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        setLanguage();
    }

    public static Context getContext() {
        return sContext;
    }

    public static boolean isTimeSlotTodayMorning() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour > 0 && hour < 12;
    }

    public static boolean isTimeSlotTodayEvening() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour >= 12 && hour < 24;
    }

    public static String getSlot(int position) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("EEE-dd-MMMM-yyyy", Locale.getDefault());

        c.setTime(new Date());
        if (position == 1) {

        } else if (position == 2) {
            c.add(Calendar.DATE, 1);
        } else if (position == 3) {
            c.add(Calendar.DATE, 2);
        } else if (position == 4) {
            c.add(Calendar.DATE, 3);
        } else if (position == 5) {
            c.add(Calendar.DATE, 4);
        }

        return format.format(c.getTime());
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void setLocale() {
        String language = PreferenceController.getStringPreference(PreferenceController.PreferenceKeys.PREFERENCE_LANGUAGE);
        if (language.equals("")) {
            language = "en";
        }
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        final Resources resources = getResources();
        final Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        setLanguage();
    }

    private void setLanguage() {
        USER_LANGUAGE = PreferenceController.getStringPreference(PreferenceController.PreferenceKeys.PREFERENCE_LANGUAGE);
        if (USER_LANGUAGE.equals("") || USER_LANGUAGE.equals("en")) {
            USER_LANGUAGE = "en-gb";
        }
    }
}
