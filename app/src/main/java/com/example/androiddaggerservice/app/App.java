package com.example.androiddaggerservice.app;


import android.app.PendingIntent;
import android.content.Intent;

import com.example.androiddaggerservice.data.di.component.DaggerAppDaggerComponent;
import com.example.androiddaggerservice.presentation.MainActivity;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import io.reactivex.Notification;

public class App extends DaggerApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        /*Notification notification = new Notification(R.drawable.icon, getText(R.string.ticker_text),
                System.currentTimeMillis());
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setLatestEventInfo(this, getText(R.string.notification_title),
                getText(R.string.notification_message), pendingIntent);
        startForeground(ONGOING_NOTIFICATION_ID, notification);*/
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppDaggerComponent.builder().create(this);
    }
}
