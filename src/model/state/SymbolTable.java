package model.state;

import model.type.SimpleType;
import model.type.Type;
import model.value.Value;

import java.util.Iterator;
import java.util.Map;

public interface SymbolTable<K, V> {
    // String = K, Value = V
    boolean isDefined(K variableName);
//    Type getType(K variableName);
    void update(K variableName, V value);
    Value getValue(K variableName);
    void declareVariable(K variableName, Type Type);
    Iterator<K> iterator();
    Map<K, V> getContent();
    SymbolTable<K, V> deepCopy();
}
