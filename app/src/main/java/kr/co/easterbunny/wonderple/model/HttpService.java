package kr.co.easterbunny.wonderple.model;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by scona on 2017-01-12.
 */

public interface HttpService {


    @FormUrlEncoded
    @POST("logincheck_android.php")
    Call<JsonObject> loginCheck(@Field("name") String name,
                            @Field("type") String type,
                            @Field("idnum") String idNum);

    @FormUrlEncoded
    @POST("logincheck_email_android.php")
    Call<JsonObject>  loginCheckEmail(@Field("email") String email,
                                   @Field("type") String type,
                                   @Field("password") String password);

    @FormUrlEncoded
    @POST("pushregister_android.php")
    Call<JsonObject> pushRegister(@Field("uid") String uid,
                              @Field("pushid") String pushId,
                              @Field("picture") String picture);

    @FormUrlEncoded
    @POST("signup_android.php")
    Call<JsonObject> signUp(@Field("name") String name,
                            @Field("type") String type,
                            @Field("picture") String picture,
                            @Field("idnum") String idNum);

    @FormUrlEncoded
    @POST("signup_email_android.php")
    Call<JsonObject> signUpEmail(@Field("email") String email,
                             @Field("name") String name,
                             @Field("password") String password,
                             @Field("type") String type);

    @FormUrlEncoded
    @POST("pwrequest_android.php")
    Call<JsonObject> pwRequest(@Field("email") String email);

    @FormUrlEncoded
    @POST("loadmain_android.php")
    Call<LoadMainResult> loadMain(@Field("uid") String uid);
}
