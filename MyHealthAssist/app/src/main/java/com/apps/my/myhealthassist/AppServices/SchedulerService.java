package com.apps.my.myhealthassist.AppServices;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.apps.my.myhealthassist.DataModal.Repository;
import com.apps.my.myhealthassist.DataModal.Scheduler;
import com.apps.my.myhealthassist.Utilities.Utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Manoj on 10/17/2017.
 */

public class SchedulerService extends Service {
    private static final String TAG = "SchedulerService";
    private static final int ALARM_COUNTER = 4;
    private Timer mTaskTimer;
    private Timer mSchedulerTimer;
    private List<Timer> mTimer = new ArrayList<Timer>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mTaskTimer = new Timer();

        mTaskTimer.schedule(tTimerTask, new Date(), 5000);

        return Service.START_STICKY;
    }

    TimerTask tTimerTask = new TimerTask() {
        @Override
        public void run() {
            for (Scheduler scheduler : Repository.getScheduler()) {
                if (scheduler.isScheduled() == false)
                {
                    mSchedulerTimer = new Timer();

                    try {

                        DateFormat dFormat = new SimpleDateFormat("yyyy/MM/dd");
                        DateFormat tFormat = new SimpleDateFormat("HH:mm:ss");

                        String date = dFormat.format(scheduler.getDate());
                        String time = tFormat.format(scheduler.getTime());

                        DateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
                        final Date scheduleDate = dateFormatter.parse(date + " " + time);

                        Log.i(TAG, "INFO : " + scheduleDate.toString());

                        mSchedulerTimer.schedule(new ScheduledTask(scheduler) {
                            @Override
                            public void run() {
                                super.run();

                                try {

                                    if (Utility.getListeningLatch() != null)
                                        Utility.getListeningLatch().await();

                                    Utility.setMessageLatch(1);

                                    for (int i = 0; i < ALARM_COUNTER; i++) {

                                        try {
                                            triggerAlarm(scheduler);

                                            Thread.sleep(4000);
                                        } catch (Exception e) {

                                        }
                                    }

                                    Utility.getMessageLatch().countDown();
                                } catch (Exception e) {
                                    Log.e(TAG, "ERROR : " + e.toString());
                                }
                            }
                        }, scheduleDate);

                        scheduler.setScheduled(true);

                        Repository.updateScheduler(scheduler);

                        mTimer.add(mSchedulerTimer);
                    } catch (Exception e) {
                        Log.i(TAG, "ERROR : " + e);
                    }
                }
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mTaskTimer.cancel();
        mSchedulerTimer.cancel();
        tTimerTask.cancel();

        for (Timer timer : mTimer) {
            timer.cancel();
        }
    }

    private void triggerAlarm(Scheduler scheduler){
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        vibrator.vibrate(1000);

        Utility.mVoiceManager.initQueue(scheduler.getVoiceMessage(), true);
    }
}

class ScheduledTask extends TimerTask  {
    Scheduler scheduler;

    public ScheduledTask(Scheduler value) {
        this.scheduler = value;
    }

    @Override
    public void run() {

    }
}
