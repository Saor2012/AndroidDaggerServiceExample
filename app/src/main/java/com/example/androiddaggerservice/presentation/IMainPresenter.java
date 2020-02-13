package com.example.androiddaggerservice.presentation;

import com.example.androiddaggerservice.presentation.base.BasePresenter;

public interface IMainPresenter {
    interface View {
        void sendImage(byte[] byteArray);
        void toast(String messag);
    }
    interface Presenter extends BasePresenter<View> {
        void init();
        void sendImage();
    }
}
