package com.example.androiddaggerservice.presentation;

import androidx.annotation.Nullable;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements IMainPresenter.View {
    @Inject
    IMainPresenter.Presenter presenter;
    private Context insertContext;
    private BroadcastReceiver broadcastReceiver;
    private final int REQUEST_CODE = 1004;
    private final String REQUEST_ACTION_CONTEXT = "REQUEST_ACTION_CONTEXT";
    private final String REQUEST_ACTION_VALUE = "REQUEST_ACTION_VALUE";
    private final String RESULT_ACTION_CONTEXT = "RESULT_ACTION_CONTEXT";
    private final String RESULT_ACTION_VALUE = "RESULT_ACTION_VALUE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        //Object[] response = intent.getExtras(REQUEST_ACTION);
        //presenter.init(res);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (broadcastReceiver == null) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                }
            };
            registerReceiver(broadcastReceiver, new IntentFilter(RESULT_ACTION_VALUE));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){
            if (resultCode == RESULT_OK){
                if (data != null && data.getStringExtra(REQUEST_ACTION_VALUE) != null && data.getByteArrayExtra(REQUEST_ACTION_CONTEXT) != null){
                    //String base64 = data.getStringExtra(RESULT_ACTION);
                    String response = data.getStringExtra(RESULT_ACTION_VALUE);
                    //String context = data.getStringExtra(RESULT_ACTION_VALUE);
                }
            }
        }
    }



    @Override
    public void sendImage(byte[] byteArray) {
        startActivityForResult(new Intent()
            .setClassName("yuri.lechshnko.com.intentexample",
                    "yuri.lechshnko.com.intentexample.MainActivity")
            .putExtra(REQUEST_ACTION_VALUE, byteArray), REQUEST_CODE);
    }

    @Override
    public void toast(String messag) {
        Toast.makeText(this,messag,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.startView(this);
        presenter.init();
    }

    @Override
    protected void onDestroy() {
        if (presenter != null){
            presenter.detachView();
            presenter = null;
        }
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        super.onDestroy();
    }
}
