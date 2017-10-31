package com.apps.my.myhealthassist;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.apps.my.myhealthassist.APIService.RestAsyncTask;
import com.apps.my.myhealthassist.APIService.RestServiceManager;
import com.apps.my.myhealthassist.AppServices.SchedulerService;
import com.apps.my.myhealthassist.DataModal.Repository;
import com.apps.my.myhealthassist.DataModal.Scheduler;
import com.apps.my.myhealthassist.SpeechService.SpeechRecognizerManager;
import com.apps.my.myhealthassist.SpeechService.VoiceCommandManager;
import com.apps.my.myhealthassist.Utilities.Utility;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION_CODE = 1;
    private static final int REQUEST_READ_CALENDAR_PERMISSION_CODE = 1;
    private static final int REQUEST_WRITE_CALENDAR_PERMISSION_CODE = 1;
    private static final int REQUEST_VIBRATE_PERMISSION_CODE = 1;
    private static Context mApplicationContext;

    private Intent iScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApplicationContext = getApplicationContext();

        // Trigger scheduler service
        iScheduler = new Intent(mApplicationContext, SchedulerService.class);
        mApplicationContext.startService(iScheduler);

        /*********************************Start check for permissions*****************************/

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.RECORD_AUDIO);
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.WRITE_CALENDAR);
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_CALENDAR);
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.VIBRATE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.VIBRATE);
        }

        /*********************************End check for permissions*****************************/

        if(Utility.mSpeechManager == null) {
            setListener();
        }
        else if(!Utility.isListening()) {
            Utility.mSpeechManager.destroy();
            setListener();
        }

        Utility.mRestServiceManager = new RestServiceManager(MainActivity.this);


        /********************************Configure Rest Service Receiver ********************************/

        Intent intent = new Intent();
        intent.setAction(RestServiceManager.ACTION);

        sendBroadcast(intent);
    }

    @Override
    protected void onPause() {
        if(Utility.mSpeechManager != null) {
            Utility.mSpeechManager.destroy();
            Utility.mSpeechManager = null;
        }

        super.onPause();
    }

    @Override
    public void onResume()
    {
        if(Utility.mSpeechManager == null) {
            setListener();
        }

        super.onResume();
    }

    @Override
    public void onDestroy ()
    {
        mApplicationContext.stopService(iScheduler);

        if(Utility.mSpeechManager != null) {
            Utility.mSpeechManager.destroy();
            Utility.mSpeechManager = null;
        }

        super.onDestroy();
    }

    public static Context getContext() {
        return mApplicationContext;
    }

    private void setListener()
    {
        Utility.mSpeechManager = new SpeechRecognizerManager(this, new SpeechRecognizerManager.onResultsReady() {
            @Override
            public void onResults(ArrayList<String> results) {

                if(results != null && results.size() > 0) {
                    //mVoiceManager.initQueue(results.get(0));

                    Log.i(TAG, "onResults " + String.valueOf(Utility.isSilentMode()));

                    if (results.get(0).toLowerCase().equals(getString(R.string.voice_name))) {
                        Utility.mVoiceManager.initQueue(getString(R.string.help), false);

                        Utility.setSilentMode(false);
                    } else if (Utility.isSilentMode() == false) {

                        Utility.setSilentMode(false);

                        JSONObject json = new JSONObject();

                        try {

                            json.put("query", results.get(0));
                            json.put("converseType", Repository.APIRequest().getConverseType());
                            json.put("lang", Repository.APIRequest().getLanguage());
                            json.put("sessionId", Repository.APIRequest().getSessionID());

                            Utility.mRestServiceManager.getContent(json);

                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage());
                        }
                    }
                    else {
                        Utility.mVoiceManager.initQueue("", false);
                    }
                }
            }
        });

        Utility.mVoiceManager = new VoiceCommandManager();
        Utility.mVoiceManager.init(MainActivity.this, Utility.mSpeechManager);
    }

    private void requestPermission(String permission) {

        switch (permission){
            case Manifest.permission.RECORD_AUDIO: {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.RECORD_AUDIO)) {
                    Toast.makeText(this, "Requires RECORD_AUDIO permission", Toast.LENGTH_SHORT).show();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[] { Manifest.permission.RECORD_AUDIO },
                            REQUEST_RECORD_AUDIO_PERMISSION_CODE);
                }

                break;
            }
            case Manifest.permission.WRITE_CALENDAR: {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_CALENDAR)) {
                    Toast.makeText(this, "Requires WRITE_CALENDAR permission", Toast.LENGTH_SHORT).show();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[] { Manifest.permission.WRITE_CALENDAR },
                            REQUEST_WRITE_CALENDAR_PERMISSION_CODE);
                }

                break;
            }
            case Manifest.permission.READ_CALENDAR: {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_CALENDAR)) {
                    Toast.makeText(this, "Requires READ_CALENDAR permission", Toast.LENGTH_SHORT).show();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[] { Manifest.permission.READ_CALENDAR },
                            REQUEST_READ_CALENDAR_PERMISSION_CODE);
                }

                break;
            }
            case Manifest.permission.VIBRATE: {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.VIBRATE)) {
                    Toast.makeText(this, "Requires VIBRATE permission", Toast.LENGTH_SHORT).show();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[] { Manifest.permission.VIBRATE },
                            REQUEST_VIBRATE_PERMISSION_CODE);
                }

                break;
            }
            default:
                break;
        }
    }
}
