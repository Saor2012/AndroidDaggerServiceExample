package com.example.androiddaggerservice.presentation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.androiddaggerservice.R;
import com.example.androiddaggerservice.app.App;
import com.example.androiddaggerservice.data.ServicePos;
import com.example.androiddaggerservice.databinding.ActivityMainBinding;

import java.io.Serializable;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements IMainPresenter.View {
    @Inject
    IMainPresenter.Presenter presenter;
    private ActivityMainBinding binding;
    private Context insertContext;
    private BroadcastReceiver broadcastReceiver;
    private final int REQUEST_CODE = 1004;
    private final String RESULT_CODE = "REQUEST_CODE";
    private final String REQUEST_ACTION_CONTEXT = "REQUEST_ACTION_CONTEXT";
    private final String REQUEST_ACTION_VALUE = "REQUEST_ACTION_VALUE";
    private final String RESULT_ACTION_CONTEXT = "RESULT_ACTION_CONTEXT";
    private final String RESULT_ACTION_VALUE = "RESULT_ACTION_VALUE";
    private final String REQUEST_CODE_GPS = "GPS_LOCATION";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setEvent(presenter);
        Intent intent = getIntent();
        if (intent != null && Objects.equals(intent.getStringExtra(REQUEST_ACTION_VALUE), REQUEST_CODE_GPS)) {
            String response = intent.getStringExtra(RESULT_ACTION_VALUE);
            insertContext = (Context) intent.getExtras().getSerializable(REQUEST_ACTION_CONTEXT);
        }
        if (!runtimePermissions()) {
            enableBtns();
        }
        //presenter.init();
//        binding.startServiceBtn.setOnClickListener(v -> {
//            if (!isServiceRunning(ServicePos.class)){
//                Log.e("MainActivity","In startNewService");
//                startService(new Intent(this,ServicePos.class));
//            }
//        });
//        binding.stopServiceBtn.setOnClickListener(v -> {
//            if (isServiceRunning(ServicePos.class)){
//                Log.e("MainActivity","In stopNewService");
//                stopService(new Intent(this,ServicePos.class));
//            }
//        });
//        binding.restartAppBtn.setOnClickListener(v -> {
//            if (isServiceRunning(ServicePos.class)){
//
//            }
//        });

    }

    private boolean runtimePermissions() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            }, 100);
            return true;
        }
        return false;
    }

    private void enableBtns() {
        binding.startServiceBtn.setOnClickListener(v -> presenter.startServiceBtn());
        binding.stopServiceBtn.setOnClickListener(v -> presenter.stopServiceBtn());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                enableBtns();
            } else {
                runtimePermissions();
            }
        }
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (broadcastReceiver == null) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    binding.resultView.setText(intent.getStringExtra(RESULT_ACTION_VALUE));
                }
            };
            registerReceiver(broadcastReceiver, new IntentFilter("location_update"));
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                if (data != null && data.getStringExtra(REQUEST_ACTION_VALUE) != null && data.getExtras().getSerializable(REQUEST_ACTION_CONTEXT) != null){
//                    String response = data.getStringExtra(RESULT_ACTION_VALUE);
//                    Context context = (Context) data.getExtras().getSerializable(REQUEST_ACTION_CONTEXT);
//                }
//            }
//        }
//    }

    @Override
    public void sendQuery(String coords) {
        startActivityForResult(new Intent()
            .setClassName(insertContext.getPackageName(),
                    insertContext.getPackageName() + ".MainActivity")
            .putExtra(REQUEST_ACTION_VALUE, coords), REQUEST_CODE);
    }

    @Override
    public void toast(String messag) {
        Toast.makeText(this,messag,Toast.LENGTH_LONG).show();
    }

    @Override
    public void startNewService() {
        if (!isServiceRunning(ServicePos.class)){
            Log.e("MainActivity","In startNewService");
            startService(new Intent(this, ServicePos.class));
        }
    }

    @Override
    public void stopNewService() {
        if (isServiceRunning(ServicePos.class)){
            Log.e("MainActivity","In stopNewService");
            stopService(new Intent(this,ServicePos.class));
            binding.resultView.setText(binding.resultView.getText() + " - Stop gps location handing");
        }
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
