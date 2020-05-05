package qa.happytots.yameenhome.datamodel;

import android.content.Context;
import android.content.SharedPreferences;

import qa.happytots.yameenhome.YameenApplication;

/**
 * Created by harishkumar
 */

public class PreferenceController {
    private static final String PREFERENCE_NAME = "pref_yameen";

    public interface PreferenceKeys {
        String PREFERENCE_CART_COUNT = "cart_count";
        String PREFERENCE_IS_WELCOME_VIEWED = "is_welcome_viewed";
        String PREFERENCE_LOGGED_STATUS = "LOGGED_STATUS";
        String PREFERENCE_CUSTOMER_ID = "LOGGED_CUSTOMER_ID";
        String PREFERENCE_LOGGED_GROUP_ID = "LOGGED_GROUP_ID";
        String PREFERENCE_LOGGED_STORE_ID = "LOGGED_STORE_ID";
        String PREFERENCE_LOGGED_LANGUAGE_ID = "LOGGED_LANGUAGE_ID";
        String PREFERENCE_LOGGED_FIRST_NAME = "LOGGED_FIRST_NAME";
        String PREFERENCE_LOGGED_LAST_NAME = "LOGGED_LAST_NAME";
        String PREFERENCE_LOGGED_EMAIL = "LOGGED_EMAIL";
        String PREFERENCE_LOGGED_TELEPHONE = "LOGGED_TELEPHONE";
        String PREFERENCE_LOGGED_ADDED_DATE = "LOGGED_ADDED_DATE";
        String PREFERENCE_SELECTED_DELIVERY_DATE = "DELIVERY_DATE";
        String PREFERENCE_SELECTED_DELIVERY_SLOT = "DELIVERY_SLOT";
        String PREFERENCE_SELECTED_PAYMENT_METHOD = "PAYMENT_METHOD";
        String PREFERENCE_SELECTED_IS_FREE_DELIVERY = "IS_FREE_DELIVERY";
        String PREFERENCE_USER_COUNTRY_ID = "USER_COUNTRY_ID";
        String PREFERENCE_LOCATION_ID = "USER_LOCATION_ID";
        String PREFERENCE_LANGUAGE = "language";
    }

    public static void setPreference(String key, String value) {
        SharedPreferences preferences = YameenApplication.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setPreference(String key, int value) {
        SharedPreferences preferences = YameenApplication.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void setPreference(String key, float value) {
        SharedPreferences preferences = YameenApplication.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public static void setPreference(String key, boolean value) {
        SharedPreferences preferences = YameenApplication.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void setPreference(String key, long value) {
        SharedPreferences preferences = YameenApplication.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static String getStringPreference(String key) {
        SharedPreferences preferences = YameenApplication.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static int getIntPreference(String key) {
        SharedPreferences preferences = YameenApplication.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(key, -1);
    }

    public static float getFloatPreference(String key) {
        SharedPreferences preferences = YameenApplication.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return preferences.getFloat(key, -1f);
    }

    public static boolean getBooleanPreference(String key) {
        SharedPreferences preferences = YameenApplication.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public static long getLongPreference(String key) {
        SharedPreferences preferences = YameenApplication.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return preferences.getLong(key, -1);
    }

    public static void clearData() {
        SharedPreferences preferences = YameenApplication.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        preferences.edit().clear().commit();
    }

    public static boolean isUsrLoggedIn() {
        return PreferenceController.getBooleanPreference(PreferenceController.PreferenceKeys.PREFERENCE_LOGGED_STATUS);
    }
}
