package com.apps.my.myhealthassist.APIService;

import android.content.Context;
import android.util.Log;

import com.apps.my.myhealthassist.Utilities.Config;

import org.json.JSONObject;

import java.net.URI;

import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by Manoj on 10/14/2017.
 */

public class RestServiceManager {
    private static final String TAG = "RestServiceManager";
    public static final String ACTION = "SendToApi";

    private Context mContext;
    private RestAsyncTask task;

    public RestServiceManager(Context context)
    {
        mContext = context;
    }

    public void getContent(JSONObject json)
    {
        try
        {
            HttpPost httpPost = new HttpPost(new URI(Config.getAPI_URL()));
            httpPost.setHeader("Content-Type", "application/json");
            StringEntity se = new StringEntity( json.toString());
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            httpPost.setEntity(se);

            task = new RestAsyncTask(mContext, ACTION);
            task.execute(httpPost);
        }
        catch (Exception e)
        {
            Log.e(TAG, e.getMessage());
        }
    }
}
