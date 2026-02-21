package model.state;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapBarrierTable implements BarrierTable {
    private Map<Integer, Pair<Integer, List<Integer>>> map = new ConcurrentHashMap<>();

    @Override
    public synchronized int declareVariable(int nr) {
        int firstFreeLocation = 0;
        while (true) {
            if (map.containsKey(firstFreeLocation)) {
                firstFreeLocation++;
            } else {
                map.put(firstFreeLocation, new Pair<>(nr, new ArrayList<>()));
                return firstFreeLocation;
            }
        }
    }

    @Override
    public synchronized boolean lookup(int foundIndex) {
        return map.containsKey(foundIndex);
    }

    @Override
    public synchronized Pair<Integer, List<Integer>> getPair(int foundIndex) {
        return map.get(foundIndex);
    }

    @Override
    public Iterator<Integer> iterator() {
        return map.keySet().iterator();
    }
}
