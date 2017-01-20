package kr.co.easterbunny.wonderple.library;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import kr.co.easterbunny.wonderple.model.SignInResult;
import kr.co.easterbunny.wonderple.model.User;


/**
 * Created by kimjeonghwi on 2016. 7. 14..
 */
public class WonderpleLib
{
    private static WonderpleLib sharedWonderpleLibObj;
    private SharedPreferences.Editor shardPrefEd;
    SharedPreferences sharedPref;

    SignInResult currentUser;

    public static final String LOGIN_TYPE_WONDERPLE = "WonderpleUser";
    public static final String LOGIN_TYPE_FACEBOOK_USER = "FacebookUser";
    public static final String LOGIN_TYPE_KAKAO = "KakaoUser";





    public static WonderpleLib getInstance()
    {
        if(sharedWonderpleLibObj == null)
        {
            sharedWonderpleLibObj = new WonderpleLib();
        }

        return sharedWonderpleLibObj;
    }


    public void func00_runAfterDelay(Runnable runnable, long delayTime)
    {
        Handler mHandler = new Handler();
        mHandler.postDelayed(runnable,delayTime);

    }



    /// 사용자의 데이터를 파일로 저장한다
    public void func01_saveUserDataToFile(Context context, SignInResult signInResult)
    {

        SharedPreferences.Editor editor = getSharedPrefEdit(context);
        if(signInResult != null)
        {

            if(signInResult.sessionToken != null)
            {
                editor.putString("sessionToken",signInResult.sessionToken);
            }

            if(signInResult.user.dob != null)
            {
                editor.putString("dob",signInResult.user.dob);
            }

            if(signInResult.user.email != null)
            {
                editor.putString("email",signInResult.user.email);
            }

            if(signInResult.user.username != null)
            {
                editor.putString("username",signInResult.user.username);
            }

            if(signInResult.user.firstname != null)
            {
                editor.putString("firstname",signInResult.user.firstname);
            }

            if(signInResult.user.gender != null)
            {
                editor.putString("gender",signInResult.user.gender);
            }


            if(signInResult.user.lastname != null)
            {
                editor.putString("lastname",signInResult.user.lastname);
            }

            if(signInResult.user.image != null)
            {
                editor.putString("image",signInResult.user.image);
            }

            if(signInResult.user.latitude != null)
            {
                editor.putString("latitude",signInResult.user.latitude);
            }

            if(signInResult.user.longitude != null)
            {
                editor.putString("longitude",signInResult.user.longitude);
            }

            if(signInResult.user.snsid != null)
            {
                editor.putString("snsid",signInResult.user.snsid);
            }

            if(signInResult.user.snstype != null)
            {
                editor.putString("snstype",signInResult.user.snstype);
            }

            if(signInResult.user.udid != null)
            {
                editor.putString("udid",signInResult.user.udid);
            }



            editor.commit();



        }
        else
        {
            Log.d("JIN"," func01_saveUserData --> 잘못된 API 호출입니다.");
            return ;
        }
    }


    public SignInResult func01_loadUserDataToFile(Context context)
    {
        SignInResult makedSignInResult = new SignInResult();

        SharedPreferences pref = getSharedPref(context);

        String sessionToken = pref.getString("sessionToken","");
        String dob = pref.getString("dob","");
        String email = pref.getString("email","");
        String username = pref.getString("username","");
        String firstname = pref.getString("firstname","");
        String gender = pref.getString("gender","");
        String lastname = pref.getString("lastname","");
        String image = pref.getString("image","");
        String latitude = pref.getString("latitude","");
        String longitude = pref.getString("longitude","");
        String snsid = pref.getString("snsid","");
        String snstype = pref.getString("snstype","");
        String udid = pref.getString("udid","");




        makedSignInResult.sessionToken = sessionToken;

        makedSignInResult.user = new User();
        makedSignInResult.user.dob = dob;
        makedSignInResult.user.email = email;
        makedSignInResult.user.username = username;
        makedSignInResult.user.firstname = firstname;
        makedSignInResult.user.gender = gender;
        makedSignInResult.user.lastname = lastname;
        makedSignInResult.user.image = image;
        makedSignInResult.user.latitude = latitude;
        makedSignInResult.user.longitude = longitude;
        makedSignInResult.user.snsid = snsid;
        makedSignInResult.user.snstype = snstype;
        makedSignInResult.user.udid = udid;

        Log.d("JIN","func01_loadUserDataToFile 확인 0001 : latitude --> "+latitude);

        Log.d("JIN","func01_loadUserDataToFile 확인 0002 : udid --> "+udid);
        Log.d("JIN","func01_loadUserDataToFile 확인 0003 : makedSignInResult.sessionToken --> "+makedSignInResult.sessionToken);

        if(!makedSignInResult.sessionToken.equals(""))
        {
            currentUser = makedSignInResult;
            return currentUser;
        }

        return null;

    }


    public SignInResult func01_loadUserDataFromMemory()
    {
        return currentUser;
    }





    public String func01_loadUsernameCheckSNSAndUDID(Context context)
    {
        if(currentUser.user.snsid != null  && !"".equals(currentUser.user.snsid))
        {
            return currentUser.user.snsid;
        }
        else if(currentUser.user.username != null  && !"".equals(currentUser.user.username))
        {
            return currentUser.user.username;
        }
        else if(currentUser.user.udid != null  && !"".equals(currentUser.user.udid))
        {
            return currentUser.user.udid;
        }

        return "";
    }


