package citytracksaware.core.util;

import android.content.*;
import android.net.*;
import android.util.*;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import org.json.*;

import java.io.*;

/**
 * Created by Elton Soares on 11/5/2017.
 */

public class UploadManager{

    public static final String TAG = "UploadManager";
    private Context context;
    private RequestQueue requestQueue;
    private String serverURL;
    private DefaultRetryPolicy retryPolicy;
    private static UploadManager instance;

    public static UploadManager getInstance(){
        return instance;
    }

    public static UploadManager getInstance(Context context, String serverURL){
        if(instance == null){
            instance = new UploadManager(context, serverURL);
        }
        return instance;
    }

    protected UploadManager(Context context, String serverURL){
        this.context = context;
        this.serverURL = serverURL;
        this.requestQueue = Volley.newRequestQueue(context);
        this.retryPolicy = new DefaultRetryPolicy(20 * 1000, 1, 1.0f);
    }

    public String getServerURL() {
        return serverURL;
    }

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public boolean isWiFiAvailable() {
        return isNetworkAvailable(ConnectivityManager.TYPE_WIFI);
    }

    public boolean isNetworkAvailable(){
        NetworkInfo activeNetwork = getActiveNetwork();
        return activeNetwork != null;
    }

    private NetworkInfo getActiveNetwork() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE
        );
        return cm.getActiveNetworkInfo();
    }

    public boolean isNetworkAvailable(int networkType){
        NetworkInfo activeNetwork = getActiveNetwork();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == networkType) {
                return true;
            }
        }
        return false;
    }

    public void uploadToServer(JSONObject json, String path){


        final String URL = serverURL + path;

        Log.i("upload-manager", "Request to " + path + " added to queue.");
        JsonObjectRequest request = new JsonObjectRequest(URL, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                        } catch (JSONException e) {
                            Log.i(TAG, e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("upload-manager", "Error sending data to " + URL);
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                });
        request.setRetryPolicy(retryPolicy);

        requestQueue.add(request);

    }

}
