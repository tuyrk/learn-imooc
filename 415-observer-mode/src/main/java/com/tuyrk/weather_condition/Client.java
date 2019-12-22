package com.tuyrk.weather_condition;

import org.junit.Test;

/**
 * 区别对待观察者-测试类
 *
 * @author tuyrk
 */
public class Client {
    @Test
    public void test1() {
        // 1. 创建目标
        ConcreteWeatherSubject weatherSubject = new ConcreteWeatherSubject();
        // 2. 创建观察者
        ConcreteObserver girlObserver = new ConcreteObserver();
        girlObserver.setObserverName("女朋友");
        girlObserver.setRemindThing("宅在家里");

        ConcreteObserver mumObserver = new ConcreteObserver();
        mumObserver.setObserverName("老妈");
        mumObserver.setRemindThing("收衣服啦");
        // 3. 注册观察者
        weatherSubject.attach(girlObserver);
        weatherSubject.attach(mumObserver);
        // 4. 目标发布天气
        weatherSubject.setWeatherContent("下雪");
        weatherSubject.setWeatherContent("下雨");
        weatherSubject.setWeatherContent("下冰雹");
    }
}