    public String func01_getUserRealNameConcatenateLastNameAndFirstName(Context context)
    {
        String lastName = currentUser.user.lastname;
        String firstName = currentUser.user.firstname;
        String makedName;
        if(lastName != null && firstName != null)
        {
            makedName = firstName+" "+lastName;
        }
        else
        {
            makedName = func01_loadUsernameCheckSNSAndUDID(context);
        }
        return makedName;
    }


    public String func01_getLoginTypeOfUser()
    {
        if(currentUser.user.snsid != null  && !"".equals(currentUser.user.snsid))
        {
            return LOGIN_TYPE_FACEBOOK_USER;
        }
        else if(currentUser.user.username != null  && !"".equals(currentUser.user.username))
        {
            return LOGIN_TYPE_WONDERPLE;
        }
        else if(currentUser.user.udid != null  && !"".equals(currentUser.user.udid))
        {
            return LOGIN_TYPE_KAKAO;
        }
        return "";
    }


    public void func01_userLogoutRemoveUserData(Context context)
    {
        currentUser = null;
        getSharedPrefEdit(context).putString("sessionToken","");
        getSharedPrefEdit(context).commit();


    }



    public boolean func02_isUserKeppSignInMode(Context context)
    {
        return getSharedPref(context).getBoolean("isUserKeppSignInMode",false);

    }

    public void func02_saveIsUserKeepSignInMode(Context context, boolean isKeep)
    {
        getSharedPrefEdit(context).putBoolean("isUserKeppSignInMode",isKeep);
        getSharedPrefEdit(context).commit();

    }










    public String func03_convertDateFromServerToApp(String dateString)
    {
        if(dateString != null && !"".equals(dateString) && dateString.contains("."))
        {
            String makedString = dateString.replace(".","/");
            return makedString;
        }

        return "";
    }





    public void func04_showSimplDialog(Context context, String message, String buttonMessage, DialogInterface.OnClickListener btnListener)
    {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setMessage(message);
        alertBuilder.setNegativeButton(buttonMessage,btnListener);
        alertBuilder.setCancelable(false);
        alertBuilder.create().show();

    }


    public void func04_showSimplSelect2Dialog(Context context, String message, String yesBtn, String noBtn, DialogInterface.OnClickListener yesBtnListener, DialogInterface.OnClickListener noBtnListener)
    {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setMessage(message);
        alertBuilder.setPositiveButton(yesBtn,yesBtnListener);
        alertBuilder.setNegativeButton(noBtn,noBtnListener);
        alertBuilder.setCancelable(false);
        alertBuilder.create().show();

    }











    public int func06_getTiltValueFromAngle(float degreeValue)
    {

        int returnValue = 0;

        if(
                ( 22.5 <= degreeValue  && degreeValue <= 67.5)
                        || ( -337.5 <= degreeValue  && degreeValue <= -292.5)
                )
        {
            returnValue = 2;
        }
        else if(
                ( 67.5 <= degreeValue  && degreeValue <= 112.5)
                        || ( -292.5 <= degreeValue  && degreeValue <= -247.5)
                )
        {
            returnValue = 3;
        }
        else if(
                ( 112.5 <= degreeValue  && degreeValue <= 157.5)
                        || ( -247.5 <= degreeValue  && degreeValue <= -202.5)
                )
        {
            returnValue = 4;
        }
        else if(
                ( 157.5 <= degreeValue  && degreeValue <= 202.5)
                        || ( -202.5 <= degreeValue  && degreeValue <= -157.5)
                )
        {
            returnValue = 5;
        }
        else if(
                (202.5 <= degreeValue && degreeValue <= 247.5)
                        || (-157.5 <= degreeValue && degreeValue <= -112.5)
                )
        {
            returnValue = 6;
        }
        else if(
                (247.5 <= degreeValue && degreeValue <= 292.5)
                        || (-112.5 <= degreeValue && degreeValue <= -67.5)
                )
        {
            returnValue = 7;
        }
        else if((292.5 <= degreeValue && degreeValue <= 337.5)
                || (  -67.5 <= degreeValue && degreeValue <= -22.5 )
                )
        {
            returnValue = 8;
        }
        else if(
                (337.5 <= degreeValue && degreeValue <= 360)
                        || (0 <= degreeValue && degreeValue <= 22.5)

                        || (  -22.5 <= degreeValue && degreeValue <= 0 )
                        || (  -360 <= degreeValue && degreeValue <= -337.5 )
                )
        {
            returnValue = 1;
        }
        else
        {
            Log.d("JIN","피해가는 값 확인 : "+degreeValue);
        }

        return returnValue;
    }




    public static void func07_setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }















    SharedPreferences getSharedPref(Context context)
    {
        if(sharedPref == null)
        {
            sharedPref = context.getSharedPreferences("WonderpleLib" , context.MODE_PRIVATE);
        }

        return sharedPref;

    }

    SharedPreferences.Editor getSharedPrefEdit(Context context)
    {
        if(shardPrefEd == null)
        {
            shardPrefEd = context.getSharedPreferences("WonderpleLib" , context.MODE_PRIVATE).edit();
        }

        return shardPrefEd;
    }
}
