package com.example.androiddaggerservice.presentation;

import com.example.androiddaggerservice.presentation.base.BasePresenter;

public interface IMainPresenter {
    interface View {
        void sendQuery(String coords);
        void toast(String messag);
        void startNewService();
        void stopNewService();
    }
    interface Presenter extends BasePresenter<View> {
        void init();
        void sendQuery();
        void startServiceBtn();
        void stopServiceBtn();
    }
}
