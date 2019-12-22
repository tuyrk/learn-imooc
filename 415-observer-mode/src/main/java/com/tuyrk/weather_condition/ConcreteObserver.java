package com.tuyrk.weather_condition;

import lombok.Data;

/**
 * 观察者的实现类
 *
 * @author tuyrk
 */
@Data
public class ConcreteObserver implements Observer {
    /**
     * 观察者的名称，是谁收到了这个消息
     */
    private String observerName;
    /**
     * 天气的内容信息，这个消息从目标处获取
     */
    private String weatherContent;
    /**
     * 提醒的内容，不同的观察者提醒不同的内容
     */
    private String remindThing;

    /**
     * 获取目标类的状态，同步到观察者的状态中
     *
     * @param weatherSubject 传入的目标对象，方便获取相应的目标对象的状态
     */
    @Override
    public void update(AbstractWeatherSubject weatherSubject) {
        this.weatherContent = ((ConcreteWeatherSubject) weatherSubject).getWeatherContent();
        System.out.println(observerName + "收到了天气信息" + weatherContent + "，准备去做" + remindThing);
    }
}
