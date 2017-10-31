package com.apps.my.myhealthassist.Utilities;

import android.content.BroadcastReceiver;

import com.apps.my.myhealthassist.APIService.RestServiceManager;
import com.apps.my.myhealthassist.SpeechService.SpeechRecognizerManager;
import com.apps.my.myhealthassist.SpeechService.VoiceCommandManager;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Manoj on 10/17/2017.
 */

public final class Utility {
    public static SpeechRecognizerManager mSpeechManager;
    public static VoiceCommandManager mVoiceManager;
    public static RestServiceManager mRestServiceManager;
    public static int ListeningCounter = 0;
    public static BroadcastReceiver mReceiver;

    private static CountDownLatch _latchListening;
    private static CountDownLatch _latchMessage;
    private static boolean _isListening = false;
    private static boolean _silentMode = false;

    private Utility()
    {

    }

    public static void setSilentMode(boolean value)
    {
        _silentMode = value;

        if (value == false)
            ListeningCounter = 0;
    }

    public static boolean isSilentMode()
    {
        return _silentMode;
    }

    public static boolean isListening() {
        return _isListening;
    }

    public static void setListening(boolean value) {
        _isListening = value;
    }

    public static void setListeningLatch(int value) {
        _latchListening = new CountDownLatch(value);
    }

    public static CountDownLatch getListeningLatch() {
        return _latchListening;
    }

    public static void setMessageLatch(int value) {
        _latchMessage = new CountDownLatch(value);
    }

    public static CountDownLatch getMessageLatch() {
        return _latchMessage;
    }
}
