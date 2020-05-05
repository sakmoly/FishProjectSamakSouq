package qa.happytots.yameenhome.view.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import qa.happytots.yameenhome.BuildConfig;
import qa.happytots.yameenhome.DataBase.DatabaseHandler;
import qa.happytots.yameenhome.HomeActivity;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.Utility.UtilityClass;
import qa.happytots.yameenhome.components.AlertUtil;
import qa.happytots.yameenhome.components.YameenTextView;
import qa.happytots.yameenhome.datamodel.PreferenceController;
import qa.happytots.yameenhome.datamodel.network.NetworkManager;
import qa.happytots.yameenhome.datamodel.network.Response;
import qa.happytots.yameenhome.model.Constants;
import qa.happytots.yameenhome.model.user.Data;
import qa.happytots.yameenhome.model.user.UserResponse;
import qa.happytots.yameenhome.view.ui.activities.ContentActivity;
import qa.happytots.yameenhome.view.ui.activities.DeliveryAddressActivity;
import qa.happytots.yameenhome.view.ui.activities.Login;
import qa.happytots.yameenhome.view.ui.activities.MyAccount;
import qa.happytots.yameenhome.view.ui.activities.MyOrderActivity;
import qa.happytots.yameenhome.view.ui.activities.ServiceAvailableArea;
import qa.happytots.yameenhome.view.ui.activities.Support;

public class MeFragment extends Fragment implements NetworkManager.OnNetworkResponseListener {

    private static final int REQUEST_CODE = 10001;
    private static final int REQUEST_CODE_FOR_LOGIN = 10002;

    private static final int API_ACCOUNT = 0;
    private static final int API_LOGOUT = 1;
    private static final int API_CART_COUNT = 2;


    TextView User_name, user_email, tv3, tv4, tv6, tv7;
    TextView tvRefer;
    TextView tvMobileNo;
    AppCompatImageView ivEdit;
    private YameenTextView tvAddress;
    private TextView tvLogout;

    private boolean isUserLoggedIn = false;
    private Data mUserData;

    private int mCurrentAPI;

    public MeFragment() {

    }

    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_me, container, false);

        ivEdit = view.findViewById(R.id.edit_iv);
        User_name = view.findViewById(R.id.tv1);
        user_email = view.findViewById(R.id.tv2);
        tv3 = view.findViewById(R.id.tv3);
        tv4 = view.findViewById(R.id.tv4);
        tv6 = view.findViewById(R.id.tv6);
        tv7 = view.findViewById(R.id.tv7);
        tvRefer = view.findViewById(R.id.tv8);
        tvMobileNo = view.findViewById(R.id.tv_mobile_no);
        tvLogout = view.findViewById(R.id.tv_logout);
        tvAddress = view.findViewById(R.id.tv_address);

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyAccount.class);
                intent.putExtra(MyAccount.BUNDLE_ACCOUNT_DETAILS, mUserData);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ServiceAvailableArea.class);
                startActivity(intent);
            }
        });

        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyOrderActivity.class);
                startActivity(intent);
            }
        });

        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Support.class);
                intent.putExtra(Support.BUNDLE_USER_LOGIN_STATUS, isUserLoggedIn);
                intent.putExtra(Support.BUNDLE_USER_DETAILS, mUserData);
                startActivity(intent);
            }
        });

        tv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), ContentActivity.class);
//                intent.putExtra(ContentActivity.BUNDLE_URL, "https://yameen.ae/About-us.html");
//                intent.putExtra(ContentActivity.BUNDLE_TITLE, "About Us");
//                startActivity(intent);
            }
        });

        user_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isUserLoggedIn) {
                    Intent intent = new Intent(getContext(), Login.class);
                    startActivityForResult(intent, REQUEST_CODE_FOR_LOGIN);
                }
            }
        });

        tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DeliveryAddressActivity.class);
                startActivity(intent);
            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutAlert();
            }
        });

        tvRefer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Yameen");
                    String shareMessage= "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkUserLogged();
    }

    private void checkUserLogged() {
        mCurrentAPI = API_ACCOUNT;
        DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForCheckingUserLogged(databaseHandler.getSessionValue());
        UtilityClass.showProgressDialog(getContext());
    }

    private void logout() {
        mCurrentAPI = API_LOGOUT;
        DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForLogout(databaseHandler.getSessionValue());
        UtilityClass.showProgressDialog(getContext());
    }

    @Override
    public void successResponse(Response response) {
        UtilityClass.dismissProgressDialog();
        if (mCurrentAPI == API_ACCOUNT) {
            UserResponse dataRes = (UserResponse) response.body;
            if (dataRes.getSuccess() == Constants.SUCCESS_RESPONSE) {
                Data user = dataRes.getData();
                mUserData = user;
                showUserData();
            } else {
                isUserLoggedIn = false;
                logoutUI();
            }
            checkCartCount();
        } else {
            if (response.code == 200) {
                isUserLoggedIn = false;
                qa.happytots.yameenhome.components.Toast.makeToast("Successful Logout");
                logoutUI();
            }
        }
    }

    @Override
    public void failureResponse(Response response) {
        UtilityClass.dismissProgressDialog();
        isUserLoggedIn = false;
        if (mCurrentAPI == API_ACCOUNT) {
            logoutUI();
        } else {
            qa.happytots.yameenhome.components.Toast.makeToast("Successful Logout");
            logoutUI();
        }
    }

    @Override
    public void noInternet() {
        UtilityClass.dismissProgressDialog();
        qa.happytots.yameenhome.components.Toast.makeToast(R.string.no_internet);
    }

    private void checkCartCount() {
        ((HomeActivity)getActivity()).callForCart();
    }

    private void logoutUI() {
        user_email.setText(R.string.login_or_signup);
        User_name.setVisibility(View.INVISIBLE);
        tvMobileNo.setVisibility(View.INVISIBLE);
        ivEdit.setVisibility(View.GONE);
        tvLogout.setVisibility(View.GONE);
        mUserData = null;

        PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_LOGGED_STATUS, false);
        PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_CART_COUNT, 0);
        ((HomeActivity) getActivity()).updateCartCount();
    }

    private void showUserData() {
        isUserLoggedIn = true;
        String username = mUserData.getFirstname() + " " + mUserData.getLastname();
        User_name.setText(username);
        user_email.setText(mUserData.getEmail());
        tvMobileNo.setText(mUserData.getTelephone());
        User_name.setVisibility(View.VISIBLE);
        ivEdit.setVisibility(View.VISIBLE);
        user_email.setVisibility(View.VISIBLE);
        tvMobileNo.setVisibility(View.VISIBLE);
        tvLogout.setVisibility(View.VISIBLE);
    }

    private void showLogoutAlert() {
        AlertUtil.makeAlert(getContext(), "Are you sure you want to logout?",
                "Logout",
                "Cancel",
                new AlertUtil.OnAlertInteractorListener() {
                    @Override
                    public void onPositiveClick() {
                        logoutOperation();
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                mUserData = data.getParcelableExtra(MyAccount.BUNDLE_ACCOUNT_DETAILS);
                showUserData();
            }
        }
        if (requestCode == REQUEST_CODE_FOR_LOGIN) {
            if (resultCode == Activity.RESULT_OK) {
                checkUserLogged();
            }
        }
    }

    private void logoutOperation() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.GOOGLE_WEB_CLIENT_ID)
                .requestEmail()
                .requestProfile()
                .build();

        LoginManager.getInstance().logOut();


        GoogleSignInClient mSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        mSignInClient.signOut();

        logout();
    }
}