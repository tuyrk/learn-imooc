package com.tuyrk.weather_jdk;

import lombok.Data;

import java.util.Observable;
import java.util.Observer;

/**
 * 使用JDK实现观察者模式，具体的观察者对象
 *
 * @author tuyrk
 */
@Data
public class ConcreteObserver implements Observer {
    /**
     * 观察者的名称，是谁收到了这个信息
     */
    private String observerName;

    @Override
    public void update(Observable o, Object arg) {
        // 推模型
        System.out.println(observerName + "收到了消息，目标对象推送过来的是：" + arg);
        // 拉模型
        ConcreteWeatherSubject weatherSubject = (ConcreteWeatherSubject) o;
        System.out.println(observerName + "收到了消息，主动到目标对象中去拉：" + weatherSubject.getContent());
    }
}
