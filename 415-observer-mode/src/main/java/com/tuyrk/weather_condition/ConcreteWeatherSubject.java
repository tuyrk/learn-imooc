package com.tuyrk.weather_condition;

import lombok.Getter;

import java.util.Objects;

/**
 * 天气目标的实现类
 *
 * @author tuyrk
 */
@Getter
public class ConcreteWeatherSubject extends AbstractWeatherSubject {

    /**
     * 目标对象的状态
     * 天气情况：晴天、下雨、下雪
     */
    private String weatherContent;

    @Override
    protected void notifyObservers() {
        // 遍历所有注册的观察者
        this.observers.forEach(observer -> {
            /*
            规则是：
                1. 「女朋友」需要「下雨」的条件通知，其他条件不通知
                2. 「老妈」需要「下雨」或「下雪」的条件通知，其他条件不通知
             */

            // 1. 如果天气是晴天
            // do nothing...

            // 2. 如果天气是下雨
            if (Objects.equals("下雨", this.getWeatherContent())) {
                if (Objects.equals("女朋友", observer.getObserverName())) {
                    observer.update(this);
                }
                if (Objects.equals("老妈", observer.getObserverName())) {
                    observer.update(this);
                }
            }

            // 3. 如果天气是下雪
            if (Objects.equals("下雪", this.getWeatherContent())) {
                if (Objects.equals("老妈", observer.getObserverName())) {
                    observer.update(this);
                }
            }

        });
    }

    public void setWeatherContent(String weatherContent) {
        this.weatherContent = weatherContent;
        this.notifyObservers();
    }
}
