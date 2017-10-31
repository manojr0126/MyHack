package com.apps.my.myhealthassist.DataModal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manoj on 10/16/2017.
 */

public class Repository {
    private static List<Scheduler> lstScheduler = new ArrayList<Scheduler>();
    private static APIResponse apiResponse = new APIResponse();
    private static APIRequest apiRequest = new APIRequest();

    public static List<Scheduler> getScheduler()
    {
        return lstScheduler;
    }

    public static void addScheduler(Scheduler value)
    {
        lstScheduler.add(value);
    }

    public static void updateScheduler(Scheduler value)
    {
        for (int i = 0; i < lstScheduler.size(); i++) {

            if (lstScheduler.get(i).getID() == value.getID()) {

                lstScheduler.get(i).setScheduled(value.isScheduled());
            }
        }
    }

    public static APIRequest APIRequest()
    {
        return apiRequest;
    }

    public static APIResponse APIResponse()
    {
        return apiResponse;
    }
}
