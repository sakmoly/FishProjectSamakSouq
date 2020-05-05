package qa.happytots.yameenhome.datamodel.network;


import android.util.Log;

import com.google.gson.JsonObject;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import qa.happytots.yameenhome.YameenApplication;
import qa.happytots.yameenhome.model.CommonResponse;
import qa.happytots.yameenhome.model.CommonResponseWithData;
import qa.happytots.yameenhome.model.SocialLoginRequest;
import qa.happytots.yameenhome.model.account.AccountRequest;
import qa.happytots.yameenhome.model.cart.CartUpdateRequest;
import qa.happytots.yameenhome.model.cart.CartUpdateResponse;
import qa.happytots.yameenhome.model.cart.add.response.CartAddResponse;
import qa.happytots.yameenhome.model.contact.ContactRequest;
import qa.happytots.yameenhome.model.country.CountryResponse;
import qa.happytots.yameenhome.model.country.zone.ZoneResponse;
import qa.happytots.yameenhome.model.coupon.CouponRequest;
import qa.happytots.yameenhome.model.coupon.CouponResponse;
import qa.happytots.yameenhome.model.fetchaddress.Address;
import qa.happytots.yameenhome.model.fetchaddress.DeliveryAddressResponse;
import qa.happytots.yameenhome.model.location.LocationResponse;
import qa.happytots.yameenhome.model.order.detail.OrderDetailResponse;
import qa.happytots.yameenhome.model.order.response.OrderResponse;
import qa.happytots.yameenhome.model.order_overview.OrderOverviewResponse;
import qa.happytots.yameenhome.model.payment.methods.request.PaymentExistingAddressRequest;
import qa.happytots.yameenhome.model.payment.methods.request.PaymentMethodRequest;
import qa.happytots.yameenhome.model.payment.methods.response.PaymentMethodResponse;
import qa.happytots.yameenhome.model.quickview.category.QuickViewCategoryResponse;
import qa.happytots.yameenhome.model.quickview.product.QuickViewProductByCategoryResponse;
import qa.happytots.yameenhome.model.register.request.RegisterRequest;
import qa.happytots.yameenhome.model.register.response.RegisterResponse;
import qa.happytots.yameenhome.model.shipping.add.ShippingMethodRequest;
import qa.happytots.yameenhome.model.shipping.fetch.response.ShippingMethodResponse;
import qa.happytots.yameenhome.model.user.UserResponse;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.Cart_Response;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.FishList_Response;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.LoginRawClass;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.LoginResponse;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.MainBannerResponse;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.Session_TokenResponse;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.SmallBannerResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static qa.happytots.yameenhome.datamodel.network.APIPrototype.HEADER_MERCHANT_ID;

/**
 * Created by El nino yadhu on 15-08-2018.
 */

public class NetworkManager {


    public interface OnNetworkResponseListener {
        void successResponse(Response response);

        void failureResponse(Response response);

        void noInternet();
    }

    private static NetworkManager sManager;
    private static APIPrototype mAPIInstance;
    private OnNetworkResponseListener mListener;

