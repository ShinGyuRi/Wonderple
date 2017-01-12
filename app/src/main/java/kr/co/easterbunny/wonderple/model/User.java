package kr.co.easterbunny.wonderple.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gyul on 2016-06-22.
 */
public class User {



    @SerializedName("dob")
    public String dob;


    @SerializedName("email")
    public String email;

    @SerializedName("username")
    public String username;

    @SerializedName("firstname")
    public String firstname;

    @SerializedName("gender")
    public String gender;

    @SerializedName("image")
    public String image;


    @SerializedName("lastname")
    public String lastname;

    @SerializedName("latitude")
    public String latitude;

    @SerializedName("longitude")
    public String longitude;

    @SerializedName("snsid")
    public String snsid;

    @SerializedName("snstype")
    public String snstype;

    @SerializedName("udid")
    public String udid;



}