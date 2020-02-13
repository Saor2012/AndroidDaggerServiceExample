package com.example.androiddaggerservice.presentation;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

public class MainPresenter implements IMainPresenter.Presenter{
    @Inject
    IMainPresenter.View view;

    @Inject
    public MainPresenter() {}

    @Override
    public void detachView() {
        if (view != null) view = null;
    }

    @Override
    public void startView(IMainPresenter.View view) {}

    @Override
    public void init() {

    }

    @Override
    public void sendImage() {
        if (image != null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            //String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            view.sendImage(byteArray);
        }else {
            view.toast("Error image null method send Presenter");
        }
    }
}
