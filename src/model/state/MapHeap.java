package model.state;

import model.value.Value;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MapHeap implements Heap {
    private final Map<Integer, Value> map = new ConcurrentHashMap<>();

    @Override
    public int createEntry(Value value) {
        var keys = map.keySet();
        int newFreeAddress = 0;
        while (true) {
            if (!keys.contains(newFreeAddress)) {
                break;
            } else {
                newFreeAddress++;
            }
        }
        map.put(newFreeAddress, value);
        return newFreeAddress;
    }

    @Override
    public Value getEntry(int address) {
        return map.get(address);
    }

    @Override
    public boolean isKey(int address) {
        return map.containsKey(address);
    }

    @Override
    public void updateEntry(int address, Value value) {
        map.put(address, value);
    }

    @Override
    public Set<Map.Entry<Integer, Value>> entrySet() {
        return map.entrySet();
    }

    @Override
    public void setContent(Map<Integer, Value> integerValueMap) {
        map.putAll(integerValueMap);
    }

    @Override
    public Iterator<Integer> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Heap:");
        for (var entry : map.entrySet()) {
            sb.append("\n\t\t").append(entry.getKey()).append(": ").append(entry.getValue().toString());
        }
        return sb.toString();
    }
}
