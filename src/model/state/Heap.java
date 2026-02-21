package model.state;

import model.value.Value;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public interface Heap {
    String toString();
    int createEntry(Value value);
    Value getEntry(int address);
    boolean isKey(int address);
    void updateEntry(int address, Value value);
    Set<Map.Entry<Integer, Value>> entrySet();
    void setContent(Map<Integer, Value> integerValueMap);
    Iterator<Integer> iterator();
}

