package observerPattern;

import java.util.HashSet;

public class Subject {
	
	HashSet<Observer> Observers = new HashSet<Observer>();
	
	public void attach(Observer obs) {
		
		Observers.add(obs);
	}
	
	public void detach(Observer obs) {
		
		Observers.remove(obs);
	}
	
	/* there is a method notify() in the Object base class in Java,
	 * so we have to use a different name. This name makes the
	 * meaning clearer anyway.
	 */
	public void notifyObservers() {
		
		for (Observer o : Observers) {
			o.update();
		}
	}

}
