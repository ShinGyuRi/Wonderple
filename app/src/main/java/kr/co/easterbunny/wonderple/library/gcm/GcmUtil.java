package kr.co.easterbunny.wonderple.library.gcm;

import android.app.Activity;

import com.google.android.gms.common.ConnectionResult;

import kr.co.easterbunny.wonderple.library.util.Debug;


/**
 * Created by jinsin on 16. 7. 12..
 */
public class GcmUtil {

    public static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    public static String REGISTRATION_COMPLETE = "REGISTRATION_COMPLETE";

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
//    public static boolean checkPlayServices(Activity activity) {
//        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
//        int resultCode = apiAvailability.isGooglePlayServicesAvailable(activity.getApplicationContext());
//        Debug.showDebug("GCM TEST =========== 1");
//        if (resultCode != ConnectionResult.SUCCESS) {
//            Debug.showDebug("GCM TEST ============== 2");
//            if (apiAvailability.isUserResolvableError(resultCode)) {
//                Debug.showDebug("GCM TEST ========== 3");
//                apiAvailability.getErrorDialog(activity, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
//            } else {
//                Debug.showDebug("This device is not supported.");
//            }
//            return false;
//        }
//        return true;
//    }
}