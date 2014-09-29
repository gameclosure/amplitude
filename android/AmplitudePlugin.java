package com.tealeaf.plugin.plugins;

import com.amplitude.api.Amplitude;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import com.tealeaf.logger;
import com.tealeaf.TeaLeaf;
import com.tealeaf.plugin.IPlugin;
import java.io.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.tealeaf.util.HTTP;
import java.net.URI;
import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;


public class AmplitudePlugin implements IPlugin {
    Activity activity;

    private JSONObject globalProperties = new JSONObject();

    public AmplitudePlugin() {

    }

    public void onCreateApplication(Context applicationContext) {

    }

    public void onCreate(Activity activity, Bundle savedInstanceState) {
        this.activity = activity;
        PackageManager manager = activity.getPackageManager();
        String amplitudeKey = "";
        String amplitudeKeyStaging = "";
        try {
            Bundle meta = manager.getApplicationInfo(activity.getPackageName(), PackageManager.GET_META_DATA).metaData;
            if (meta != null) {
                boolean debug = meta.getBoolean("WEEBY_DEBUG", true);

                if (debug) {
                    amplitudeKey = meta.getString("AMPLITUDE_KEY_STAGING");
                } else {
                    amplitudeKey = meta.getString("AMPLITUDE_KEY");
                }
            }
        } catch (Exception e) {
            logger.log("{amplitude} EXCEPTION", "" + e.getMessage());
        }

        Amplitude.initialize(activity, amplitudeKey);

		logger.log("{amplitude} Initialized with key", amplitudeKey);
    }

    public void onResume() {
        Amplitude.startSession();
    }

    public void onStart() {
    }

    public void onPause() {
        Amplitude.endSession();
    }

    public void onStop() {
    }

    public void setGlobalProperty(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            String key = obj.getString("key");
            String value = obj.getString("value");

            globalProperties.put(key, value);
            Amplitude.setUserProperties(globalProperties);
        } catch (JSONException exception) {
        }
    }

    public void track(String json) {
        String eventName = "noName";
        try {
            JSONObject obj = new JSONObject(json);
            eventName = obj.getString("eventName");
            Map<String, String> params = new HashMap<String, String>();
            JSONObject paramsObj = obj.optJSONObject("params");
            if (paramsObj == null) {
                paramsObj = new JSONObject();
            }
            Amplitude.logEvent(eventName, paramsObj);
            logger.log("{amplitude} track - success: " + eventName);
        } catch (JSONException e) {
            logger.log("{amplitude} track - failure: " + eventName + " - " + e.getMessage());
        }
    }

    public void setUserId(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            String id = obj.getString("userId");
            Amplitude.setUserId(id);
        } catch (JSONException e) {
            logger.log("{amplitude} setUserId - failure: " + e.getMessage());
        }
    }

    public void trackRevenue (String json) {
        try {
            JSONObject obj = new JSONObject(json);
            String id = obj.getString("id");
            double price = obj.getDouble("price");
            int quantity = obj.getInt("quantity");
            Amplitude.logRevenue(id, quantity, price);
        } catch (JSONException e) {
            logger.log("{amplitude} trackRevenue - failure: " + e.getMessage());
        }
    }

    public void setUserProperties (String json) {
        try {
            JSONObject props = new JSONObject(json);
            Amplitude.setUserProperties(props);
        } catch (JSONException e) {
            logger.log("{amplitude} setUserProperties error: " + e.getMessage());
        }
    }

    public void onDestroy() {
    }

    public void onNewIntent(Intent intent) {

    }

    public void setInstallReferrer(String referrer) {

    }

    public void onActivityResult(Integer request, Integer result, Intent data) {

    }

    public boolean consumeOnBackPressed() {
        return true;
    }

    public void onBackPressed() {
    }

}
