/*
Bruno Messeder dos Anjos
Thiago Melcop Sant’Anna
 */

package model;

import model.Evento.Tipo;

import java.util.*;

class Observers {

    final Object parent;
    private final List<Observer> observers = new ArrayList<>();
    private final List<List<Tipo>> tipos = new ArrayList<>();

    Observers(Object parent, Observer observer, Tipo[] tipos) {
        this.parent = parent;
        addObserver(observer, tipos);
    }

    void addObserver(Observer observer, Tipo[] tipos) {
        observers.add(observer);
        this.tipos.add(Arrays.asList(tipos));
    }

    void update(Evento evento) {
        for (int index = 0; index < observers.size(); index++) {
            if (tipos.get(index).contains(evento.tipo)) {
                observers.get(index).update(evento);
            }
        }
    }

}

public abstract class Observable {

    private final List<Observers> observers = new ArrayList<>();

    private final List<Object> removeQueue = new ArrayList<>();

    private boolean notifying = false;

    public void addObserver(Object parent, Observer observer, Tipo... tipos) {
        for (Observers observers : observers) {
            if (observers.parent == parent) {
                observers.addObserver(observer, tipos);
                return;
            }
        }

        observers.add(new Observers(parent, observer, tipos));
    }

    public void removeObserver(Object parent) {
        removeQueue.add(parent);

        if (!notifying) {
            removeObserverQueue();
        }
    }

    protected void notifyObservers(Evento evento) {
        notifying = true;

        for (Observers observers : observers) {
            observers.update(evento);
        }

        notifying = false;

        removeObserverQueue();
    }

    private void removeObserverQueue() {
        observers.removeIf(observer -> removeQueue.contains(observer.parent));
        removeQueue.clear();
    }

}