    private NetworkManager() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIPrototype.API_BASE_URL)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        mAPIInstance = retrofit.create(APIPrototype.class);

    }

    public static NetworkManager getInstance() {
        if (sManager == null) {
            sManager = new NetworkManager();
        }
        return sManager;
    }

    public void setNetworkListener(OnNetworkResponseListener mListener) {
        this.mListener = mListener;
    }

    public void removeNetworkListener() {
        this.mListener = null;
    }

    private Map<String, String> formHeader(String sessionid) {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Oc-Merchant-Id", HEADER_MERCHANT_ID);
        if (!sessionid.equals("")) {
            headers.put("X-Oc-Session", sessionid);
        }
        headers.put("X-Oc-Merchant-Language", YameenApplication.USER_LANGUAGE);
        return headers;
    }


    public void callAPIForSession() {
        Call<Session_TokenResponse> tokenResponseCall = mAPIInstance.getSession(APIPrototype.HEADER_MERCHANT_ID);
        tokenResponseCall.enqueue(new Callback<Session_TokenResponse>() {
            @Override
            public void onResponse(Call<Session_TokenResponse> call, retrofit2.Response<Session_TokenResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<Session_TokenResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForLogin(String sessionID, LoginRawClass request) {
        Call<LoginResponse> responseCall = mAPIInstance.loginPost(formHeader(sessionID), request);
        responseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForSocialLogin(String sessionID, SocialLoginRequest request) {
        Call<LoginResponse> responseCall = mAPIInstance.socialLogin(formHeader(sessionID), request);
        responseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForAddress(String sessionID) {
        Call<DeliveryAddressResponse> requestCall = mAPIInstance.getAddress(formHeader(sessionID));
        requestCall.enqueue(new Callback<DeliveryAddressResponse>() {
            @Override
            public void onResponse(Call<DeliveryAddressResponse> call, retrofit2.Response<DeliveryAddressResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<DeliveryAddressResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForAddingAddress(String sessionID, Address address) {
        Call<qa.happytots.yameenhome.retrofit.Retrofit_Models.DeliveryAddressResponse> requestCall = mAPIInstance.addAddress(formHeader(sessionID), address);
        requestCall.enqueue(new Callback<qa.happytots.yameenhome.retrofit.Retrofit_Models.DeliveryAddressResponse>() {
            @Override
            public void onResponse(Call<qa.happytots.yameenhome.retrofit.Retrofit_Models.DeliveryAddressResponse> call, retrofit2.Response<qa.happytots.yameenhome.retrofit.Retrofit_Models.DeliveryAddressResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<qa.happytots.yameenhome.retrofit.Retrofit_Models.DeliveryAddressResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForEditingAddress(String sessionID, Address address) {
        Call<qa.happytots.yameenhome.retrofit.Retrofit_Models.DeliveryAddressResponse> requestCall = mAPIInstance.editAddress(formHeader(sessionID), address.getAddressId(), address);
        requestCall.enqueue(new Callback<qa.happytots.yameenhome.retrofit.Retrofit_Models.DeliveryAddressResponse>() {
            @Override
            public void onResponse(Call<qa.happytots.yameenhome.retrofit.Retrofit_Models.DeliveryAddressResponse> call, retrofit2.Response<qa.happytots.yameenhome.retrofit.Retrofit_Models.DeliveryAddressResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<qa.happytots.yameenhome.retrofit.Retrofit_Models.DeliveryAddressResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForgetQuickView(String sessionID) {
        Call<QuickViewCategoryResponse> requestCall = mAPIInstance.getQuickViewList(formHeader(sessionID));
        requestCall.enqueue(new Callback<QuickViewCategoryResponse>() {
            @Override
            public void onResponse(Call<QuickViewCategoryResponse> call, retrofit2.Response<QuickViewCategoryResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<QuickViewCategoryResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForgetQuickViewByCategory(String sessionID, String id) {
        Call<QuickViewProductByCategoryResponse> requestCall = mAPIInstance.getProductByCategory(formHeader(sessionID), id);
        requestCall.enqueue(new Callback<QuickViewProductByCategoryResponse>() {
            @Override
            public void onResponse(Call<QuickViewProductByCategoryResponse> call, retrofit2.Response<QuickViewProductByCategoryResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<QuickViewProductByCategoryResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void getProductByCategoryAtHome(String sessionID, String id) {
        Call<FishList_Response> requestCall = mAPIInstance.getProductByCategoryAtHome(formHeader(sessionID), id);
        requestCall.enqueue(new Callback<FishList_Response>() {
            @Override
            public void onResponse(Call<FishList_Response> call, retrofit2.Response<FishList_Response> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<FishList_Response> call, Throwable t) {
                failureResponse(t);
            }
        });
    }


    public void callAPIForPriceRangeFilter(String sessionID, String priceRange) {
        Call<FishList_Response> requestCall = mAPIInstance.getPriceRangeFilterResponse(formHeader(sessionID), priceRange);
        requestCall.enqueue(new Callback<FishList_Response>() {
            @Override
            public void onResponse(Call<FishList_Response> call, retrofit2.Response<FishList_Response> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<FishList_Response> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForSizeFilter(String sessionID, String id) {
        Call<FishList_Response> requestCall = mAPIInstance.getFishProductByCategory(formHeader(sessionID), id);
        requestCall.enqueue(new Callback<FishList_Response>() {
            @Override
            public void onResponse(Call<FishList_Response> call, retrofit2.Response<FishList_Response> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<FishList_Response> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForUserRegister(String sessionID, RegisterRequest request) {
        Call<RegisterResponse> requestCall = mAPIInstance.registerUser(formHeader(sessionID), request);
        requestCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, retrofit2.Response<RegisterResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForFistList(String zoneId) {
        Call<FishList_Response> responseCall = mAPIInstance.getFishList(formHeader(""), zoneId);
        responseCall.enqueue(new Callback<FishList_Response>() {
            @Override
            public void onResponse(Call<FishList_Response> call, retrofit2.Response<FishList_Response> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<FishList_Response> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForMainBanner() {
        Call<MainBannerResponse> responseCall = mAPIInstance.getMainBanner(APIPrototype.HEADER_MERCHANT_ID, 7);
        responseCall.enqueue(new Callback<MainBannerResponse>() {
            @Override
            public void onResponse(Call<MainBannerResponse> call, retrofit2.Response<MainBannerResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<MainBannerResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForSmallBanner() {
        Call<SmallBannerResponse> responseCall = mAPIInstance.getSmallBanner(APIPrototype.HEADER_MERCHANT_ID, 6);
        responseCall.enqueue(new Callback<SmallBannerResponse>() {
            @Override
            public void onResponse(Call<SmallBannerResponse> call, retrofit2.Response<SmallBannerResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<SmallBannerResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForAddToCart(String sessionId, JsonObject request) {
        Call<CartAddResponse> responseCall = mAPIInstance.newAddTocart(APIPrototype.HEADER_MERCHANT_ID, sessionId, request);
        responseCall.enqueue(new Callback<CartAddResponse>() {
            @Override
            public void onResponse(Call<CartAddResponse> call, retrofit2.Response<CartAddResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<CartAddResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForRemoveFromCart(String sessionId, String key) {
        Call<Cart_Response> responseCall = mAPIInstance.deleteCart(formHeader(sessionId), key);
        responseCall.enqueue(new Callback<Cart_Response>() {
            @Override
            public void onResponse(Call<Cart_Response> call, retrofit2.Response<Cart_Response> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<Cart_Response> call, Throwable t) {
                failureResponse(t);
            }
        });
    }


    public void callAPIForFetchingCarts(String sessionId) {
        Call<Cart_Response> responseCall = mAPIInstance.getCartList(formHeader(sessionId));
        responseCall.enqueue(new Callback<Cart_Response>() {
            @Override
            public void onResponse(Call<Cart_Response> call, retrofit2.Response<Cart_Response> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<Cart_Response> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForUpdatingCart(String sessionId, CartUpdateRequest request) {
        Call<CartUpdateResponse> responseCall = mAPIInstance.updateCart(formHeader(sessionId), request);
        responseCall.enqueue(new Callback<CartUpdateResponse>() {
            @Override
            public void onResponse(Call<CartUpdateResponse> call, retrofit2.Response<CartUpdateResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<CartUpdateResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForFetchingPaymentAddress(String sessionId) {
        Call<qa.happytots.yameenhome.model.fetchaddress.DeliveryAddressResponse> responseCall = mAPIInstance.getPaymentAddress(formHeader(sessionId));
        responseCall.enqueue(new Callback<DeliveryAddressResponse>() {
            @Override
            public void onResponse(Call<DeliveryAddressResponse> call, retrofit2.Response<DeliveryAddressResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<DeliveryAddressResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForAddingPaymentAddress(String sessionId, qa.happytots.yameenhome.model.address.payment.response.Address address) {
        Call<CommonResponse> responseCall = mAPIInstance.addPaymentAddress(formHeader(sessionId), address);
        responseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForFetchingShippingMethods(String sessionId) {
        Call<ShippingMethodResponse> responseCall = mAPIInstance.getShippingMethods(formHeader(sessionId));
        responseCall.enqueue(new Callback<ShippingMethodResponse>() {
            @Override
            public void onResponse(Call<ShippingMethodResponse> call, retrofit2.Response<ShippingMethodResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<ShippingMethodResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForAddingShippingMethods(String sessionId, ShippingMethodRequest request) {
        Call<CommonResponse> responseCall = mAPIInstance.addShippingMethods(formHeader(sessionId), request);
        responseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForFetchingPaymentMethods(String sessionId) {
        Call<PaymentMethodResponse> responseCall = mAPIInstance.getPaymentMethods(formHeader(sessionId));
        responseCall.enqueue(new Callback<PaymentMethodResponse>() {
            @Override
            public void onResponse(Call<PaymentMethodResponse> call, retrofit2.Response<PaymentMethodResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<PaymentMethodResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForPostingPaymentMethods(String sessionId, PaymentMethodRequest request) {
        Call<CommonResponse> responseCall = mAPIInstance.postPaymentMethod(formHeader(sessionId), request);
        responseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForPostingConfirmation(String sessionId, String date, String time) {
        Call<OrderOverviewResponse> responseCall = mAPIInstance.postConfirmation(formHeader(sessionId), date, time);
        responseCall.enqueue(new Callback<OrderOverviewResponse>() {
            @Override
            public void onResponse(Call<OrderOverviewResponse> call, retrofit2.Response<OrderOverviewResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<OrderOverviewResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForPutingConfirmation(String sessionId) {
        Call<CommonResponseWithData> responseCall = mAPIInstance.putConfirmation(formHeader(sessionId));
        responseCall.enqueue(new Callback<CommonResponseWithData>() {
            @Override
            public void onResponse(Call<CommonResponseWithData> call, retrofit2.Response<CommonResponseWithData> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<CommonResponseWithData> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForCheckingUserLogged(String sessionId) {
        Call<UserResponse> responseCall = mAPIInstance.accountLogged(formHeader(sessionId));
        responseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, retrofit2.Response<UserResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForSearching(String sessionId, String queryParam) {
        Call<QuickViewProductByCategoryResponse> responseCall = mAPIInstance.searchFish(formHeader(sessionId), queryParam);
        responseCall.enqueue(new Callback<QuickViewProductByCategoryResponse>() {
            @Override
            public void onResponse(Call<QuickViewProductByCategoryResponse> call, retrofit2.Response<QuickViewProductByCategoryResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<QuickViewProductByCategoryResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForExistingPaymentAddress(String sessionId, PaymentExistingAddressRequest param) {
        Call<CommonResponse> responseCall = mAPIInstance.postShippingAddress(formHeader(sessionId), param);
        responseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForExistingShippingAddress(String sessionId, PaymentExistingAddressRequest param) {
        Call<CommonResponse> responseCall = mAPIInstance.postPaymentAddress(formHeader(sessionId), param);
        responseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForOrderDetails(String sessionId, String orderId) {
        Call<OrderDetailResponse> responseCall = mAPIInstance.getOrderDetails(formHeader(sessionId), orderId);
        responseCall.enqueue(new Callback<OrderDetailResponse>() {
            @Override
            public void onResponse(Call<OrderDetailResponse> call, retrofit2.Response<OrderDetailResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<OrderDetailResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForOrders(String sessionId) {
        Call<OrderResponse> responseCall = mAPIInstance.getOrders(formHeader(sessionId));
        responseCall.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, retrofit2.Response<OrderResponse> response) {
                Log.w("MY_ORDER_RESPONSE" , response.code() + "");
                Log.w("MY_ORDER_RESPONSE" , response.body().getData().size() + "");
                successResponse(response);
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForFetchingCountryList(String sessionId) {
        Call<CountryResponse> responseCall = mAPIInstance.countries(formHeader(sessionId));
        responseCall.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, retrofit2.Response<CountryResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForUpdatingAccount(String sessionId, AccountRequest request) {
        Call<CommonResponseWithData> responseCall = mAPIInstance.updateAccount(formHeader(sessionId), request);
        responseCall.enqueue(new Callback<CommonResponseWithData>() {
            @Override
            public void onResponse(Call<CommonResponseWithData> call, retrofit2.Response<CommonResponseWithData> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<CommonResponseWithData> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForLogout(String sessionId) {
        Call<String> responseCall = mAPIInstance.logout(formHeader(sessionId));
        responseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForGettingCoupon(String sessionId) {
        Call<CouponResponse> responseCall = mAPIInstance.getCoupons(formHeader(sessionId));
        responseCall.enqueue(new Callback<CouponResponse>() {
            @Override
            public void onResponse(Call<CouponResponse> call, retrofit2.Response<CouponResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<CouponResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForApplyCoupon(String sessionId, CouponRequest request) {
        Call<CommonResponse> responseCall = mAPIInstance.applyCoupon(formHeader(sessionId), request);
        responseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForComplaintRegister(String sessionId, ContactRequest param) {
        Call<CommonResponse> responseCall = mAPIInstance.registerComplaint(formHeader(sessionId), param);
        responseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForGettingCustomerAddress(String sessionID) {
        Call<DeliveryAddressResponse> requestCall = mAPIInstance.getCustomerAddress(formHeader(sessionID));
        requestCall.enqueue(new Callback<DeliveryAddressResponse>() {
            @Override
            public void onResponse(Call<DeliveryAddressResponse> call, retrofit2.Response<DeliveryAddressResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<DeliveryAddressResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForPosttingCustomerAddress(String sessionID, Address address) {
        Call<DeliveryAddressResponse> requestCall = mAPIInstance.addCustomerAddress(formHeader(sessionID), address);
        requestCall.enqueue(new Callback<DeliveryAddressResponse>() {
            @Override
            public void onResponse(Call<DeliveryAddressResponse> call, retrofit2.Response<DeliveryAddressResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<DeliveryAddressResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForDeletingCustomerAddress(String sessionId, String addressId) {
        Call<CommonResponse> responseCall = mAPIInstance.deleteCustomerAddress(formHeader(sessionId), addressId);
        responseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForPuttingCustomerAddress(String sessionID, Address address) {
        Call<CommonResponse> requestCall = mAPIInstance.editCustomerAddress(formHeader(sessionID), address.getAddressId(), address);
        requestCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForGettingZoneFromCountryId(String sessionID, String countryId) {
        Call<ZoneResponse> requestCall = mAPIInstance.getZoneFromCountry(formHeader(sessionID), countryId);
        requestCall.enqueue(new Callback<ZoneResponse>() {
            @Override
            public void onResponse(Call<ZoneResponse> call, retrofit2.Response<ZoneResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<ZoneResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForRemoveCoupon(String sessionId) {
        Call<CommonResponse> responseCall = mAPIInstance.removeCoupon(formHeader(sessionId));
        responseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }


    public void callAPIForOTPCheck(String sessionID, String otp, String email, String password) {
        Call<CommonResponse> requestCall = mAPIInstance.otpCheck(formHeader(sessionID), otp, email, password);
        requestCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    public void callAPIForGetLocation(String sessionID) {
      //  qa.happytots.yameenhome.components.Toast.makeToast("sessioon:"+sessionID+" calling location api");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Call<LocationResponse> requestCall = mAPIInstance.getLocations(formHeader(sessionID), "221");
        requestCall.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, retrofit2.Response<LocationResponse> response) {
                successResponse(response);
            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {
                failureResponse(t);
            }
        });
    }

    private void successResponse(retrofit2.Response response) {
        Response res = new Response();
        res.body = response.body();
        res.status = response.isSuccessful();
        res.code = response.code();
        try {
            res.errorBody = (response.errorBody() == null ? null : response.errorBody().string());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (res.body != null) {
            if (mListener != null) {
                mListener.successResponse(res);
            }
        } else {
            if (mListener != null) {
                mListener.failureResponse(res);
            }
        }
    }

    private void failureResponse(Throwable t) {
        if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
            if (mListener != null) {
                mListener.noInternet();
            }
        } else {
            Response response = new Response();
            response.errorBody = t.toString();
            if (mListener != null) {
                mListener.failureResponse(response);
            }
        }
    }
}