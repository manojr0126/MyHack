package com.apps.my.myhealthassist.APIService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;

import com.apps.my.myhealthassist.DataModal.Repository;
import com.apps.my.myhealthassist.DataModal.Scheduler;
import com.apps.my.myhealthassist.MainActivity;
import com.apps.my.myhealthassist.R;
import com.apps.my.myhealthassist.Utilities.Utility;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Manoj on 10/23/2017.
 */

public class RestServiceReceiver extends BroadcastReceiver {

    private static final String TAG = "RestServiceReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String response = intent.getStringExtra(RestAsyncTask.HTTP_RESPONSE);

        Log.i(TAG, "RESPONSE : " + response);

        if (response == null)
            return;

        try {

            JSONObject reader = new JSONObject(response);

            JSONObject result = reader.getJSONObject("result");
            String action = result.getString("action");

            JSONObject fulfillment = result.getJSONObject("fulfillment");
            String speech = fulfillment.getString("speech");

            Date startDate = new Date();
            Date startTime = new Date();

            if (action.equals("reminder.set")) {
                JSONObject parameters = result.getJSONObject("parameters");

                Repository.APIResponse().setDate(parameters.getString("date"));
                Repository.APIResponse().setRecurrence(parameters.getString("recurrence"));
                Repository.APIResponse().setTime(parameters.getString("time"));
                Repository.APIResponse().setMedicine(parameters.getString("medicine"));

                Repository.APIRequest().setConverseType(parameters.getString("conversetype"));
            }

            if (action.equals("reminder.confirm")) {
                if(Repository.APIResponse().getDate() != null && !Repository.APIResponse().getDate().isEmpty()) {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    startDate = df.parse(Repository.APIResponse().getDate());
                }

                if (Repository.APIResponse().getTime() != null && !Repository.APIResponse().getTime().isEmpty()) {
                    DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    startTime = sdf.parse(Repository.APIResponse().getTime());
                }

                Scheduler scheduler = new Scheduler();

                scheduler.setID(scheduler.getID() + 1);
                scheduler.setDate(startDate);
                scheduler.setTime(startTime);
                scheduler.setRecurrence(false);
                scheduler.setScheduled(false);
                scheduler.setVoiceMessage(String.format(MainActivity.getContext().getString(R.string.voice_medicine_reminder), Repository.APIResponse().getMedicine()));

                Repository.addScheduler(scheduler);
            }

            Utility.mVoiceManager.initQueue(speech, false);
        }
        catch (Exception e) {
            Log.i(TAG, "ERROR : " + e);
        }
    }
}
