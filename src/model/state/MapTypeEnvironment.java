package model.state;

import model.type.Type;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapTypeEnvironment implements TypeEnvironment {
    private final Map<String, Type> map = new ConcurrentHashMap<>();

    @Override
    public Type lookup(String variableName) {
        return map.get(variableName);
    }

    @Override
    public void add(String variableName, Type simpleType) {
        map.put(variableName, simpleType);
    }

    @Override
    public TypeEnvironment deepCopy() {
        var newMap = new MapTypeEnvironment();
        for (Map.Entry<String, Type> entry : map.entrySet()) {
            newMap.add(entry.getKey(), entry.getValue());
        }
        return newMap;
    }
}
