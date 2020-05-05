package qa.happytots.yameenhome.view.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import qa.happytots.yameenhome.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WelcomeFragment#getInstance(int)} factory method to
 * create an instance of this fragment.
 */
public class WelcomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private int mResourceId;

    public WelcomeFragment() {
        // Required empty public constructor
    }


    public static WelcomeFragment getInstance(int resId) {
        WelcomeFragment fragment = new WelcomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, resId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mResourceId = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        AppCompatImageView ivWelcomeImage = view.findViewById(R.id.iv_welcome);
        ivWelcomeImage.setImageResource(mResourceId);
    }
}
