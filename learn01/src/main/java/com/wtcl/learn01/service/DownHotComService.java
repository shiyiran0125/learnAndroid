package com.wtcl.learn01.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author WTCL
 */
public class DownHotComService extends Service {
    public DownHotComService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}