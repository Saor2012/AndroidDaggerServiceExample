package com.example.androiddaggerservice.presentation.base;

public interface BasePresenter<V> {
    void detachView();
    void startView(V view);
}
