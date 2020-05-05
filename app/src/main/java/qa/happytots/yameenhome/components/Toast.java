package qa.happytots.yameenhome.components;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.YameenApplication;

public class Toast {

    public static void makeToast(String message) {
        android.widget.Toast toast = new android.widget.Toast(YameenApplication.getContext());
        LayoutInflater inflater = LayoutInflater.from(YameenApplication.getContext());
        View view =  inflater.inflate(R.layout.layout_toast, null);
        YameenTextView tvMessage = view.findViewById(R.id.tv_toast_message);
        tvMessage.setText(message);

        toast.setDuration(android.widget.Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0,40);
        toast.setView(view);
        toast.show();
    }

    public static void makeToast(int message) {
        android.widget.Toast toast = new android.widget.Toast(YameenApplication.getContext());
        LayoutInflater inflater = LayoutInflater.from(YameenApplication.getContext());
        View view =  inflater.inflate(R.layout.layout_toast, null);
        YameenTextView tvMessage = view.findViewById(R.id.tv_toast_message);
        tvMessage.setText(message);

        toast.setDuration(android.widget.Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0,40);
        toast.setView(view);
        toast.show();
    }
}
