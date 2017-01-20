package kr.co.easterbunny.wonderple.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by scona on 2017-01-12.
 */

public interface HttpService {


    @FormUrlEncoded
    @POST("logincheck.php")
    Call<String> loginCheck(@Field("name") String name,
                            @Field("type") String type,
                            @Field("idnum") String idNum);

    @FormUrlEncoded
    @POST("logincheck_email.php")
    Call<String>  loginCheckEmail(@Field("email") String email,
                                   @Field("type") String type,
                                   @Field("password") String password);

    @FormUrlEncoded
    @POST("pushregister.php")
    Call<String> pushRegister(@Field("uid") String uid,
                              @Field("pushid") String pushId,
                              @Field("picture") String picture);

    @FormUrlEncoded
    @POST("sighup.php")
    Call<String> signUp(@Field("name") String name,
                        @Field("type") String type,
                        @Field("picture") String picture,
                        @Field("idnum") String idNum);

    @FormUrlEncoded
    @POST("signUp_email.php")
    Call<String> signUpEmail(@Field("email") String email,
                             @Field("name") String name,
                             @Field("password") String password,
                             @Field("type") String type);
}
