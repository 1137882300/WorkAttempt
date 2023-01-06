package com.zhong.designPatterns.observerMode;

import com.google.common.collect.Lists;
import org.springframework.core.annotation.Order;

import java.util.List;

/**
 * 具体的被观察者
 */
@Order(4)
public class SpecificObserved implements Observed {

    List<Observer> observerList = Lists.newArrayList();

    @Override
    public void addObserver(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void deleteObserver(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        observerList.forEach(x -> x.update(message));
    }
}
