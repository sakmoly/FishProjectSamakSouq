package qa.happytots.yameenhome.datamodel.network;

import com.google.gson.JsonObject;

import java.util.Map;

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
import qa.happytots.yameenhome.retrofit.Retrofit_Models.Add_to_cart_raw;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.Add_to_cart_response;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.Cart_Response;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.DeliveryAddressResponse;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.FishList_Response;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.LoginRawClass;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.LoginResponse;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.MainBannerResponse;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.Session_TokenResponse;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.SmallBannerResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface APIPrototype {

    String API_BASE_URL ="http://samaksouq.com/";//http://printechsdmm.doomdns.org:83/"; //"; // http://ascentbahrain.com/samaksouq/ , http://printechsindia.com/samaksouq/
    String HEADER_MERCHANT_ID = "12345";
//"http://192.168.103.155:83/samaksouq/";
    @GET("index.php?route=feed/rest_api/banners")
    Call<MainBannerResponse> getMainBanner(@Header("X-Oc-Merchant-Id") String merchant_id, @Query("id") int banner_id);

    @GET("index.php?route=feed/rest_api/banners")
    Call<SmallBannerResponse> getSmallBanner(@Header("X-Oc-Merchant-Id") String merchant_id, @Query("id") int banner_id);

    @GET("index.php?route=feed/rest_api/products")
    Call<FishList_Response> getFishList(@HeaderMap Map<String, String> headers, @Query("zone_id") String zoneId);

    @GET("index.php?route=feed/rest_api/session")
    Call<Session_TokenResponse> getSession(@Header("X-Oc-Merchant-Id") String merchant_id);

    @GET("index.php?route=rest/cart/cart")
    Call<Cart_Response> getCartList(@HeaderMap Map<String, String> headers);

    @Headers("Content-Type: application/json")
    @POST("index.php?route=rest/login/login")
    Call<LoginResponse> loginPost(@HeaderMap Map<String, String> headers, @Body LoginRawClass loginRawClass);

    @POST("index.php?route=rest/login/sociallogin")
    Call<LoginResponse> socialLogin(@HeaderMap Map<String, String> headers, @Body SocialLoginRequest request);

    @Headers("Content-Type: application/json")
    @POST("index.php?route=rest/cart/cart")
    Call<Add_to_cart_response> addCart(@Header("X-Oc-Merchant-Id") String merchant_id, @Header("X-Oc-Session") String session_id, @Body Add_to_cart_raw toCartRaw);

    @POST("index.php?route=rest/cart/cart")
    Call<CartAddResponse> newAddTocart(@Header("X-Oc-Merchant-Id") String merchant_id, @Header("X-Oc-Session") String session_id, @Body JsonObject request);

    @GET("index.php?route=rest/shipping_address/shippingaddress")
    Call<qa.happytots.yameenhome.model.fetchaddress.DeliveryAddressResponse> getAddress(@HeaderMap Map<String, String> headers);

    @POST("index.php?route=rest/shipping_address/shippingaddress")
    Call<DeliveryAddressResponse> addAddress(@HeaderMap Map<String, String> headers, @Body Address address);

    @PUT("index.php?route=rest/account/address")
    Call<DeliveryAddressResponse> editAddress(@HeaderMap Map<String, String> headers, @Query("id") String addressId, @Body Address address);

    @GET("index.php?route=feed/rest_api/categories")
    Call<QuickViewCategoryResponse> getQuickViewList(@HeaderMap Map<String, String> headers);

    @GET("index.php?route=feed/rest_api/products")
    Call<QuickViewProductByCategoryResponse> getProductByCategory(@HeaderMap Map<String, String> headers, @Query("category") String id);

    @GET("index.php?route=feed/rest_api/products")
    Call<FishList_Response> getProductByCategoryAtHome(@HeaderMap Map<String, String> headers, @Query("category") String id);

    @GET("index.php?route=feed/rest_api/products")
    Call<FishList_Response> getFishProductByCategory(@HeaderMap Map<String, String> headers, @Query("category") String id);


    @GET("index.php?route=feed/rest_api/products")
    Call<FishList_Response> getPriceRangeFilterResponse(@HeaderMap Map<String, String> headers, @Query("price_range") String priceRange);

    @POST("index.php?route=rest/register/register")
    Call<RegisterResponse> registerUser(@HeaderMap Map<String, String> headers, @Body RegisterRequest request);

    @DELETE("index.php?route=rest/cart/cart")
    Call<Cart_Response> deleteCart(@HeaderMap Map<String, String> headers, @Query("key") String key);

    @PUT("index.php?route=rest/cart/cart")
    Call<CartUpdateResponse> updateCart(@HeaderMap Map<String, String> headers, @Body CartUpdateRequest request);

    @GET("index.php?route=rest/payment_address/paymentaddress")
    Call<qa.happytots.yameenhome.model.fetchaddress.DeliveryAddressResponse> getPaymentAddress(@HeaderMap Map<String, String> headers);

    @POST("index.php?route=rest/payment_address/paymentaddress")
    Call<CommonResponse> addPaymentAddress(@HeaderMap Map<String, String> headers, @Body qa.happytots.yameenhome.model.address.payment.response.Address address);

    @GET("index.php?route=rest/shipping_method/shippingmethods")
    Call<ShippingMethodResponse> getShippingMethods(@HeaderMap Map<String, String> headers);

    @POST("index.php?route=rest/shipping_method/shippingmethods")
    Call<CommonResponse> addShippingMethods(@HeaderMap Map<String, String> headers, @Body ShippingMethodRequest request);

    @GET("index.php?route=rest/payment_method/payments")
    Call<PaymentMethodResponse> getPaymentMethods(@HeaderMap Map<String, String> headers);

    @POST("index.php?route=rest/payment_method/payments")
    Call<CommonResponse> postPaymentMethod(@HeaderMap Map<String, String> headers, @Body PaymentMethodRequest request);

    @FormUrlEncoded
    @POST("index.php?route=rest/confirm/confirm")
    Call<OrderOverviewResponse> postConfirmation(@HeaderMap Map<String, String> headers, @Field("delivery_date") String date, @Field("delivery_time") String time);

    @PUT("index.php?route=rest/confirm/confirm")
    Call<CommonResponseWithData> putConfirmation(@HeaderMap Map<String, String> headers);

    @GET("index.php?route=feed/rest_api/products")
    Call<QuickViewProductByCategoryResponse> searchFish(@HeaderMap Map<String, String> headers, @Query("search") String search);

    @POST("index.php?route=rest/payment_address/paymentaddress&existing=1")
    Call<CommonResponse> postPaymentAddress(@HeaderMap Map<String, String> headers, @Body PaymentExistingAddressRequest request);

    @POST("index.php?route=rest/shipping_address/shippingaddress&existing=1")
    Call<CommonResponse> postShippingAddress(@HeaderMap Map<String, String> headers, @Body PaymentExistingAddressRequest request);

    @GET("index.php?route=rest/account/account")
    Call<UserResponse> accountLogged(@HeaderMap Map<String, String> headers);

    @GET("index.php?route=feed/rest_api/countries")
    Call<CountryResponse> countries(@HeaderMap Map<String, String> headers);

    @GET("index.php?route=rest/order/orders")
    Call<OrderResponse> getOrders(@HeaderMap Map<String, String> headers);

    @GET("index.php?route=rest/order/orders")
    Call<OrderDetailResponse> getOrderDetails(@HeaderMap Map<String, String> headers, @Query("id") String orderId);

    @PUT("index.php?route=rest/account/account")
    Call<CommonResponseWithData> updateAccount(@HeaderMap Map<String, String> headers, @Body AccountRequest request);

    @POST("index.php?route=rest/logout/logout")
    Call<String> logout(@HeaderMap Map<String, String> headers);

    @GET("index.php?route=rest/cart/getCoupon")
    Call<CouponResponse> getCoupons(@HeaderMap Map<String, String> headers);

    @POST("index.php?route=rest/cart/coupon")
    Call<CommonResponse> applyCoupon(@HeaderMap Map<String, String> headers, @Body CouponRequest request);

    @POST("index.php?route=rest/contact/send")
    Call<CommonResponse> registerComplaint(@HeaderMap Map<String, String> headers, @Body ContactRequest request);

    @GET("index.php?route=rest/account/address")
    Call<qa.happytots.yameenhome.model.fetchaddress.DeliveryAddressResponse> getCustomerAddress(@HeaderMap Map<String, String> headers);

    @POST("index.php?route=rest/account/address")
    Call<qa.happytots.yameenhome.model.fetchaddress.DeliveryAddressResponse> addCustomerAddress(@HeaderMap Map<String, String> headers, @Body Address address);

    @PUT("index.php?route=rest/account/address")
    Call<CommonResponse> editCustomerAddress(@HeaderMap Map<String, String> headers, @Query("id") String addressId, @Body Address address);

    @DELETE("index.php?route=rest/account/address")
    Call<CommonResponse> deleteCustomerAddress(@HeaderMap Map<String, String> headers, @Query("id") String addressId);

    @GET("index.php?route=feed/rest_api/countries")
    Call<ZoneResponse> getZoneFromCountry(@HeaderMap Map<String, String> headers, @Query("id") String countryId);

    @DELETE("index.php?route=rest/cart/coupon")
    Call<CommonResponse> removeCoupon(@HeaderMap Map<String, String> headers);

    @FormUrlEncoded
    @POST("index.php?route=rest/register/step2")
    Call<CommonResponse> otpCheck(@HeaderMap Map<String, String> headers, @Field("otp_code") String OTPCode, @Field("customer_email")  String email, @Field("customer_password") String password);

    @GET("index.php?route=feed/rest_api/countries")
    Call<LocationResponse> getLocations(@HeaderMap Map<String, String> headers, @Query("id") String id);
}