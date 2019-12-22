package com.tuyrk.weather;

import lombok.Data;

/**
 * 具体的观察者对象。
 * 实现更新的方法，使自身的状态和目标的状态保持一致
 *
 * @author tuyrk
 */
@Data
public class ConcreteObserver implements Observer {
    /**
     * 观察者的名称，是谁收到了这个信息
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
     * 获取目标类的状态，同步到观察者的对象中
     *
     * @param subject 传入的目标对象，方便获取相应的目标对象的状态
     */
    @Override
    public void update(WeatherSubject subject) {
        this.weatherContent = ((ConcreteWeatherSubject) subject).getWeatherContent();
        System.out.println(observerName + "收到了天气信息：" + weatherContent + "，准备去做：" + remindThing);
    }
}
