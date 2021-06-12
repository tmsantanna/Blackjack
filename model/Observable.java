/*
Bruno Messeder dos Anjos
Thiago Melcop Sant’Anna
 */

package model;

import model.Evento.Tipo;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Observable implements java.io.Serializable {

    /**
	 *
	 */
	private static final long serialVersionUID = -7405773150140148817L;

	private transient List<Observers> observers = new ArrayList<>();

	private transient List<Object> removeQueue = new ArrayList<>(); //lista de observer a serem removidos

	protected List<Evento> eventHistory = new ArrayList<>();  //histórico de eventos, em caso de load

	private boolean notifying = false;

	public void onLoad() {
		//Recria as listas após o load
		observers = new ArrayList<>();
		removeQueue = new ArrayList<>();
	}

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

	public void removeObservers() {
		removeQueue.addAll(observers);

		if (!notifying) {
			removeObserverQueue();
		}
	}

	protected void notifyObservers(Evento evento, boolean salvarEvento) {
		notifying = true;

		if (salvarEvento) {
			eventHistory.add(evento);
		}

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

	//Guarda vários observers com um objeto pai
	private static class Observers {

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

}
