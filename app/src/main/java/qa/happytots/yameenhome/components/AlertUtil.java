package qa.happytots.yameenhome.components;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import qa.happytots.yameenhome.HomeActivity;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.YameenApplication;
import qa.happytots.yameenhome.datamodel.PreferenceController;

public class AlertUtil {

    public static void makeAlert(Context context, String message, String yes, String no, final OnAlertInteractorListener listener) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_confirmation, null);
        YameenTextView tvMessage = view.findViewById(R.id.tv_confirmation_message);
        YameenTextView tvCanel = view.findViewById(R.id.tv_confirmation_cancel);
        YameenTextView tvProceed = view.findViewById(R.id.tv_confirmation_proceed);

        tvMessage.setText(message);
        tvProceed.setText(yes);
        tvCanel.setText(no);

        dialogBuilder.setView(view);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        tvProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                listener.onPositiveClick();
            }
        });

        tvCanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                listener.onNegativeClick();
            }
        });
    }

    public static void makeLanguageAlert(final Context context, final OnAlertInteractorListener listener) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.alert_language, null);
        YameenTextView tvEnglish = view.findViewById(R.id.tv_language_english);
        YameenTextView tvArabic = view.findViewById(R.id.tv_language_arabic);

        dialogBuilder.setView(view);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        tvEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_LANGUAGE, "en");
                alertDialog.dismiss();
                listener.onPositiveClick();
            }
        });

        tvArabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_LANGUAGE, "ar");
                YameenApplication application = (YameenApplication) ((HomeActivity)(context)).getApplication();
                application.setLocale();
                alertDialog.dismiss();
                listener.onNegativeClick();
            }
        });
    }

    public interface OnAlertInteractorListener {
        void onPositiveClick();
        void onNegativeClick();
    }
}
