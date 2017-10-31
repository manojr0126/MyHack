package com.apps.my.myhealthassist.SpeechService;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Manoj on 10/14/2017.
 */

public class VoiceCommandManager {
    private final ReentrantLock lock = new ReentrantLock();
    private TextToSpeech mTts = null;
    private boolean isLoaded = false;
    private Activity activity;
    private HashMap<String, String> hashTts;
    private SpeechRecognizerManager mSpeechManager;
    private boolean isMessage = false;

    public void init(Context context, SpeechRecognizerManager speechManager) {
        try {
            this.activity = (Activity)context;
            this.mSpeechManager = speechManager;

            mTts = new TextToSpeech(context, onInitListener);
            hashTts = new HashMap<String, String>();
            hashTts.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "id");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TextToSpeech.OnInitListener onInitListener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            if (status == TextToSpeech.SUCCESS) {
                int result = mTts.setLanguage(Locale.US);
                isLoaded = true;

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("error", "This Language is not supported");
                }
            } else {
                Log.e("error", "Initialization Failed!");
            }

            mTts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {

                }

                @Override
                public void onDone(String utteranceId) {
                    /*activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            MainActivity.startRecognition();
                        }
                    });*/

                    if (isMessage == false) {
                        activity.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                mSpeechManager.listenAgain();
                            }
                        });
                    }
                }

                @Override
                public void onError(String utteranceId) {

                }

                @Override
                public synchronized void onStop(String utteranceId, boolean interrupted) {
                    super.onStop(utteranceId, interrupted);
                }
            });
        }
    };

    public void shutDown() {
        mTts.shutdown();
    }

    public void addQueue(String text) {
        lock.lock();

        try {
            if (isLoaded)
                mTts.speak(text, TextToSpeech.QUEUE_ADD, hashTts);
            else
                Log.e("error", "TTS Not Initialized");
        } finally {
            lock.unlock();
        }
    }

    public void initQueue(String text, boolean value) {
        lock.lock();

        try {
            isMessage = value;

            if (isLoaded)
                mTts.speak(text, TextToSpeech.QUEUE_FLUSH, hashTts);
            else
                Log.e("error", "TTS Not Initialized");
        } finally {
            lock.unlock();
        }
    }
}
