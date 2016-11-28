package com.example.fuxiangzhang.learn_rxjava;

import android.database.Observable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Observer;


import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置观察者
        rx.Observer<String> observer=new rx.Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d("asd", "Completed!");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("asd", "Error");
            }

            @Override
            public void onNext(String s) {
                Log.d("asd", "Item: " + s);
            }
        };
        //设置被观察者
        rx.Observable<String> observable= rx.Observable.create(new rx.Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        });
        //订阅
        observable.subscribe(observer);
        observable.subscribeOn(Schedulers.io());
        observable.observeOn(AndroidSchedulers.mainThread());

        //简化写法
        rx.Observable.create(new rx.Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

            }
        })
                // 指定 Subscriber 的回调发生在主线程
                .observeOn(AndroidSchedulers.mainThread())
                // 指定 subscribe() 发生在 IO 线程
                .subscribeOn(Schedulers.io())

                .subscribe(new rx.Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        });

        rx.Observable.just("just1","just2","just3")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                               @Override
                               public void call(String s) {
                                   Log.d("asd", s);
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                            }
                        },
                        new Action0() {
                            @Override
                            public void call() {
                                Log.d("asd","completed");
                            }
                        }
                );
        String[] names={"asd","fgh","jkl"};
        rx.Observable.from(names)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d("asd", s);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                }, new Action0(){
                            @Override
                            public void call() {
                                Log.d("asd","completed");
                            }
                        });
    }
}
