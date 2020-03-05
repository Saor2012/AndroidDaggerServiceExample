package com.example.androiddaggerservice.presentation;

import com.example.androiddaggerservice.data.ServicePos;

import javax.inject.Inject;

public class MainPresenter implements IMainPresenter.Presenter{
    @Inject
    IMainPresenter.View view;

    @Inject
    public MainPresenter() {}

    @Override
    public void detachView() {
        if (view != null) {
            view.stopNewService();
            view = null;
        }
    }

    @Override
    public void startView(IMainPresenter.View view) {}

    @Override
    public void init() {
        view.startNewService();
    }

    @Override
    public void sendQuery() {
        /*if (image != null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            //String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            view.sendQuery(byteArray);
        }else {
            view.toast("Error image null method send Presenter");
        }*/
    }

    @Override
    public void startServiceBtn() {
        if (view != null) view.startNewService();
    }

    @Override
    public void stopServiceBtn() {
        if (view != null) view.stopNewService();
    }
}
