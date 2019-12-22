package com.tuyrk.weather_jdk;

/**
 * 订阅天气-测试类
 *
 * @author tuyrk
 */
public class Client {
    public static void main(String[] args) {
        // 1. 创建目标
        ConcreteWeatherSubject weather = new ConcreteWeatherSubject();
        // 2. 创建观察者
        ConcreteObserver girlObserver = new ConcreteObserver();
        girlObserver.setObserverName("女朋友");

        ConcreteObserver mumObserver = new ConcreteObserver();
        mumObserver.setObserverName("老妈");
        // 3. 注册观察者
        weather.addObserver(girlObserver);
        weather.addObserver(mumObserver);
        // 4. 目标发布天气
        weather.setContent("明天天气晴朗");
    }
}