package com.tuyrk.weather;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理订阅者列表
 *
 * @author tuyrk
 */
public class WeatherSubject {
    /**
     * 订阅者列表
     */
    private List<Observer> observers = new ArrayList<>();

    /**
     * 把订阅天气的人增加到订阅者列表中
     *
     * @param observer 订阅天气的人
     */
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * 删除集合中的指定订阅天气的人
     *
     * @param observer 需要删除订阅的人
     */
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    /**
     * 通知所有已经订阅天气的人
     */
    protected void notifyObserver() {
        observers.forEach(observer -> observer.update(this));
    }
}
