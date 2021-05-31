package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {

    private final List<Observer> observers = new ArrayList<>();

    private final List<Observer> removeQueue = new ArrayList<>();

    private boolean notifying = false;

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        removeQueue.add(o);

        if (!notifying) {
            removeObserverQueue();
        }
    }

    protected void notifyObservers(Object arg) {
        notifying = true;

        for (Observer observer : observers) {
            observer.update(this, arg);
        }

        notifying = false;

        removeObserverQueue();
    }

    private void removeObserverQueue() {
        for (Observer observer : removeQueue) {
            observers.remove(observer);
        }
        removeQueue.clear();
    }

}
