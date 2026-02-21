package model.state;

import model.type.SimpleType;
import model.type.Type;
import model.value.Value;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapSymbolTable<K, V extends Value> implements SymbolTable<K, V> {
//    private Map<K, V> map = new HashMap<>();
    private Map<K, V> map = new ConcurrentHashMap<K, V>();

    @Override
    public boolean isDefined(K variableName) {
        return map.containsKey(variableName);
    }

//    @Override
//    public Type getType(K variableName) {
//        Value value = (Value)map.get(variableName);
//        return value.getType();
//    }

    @Override
    public void update(K variableName, V value) {
        map.put(variableName, value);
    }

    @Override
    public V getValue(K variableName) {
        return map.get(variableName);
    }

    @Override
    public void declareVariable(K variableName, Type simpleType) {
        map.put(variableName, (V) simpleType.getDefaultValue());
    }

    @Override
    public Iterator<K> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public Map<K, V> getContent() {
        return map;
    }

    public void setMap(Map<K, V> map) {
        this.map = map;
    }

    @Override
    public SymbolTable<K, V> deepCopy() {
        SymbolTable<K, V> symbolTable = new MapSymbolTable<>();
        for (Map.Entry<K, V> entry : this.map.entrySet()) {
            symbolTable.update(entry.getKey(), (V) entry.getValue().deepCopy());
        }
        return symbolTable;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Symbol Table:");
        for (var entry : map.entrySet()) {
            sb.append("\n\t\t").append(entry.getKey()).append(": ").append(entry.getValue().toString());
        }
        return sb.toString();
    }
}
