package blockchain;

import java.util.ArrayList;
import java.util.HashMap;

public class Observer {
    private HashMap<Miner, Event> listeners = new HashMap<>();

    public void subscribe(Miner miner,Event event) {
        listeners.put(miner, event);
    }

    public void unsubscribe(Event event, Miner miner) {
        listeners.remove(event, miner);
    }

    public void notify(Event event) {
        for (var pair : listeners.entrySet()){
            if (pair.getValue() == event){
                pair.getKey().update();
            }
        }
    }
}
