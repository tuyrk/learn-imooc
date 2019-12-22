package com.tuyrk.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 具体的目标对象。
 * 负责把有关状态存入到相应的观察者对象中
 *
 * @author tuyrk
 */
@Getter
public class ConcreteSubject extends Subject {
    /**
     * 目标对象的状态
     */
    private String subjectState;

    public void setSubjectState(String subjectState) {
        this.subjectState = subjectState;
        // 当状态发生改变时，通知观察者
        this.notifyObserver();
    }
}
