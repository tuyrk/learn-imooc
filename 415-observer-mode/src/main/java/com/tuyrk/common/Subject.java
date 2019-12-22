package com.tuyrk.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 目标类，目标对象。
 * 它知道观察它的观察者，并提供注册（添加）和删除观察者的接口
 *
 * @author tuyrk
 */
public class Subject {
    /**
     * 用来保存注册的观察者对象
     */
    private List<Observer> observers = new ArrayList<>();

    /**
     * 增加观察者
     *
     * @param observer 要注册的观察者对象
     */
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * 删除集合中指定的观察者
     *
     * @param observer 要删除的观察者对象
     */
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    /**
     * 通知所有注册的观察者对象
     */
    protected void notifyObserver() {
        observers.forEach(observer -> observer.update(this));
    }
}
