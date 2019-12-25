package com.tuyrk.weather_condition;

/**
 * 观察者接口
 * 定义一个更新的接口方法，给那些在目标对象发生改变的时候被通知的观察者对象调用
 *
 * @author tuyrk
 */
public interface Observer {
    /**
     * 更新的接口
     *
     * @param weatherSubject 传入的目标对象，方便获取相应的目标对象的状态
     */
    void update(AbstractWeatherSubject weatherSubject);

    /**
     * 获取观察者名称
     *
     * @return 观察者名称
     */
    String getObserverName();

    /**
     * 设置观察者名称
     *
     * @param observerName 观察者名称
     */
    void setObserverName(String observerName);
}
